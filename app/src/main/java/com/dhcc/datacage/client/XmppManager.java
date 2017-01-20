/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dhcc.datacage.client;
/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.util.Log;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Registration;
import org.jivesoftware.smack.provider.ProviderManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;

/**
 * This class is to manage the XMPP connection between client and server.
 * 单队列机制:(1)登录任务(2)注册任务(3)连接任务 一起被添加到队列中, 如果连接成功,调用runTask方法通知下一个任务执行.连接失败
 * ,就把队列中剩下的两个任务dropTask方法丢弃掉,调用断线重连功能.
 * 如果注册成功,也会调用runTask方法通知连接任务执行,失败的话就把队列中剩下的1个任务dropTask方法丢弃掉,调用断线重连功能.
 * 如果登录成功,也会调用runTask方法通知连接任务执行,登录服务器成功.失败调用断线重连功能.
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class XmppManager {

	private static final String LOGTAG = LogUtil.makeLogTag(XmppManager.class);

	private static final String XMPP_RESOURCE_NAME = "AndroidpnClient";

	private Context context;

	private NotificationService.TaskSubmitter taskSubmitter;

	private NotificationService.TaskTracker taskTracker;

	private SharedPreferences sharedPrefs;

	private String xmppHost;

	private int xmppPort;

	private XMPPConnection connection;

	private String username;

	private String password;

	private ConnectionListener connectionListener;

	private PacketListener notificationPacketListener;

	private Handler handler;

	private List<Runnable> taskList;

	private boolean running = false;
	/** FutureTask类同时又实现了Runnable接口，所以可以直接提交给Executor执行*/
	private Future<?> futureTask;

	private Thread reconnection;

	public XmppManager(NotificationService notificationService) {
		//服务名
		context = notificationService;
		//得到TaskSubmitter
		taskSubmitter = notificationService.getTaskSubmitter();
		//得到TaskTracker,这个跟踪器就是用来统计一种NotificationService的行为次数的，具体还没研究
		taskTracker = notificationService.getTaskTracker();
		sharedPrefs = notificationService.getSharedPreferences();

		// 拿到一些参数
		xmppHost = sharedPrefs.getString(Constants.XMPP_HOST, "localhost");
		xmppPort = sharedPrefs.getInt(Constants.XMPP_PORT, 5222);
		username = sharedPrefs.getString(Constants.XMPP_USERNAME, "");
		password = sharedPrefs.getString(Constants.XMPP_PASSWORD, "");
		//xmpp连接监视对象和NotificationPacketListener返回包监视对象。
		connectionListener = new PersistentConnectionListener(this);
		//用于处理推送消息的监听
		notificationPacketListener = new NotificationPacketListener(this);

		handler = new Handler();
		//创建一个任务队列
		taskList = new ArrayList<Runnable>();
		//启动断线重连机制
		reconnection = new ReconnectionThread(this);
	}

	public Context getContext() {
		return context;
	}

	/**
	 * 连接服务器
	 */
	public void connect() {
		Log.d(LOGTAG, "connect()...");
		submitLoginTask();
	}

	public void disconnect() {
		Log.d(LOGTAG, "disconnect()...");
		terminatePersistentConnection();
	}

	public void terminatePersistentConnection() {
		Log.d(LOGTAG, "terminatePersistentConnection()...");
		Runnable runnable = new Runnable() {

			final XmppManager xmppManager = XmppManager.this;

			public void run() {
				if (xmppManager.isConnected()) {
					Log.d(LOGTAG, "terminatePersistentConnection()... run()");
					xmppManager.getConnection().removePacketListener(
							xmppManager.getNotificationPacketListener());
					xmppManager.getConnection().disconnect();
				}
				xmppManager.runTask();
			}

		};
		addTask(runnable);
	}

	public XMPPConnection getConnection() {
		return connection;
	}

	public void setConnection(XMPPConnection connection) {
		this.connection = connection;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ConnectionListener getConnectionListener() {
		return connectionListener;
	}

	public PacketListener getNotificationPacketListener() {
		return notificationPacketListener;
	}
	/**
	 * 启动断线重连
	 */
	public void startReconnectionThread() {
		synchronized (reconnection) {
			if (reconnection == null || !reconnection.isAlive()) {
				reconnection = new ReconnectionThread(this);
				reconnection.setName("Xmpp Reconnection Thread");
				reconnection.start();
			}
		}
	}

	public Handler getHandler() {
		return handler;
	}

	/**
	 * 注册账户
	 */
	public void reregisterAccount() {
		removeAccount();
		submitLoginTask();
		runTask();
	}

	public List<Runnable> getTaskList() {
		return taskList;
	}

	public Future<?> getFutureTask() {
		return futureTask;
	}
	/**
	 * 执行队列任务
	 */
	public void runTask() {
		Log.d(LOGTAG, "runTask()...");
		//同步代码块
		synchronized (taskList) {
			running = false;
			futureTask = null;
			if (!taskList.isEmpty()) {
				Runnable runnable = (Runnable) taskList.get(0);
				taskList.remove(0);
				running = true;
				futureTask = taskSubmitter.submit(runnable);
				if (futureTask == null) {
					taskTracker.decrease();
				}
			}
		}
		taskTracker.decrease();
		Log.d(LOGTAG, "runTask()...done");
	}

	private String newRandomUUID() {
		String uuidRaw = UUID.randomUUID().toString();
		return uuidRaw.replaceAll("-", "");
	}

	private boolean isConnected() {
		return connection != null && connection.isConnected();
	}

	public boolean isAuthenticated() {
		return connection != null && connection.isConnected()
				&& connection.isAuthenticated();
	}

	private boolean isRegistered() {
		return sharedPrefs.contains(Constants.XMPP_USERNAME)
				&& sharedPrefs.contains(Constants.XMPP_PASSWORD);
	}

	/**
	 * 提交连接任务
	 */
	private void submitConnectTask() {
		Log.d(LOGTAG, "submitConnectTask()...");
		addTask(new ConnectTask());
	}

	/**
	 * 提交注册任务
	 */
	private void submitRegisterTask() {
		Log.d(LOGTAG, "submitRegisterTask()...");
		submitConnectTask();
		addTask(new RegisterTask());
	}

	/**
	 * 提交登录任务
	 */
	private void submitLoginTask() {
		Log.d(LOGTAG, "submitLoginTask()...");
		submitRegisterTask();
		addTask(new LoginTask());
	}
	/**
	 * 将任务添加到队列中
	 * @param runnable
	 */
	private void addTask(Runnable runnable) {
		Log.d(LOGTAG, "addTask(runnable)...");
		taskTracker.increase();
		synchronized (taskList) {
			if (taskList.isEmpty() && !running) {
				running = true;
				futureTask = taskSubmitter.submit(runnable);
				if (futureTask == null) {
					taskTracker.decrease();
				}
			} else {
				taskList.add(runnable);
			}
		}
		Log.d(LOGTAG, "addTask(runnable)... done");
	}

	/**
	 * 移除该账户
	 */
	private void removeAccount() {
		Editor editor = sharedPrefs.edit();
		editor.remove(Constants.XMPP_USERNAME);
		editor.remove(Constants.XMPP_PASSWORD);
		editor.commit();
	}
	/**
	 * 将任务丢弃掉
	 * @param dropCount
	 */
	private void dropTask(int dropCount) {
		synchronized (taskList) {
			if (taskList.size() >= dropCount) {
				for (int i = 0; i < dropCount; i++) {
					taskList.remove(0);
					taskTracker.decrease();
				}
			}

		}
	}

	/**
	 * 使用ASmack连接服务器
	 * 用于连接服务器，连接成功后，org.jivesoftware.smack.XMPPConnection对象的isConnected方法返回true。
	 * A runnable task to connect the server.
	 */
	private class ConnectTask implements Runnable {

		final XmppManager xmppManager;

		private ConnectTask() {
			this.xmppManager = XmppManager.this;
		}

		public void run() {
			Log.i(LOGTAG, "ConnectTask.run()...");

			if (!xmppManager.isConnected()) {
				//配置文件 参数（服务地地址，端口号，域）
				// Create the configuration for this new connection
				ConnectionConfiguration connConfig = new ConnectionConfiguration(
						xmppHost, xmppPort);
				// connConfig.setSecurityMode(SecurityMode.disabled);
				//设置安全类型
				connConfig.setSecurityMode(SecurityMode.required);
				//设置不需要SAS验证
				connConfig.setSASLAuthenticationEnabled(false);
				connConfig.setCompressionEnabled(false);
				//建立连接 用连接配置对象创建org.jivesoftware.smack.XMPPConnection对象
				XMPPConnection connection = new XMPPConnection(connConfig);
				xmppManager.setConnection(connection);

				try {
					// Connect to the server 连接服务器
					connection.connect();
					Log.i(LOGTAG, "XMPP connected successfully");

					// packet provider
					ProviderManager.getInstance().addIQProvider("notification",
							"androidpn:iq:notification",
							new NotificationIQProvider());
					//通知执行下一个任务 (注册任务)
					xmppManager.runTask();
				} catch (XMPPException e) {
					Log.e(LOGTAG, "XMPP connection failed", e);
				}
				xmppManager.dropTask(2);
				xmppManager.runTask();
				//启动断线重连
				xmppManager.startReconnectionThread();

			} else {
				Log.i(LOGTAG, "XMPP connected already");
				xmppManager.runTask();
			}
		}
	}

	/**
	 * 使用ASmack中的注册模块
	 * 用于发送包含用户名和密码的注册包给服务器，在服务器注册一个用户
	 * A runnable task to register a new user onto the server.
	 */
	private class RegisterTask implements Runnable {

		final XmppManager xmppManager;

		boolean isRegisterSucceed;

		boolean hasDropTask;

		private RegisterTask() {
			xmppManager = XmppManager.this;
		}

		public void run() {
			Log.i(LOGTAG, "RegisterTask.run()...");

			if (!xmppManager.isRegistered()) {
				isRegisterSucceed = false;
				hasDropTask = false;
				//随机生成用户名密码
				final String newUsername = newRandomUUID();
				final String newPassword = newRandomUUID();
				//通过Registration类实现注册
				Registration registration = new Registration();
				//创建包过滤器  创建一个PacketFilter对象
				PacketFilter packetFilter = new AndFilter(new PacketIDFilter(
						registration.getPacketID()), new PacketTypeFilter(
						IQ.class));

				//创建一个PacketListener返回包监听对象
				PacketListener packetListener = new PacketListener() {

					@SuppressLint("LongLogTag")
					public void processPacket(Packet packet) {
						synchronized (xmppManager) {
							Log.d("RegisterTask.PacketListener",
									"processPacket().....");
							Log.d("RegisterTask.PacketListener", "packet="
									+ packet.toXML());

							if (packet instanceof IQ) {
								//获取返回信息
								IQ response = (IQ) packet;
								//注册失败
								if (response.getType() == IQ.Type.ERROR) {
									if (!response.getError().toString()
											.contains("409")) {
										Log.e(LOGTAG,
												"Unknown error while registering XMPP account! "
														+ response.getError()
														.getCondition());
									}
									//注册成功
								} else if (response.getType() == IQ.Type.RESULT) {
									xmppManager.setUsername(newUsername);
									xmppManager.setPassword(newPassword);
									Log.d(LOGTAG, "username=" + newUsername);
									Log.d(LOGTAG, "password=" + newPassword);

									Editor editor = sharedPrefs.edit();
									editor.putString(Constants.XMPP_USERNAME,
											newUsername);
									editor.putString(Constants.XMPP_PASSWORD,
											newPassword);
									editor.commit();
									isRegisterSucceed = true;
									Log.i(LOGTAG,
											"Account registered successfully");
									if (!hasDropTask) {
										xmppManager.runTask();
									}

								}
							}
						}
					}
				};
				//添加对注册的监听
				connection.addPacketListener(packetListener, packetFilter);
				//设置类型
				registration.setType(IQ.Type.SET);
				// registration.setTo(xmppHost);
				// Map<String, String> attributes = new HashMap<String,
				// String>();
				// attributes.put("username", rUsername);
				// attributes.put("password", rPassword);
				// registration.setAttributes(attributes);
				//设置用户名
				registration.addAttribute("username", newUsername);
				//设置密码
				registration.addAttribute("password", newPassword);
				//发送注册信息到服务器  发送包
				connection.sendPacket(registration);
				try {
					Thread.sleep(10 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (xmppManager) {
					if (!isRegisterSucceed) {
						xmppManager.dropTask(1);
						xmppManager.runTask();
						//启动断线重连
						xmppManager.startReconnectionThread();
						hasDropTask = true;
					}
				}

			} else {
				Log.i(LOGTAG, "Account registered already");
				xmppManager.runTask();
			}
		}
	}

	/**
	 * 使用ASmack的登录模块
	 * 用于登陆服务器，获得服务器授权，登陆服务器成功后，
	 * org.jivesoftware.smack.XMPPConnection对象的isAuthenticated方法返回true。
	 * A runnable task to log into the server.
	 */
	private class LoginTask implements Runnable {

		final XmppManager xmppManager;

		private LoginTask() {
			this.xmppManager = XmppManager.this;
		}

		public void run() {
			Log.i(LOGTAG, "LoginTask.run()...");

			if (!xmppManager.isAuthenticated()) {
				Log.d(LOGTAG, "username=" + username);
				Log.d(LOGTAG, "password=" + password);

				try {
					//登录 调用org.jivesoftware.smack.XMPPConnection对象的login函数登陆服务器
					xmppManager.getConnection().login(
							xmppManager.getUsername(),
							xmppManager.getPassword(), XMPP_RESOURCE_NAME);
					Log.d(LOGTAG, "Loggedn in successfully");

					// connection listener
					if (xmppManager.getConnectionListener() != null) {
						xmppManager.getConnection().addConnectionListener(
								xmppManager.getConnectionListener());
					}

					// packet filter
					//将xmpp连接监听对象设置到org.jivesoftware.smack.XMPPConnection对象
					PacketFilter packetFilter = new PacketTypeFilter(
							NotificationIQ.class);
					// packet listener
					//将返回包监听对象设置到org.jivesoftware.smack.XMPPConnection对象
					PacketListener packetListener = xmppManager
							.getNotificationPacketListener();
					connection.addPacketListener(packetListener, packetFilter);
					//开启想服务器端发送心跳包
					connection.startHeartBeat();
					//通知ServiceManager中setAlias代码中 等待别名的时候  (可以不用等待了,身份验证成功)
					synchronized (xmppManager) {
						//只能由持有对像锁的线程来调用,唤配所有在此对象锁上等待的线程
						xmppManager.notifyAll();
					}
				} catch (XMPPException e) {
					Log.e(LOGTAG, "LoginTask.run()... xmpp error");
					Log.e(LOGTAG, "Failed to login to xmpp server. Caused by: "
							+ e.getMessage());
					String INVALID_CREDENTIALS_ERROR_CODE = "401";
					String errorMessage = e.getMessage();
					if (errorMessage != null
							&& errorMessage
							.contains(INVALID_CREDENTIALS_ERROR_CODE)) {
						xmppManager.reregisterAccount();
						return;
					}
					//启动断线重连
					xmppManager.startReconnectionThread();

				} catch (Exception e) {
					Log.e(LOGTAG, "LoginTask.run()... other error");
					Log.e(LOGTAG, "Failed to login to xmpp server. Caused by: "
							+ e.getMessage());
					//启动断线重连
					xmppManager.startReconnectionThread();
				} finally {
					xmppManager.runTask();
				}

			} else {
				Log.i(LOGTAG, "Logged in already");
				xmppManager.runTask();
			}

		}
	}

}
