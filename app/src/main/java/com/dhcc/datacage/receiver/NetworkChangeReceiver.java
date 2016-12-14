package com.dhcc.datacage.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by pengbangqin on 2016/12/13.
 * 监听系统中网络改变
 */

public class NetworkChangeReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager=(ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null&&networkInfo.isAvailable()){
            Toast.makeText(context,"网络已连接", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"网络已断开", Toast.LENGTH_SHORT).show();
        }
    }
}
