package com.testdevelop.mysdk.okhttp.response;

import android.os.Handler;
import android.os.Looper;

import com.testdevelop.mysdk.okhttp.ResponseEntityToModule;
import com.testdevelop.mysdk.okhttp.exception.OkHttpException;
import com.testdevelop.mysdk.okhttp.listener.DisposeDataHandle;
import com.testdevelop.mysdk.okhttp.listener.DisposeDataListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by zyx on 2017/4/15.
 */

public class CommonJsonCallback implements Callback {

    /**
     * 与服务器返回的字段的一个对应关系
     */
    protected final String RESULT_CODE = "ecode"; // 有返回则对于http请求来说是成功的，但还有可能是业务逻辑上的错误
    protected final int RESULT_CODE_VALUE = 0;
    protected final String ERROR_MSG = "emsg";
    protected final String EMPTY_MSG = "";
    protected final String COOKIE_STORE = "Set-Cookie"; // decide the server it
    // can has the value of
    // set-cookie2

    /**
     * 自定义异常类型
     */
    protected final int NETWORK_ERROR = -1; // the network relative error
    protected final int JSON_ERROR = -2; // the JSON relative error
    protected final int OTHER_ERROR = -3; // the unknow error

    /**
     * 将其它线程的数据转发到UI线程
     */
    private Handler mDeliveryHandler;
    private DisposeDataListener mListener;
    private Class<?> mClass;

    public CommonJsonCallback(DisposeDataHandle handle) {
        this.mDeliveryHandler = new Handler(Looper.getMainLooper());
        this.mListener = handle.mlistener;
        this.mClass = handle.mClass;
    }




    //请求失败处理
    @Override
    public void onFailure(Call call, final IOException e) {
       mDeliveryHandler.post(new Runnable() {
           @Override
           public void run() {
           mListener.onFailure(new OkHttpException(NETWORK_ERROR,e));
           }
       });
    }

    //真正的响应处理函数
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        final String result = response.body().string();
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
               handleResponse(result);
            }
        });
    }

    /**
     * 处理服务器返回的响应数据
     */
    private void handleResponse(Object responseObj){
      //为了保证代码的健壮性
        if (responseObj ==null && responseObj.toString().trim().equals("")){
            mListener.onFailure(new OkHttpException(NETWORK_ERROR,EMPTY_MSG));
            return;
        }

        try {
            JSONObject result = new JSONObject(responseObj.toString());
                    if (mClass==null){
                        mListener.onSuccess(responseObj);
                    }else {
                        //即需要我们将json对象转化为实体对象
                        Object obj = ResponseEntityToModule.parseJsonObjectToModule(result, mClass);
                        //表明了正确转为了实体对象
                        if (obj != null) {
                            mListener.onSuccess(obj);
                        } else {
                            //返回的不是一个合法的json
                            mListener.onFailure(new OkHttpException(JSON_ERROR, EMPTY_MSG));
                        }
                    }





        } catch (JSONException e) {
            mListener.onFailure(new OkHttpException(OTHER_ERROR, e.getMessage()));
            e.printStackTrace();
        }
    }
}
