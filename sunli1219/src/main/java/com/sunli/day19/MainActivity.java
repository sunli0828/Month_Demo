package com.sunli.day19;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.sunli.day19.bean.CarBean;
import com.sunli.day19.mvp.presenter.IPresenterImpl;
import com.sunli.day19.mvp.view.IView;
import com.sunli.day19.okhttp.utils.ApiUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IView, View.OnClickListener {

    private IPresenterImpl iPresenter;
    private RecyclerView main_recycler_car;
    private CheckBox main_ck_all;
    private TextView main_text_totalPrice;
    private Button main_btn_totalNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iPresenter = new IPresenterImpl(this);
        iPresenter.startRequest(String.format(ApiUtils.URL_SHOPCAR_INFO, 71), null, CarBean.class);
        initView();


    }

    private void initView() {
        main_recycler_car = findViewById(R.id.main_recycle_car);
        main_ck_all = findViewById(R.id.main_ck_all);
        main_text_totalPrice = findViewById(R.id.main_text_totalPrice);
        main_btn_totalNum = findViewById(R.id.main_btn_totalNum);

        main_ck_all.setOnClickListener(this);
        main_btn_totalNum.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_ck_all:
                break;
            case R.id.main_btn_totalNum:
                break;
            default:
                break;
        }
    }

    @Override
    public void showResponseData(Object data) {

    }

    @Override
    public void showResponseFail(Exception e) {
        Toast.makeText(MainActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onDetach();
    }
}
