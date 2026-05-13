package com.mdsd.docare.hemodialysis.app.core.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.mdsd.docare.hemodialysis.app.R;
import com.mdsd.docare.hemodialysis.app.util.UploadPhotoUtil;
import com.mdsd.library.manage.upload.UploadFileManage;
import com.mdsd.library.manage.upload.UploadFileTask;
import com.mdsd.library.net.http.HttpClient;
import com.mdsd.library.net.http.HttpParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.UUID;

/**
 * Created by steven on 2017/12/11.
 * Email-songzhonghua_1987@msn.com
 */

public class BasePhotoActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener {
    private static final String TAG = TakePhotoActivity.class.getName();
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    public static final String APP_FILE_ROOT = Environment.getExternalStorageDirectory() + File.separator + "HemoMobile";
    public String baseHemoId;
    public String baseUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setMaxSize(500000);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    @Override
    public void takeSuccess(final TResult result) {
        /*if (result == null || result.getImage() == null || result.getImage().getOriginalPath() == null) {
            return;
        }
        onUploadCompleted(result, "");
        Log.i(TAG, "takeSuccess：" + result.getImage().getCompressPath());*/
    }

    protected void showImageDialog(ViewGroup llRoot) {
        UploadPhotoUtil.showUploadImageDialog(this, llRoot, new UploadPhotoUtil.UploadEvent() {
            @Override
            public void onCapture() {
                getTakePhoto().onPickFromCapture(getImgCacheUri());
            }

            @Override
            public void onGallery() {
                getTakePhoto().onPickFromGallery();
            }
        });
    }

    protected void setMaxSize(int size) {
        CompressConfig config = new CompressConfig.Builder()
                .setMaxSize(size)
                .create();
        takePhoto.onEnableCompress(config, true);
    }

    /*public void onUploadCompleted(TResult result, String path) {
        if (result != null) {
            String filepath = result.getImage().getOriginalPath();
            String requestId = UUID.randomUUID().toString();
            File file = new File(filepath);
            HttpParams httpParams = new HttpParams();
            httpParams.put("HemoId", baseHemoId);
            try {
                httpParams.put("HemoPhoto", file);
            } catch (FileNotFoundException e) {
                Log.i(TAG,e.getMessage());
            }
            UploadFileManage.UploadFile(HttpClient.Method.POST, httpParams, requestId, baseUrl);
        }
    }*/

    @Override
    public void takeFail(TResult result, String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void takeCancel() {
        Log.i(TAG, getResources().getString(R.string.msg_operation_canceled));
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    public static Uri getImgCacheUri() {
        File file = new File(getCachePath() +
                "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        return Uri.fromFile(file);
    }

    public static String getCachePath() {
        File f = new File(APP_FILE_ROOT + "/");
        if (!f.exists()) {
            f.mkdirs();
        }
        return f.getAbsolutePath();
    }

    public static String getTAG() {
        return TAG;
    }
}
