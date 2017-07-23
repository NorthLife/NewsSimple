package learn.com.newssimple.news.widget;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.convenientbanner.ConvenientBanner;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import learn.com.newssimple.NewsAdapter;
import learn.com.newssimple.R;
import learn.com.newssimple.beans.NewsBean;
import learn.com.newssimple.beans.NewsBeanRealm;
import learn.com.newssimple.common.Urls;
import learn.com.newssimple.main.NewsDetailActivity;
import learn.com.newssimple.main.view.BaseActivity;
import learn.com.newssimple.main.view.BaseFragment;
import learn.com.newssimple.news.presenter.NewsPresenter;
import learn.com.newssimple.news.presenter.NewsPresenterImpl;
import learn.com.newssimple.news.view.NewsView;

/**
 * Created by zyx on 2017/6/12.
 */

public class  NewListFragment extends BaseFragment implements NewsView, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "NewsListFragment";

    private SwipeRefreshLayout mSwipeRefreshWidget;
    private LinearLayoutManager mLayoutManager;
    private NewsAdapter mAdapter;
    private List<NewsBean> mData;
    private NewsPresenter mNewsPresenter;
    private int mType = NewsFragment.NEWS_TYPE_TOP;
    private int pageIndex = 0;
    private ConvenientBanner banner;
    private BaseActivity mActivity;

    public static NewListFragment newInstance(int type){
        Bundle args = new Bundle();
        NewListFragment fragment = new NewListFragment();
        args.putInt("type",type);
        fragment.setArguments(args);
        return fragment;
    }




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsPresenter = new NewsPresenterImpl(this);
        mType = getArguments().getInt("type");
    }

    @Override
    public void showProgress() {
        mSwipeRefreshWidget.setRefreshing(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     View view = inflater.inflate(R.layout.fragment_newlist,null);
     mSwipeRefreshWidget = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
     mSwipeRefreshWidget.setColorSchemeResources(R.color.primary,
                R.color.primary_dark, R.color.primary_light,
                R.color.accent);
     mSwipeRefreshWidget.setOnRefreshListener(this);
     recyclerView = (RecyclerView)view.findViewById(R.id.recycle_view);
     recyclerView.setHasFixedSize(true);
      mActivity = (BaseActivity) getActivity();
     mLayoutManager = new LinearLayoutManager(mActivity);
     recyclerView.setItemAnimator(new DefaultItemAnimator());
      recyclerView.setLayoutManager(mLayoutManager);
     mAdapter = new NewsAdapter(mActivity.getApplicationContext());
     mAdapter.setOnItemClickListener(mOnItemClickListener);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnScrollListener(mOnScrollListener);

        onRefresh();
        return view;
    }

    private void initBanner() {
      if (banner == null){
          if (recyclerView.getChildCount()!=0 && mLayoutManager.findFirstVisibleItemPosition() == 0){
              banner = (ConvenientBanner) mLayoutManager.findViewByPosition(0);
              banner.setScrollDuration(1000);
              banner.startTurning(5000);
          }
      }
    }


    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        private int lastVisibleItem;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            Log.d("DataCount", String.valueOf(mAdapter.getItemCount()));
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == mAdapter.getItemCount()
                    && mAdapter.isShowFooter()) {
                //加载更多
                mNewsPresenter.loadNews(mType, pageIndex + Urls.PAZE_SIZE);
            }
        }
    };


    @Override
    public void addNews(List<NewsBean> newsList) {
        mAdapter.isShowFooter(true);
        if(mData == null) {
            mData = new ArrayList<NewsBean>();
            Realm realm = Realm.getDefaultInstance();
            final RealmResults<NewsBeanRealm> BannerData = realm.where(NewsBeanRealm.class).findAll();
           realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    BannerData.deleteAllFromRealm();
                }
            });
                for (int i = 0; i < 4; i++) {
                    final NewsBeanRealm Bannerdata = new NewsBeanRealm();
                    Bannerdata.setImgsrc(newsList.get(i).getImgsrc());

                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.copyToRealm(Bannerdata);
                        }
                    });

                mAdapter.setBannerData();
            }
        }
        mData.addAll(newsList);
        if(pageIndex == 0) {
            int i = 0;
            i++;
            Log.d("加载数据", String.valueOf(i));
            mAdapter.setmData(mData);

        } else {
            //如果没有更多数据了,则隐藏footer布局
            if(newsList == null || newsList.size() == 0) {
                mAdapter.isShowFooter(false);
            }
            mAdapter.notifyDataSetChanged();
        }
        pageIndex += Urls.PAZE_SIZE;
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshWidget.setRefreshing(false);
    }

    @Override
    public void showLoadFailMsg() {
        if(pageIndex == 0) {
            mAdapter.isShowFooter(false);
            mAdapter.notifyDataSetChanged();
        }
        View view = getActivity() == null ? recyclerView.getRootView() : getActivity().findViewById(R.id.drawer_layout);
        Snackbar.make(view, "加载数据失败", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        pageIndex = 0;
        if(mData != null) {
            mData.clear();
        }


        mNewsPresenter.loadNews(mType, pageIndex);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //banner.stopTurning();
    }

    private NewsAdapter.OnItemClickListener mOnItemClickListener = new NewsAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            if (mData.size() <= 0) {
                return;
            }
            NewsBean news = mAdapter.getItem(position-1);
            Intent intent = new Intent(getActivity(),NewsDetailActivity.class);
            //设置详情内容
            intent.putExtra("news", news);
            View transitionView = view.findViewById(R.id.ivNews);
            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                            transitionView, getString(R.string.transition_news_img));
            ActivityCompat.startActivity(getActivity(),intent,options.toBundle());
        }
    };

}
