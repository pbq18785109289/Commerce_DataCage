package com.dhcc.datacage.utils;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by pengbangqin on 16-10-28.
 */
public class MyApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
    }
}
