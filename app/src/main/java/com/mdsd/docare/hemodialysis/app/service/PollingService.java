package com.mdsd.docare.hemodialysis.app.service;

import com.mdsd.docare.hemodialysis.app.BuildConfig;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 负责轮询的service
 *
 * @author jun.w
 * @since 2016-07-05 16:15
 */
public abstract class PollingService extends IntentService implements ServiceManage {

    private static final int POLLING_INTERVA_DEF = 30;

    public PollingService() {
        super("PollingService");
    }

    @Override
    public void start(final Context context) {
        if (BuildConfig.DEBUG) Log.d(getClass().getSimpleName(), "服务启动");

        final int intervalTime = getPollingInterva(context) * 1000;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        //包装需要执行Service的Intent
        Intent intent = new Intent(context, getClass());
        PendingIntent pendingIntent = PendingIntent.getService(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //使用AlarmManger的setRepeating方法设置定期执行的时间间隔（seconds秒）和需要执行的Service
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, intervalTime,
                intervalTime, pendingIntent);

    }

    @Override
    public void stop(Context context) {
        if (BuildConfig.DEBUG) Log.d(getClass().getSimpleName(), "服务停止");

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, getClass());
        PendingIntent pendingIntent = PendingIntent.getService(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }


    protected int getPollingInterva(Context context) {
        return POLLING_INTERVA_DEF;
    }
}
