package com.dhcc.datacage.client;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;
import com.dhcc.datacage.R;

/**
 * 开机自启动打开服务
 * @author pengbangqin
 *
 */
public class BootCompletedReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context,"开机自动启动服务",Toast.LENGTH_LONG).show();
		SharedPreferences preferences = context.getSharedPreferences(
				Constants.SHARED_PREFERENCE_NAME, Context.MODE_APPEND);
		if (preferences.getBoolean(Constants.SETTINGS_AUTO_START, true)) {
			ServiceManager serviceManager = new ServiceManager(context);
			serviceManager.setNotificationIcon(R.mipmap.logo);
			serviceManager.startService();
			Log.d("BootCompletedReceiver",
					"boot compeleted,serviceManager startService");
		}
	}

}
