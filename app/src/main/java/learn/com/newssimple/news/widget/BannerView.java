package learn.com.newssimple.news.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.holder.Holder;

import learn.com.newssimple.R;
import learn.com.newssimple.Utils.ImageLoaderUtils;
import learn.com.newssimple.beans.NewsBean;
import learn.com.newssimple.beans.NewsBeanRealm;

/**
 * Created by zyx on 2017/7/3.
 */

public class BannerView implements Holder<NewsBeanRealm> {
    private View view;
    @Override
    public View createView(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.card_item_big,null);
        return view;
    }

    @Override
    public void UpdateUI(Context context, int position, NewsBeanRealm data) {
        ImageView imageView = (ImageView) view.findViewById(R.id.story_img);
        TextView textView= (TextView) view.findViewById(R.id.news_title);
        ImageLoaderUtils.display(context,imageView,data.getImgsrc());
        textView.setText(data.getTitle());
    }
}
