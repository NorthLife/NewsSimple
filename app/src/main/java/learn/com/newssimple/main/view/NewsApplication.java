package learn.com.newssimple.main.view;

import android.app.Application;
import android.content.Context;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by zyx on 2017/7/4.
 */

public class NewsApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        ZXingLibrary.initDisplayOpinion(this);
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);
    }
    public static Context getContext(){
        return context;
    }
}
