package com.mdsd.library.event;

import android.support.annotation.NonNull;

/**
 * 描述: 带请求ID的事件
 */
public class RequestIdEvent {
    @NonNull
    public String requestId;

    public RequestIdEvent(@NonNull String requestId) {
        this.requestId = requestId;
    }
}
