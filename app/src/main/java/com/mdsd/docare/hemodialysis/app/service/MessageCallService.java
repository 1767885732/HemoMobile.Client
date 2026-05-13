package com.mdsd.docare.hemodialysis.app.service;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.List;

import com.mdsd.docare.hemodialysis.app.BuildConfig;
import com.mdsd.docare.hemodialysis.app.R;
import com.mdsd.docare.hemodialysis.app.core.entity.AppContext;
import com.mdsd.docare.hemodialysis.app.receiver.MessageHandleReceiver;


/**
 * 通知service
 *
 * @author jun.w
 * @since 2016-07-05 16:11
 */
public class MessageCallService extends PollingService {

    public static final int MESSAGE_ID = 0x00;

    private NotificationManager mNotificationManager;
    private Notification mNotification;
    private PendingIntent clickPendingIntent;
    private PendingIntent deletePendingIntent;
    private Context mContext;
    private static MessageCallService mcs = null;

    public static MessageCallService getInstance() {
        if (mcs != null) {
            return mcs;
        }
        return null;
    }

    public void getContext(Context mContext){
        this.mContext=mContext;
    }

    
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mcs=this;
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent clickIntent = new Intent(this, MessageHandleReceiver.class);
        clickIntent.setAction(MessageHandleReceiver.ACTION_CLICK);
        clickPendingIntent = PendingIntent.getBroadcast(this, 0, clickIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Intent deleteIntent = new Intent(this, MessageHandleReceiver.class);
        deleteIntent.setAction(MessageHandleReceiver.ACTION_DELETE);
        deletePendingIntent = PendingIntent.getBroadcast(this, 0, deleteIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        start(MessageCallService.this);
    }

    @Override
    public void start(Context context) {
        super.start(context);
    }

    @Override
    public void stop(Context context) {
        super.stop(context);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (AppContext.getInstance().currentUser == null) {
            if (BuildConfig.DEBUG) Log.d("MessageCallService", "没有用户信息");
//            stop(MessageCallService.this);
            return;
        }

        showNotification();
//        msgAction.getAlertInfolList(new OnMessageListener<List<Message>>(mContext, "正在查询中，请稍后...") {
//
//                    @Override
//                    public void onSuccess(List<Message> result) {
//                        if(result!=null&&result.size()!=0) {
//                            showNotification();
////                            stop(MessageCallService.this);
//                        }
//                    }
//
//                    @Override
//                    public void onError(VolleyError error) {
//                        super.onError(error);
//                    }
//                });
//
//        RepositoryProvider.provideCareItemsRepository().getCallInfoList(mUserModel.deptId(),
//                new DataCallback<List<MessageEntity>>() {
//                    @Override
//                    public void onPreExecute() {
//                        if (BuildConfig.DEBUG) Log.d("MessageCallService", "消息获取中...");
//
//                    }
//
//                    @Override
//                    public void onSuccess(Result<List<MessageEntity>> messageEntities) {
//                        showNotification();
//                        stop(MessageCallService.this);
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        if (BuildConfig.DEBUG) Log.d("MessageCallService", "消息获取完成");
//                    }
//                });
    }

//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//
//        if (mUserModel == null) {
//            if (BuildConfig.DEBUG) Log.d("MessageCallService", "没有用户信息");
//            stop(MessageCallService.this);
//            return super.onStartCommand(intent, flags, startId);
//        }
//
//        RepositoryProvider.provideCareItemsRepository().getCallInfoList(mUserModel.deptId(),
//                new DataCallback<List<MessageEntity>>() {
//                    @Override
//                    public void onPreExecute() {
//                        if (BuildConfig.DEBUG) Log.d("MessageCallService", "消息获取中...");
//
//                    }
//
//                    @Override
//                    public void onSuccess(Result<List<MessageEntity>> messageEntities) {
//                        showNotification();
//                        stop(MessageCallService.this);
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        if (BuildConfig.DEBUG) Log.d("MessageCallService", "消息获取完成");
//                    }
//                });
//
//        return super.onStartCommand(intent, flags, startId);
//    }


    @Override
    protected int getPollingInterva(Context context) {
        int defTime = super.getPollingInterva(context);

        String timeStr = PreferenceManager.getDefaultSharedPreferences(context).getString(
                context.getString(R.string.pre_key_message_interva), defTime + "");

        int time = defTime;

        try {
            time = Integer.parseInt(timeStr);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return time;
    }


    /**
     * 通知有新任务
     */
    private void showNotification() {

        if (mNotification == null)
            mNotification = new NotificationCompat.Builder(getBaseContext())
                    .setTicker("您有新的呼叫消息")
                    .setContentTitle("呼叫消息")
                    .setContentText("您有新的呼叫消息")
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentIntent(clickPendingIntent)
                    .setDeleteIntent(deletePendingIntent)
                    .setAutoCancel(true)
                    .build();
        mNotification.defaults = PreferenceManager
                .getDefaultSharedPreferences(this).getBoolean(
                        getString(R.string.pre_key_message_sound), true) ?
                Notification.DEFAULT_ALL : Notification.DEFAULT_VIBRATE;
        mNotificationManager.notify(MESSAGE_ID, mNotification);

    }

}
