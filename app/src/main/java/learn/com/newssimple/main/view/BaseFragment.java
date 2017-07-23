package learn.com.newssimple.main.view;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

/**
 * Created by zyx on 2017/7/4.
 */

public class BaseFragment extends Fragment {
 public RecyclerView recyclerView;
    public RecyclerView getmRecyclerView() {
        return recyclerView;
    }
}
