package com.sunli.test1220;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.sunli.test1220.adapter.ChildrenAdapter;
import com.sunli.test1220.adapter.ParentAdapter;
import com.sunli.test1220.bean.CarBean;
import com.sunli.test1220.mvp.presenter.IPresenterImpl;
import com.sunli.test1220.mvp.view.IView;
import com.sunli.test1220.okhttp.utils.ApiUtils;

import java.util.List;

public class LevelLinkActivity extends AppCompatActivity implements IView, View.OnClickListener{

    private IPresenterImpl iPresenter;
    private RecyclerView recycler_parent;
    private RecyclerView recycler_children;
    private boolean flag = true;
    private ParentAdapter parentAdapter;
    private int index;
    private ChildrenAdapter childrenAdapter;
    private List<CarBean.DataBean> carBeanData;
    private Button btn_animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_link);

        iPresenter = new IPresenterImpl(this);
        iPresenter.startRequest(ApiUtils.URL_SHOPCAR_INFO, null, CarBean.class);
        recycler_parent = findViewById(R.id.level_recycler_parent);
        recycler_children = findViewById(R.id.level_recycler_children);
        btn_animator = findViewById(R.id.level_btn_animator_code);
        btn_animator.setOnClickListener(this);

        recycler_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iPresenter.startRequest(ApiUtils.URL_SHOPCAR_INFO, null, CarBean.class);
            }
        });
        showParent();
        parentAdapter.setListener(new ParentAdapter.ParentCallbackListener() {
            @Override
            public void parentCallbackListener(int i) {
                index = i;
                flag = false;
                showChildren();
            }
        });
    }

    private void showParent() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(LevelLinkActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_parent.setLayoutManager(linearLayoutManager);
        parentAdapter = new ParentAdapter(this);
        recycler_parent.setAdapter(this.parentAdapter);
        recycler_parent.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
    }

    private void showChildren() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_children.setLayoutManager(linearLayoutManager);
        flag = false;

        childrenAdapter = new ChildrenAdapter(this);
        recycler_children.setAdapter(childrenAdapter);
        recycler_children.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        iPresenter.startRequest(ApiUtils.URL_SHOPCAR_INFO, null, CarBean.class);
        childrenAdapter.setListBeanList(carBeanData.get(index).getList());
    }

    @Override
    public void showResponseData(Object data) {
        CarBean carBean = (CarBean) data;
        carBeanData = carBean.getData();
        if (carBeanData != null) {
            carBeanData.remove(0);
            parentAdapter.setDataBeanList(carBeanData);
        }
    }

    @Override
    public void showResponseFail(Exception e) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onDetach();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.level_btn_animator_code:
                Intent intentAnimator = new Intent(LevelLinkActivity.this, AnimatorOrCodeActivity.class);
                startActivity(intentAnimator);
                break;
            default:
                break;
        }
    }
}
