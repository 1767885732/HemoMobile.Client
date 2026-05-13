package com.mdsd.library.callback;

import android.support.annotation.NonNull;

import com.mdsd.library.core.bluetooth.Device;

/**
 * 描述: 蓝牙扫描回调
 */
public interface ScanListener {
    /**
     * 扫描开始
     */
    void onScanStart();

    /**
     * 扫描结束
     */
    void onScanStop();

    /**
     * 扫描结果
     * @param device 设备
     */
    void onScanResult(@NonNull Device device);
}
