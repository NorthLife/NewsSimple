package learn.com.newssimple.news.model;

/**
 * Created by zyx on 2017/6/12.
 */

public interface NewsModel {

    void loadNews(String url, int type, OnLoadNewsListListener listener);

    void loadNewsDetail(String docid, OnLoadNewsDetailListener listener);
}
