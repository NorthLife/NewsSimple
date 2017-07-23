package learn.com.newssimple;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import learn.com.newssimple.Utils.ImageLoaderUtils;
import learn.com.newssimple.beans.NewsBean;
import learn.com.newssimple.beans.NewsBeanRealm;
import learn.com.newssimple.main.view.BaseActivity;
import learn.com.newssimple.news.widget.BannerView;

/**
 * Created by zyx on 2017/6/11.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RealmChangeListener<Realm> {
    private List<NewsBean> mData;
    private boolean mShowFooter = true;
    private Context mContext;
    public ConvenientBanner<NewsBeanRealm> banner;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_BANNER = 1;
    private static final int TYPE_FOOTER = 2;
    private OnItemClickListener mOnItemClickListener;
    private List<NewsBeanRealm> BannerData = new ArrayList<>();
    private Realm realm;
    public NewsAdapter(Context mContext) {
        this.mContext = mContext;
        realm = Realm.getDefaultInstance();
        realm.addChangeListener(this);
    }

    public void setBannerData() {
        //BannerData = activity.mRealm.where(NewsBeanRealm.class).findAll();

        BannerData = realm.where(NewsBeanRealm.class).findAll();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_news, parent, false);
            return new ItemViewHolder(v);
        } else if (viewType == TYPE_BANNER){
           View v = LayoutInflater.from(parent.getContext())
                   .inflate(R.layout.banner_news,parent,false);
            return new BannerViewHolder(v);
        }
          else {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.footer, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder) {
          //因为banner占据1一个位置，所以...
            NewsBean news = mData.get(position-1);
            if(news == null) {
                return;
            }
            ((ItemViewHolder) holder).mTitle.setText(news.getTitle());
            ((ItemViewHolder) holder).mDesc.setText(news.getDigest());
            ImageLoaderUtils.display(mContext, ((ItemViewHolder) holder).mNewsImg, news.getImgsrc());
        }
        if (holder instanceof BannerViewHolder){
            //设置banner
            //从realm数据库获取banner数据
            ((BannerViewHolder) holder).banner.setPages(new CBViewHolderCreator<BannerView>() {
                @Override
                public BannerView createHolder() {
                    return new BannerView();
                }
            },BannerData).startTurning(5000).setScrollDuration(1000);
            banner = ((BannerViewHolder) holder).banner;
        }
    }
    public void setmData(List<NewsBean> data){
        this.mData = data;
        banner.notifyDataSetChanged();
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        int begin = mShowFooter?1:0;
        if (mData==null){
            return begin;
        }
        return mData.size()+begin+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(!mShowFooter) {
            return TYPE_ITEM;
        }
        if (position ==0){
            return TYPE_BANNER;
        }
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public void onChange(Realm realm) {
        if (banner != null){
            banner.notifyDataSetChanged();
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mTitle;
        public TextView mDesc;
        public ImageView mNewsImg;

        public ItemViewHolder(View v) {
            super(v);
            mTitle = (TextView) v.findViewById(R.id.tvTitle);
            mDesc = (TextView) v.findViewById(R.id.tvDesc);
            mNewsImg = (ImageView) v.findViewById(R.id.ivNews);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
         if (mOnItemClickListener!=null){
             mOnItemClickListener.onItemClick(view, this.getPosition());
         }
        }
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder {

        public ConvenientBanner banner;
        public BannerViewHolder(View itemView) {
            super(itemView);
            banner = (ConvenientBanner) itemView.findViewById(R.id.news_banner);
        }
    }

    public void isShowFooter(boolean showFooter) {
        this.mShowFooter = showFooter;
    }

    public boolean isShowFooter() {
        return this.mShowFooter;
    }

    public NewsBean getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View view) {
            super(view);
        }

    }
    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
