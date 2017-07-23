package learn.com.newssimple.main.view;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.testdevelop.mysdk.okhttp.CommonOkhttpClient;

import io.realm.Realm;
import learn.com.newssimple.R;

/**
 * Created by zyx on 2017/7/3.
 */

public abstract class BaseActivity extends AppCompatActivity {
    public Realm mRealm;
    protected int layoutId = R.layout.activity_base;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        initLayoutId();
        super.onCreate(savedInstanceState, persistentState);
        initViews();

        //
        Log.d("BaseActivity", "mREalm");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initLayoutId();
        super.onCreate(savedInstanceState);
        initViews();
    }

    protected abstract void initLayoutId();

    protected void initViews() {
        setContentView(layoutId);
        mRealm = Realm.getDefaultInstance();

    }


    @Override
    protected void onDestroy() {
        CommonOkhttpClient.cancelTag(this);
        super.onDestroy();
        if (mRealm != null) {
            if (!mRealm.isClosed()) {
                mRealm.close();
            }

        }
        mRealm = null;

    }
}
