package learn.com.newssimple.news.view;

import java.util.List;

import learn.com.newssimple.beans.NewsBean;

/**
 * Created by zyx on 2017/6/12.
 */

public interface NewsView {

    void showProgress();

    void addNews(List<NewsBean> newsList);

    void hideProgress();

    void showLoadFailMsg();
}
