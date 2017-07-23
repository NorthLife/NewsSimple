package com.testdevelop.mysdk.okhttp;

import com.testdevelop.mysdk.okhttp.https.HttpsUtils;
import com.testdevelop.mysdk.okhttp.response.CommonJsonCallback;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by zyx on 2017/4/14.
 * @function 请求的发送，请求参数的配置，https支持。
 */

public class CommonOkhttpClient {
    private static final int TIME_OUT = 30;//超时参数
    private static OkHttpClient mOkHttpClient;

    //为我们的client去配置参数
    static {

        //创建我们client对象的构建者
        OkHttpClient.Builder okHttpBuilder =new OkHttpClient.Builder();

        okHttpBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpBuilder.followRedirects(true); //允许重定向

        //https支持
//        okHttpBuilder.hostnameVerifier(new HostnameVerifier() {
//            @Override
//            public boolean verify(String hostname, SSLSession session) {
//                return true;
//            }
//        });
//        okHttpBuilder.sslSocketFactory(HttpsUtils.getSslSocketFactory());
//        //生成我们的client对象
        mOkHttpClient = okHttpBuilder.build();
    }

    /**
     * 发送具体的http/https请求
     * @param request
     * @param commCallback
     * @return call
     */
    public static Call sendRequest(Request request, CommonJsonCallback commCallback){
         Call call = mOkHttpClient.newCall(request);
        call.enqueue(commCallback);
        return call;
    }

    public static void cancelTag(Object tag)
    {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls())
        {
            if (tag.equals(call.request().tag()))
            {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls())
        {
            if (tag.equals(call.request().tag()))
            {
                call.cancel();
            }
        }
    }
}
