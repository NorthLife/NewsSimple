package learn.com.newssimple.news.presenter;

import android.content.Context;

import learn.com.newssimple.beans.NewsDetailBean;
import learn.com.newssimple.news.model.NewsModel;
import learn.com.newssimple.news.model.NewsModelImpl;
import learn.com.newssimple.news.model.OnLoadNewsDetailListener;
import learn.com.newssimple.news.view.NewsDetailView;

/**
 * Created by zyx on 2017/7/7.
 */

public class NewsDetailPresenterImpl implements NewsDetailPresenter, OnLoadNewsDetailListener {
    private Context mContent;
    private NewsDetailView mNewsDetailView;
    private NewsModel mNewsModel;

    public NewsDetailPresenterImpl(Context mContent, NewsDetailView mNewsDetailView) {
        this.mContent = mContent;
        this.mNewsDetailView = mNewsDetailView;
        mNewsModel = new NewsModelImpl();
    }

    @Override
    public void loadNewsDetail(String docId) {
        mNewsDetailView.showProgress();
        mNewsModel.loadNewsDetail(docId, this);
    }

    @Override
    public void onSuccess(NewsDetailBean newsDetailBean) {
        if (newsDetailBean != null) {
            mNewsDetailView.showNewsDetialContent(newsDetailBean.getBody());
        }
        mNewsDetailView.hideProgress();
    }

    @Override
    public void onFailure(String msg, Exception e) {
        mNewsDetailView.hideProgress();
    }
}
