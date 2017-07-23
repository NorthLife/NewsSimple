package learn.com.newssimple;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



import java.util.List;

import learn.com.newssimple.beans.ExpressName;

/**
 * Created by zyx on 2017/7/12.
 */

public class MyAdapter extends BaseAdapter {
    private List<ExpressName> mlist;
    private Context mcontext;

    public MyAdapter(List<ExpressName> mlist, Context mcontext) {
        this.mlist = mlist;
        this.mcontext = mcontext;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
        convertView = layoutInflater.inflate(R.layout.spinner_item,null);
        if (convertView!=null){
            TextView express = (TextView) convertView.findViewById(R.id.express);
            express.setText(mlist.get(position).getCom());
        }
        return convertView;
    }
}
