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

/**
 * Static constants for this package.
 *  一些基本的参数常量
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class Constants {
    /** 保存到SharedPreferences中的文件名*/
    public static final String SHARED_PREFERENCE_NAME = "client_preferences";

    // PREFERENCE KEYS
    /** 应用的包名*/
    public static final String CALLBACK_ACTIVITY_PACKAGE_NAME = "CALLBACK_ACTIVITY_PACKAGE_NAME";
    /** 当前的类名*/
    public static final String CALLBACK_ACTIVITY_CLASS_NAME = "CALLBACK_ACTIVITY_CLASS_NAME";
    /** API_KEY*/
    public static final String API_KEY = "API_KEY";
    /** 版本号*/
    public static final String VERSION = "VERSION";
    /** ip域名*/
    public static final String XMPP_HOST = "XMPP_HOST";
    /** 端口*/
    public static final String XMPP_PORT = "XMPP_PORT";
    /** 用户名*/
    public static final String XMPP_USERNAME = "XMPP_USERNAME";
    /** 密码*/
    public static final String XMPP_PASSWORD = "XMPP_PASSWORD";

    // public static final String USER_KEY = "USER_KEY";

    public static final String DEVICE_ID = "DEVICE_ID";

    public static final String EMULATOR_DEVICE_ID = "EMULATOR_DEVICE_ID";
    /** 推送图标*/
    public static final String NOTIFICATION_ICON = "NOTIFICATION_ICON";
    /** 已推送的数量*/
    public static final String NOTIFICATION_RESULT = "NOTIFICATION_RESULT";

    public static final String SETTINGS_AUTO_START = "SETTINGS_AUTO_START";

    public static final String SETTINGS_NOTIFICATION_ENABLED = "SETTINGS_NOTIFICATION_ENABLED";

    public static final String SETTINGS_SOUND_ENABLED = "SETTINGS_SOUND_ENABLED";

    public static final String SETTINGS_VIBRATE_ENABLED = "SETTINGS_VIBRATE_ENABLED";

    public static final String SETTINGS_TOAST_ENABLED = "SETTINGS_TOAST_ENABLED";

    // NOTIFICATION FIELDS

    public static final String NOTIFICATION_ID = "NOTIFICATION_ID";

    public static final String NOTIFICATION_API_KEY = "NOTIFICATION_API_KEY";

    public static final String NOTIFICATION_TITLE = "NOTIFICATION_TITLE";

    public static final String NOTIFICATION_MESSAGE = "NOTIFICATION_MESSAGE";

    public static final String NOTIFICATION_URI = "NOTIFICATION_URI";

    public static final String NOTIFICATION_IMAGE_URL = "NOTIFICATION_IMAGE_URL";

    // INTENT ACTIONS

    /**推送的广播接收器*/
    public static final String ACTION_SHOW_NOTIFICATION = "org.androidpn.client.SHOW_NOTIFICATION";

    public static final String ACTION_NOTIFICATION_CLICKED = "org.androidpn.client.NOTIFICATION_CLICKED";

    public static final String ACTION_NOTIFICATION_CLEARED = "org.androidpn.client.NOTIFICATION_CLEARED";

}
