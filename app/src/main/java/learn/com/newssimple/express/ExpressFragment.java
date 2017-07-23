package learn.com.newssimple.express;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.List;

import learn.com.newssimple.MyAdapter;
import learn.com.newssimple.R;
import learn.com.newssimple.Utils.AddExpressName;
import learn.com.newssimple.beans.ExpressName;
import learn.com.newssimple.express.widget.ExpressDetailActivity;
import learn.com.newssimple.main.view.NewsApplication;

/**
 * Created by zyx on 2017/7/13.
 */

public class ExpressFragment extends Fragment {
    private Spinner spinner;
    private Button scan;
    private Button apply;
    private ExpressName expressName;
    private EditText expressNum;
    private String currentNo;
    public static final int REQUEST_CODE = 111;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.express_fragment,null);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        apply = (Button) view.findViewById(R.id.apply);
        expressNum = (EditText) view.findViewById(R.id.editText);
        scan = (Button) view.findViewById(R.id.scan);
        final List<ExpressName> expresses = new ArrayList<>();
        AddExpressName.addData(expresses);
        MyAdapter adapter = new MyAdapter(expresses,getActivity());
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                expressName = expresses.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentNo = String.valueOf(expressNum.getText()).trim();
                Log.d("currentNo",currentNo);
                Intent intent =new Intent();
                if (!currentNo.isEmpty()){
                Bundle bundle = new Bundle();
                bundle.putString("com",expressName.getNo());
                bundle.putString("no",currentNo);
                intent.putExtras(bundle);
                intent.setClass(getContext(), ExpressDetailActivity.class);
                startActivity(intent);
                }else {
                    Toast.makeText(getContext(),"单号不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT>22){
                    if (ContextCompat.checkSelfPermission(NewsApplication.getContext(), Manifest.permission.CAMERA)!= PackageManager
                            .PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{android.Manifest.permission.CAMERA},20);

                    }else {
                        Intent intent = new Intent(NewsApplication.getContext(), CaptureActivity.class);
                        startActivityForResult(intent,REQUEST_CODE);
                    }
                }


            }
        });


        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==REQUEST_CODE){
            //处理扫描结果
            if (data != null){
                Bundle bundle = data.getExtras();
                if (bundle==null){
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE)==CodeUtils.RESULT_SUCCESS){
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    expressNum.setText(result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(NewsApplication.getContext(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 20:
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(NewsApplication.getContext(), CaptureActivity.class);
                    startActivityForResult(intent,REQUEST_CODE);
                }
                break;
            default:
        }
    }
}
