package com.testdevelop.mysdk.okhttp.listener;

/**
 * Created by zyx on 2017/4/15.
 */

public class DisposeDataHandle {
    public DisposeDataListener mlistener = null;
    public Class<?> mClass = null;

    public DisposeDataHandle(DisposeDataListener listener){
        this.mlistener = listener;
    }

    public DisposeDataHandle(Class<?> clazz,DisposeDataListener listener) {
        this.mClass = clazz;
        this.mlistener = listener;
    }
}
