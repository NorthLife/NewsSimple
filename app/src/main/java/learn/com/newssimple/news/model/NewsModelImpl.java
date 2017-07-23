package learn.com.newssimple.news.model;

import android.util.Log;

import com.google.gson.Gson;
import com.testdevelop.mysdk.okhttp.CommonOkhttpClient;
import com.testdevelop.mysdk.okhttp.listener.DisposeDataHandle;
import com.testdevelop.mysdk.okhttp.listener.DisposeDataListener;
import com.testdevelop.mysdk.okhttp.request.CommonRequest;
import com.testdevelop.mysdk.okhttp.response.CommonJsonCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import learn.com.newssimple.beans.NewsBean;
import learn.com.newssimple.beans.NewsDetailBean;
import learn.com.newssimple.common.Urls;
import learn.com.newssimple.news.NewsJsonUtils;
import learn.com.newssimple.news.widget.NewsFragment;

/**
 * Created by zyx on 2017/6/12.
 */

public class NewsModelImpl implements NewsModel {
    /**
     *加载新闻列表
     * @param url
     * @param type
     * @param listener
     */
    @Override
    public void loadNews(String url, final int type, final OnLoadNewsListListener listener) {
        CommonOkhttpClient.sendRequest(CommonRequest.createGetRequest(url,null),new CommonJsonCallback(new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                List<NewsBean> newsBeanList = NewsJsonUtils.readJsonNewsBeans((String) responseObj, getID(type));

                listener.onSuccess(newsBeanList);
                Log.d("success","cccc");
            }

            @Override
            public void onFailure(Object reasonObj) {
                listener.onFailure("load news list failure.", (Exception) reasonObj);
            }
        })));
    }

    @Override
    public void loadNewsDetail(final String docid, final OnLoadNewsDetailListener listener) {
        String url = getDetailUrl(docid);
        CommonOkhttpClient.sendRequest(CommonRequest.createGetRequest(url,null),new CommonJsonCallback(new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                NewsDetailBean newsDetailBean = NewsJsonUtils.readJsonNewsDetailBeans((String) responseObj, docid);
                listener.onSuccess(newsDetailBean);
            }

            @Override
            public void onFailure(Object reasonObj) {
                listener.onFailure("load news detail info failure.", (Exception) reasonObj);
            }
        })));
    }
    /**
     * 获取ID
     * @param type
     * @return
     */
    private String getID(int type) {
        String id;
        switch (type) {
            case NewsFragment.NEWS_TYPE_TOP:
                id = Urls.TOP_ID;
                break;
            case NewsFragment.NEWS_TYPE_NBA:
                id = Urls.NBA_ID;
                break;
            case NewsFragment.NEWS_TYPE_CARS:
                id = Urls.CAR_ID;
                break;
            case NewsFragment.NEWS_TYPE_JOKES:
                id = Urls.JOKE_ID;
                break;
            default:
                id = Urls.TOP_ID;
                break;
        }
        return id;
    }
    private String getDetailUrl(String docId) {
        StringBuffer sb = new StringBuffer(Urls.NEW_DETAIL);
        sb.append(docId).append(Urls.END_DETAIL_URL);
        return sb.toString();
    }
}
