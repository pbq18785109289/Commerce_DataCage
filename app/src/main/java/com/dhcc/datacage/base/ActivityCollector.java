package com.dhcc.datacage.base;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pengbangqin on 2016/12/13.
 * 活动管理器
 */

public class ActivityCollector {
    public static List<Activity> activities=new ArrayList<>();

    /**
     * 向list中添加一个活动
     * @param activity
     */
    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    /**
     * 从list中移除一个活动
     * @param activity
     */
    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    /**
     * 将list存储的活动全部销毁掉
     */
    public static void finishAll(){
        for (Activity activity:activities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
