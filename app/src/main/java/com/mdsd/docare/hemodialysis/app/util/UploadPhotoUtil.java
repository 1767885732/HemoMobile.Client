package com.mdsd.docare.hemodialysis.app.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import com.mdsd.docare.hemodialysis.app.R;

/**
 * Created by steven on 2017/12/7.
 * Email-songzhonghua_1987@msn.com
 */
public class UploadPhotoUtil {

    public interface UploadEvent {
        void onCapture();

        void onGallery();

    }

    public static void showUploadImageDialog(Context context, View view, final UploadEvent uploadEvent) {
        final Activity activity = ((Activity) context);
        View popupView = activity.getLayoutInflater().inflate(R.layout.popupwindow_file_upload, null);

        final PopupWindow mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(context.getResources(), (Bitmap) null));

        mPopupWindow.getContentView().setFocusableInTouchMode(true);
        mPopupWindow.getContentView().setFocusable(true);
        popupView.findViewById(R.id.take_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadEvent.onCapture();
                mPopupWindow.dismiss();
            }
        });

        popupView.findViewById(R.id.take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadEvent.onGallery();
                mPopupWindow.dismiss();
            }
        });
        popupView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow.setAnimationStyle(R.style.anim_menu_bottombar);
        mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        // 产生背景变暗效果
        final WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 0.4f;
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        activity.getWindow().setAttributes(lp);

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lp.alpha = 1f;
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                activity.getWindow().setAttributes(lp);
            }
        });
    }

}
