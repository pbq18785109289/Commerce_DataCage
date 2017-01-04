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


import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.dhcc.datacage.utils.MyApp;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Packet;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class notifies the receiver of incoming notifcation packets asynchronously.
 * 接收服务器推送的消息
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class NotificationPacketListener implements PacketListener {

    private static final String LOGTAG = LogUtil
            .makeLogTag(NotificationPacketListener.class);

    private final XmppManager xmppManager;

    public NotificationPacketListener(XmppManager xmppManager) {
        this.xmppManager = xmppManager;
    }

    @Override
    public void processPacket(Packet packet) {
        Log.d(LOGTAG, "NotificationPacketListener.processPacket()...");
        Log.d(LOGTAG, "packet.toXML()=" + packet.toXML());

        if (packet instanceof NotificationIQ) {
            NotificationIQ notification = (NotificationIQ) packet;

            if (notification.getChildElementXML().contains(
                    "androidpn:iq:notification")) {
                String notificationId = notification.getId();
                String notificationApiKey = notification.getApiKey();
                String notificationTitle = notification.getTitle();
                String notificationMessage = notification.getMessage();
                //                String notificationTicker = notification.getTicker();
                String notificationUri = notification.getUri();
                String notificationImageUrl = notification.getImageUrl();

                //将推送的数据存储到数据库中保存历史消息
                NotificationHistory history=new NotificationHistory();
                history.setApiKey(notificationApiKey);
                history.setTitle(notificationTitle);
                history.setMessage(notificationMessage);
                history.setUri(notificationUri);
                history.setImageUrl(notificationImageUrl);
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
                history.setTime(sdf.format(new Date()));
                history.save();

                //保存推送消息的数量
                int result=ServiceManager.sharedPrefs.getInt(Constants.NOTIFICATION_RESULT,0);
                result+=1;
                ServiceManager serviceManager=new ServiceManager(MyApp.getContext());
                serviceManager.setNotificationResult(result);
                //发送广播通知工作台改变推送数
                Intent i1=new Intent("com.dhcc.datacage.DATA_CHANGE");
                MyApp.getContext().sendBroadcast(i1);

                Intent intent = new Intent(Constants.ACTION_SHOW_NOTIFICATION);
                intent.putExtra(Constants.NOTIFICATION_ID, notificationId);
                intent.putExtra(Constants.NOTIFICATION_API_KEY,
                        notificationApiKey);
                intent
                        .putExtra(Constants.NOTIFICATION_TITLE,
                                notificationTitle);
                intent.putExtra(Constants.NOTIFICATION_MESSAGE,
                        notificationMessage);
                intent.putExtra(Constants.NOTIFICATION_URI, notificationUri);
                intent.putExtra(Constants.NOTIFICATION_IMAGE_URL, notificationImageUrl);
                //                intent.setData(Uri.parse((new StringBuilder(
                //                        "notif://notification.androidpn.org/")).append(
                //                        notificationApiKey).append("/").append(
                //                        System.currentTimeMillis()).toString()));

                xmppManager.getContext().sendBroadcast(intent);
                //如果消息接收成功,则向服务器发送一个IQ回执
                DeliverConfirmIQ deliverConfirmIQ=new DeliverConfirmIQ();
                deliverConfirmIQ.setUuid(notificationId);
                //向服务器发送数据 set
                deliverConfirmIQ.setType(IQ.Type.SET);
                //发送
                xmppManager.getConnection().sendPacket(deliverConfirmIQ);
            }
        }

    }

}
