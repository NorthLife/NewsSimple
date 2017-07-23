package learn.com.newssimple.news.presenter;

import android.util.Log;

import java.util.List;

import learn.com.newssimple.beans.NewsBean;
import learn.com.newssimple.common.Urls;
import learn.com.newssimple.news.model.NewsModel;
import learn.com.newssimple.news.model.NewsModelImpl;
import learn.com.newssimple.news.model.OnLoadNewsListListener;
import learn.com.newssimple.news.view.NewsView;
import learn.com.newssimple.news.widget.NewsFragment;

/**
 * Created by zyx on 2017/6/12.
 */

public class NewsPresenterImpl implements NewsPresenter, OnLoadNewsListListener {
    private static final String TAG = "NewsPresenterImpl";
    private NewsView mNewsView;
    private NewsModel mNewsModel;
    @Override
    public void loadNews(final int type, final int pageIndex) {
        String url = getUrl(type, pageIndex);
      //  LogUtils.d(TAG, url);
        Log.d(TAG,url);
        //只有第一页的或者刷新的时候才显示刷新进度条
        if(pageIndex == 0) {
            mNewsView.showProgress();
        }
        mNewsModel.loadNews(url, type, this);
    }

    /**
     * 根据类别和页面索引创建url
     * @param type
     * @param pageIndex
     * @return
     */
    private String getUrl(int type, int pageIndex) {
        StringBuffer sb = new StringBuffer();
        switch (type) {
            case NewsFragment.NEWS_TYPE_TOP:
                sb.append(Urls.TOP_URL).append(Urls.TOP_ID);
                break;
            case NewsFragment.NEWS_TYPE_NBA:
                sb.append(Urls.COMMON_URL).append(Urls.NBA_ID);
                break;
            case NewsFragment.NEWS_TYPE_CARS:
                sb.append(Urls.COMMON_URL).append(Urls.CAR_ID);
                break;
            case NewsFragment.NEWS_TYPE_JOKES:
                sb.append(Urls.COMMON_URL).append(Urls.JOKE_ID);
                break;
            default:
                sb.append(Urls.TOP_URL).append(Urls.TOP_ID);
                break;
        }
        sb.append("/").append(pageIndex).append(Urls.END_URL);
        Log.d("请求",sb.toString());
        return sb.toString();
    }


    public NewsPresenterImpl(NewsView newsView) {
        this.mNewsView = newsView;
        this.mNewsModel = new NewsModelImpl();
    }

    @Override
    public void onSuccess(List<NewsBean> list) {
        mNewsView.hideProgress();
        mNewsView.addNews(list);
    }

    @Override
    public void onFailure(String msg, Exception e) {
        mNewsView.hideProgress();
        mNewsView.showLoadFailMsg();
    }
}
