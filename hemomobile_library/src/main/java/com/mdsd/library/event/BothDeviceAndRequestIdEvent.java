package com.mdsd.library.event;

import android.support.annotation.NonNull;

import com.mdsd.library.core.bluetooth.Device;

/**
 * 描述: 带请求ID和设备的事件
 */
public class BothDeviceAndRequestIdEvent<D extends Device> {
    /** 设备 */
    public @NonNull
    D device;
    @NonNull
    public String requestId;

    public BothDeviceAndRequestIdEvent(@NonNull D device, @NonNull String requestId) {
        this.device = device;
        this.requestId = requestId;
    }
}
