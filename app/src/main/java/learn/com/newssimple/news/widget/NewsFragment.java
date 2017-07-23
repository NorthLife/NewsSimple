package learn.com.newssimple.news.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import learn.com.newssimple.R;
import learn.com.newssimple.main.view.BaseActivity;
import learn.com.newssimple.main.view.BaseFragment;

/**
 * Created by zyx on 2017/6/14.
 */

public class NewsFragment extends Fragment{
    public static final int NEWS_TYPE_TOP = 0;
    public static final int NEWS_TYPE_NBA = 1;
    public static final int NEWS_TYPE_CARS = 2;
    public static final int NEWS_TYPE_JOKES = 3;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<BaseFragment> fragments = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_news,null);
       mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
       mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
       mViewPager.setOffscreenPageLimit(3);
       setupViewPager(mViewPager);
       mTabLayout.addTab(mTabLayout.newTab().setText("头条"));
       mTabLayout.addTab(mTabLayout.newTab().setText("NBA"));
       mTabLayout.addTab(mTabLayout.newTab().setText("汽车"));
       mTabLayout.addTab(mTabLayout.newTab().setText("笑话"));
       mTabLayout.setupWithViewPager(mViewPager);
       mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
           @Override
           public void onTabSelected(TabLayout.Tab tab) {

           }

           @Override
           public void onTabUnselected(TabLayout.Tab tab) {

           }

           @Override
           public void onTabReselected(TabLayout.Tab tab) {
             scrollToTop(fragments.get(tab.getPosition()).getmRecyclerView());
           }
       });
        return view;
    }


    //滑动到顶部
    private void scrollToTop(RecyclerView list){
        if (list!=null){
            int lastPosition;
            LinearLayoutManager manager = (LinearLayoutManager) list.getLayoutManager();
            lastPosition = manager.findLastVisibleItemPosition();
            if (lastPosition < 50){
                list.smoothScrollToPosition(0);
            }else {
                list.scrollToPosition(0);
            }
        }
    }

    private void setupViewPager(ViewPager mViewPager){
        //Fragment中嵌套使用Fragment一定要使用getChildFragmentManager(),否则会有问题
        //一般是使用getSupportFragmentManager()
        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        fragments.add(NewListFragment.newInstance(NEWS_TYPE_TOP));
        fragments.add(NewListFragm.newInstance(NEWS_TYPE_NBA));
        fragments.add(NewListFragm.newInstance(NEWS_TYPE_CARS));
        fragments.add(NewListFragm.newInstance(NEWS_TYPE_JOKES));
        String[] titles = new String[]{"头条","NBA","汽车","笑话"};
        List<String > fragmenttitles = Arrays.asList(titles);
        adapter.setFragments(fragments,fragmenttitles);
        mViewPager.setAdapter(adapter);
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter{
      //  private final List<Fragment> mFragments = new ArrayList<>();
        private List<BaseFragment> fragments = new ArrayList<>();
        private List<String > mFragmentTitles = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }



        public void setFragments(List<BaseFragment> fragments, List<String> mFragmentTitles) {
            this.fragments = fragments;
            this.mFragmentTitles = mFragmentTitles;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
           return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
