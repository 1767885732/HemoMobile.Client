package com.mdsd.docare.hemodialysis.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mdsd.docare.hemodialysis.app.core.entity.AppContext;
import com.mdsd.docare.hemodialysis.app.service.MessageCallService;
import com.mdsd.docare.hemodialysis.app.ui.main.LoginActivity;
import com.mdsd.docare.hemodialysis.app.ui.patient.AlertDialog;

/**
 * @author Jianyu.L (<a href="mailto:lijianyu2012@gmail.com">lijianyu2012@gmail.com</a>)
 * @since 2016-03-18 16:30
 */
public class MessageHandleReceiver extends BroadcastReceiver {

    public static final String ACTION_DELETE = "com.mdsd.mobile.action.message.delete";
    public static final String ACTION_CLICK = "com.mdsd.mobile.action.message.click";
    private MessageCallService mcs;

    @Override
    public void onReceive(Context context, Intent intent) {
        mcs=MessageCallService.getInstance();
        mcs.getContext(context);
        if (ACTION_CLICK.equals(intent.getAction())) {
            // 判断是否登录，登录状态下直接到消息列表，非登录状态到登录页面
            Intent clickIntent = new Intent();
            clickIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (AppContext.getInstance().currentUser == null) {
                clickIntent.setClass(context, LoginActivity.class);
            } else {
                clickIntent.setClass(context, AlertDialog.class);
            }

            context.startActivity(clickIntent);
        } else if (ACTION_DELETE.equals(intent.getAction())) {
            if (AppContext.getInstance().currentUser != null)
                mcs.start(context);
        }


    }
}
