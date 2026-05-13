package com.mdsd.library.event;

import android.support.annotation.NonNull;

import com.mdsd.library.core.bluetooth.Device;

/**
 * 描述: 带设备的事件
 */
public class DeviceEvent<D extends Device> {
    /** 设备 */
    public @NonNull
    D device;

    public DeviceEvent(@NonNull D device) {
        this.device = device;
    }
}
