package com.sunli.month_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.sunli.month_demo.adapter.SellerAdapter;
import com.sunli.month_demo.bean.CarBean;
import com.sunli.month_demo.mvp.presenter.IPresenterImpl;
import com.sunli.month_demo.mvp.view.IView;
import com.sunli.month_demo.okhttp.utils.ApiUtils;
import com.sunli.month_demo.okhttp.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements IView, View.OnClickListener {

    private IPresenterImpl iPresenter;
    private RecyclerView home_recycle_car;
    private CheckBox home_checkAll;
    private Button home_btn_pay;
    private TextView home_text_sumPrice;
    private SellerAdapter adapter;
    private List<CarBean.DataBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        iPresenter = new IPresenterImpl(this);
        iPresenter.startRequest(String.format(ApiUtils.TYPE_CAR, 71), null, CarBean.class);
        initView();

    }

    private void initView() {
        home_recycle_car = findViewById(R.id.home_recycle_car);
        home_checkAll = findViewById(R.id.home_checkAll);
        home_text_sumPrice = findViewById(R.id.home_text_sumPrice);
        home_btn_pay = findViewById(R.id.home_btn_pay);

        home_checkAll.setOnClickListener(this);
        home_btn_pay.setOnClickListener(this);

        adapter = new SellerAdapter(this);
        home_recycle_car.setAdapter(adapter);

        adapter.setListener(new SellerAdapter.ShopCallBackListener() {
            @Override
            public void callBack(List<CarBean.DataBean> list) {
                double sumPrice = 0;
                int num = 0;
                int sumNums = 0;
                for (int a = 0; a < list.size(); a++) {
                    List<CarBean.DataBean.ListBean> listAll = list.get(a).getList();
                    for (int i = 0; i < listAll.size(); i++) {
                        sumNums += listAll.get(i).getNum();
                        if (listAll.get(i).isCheck()) {
                            sumPrice += listAll.get(i).getPrice() * listAll.get(i).getNum();
                            num += listAll.get(i).getNum();
                        }
                    }
                }

                if (num < sumNums) {
                    home_checkAll.setChecked(false);
                } else {
                    home_checkAll.setChecked(true);
                }

                home_text_sumPrice.setText("合计: ¥ " + sumPrice);
                home_btn_pay.setText("付款(" + sumNums + ")");
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_checkAll:
                checkCar(home_checkAll.isChecked());
                adapter.notifyDataSetChanged();
                break;
            case R.id.home_btn_pay:
                break;
            default:
                break;
        }
    }

    private void checkCar(boolean selected) {
        double sumPrice = 0;
        int num = 0;
        for (int a = 0; a < list.size(); a++) {
            CarBean.DataBean dataBean = list.get(a);
            dataBean.setCheck(selected);

            List<CarBean.DataBean.ListBean> listAll = list.get(a).getList();
            for (int i = 0; i < listAll.size(); i++) {
                listAll.get(i).setCheck(selected);
                sumPrice += listAll.get(i).getPrice() * listAll.get(i).getNum();
                num += listAll.get(i).getNum();
            }
        }
        if (selected) {
            home_text_sumPrice.setText("合计: ¥ " + sumPrice);
            home_btn_pay.setText("付款(" + num + ")");
        } else {
            home_text_sumPrice.setText("合计: ¥ 0.00");
            home_btn_pay.setText("付款(0)");
        }
    }

    @Override
    public void showResponseData(Object data) {
        if (data instanceof CarBean) {
            CarBean carBean = (CarBean) data;
            List<CarBean.DataBean> list = carBean.getData();
            if (list != null) {
                list.remove(0);
                adapter.setList(list);
            }
        }
    }

    @Override
    public void showResponseFail(Exception e) {
        Toast.makeText(HomeActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onDetach();
    }
}
