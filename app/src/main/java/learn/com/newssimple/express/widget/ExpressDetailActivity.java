package learn.com.newssimple.express.widget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.testdevelop.mysdk.okhttp.CommonOkhttpClient;
import com.testdevelop.mysdk.okhttp.listener.DisposeDataHandle;
import com.testdevelop.mysdk.okhttp.listener.DisposeDataListener;
import com.testdevelop.mysdk.okhttp.request.CommonRequest;
import com.testdevelop.mysdk.okhttp.response.CommonJsonCallback;

import java.util.ArrayList;
import java.util.List;

import learn.com.newssimple.R;
import learn.com.newssimple.beans.ExpressDataList;
import learn.com.newssimple.beans.LogisticInf;
import learn.com.newssimple.common.Urls;

public class ExpressDetailActivity extends AppCompatActivity {
    private ListView listView;
    private String com;
    private String no;
    private ProgressBar progressBar;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express_detail);
        listView = (ListView) findViewById(R.id.listview);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        com = "&com="+bundle.getString("com");
        no = "&no="+bundle.getString("no");
        String url = Urls.ExpressHost +com +no;
        CommonOkhttpClient.sendRequest(CommonRequest.createGetRequest(url,null),new CommonJsonCallback(new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                progressBar.setVisibility(View.GONE);
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
                Gson gson = gsonBuilder.create();
                LogisticInf data = gson.fromJson(responseObj.toString(),LogisticInf.class);
                if (data.getResultcode()==0){
                    listView.setAdapter(new ArrayAdapter<String>(ExpressDetailActivity.this, android.R.layout.simple_list_item_1, getData(data)));
                }else {
                    Toast.makeText(ExpressDetailActivity.this,"查询不到物流信息或发生了其他错误",Toast.LENGTH_SHORT).show();
                 }
                }

            @Override
            public void onFailure(Object reasonObj) {

            }
        })));



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<String> getData(LogisticInf Logisticdata) {
        List<String> data = new ArrayList<>();

       for (int i = 0; i < Logisticdata.getResult().getList().size();i++){
           ExpressDataList list = Logisticdata.getResult().getList().get(i);
           data.add(list.getDatetime()+list.getRemark());
       }
       return data;
    }
}
