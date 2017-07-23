package com.testdevelop.mysdk.okhttp.listener;

/**
 * 自定义事件监听
 */

public interface DisposeDataListener {
    /**
     * 请求成功事件回调
     */
    public void onSuccess(Object responseObj);

    /**
     * 请求失败事件回调
     */
    public void onFailure(Object reasonObj);
}
