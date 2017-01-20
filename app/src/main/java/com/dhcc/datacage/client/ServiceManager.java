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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Log;

import org.jivesoftware.smack.packet.IQ;

import java.util.List;
import java.util.Properties;

/**
 * This class is to manage the notificatin service and to load the configuration.
 *	开启一个服务管理器:
 *  (1)首先在构造函数中加载了properties文件来读取基本配置的参数(apikey,端口,ip等),再保存到sharedPreferences中
 *	(2)开启通知服务NotificationService
 *	(3)设置通知图标
 *	(4)设置
 */
public final class ServiceManager {

	private static final String LOGTAG = LogUtil
			.makeLogTag(ServiceManager.class);

	private Context context;

	public static SharedPreferences sharedPrefs;

	private Properties props;

	private String version = "0.5.0";

	private String apiKey;

	private String xmppHost;

	private String xmppPort;

	private String callbackActivityPackageName;

	private String callbackActivityClassName;

	public ServiceManager(Context context) {
		this.context = context;

		if (context instanceof Activity) {
			Log.i(LOGTAG, "Callback Activity...");
			Activity callbackActivity = (Activity) context;
			callbackActivityPackageName = callbackActivity.getPackageName();
			callbackActivityClassName = callbackActivity.getClass().getName();
		}

		//读取配置文件androidpn
		props = loadProperties();
		apiKey = props.getProperty("apiKey", "");
		xmppHost = props.getProperty("xmppHost", "127.0.0.1");
		xmppPort = props.getProperty("xmppPort", "5222");

		Log.i(LOGTAG, "apiKey=" + apiKey);
		Log.i(LOGTAG, "xmppHost=" + xmppHost);
		Log.i(LOGTAG, "xmppPort=" + xmppPort);

		sharedPrefs = context.getSharedPreferences(
				Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
		Editor editor = sharedPrefs.edit();
		editor.putString(Constants.API_KEY, apiKey);
		editor.putString(Constants.VERSION, version);
		editor.putString(Constants.XMPP_HOST, xmppHost);
		editor.putInt(Constants.XMPP_PORT, Integer.parseInt(xmppPort));
		editor.putString(Constants.CALLBACK_ACTIVITY_PACKAGE_NAME,
				callbackActivityPackageName);
		editor.putString(Constants.CALLBACK_ACTIVITY_CLASS_NAME,
				callbackActivityClassName);
		editor.commit();
		// Log.i(LOGTAG, "sharedPrefs=" + sharedPrefs.toString());
	}

	/**
	 * 开启推送服务
	 */
	public void startService() {
		Thread serviceThread = new Thread(new Runnable() {
			@Override
			public void run() {
//				Intent intent = NotificationService.getIntent();
				Intent intent = new Intent(context,NotificationService.class);
				context.startService(intent);
			}
		});
		serviceThread.start();
	}

	/**
	 * 停止推送服务
	 */
	public void stopService() {
		Intent intent = NotificationService.getIntent();
		context.stopService(intent);
	}
	/**
	 * 设置别名
	 * @param alias
	 */
	public void setAlias(final String alias) {
		final String username = sharedPrefs.getString(Constants.XMPP_USERNAME,
				"");
		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(alias)) {
			return;
		}
		new Thread(new Runnable() {
			//等待1s当NotificationService服务启动后才能拿到他的实例
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//拿到NotificationService的实例
				NotificationService notificationService = NotificationService
						.getNotificationService();
				XmppManager xmppManager = notificationService.getXmppManager();
				//等到客户端和服务器端身份验证结束之后,再将别名进行发送到服务器
				if (xmppManager != null) {
					if (!xmppManager.isAuthenticated()) {
						try {
							synchronized (xmppManager) {
								Log.d(LOGTAG,
										"setalia wait for authenticated...");
								xmppManager.wait();
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					Log.d(LOGTAG, "authenticated alreadly , send setaliasiq now");
					SetAliasIQ iq = new SetAliasIQ();
					iq.setType(IQ.Type.SET);
					iq.setUsername(username);
					iq.setAlias(alias);
					xmppManager.getConnection().sendPacket(iq);
				}
			}
		}).start();
	}
	/**
	 * 设置标签
	 * @param tagsList
	 */
	public void setTags(final List<String> tagsList) {
		final String username = sharedPrefs.getString(Constants.XMPP_USERNAME,
				"");
		if (tagsList == null || tagsList.isEmpty()
				|| TextUtils.isEmpty(username)) {
			return;
		}
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				NotificationService notificationService = NotificationService
						.getNotificationService();
				XmppManager xmppManager = notificationService.getXmppManager();
				if (xmppManager != null) {
					if (!xmppManager.isAuthenticated()) {
						try {
							synchronized (xmppManager) {
								Log.d(LOGTAG,
										"settags wait for authenticated...");
								//致使当前线程等待,wait只能由持有对像锁的线程来调用
								xmppManager.wait();
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

					}
					Log.d(LOGTAG, "authenticated alreadly , send settagsiq now");
					SetTagsIQ iq = new SetTagsIQ();
					iq.setType(IQ.Type.SET);
					iq.setUsername(username);
					iq.setTagList(tagsList);
					xmppManager.getConnection().sendPacket(iq);
				}
			}
		}).start();
	}

	/**
	 * 加载raw文件下的配置
	 * @return
	 */
	private Properties loadProperties() {
		Properties props = new Properties();
		try {
			/**
			 * 利用资源名获取其ID
			 * 第一个参数:资源的名称 androidpn
			 * 第二个参数:资源的类型(drawable,string,raw等)
			 * 第三个参数:包名
			 */
			int id = context.getResources().getIdentifier("androidpn", "raw",
					context.getPackageName());
			//获取raw文件数据 操作InputStream得到数据。
			props.load(context.getResources().openRawResource(id));
		} catch (Exception e) {
			Log.e(LOGTAG, "Could not find the properties file.", e);
			// e.printStackTrace();
		}
		return props;
	}

	/**
	 * 设置推送图标
	 * @param iconId
	 */
	public void setNotificationIcon(int iconId) {
		Editor editor = sharedPrefs.edit();
		editor.putInt(Constants.NOTIFICATION_ICON, iconId);
		editor.commit();
	}
	/**
	 * 设置已推送的数量
	 * @param result
	 */
	public void setNotificationResult(int result) {
		Editor editor = sharedPrefs.edit();
		editor.putInt(Constants.NOTIFICATION_RESULT, result);
		editor.commit();
	}

	/**
	 * 推送设置
	 * @param context
     */
	public static void viewNotificationSettings(Context context) {
		Intent intent = new Intent().setClass(context,
				NotificationSettingsActivity.class);
		context.startActivity(intent);
	}

}
