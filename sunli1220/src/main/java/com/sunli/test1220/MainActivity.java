package com.sunli.test1220;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sunli.test1220.adapter.SellerAdapter;
import com.sunli.test1220.bean.CarBean;
import com.sunli.test1220.mvp.presenter.IPresenterImpl;
import com.sunli.test1220.mvp.view.IView;
import com.sunli.test1220.okhttp.utils.ApiUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IView, View.OnClickListener {

    private IPresenterImpl iPresenter;
    private RecyclerView main_recycler_car;
    private CheckBox main_ck_all;
    private TextView main_text_totalPrice;
    private Button main_btn_totalNum;
    private List<CarBean.DataBean> carBeanData;
    private SellerAdapter sellerAdapter;
    private Button main_btn_linkage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iPresenter = new IPresenterImpl(this);

        iPresenter.startRequest(ApiUtils.URL_SHOPCAR_INFO, null, CarBean.class);

        initView();
    }

    private void initView() {
        main_recycler_car = findViewById(R.id.main_recycler_car);
        main_ck_all = findViewById(R.id.main_ck_all);
        main_text_totalPrice = findViewById(R.id.main_text_totalPrice);
        main_btn_totalNum = findViewById(R.id.main_btn_totalNum);
        main_btn_linkage = findViewById(R.id.main_btn_linkage);

        main_ck_all.setOnClickListener(this);
        main_btn_totalNum.setOnClickListener(this);
        main_btn_linkage.setOnClickListener(this);

        sellerAdapter = new SellerAdapter(this);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        main_recycler_car.setLayoutManager(linearLayoutManager);
        main_recycler_car.setAdapter(sellerAdapter);

        sellerAdapter.setListener(new SellerAdapter.CarCallBackListener() {
            @Override
            public void callBack(List<CarBean.DataBean> list) {
                double totalPrice = 0;
                int num = 0, totalNum = 0;
                for (int a = 0; a < list.size(); a++) {
                    List<CarBean.DataBean.ListBean> listSellerThings = list.get(a).getList();
                    for (int i = 0; i < listSellerThings.size(); i++) {
                        totalNum += listSellerThings.get(i).getNum();
                        if (listSellerThings.get(i).isCheck()) {
                            totalPrice += listSellerThings.get(i).getPrice() * listSellerThings.get(i).getNum();
                            num += listSellerThings.get(i).getNum();
                        }
                    }
                }

                if (num < totalNum) {
                    main_ck_all.setChecked(false);
                } else {
                    main_ck_all.setChecked(true);
                }

                main_text_totalPrice.setText("合计: ¥" + totalPrice);
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
                Intent intentMap = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intentMap);
                break;
            case R.id.main_btn_linkage:
                Intent intentLevel = new Intent(MainActivity.this, LevelLinkActivity.class);
                startActivity(intentLevel);
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
    public void showResponseData(Object data) {
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
    public void showResponseFail(Exception e) {}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onDetach();
    }
}
