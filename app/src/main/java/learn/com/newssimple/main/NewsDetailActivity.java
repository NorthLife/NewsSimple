package learn.com.newssimple.main;

import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import learn.com.newssimple.NewsAdapter;
import learn.com.newssimple.R;
import learn.com.newssimple.Utils.ImageLoaderUtils;
import learn.com.newssimple.beans.NewsBean;
import learn.com.newssimple.news.presenter.NewsDetailPresenter;
import learn.com.newssimple.news.presenter.NewsDetailPresenterImpl;
import learn.com.newssimple.news.view.NewsDetailView;

public class NewsDetailActivity extends AppCompatActivity implements NewsDetailView {
    private NewsBean news ;
    private NewsDetailPresenter mNewsDetailPresenter;
    private ProgressBar mProgressBar;
    private TextView tv;
    private HtmlTextView mTVNewsContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        ImageView img = (ImageView) findViewById(R.id.img);
        mTVNewsContent = (HtmlTextView) findViewById(R.id.htNewsContent);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        news = (NewsBean) getIntent().getSerializableExtra("news");
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
       // collapsingToolbar.setTitle(news.getTitle());
        ImageLoaderUtils.display(getApplicationContext(), img, news.getImgsrc());
        mNewsDetailPresenter = new NewsDetailPresenterImpl(getApplication(), this);
        mNewsDetailPresenter.loadNewsDetail(news.getDocid());
    }


    @Override
    public void showNewsDetialContent(String newsDetailContent) {
        mTVNewsContent.setHtmlFromString(newsDetailContent, new HtmlTextView.LocalImageGetter());
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }
}
