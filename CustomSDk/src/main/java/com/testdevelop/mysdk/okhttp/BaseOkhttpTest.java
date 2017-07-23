package com.testdevelop.mysdk.okhttp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.testdevelop.mysdk.okhttp.listener.DisposeDataHandle;
import com.testdevelop.mysdk.okhttp.listener.DisposeDataListener;
import com.testdevelop.mysdk.okhttp.request.CommonRequest;
import com.testdevelop.mysdk.okhttp.response.CommonJsonCallback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zyx on 2017/4/14.
 */

public class BaseOkhttpTest extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OkHttpClient client = new OkHttpClient();
    }
    /**
     * 用OkHttp发送一个最基本的需求
     */
    private void sendRequest(){

        //创建okhttpClient对象
        OkHttpClient mOkhttpClient = new OkHttpClient();
        //创建一个Request
        final Request request = new Request.Builder()
                .url("https://www.imooc.com/")
                .build();
        //new call
        Call call = mOkhttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }
    private void test(){
        CommonOkhttpClient.sendRequest(CommonRequest.createGetRequest("http://www.imooc.com",null),new CommonJsonCallback(new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
            }

            @Override
            public void onFailure(Object reasonObj) {
            }
        })));

    }
}
