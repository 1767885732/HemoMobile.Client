package com.mdsd.library.callback;

import android.support.annotation.NonNull;

import com.mdsd.library.core.bluetooth.Connection;
import com.mdsd.library.core.bluetooth.Device;

/**
 * 描述: 蓝牙连接状态回调
 */
public interface ConnectionStateChangeListener {
    
    /**
     * 连接状态变化
     * @param device 设备。device.connectionState: 连接状态<br> {@link Connection#STATE_DISCONNECTED}<br> {@link Connection#STATE_CONNECTING}<br>
     *              {@link Connection#STATE_RECONNECTING}<br> {@link Connection#STATE_CONNECTED}<br>
     *              {@link Connection#STATE_SERVICE_DISCOVERING}<br> {@link Connection#STATE_SERVICE_DISCOVERED}
     */
    void onConnectionStateChanged(@NonNull Device device);

    /**
     * 连接失败
     * @param type 失败类型。<br>{@link Connection#CONNECT_FAIL_TYPE_MAXIMUM_RECONNECTION}<br> {@link Connection#CONNECT_FAIL_TYPE_UNSPECIFIED_MAC_ADDRESS}
     */
    void onConnectFailed(@NonNull Device device, int type);
}
