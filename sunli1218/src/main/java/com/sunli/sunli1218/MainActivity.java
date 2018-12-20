package com.sunli.sunli1218;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.sunli.sunli1218.adapter.SellerAdapter;
import com.sunli.sunli1218.bean.CarBean;
import com.sunli.sunli1218.mvp.presenter.IPresenterImpl;
import com.sunli.sunli1218.mvp.view.IView;
import com.sunli.sunli1218.okhttp.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IView,View.OnClickListener {

    private IPresenterImpl iPresenter;
    private RecyclerView main_recycle_car;
    private CheckBox main_ck_all;
    private TextView main_text_totalPrice;
    private Button main_btn_totalNum;
    private SellerAdapter sellerAdapter;
    private List<CarBean.DataBean> carBeanData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iPresenter = new IPresenterImpl(this);

        iPresenter.startRequest(String.format(ApiUtils.URL_SHOPCAR_INFO, 71), null, CarBean.class);

        initView();
    }

    private void initView() {
        main_recycle_car = findViewById(R.id.main_recycle_car);
        main_ck_all = findViewById(R.id.main_ck_all);
        main_text_totalPrice = findViewById(R.id.main_text_totalPrice);
        main_btn_totalNum = findViewById(R.id.main_btn_totalNum);

        main_ck_all.setOnClickListener(this);
        main_btn_totalNum.setOnClickListener(this);

        sellerAdapter = new SellerAdapter(this);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        main_recycle_car.setLayoutManager(linearLayoutManager);
        main_recycle_car.setAdapter(sellerAdapter);

        sellerAdapter.setListener(new SellerAdapter.CarCallBackListener() {
            @Override
            public void callBack(List<CarBean.DataBean> list) {
                double totalPrice = 0;
                int num = 0;
                int totalNum = 0;
                for (int a = 0; a < list.size(); a++) {
                    List<CarBean.DataBean.ListBean> listSellerThings = list.get(a).getList();
                    for (int i = 0; i < listSellerThings.size(); i++) {
                        totalNum += listSellerThings.get(i).getNum();
                        if (listSellerThings.get(i).isCheck()) {
                            totalPrice = totalPrice + (listSellerThings.get(i).getPrice() * listSellerThings.get(i).getNum());
                            num = num + listSellerThings.get(i).getNum();
                        }
                    }
                }

                if (num < totalNum) {
                    main_ck_all.setChecked(false);
                } else {
                    main_ck_all.setChecked(true);
                }

                main_text_totalPrice.setText("合计: " + totalPrice);
                main_btn_totalNum.setText("付款(" + num + ")");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_ck_all:
                boolean checked = main_ck_all.isChecked();
                checkSeller(checked);
                break;
            case R.id.main_btn_totalNum:
                Intent intent = new Intent(MainActivity.this, PaymentActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void checkSeller(boolean bool) {

        double totalPrice = 0;
        int num = 0;

        for (int a = 0; a < carBeanData.size(); a++) {
            CarBean.DataBean dataBean = carBeanData.get(a);
            dataBean.setCheck(bool);

            List<CarBean.DataBean.ListBean> listSellerThings = carBeanData.get(a).getList();

            for (int i = 0; i < listSellerThings.size(); i++) {
                listSellerThings.get(i).setCheck(bool);
                totalPrice = totalPrice + (listSellerThings.get(i).getPrice() * listSellerThings.get(i).getNum());
                num = num + listSellerThings.get(i).getNum();
            }
        }

        if (bool) {
            main_text_totalPrice.setText("合计: " + totalPrice);
            main_btn_totalNum.setText("付款(" + num + ")");
        } else {
            main_text_totalPrice.setText("合计: 0.00");
            main_btn_totalNum.setText("付款(0)");
        }
        sellerAdapter.notifyDataSetChanged();
    }

    @Override
    public void success(Object data) {
        if (data instanceof CarBean) {
            CarBean carBean = (CarBean) data;

            carBeanData = carBean.getData();

            if (carBeanData != null) {
                carBeanData.remove(0);
                sellerAdapter.setList(carBeanData);
            }
        }
    }

    @Override
    public void fail(Exception e) {
        Toast.makeText(this, "请求数据失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onDetach();
    }
}
