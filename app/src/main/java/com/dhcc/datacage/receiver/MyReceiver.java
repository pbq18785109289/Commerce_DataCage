package com.dhcc.datacage.receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.dhcc.datacage.activity.workbench.InfoNotification_Activity;
import com.dhcc.datacage.model.InfoNotify;
import java.text.SimpleDateFormat;
import java.util.Date;
import cn.jpush.android.api.JPushInterface;



/**
 * Created by pengbangqin on 2016/12/5.
 * 广播接收器:用于接收服务器发送的消息通知
 */

public class MyReceiver extends BroadcastReceiver{
    private NotificationManager nm;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(nm==null){
            nm= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Bundle bundle=intent.getExtras();
        if(JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())){
            Log.i("zll","成功接收到推送的通知消息");
            receivingNotification(context,bundle);
        }else if(JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())){
            Log.i("zll","点击了打开通知"+bundle);
            openNotification(context,bundle);
        }
    }

    /**
     * 接收通知的逻辑处理
     * @param context
     * @param bundle
     */
    private void receivingNotification(Context context, Bundle bundle) {
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        Log.d("zll", " title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        Log.d("zll", "message : " + message);
        //设置日期格式
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=format.format(new Date());
        //将收到的消息装入实体类InfoNotify中
        InfoNotify notify=new InfoNotify();
        notify.setTitle(title);
        notify.setMessage(message);
        notify.setTime(time);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Log.d("zll", "extras : " + extras);
    }

    /**
     * 打开通知逻辑处理
     * @param context
     * @param bundle
     */
    private void openNotification(Context context, Bundle bundle){
        Intent mIntent = new Intent(context, InfoNotification_Activity.class);
        mIntent.putExtras(bundle);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mIntent);
    }
}
