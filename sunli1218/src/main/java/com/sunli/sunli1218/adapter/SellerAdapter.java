package com.sunli.sunli1218.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sunli.sunli1218.R;
import com.sunli.sunli1218.bean.CarBean;

import java.util.ArrayList;
import java.util.List;

public class SellerAdapter extends RecyclerView.Adapter<SellerAdapter.SellerViewHolder> {

    private List<CarBean.DataBean> dataBeanList = new ArrayList<>();
    private Context dataContext;

    public SellerAdapter(Context dataContext) {
        this.dataContext = dataContext;
    }

    @NonNull
    @Override
    public SellerAdapter.SellerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(dataContext, R.layout.adapter_item_seller, null);
        SellerViewHolder sellerViewHolder = new SellerViewHolder(view);
        return sellerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SellerAdapter.SellerViewHolder sellerViewHolder, final int i) {
        sellerViewHolder.textView.setText(dataBeanList.get(i).getSellerName());

        final SellerThingsAdapter sellerThingsAdapter = new SellerThingsAdapter(dataBeanList.get(i).getList(), dataContext);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(dataContext);
        sellerViewHolder.recyclerView.setLayoutManager(linearLayoutManager);
        sellerViewHolder.recyclerView.setAdapter(sellerThingsAdapter);

        sellerViewHolder.ckBox.setChecked(dataBeanList.get(i).isCheck());

        sellerThingsAdapter.setListener(new SellerThingsAdapter.CarCallBackListener() {
            @Override
            public void callBack() {
                if (mCarCallBackListener != null) {
                    mCarCallBackListener.callBack(dataBeanList);
                }

                List<CarBean.DataBean.ListBean> listBeans = dataBeanList.get(i).getList();
                boolean isAllChecked = true;
                for (CarBean.DataBean.ListBean bean : listBeans) {
                    if (!bean.isCheck()) {
                        isAllChecked = false;
                        break;
                    }
                }

                sellerViewHolder.ckBox.setChecked(isAllChecked);
                dataBeanList.get(i).setCheck(isAllChecked);
            }
        });

        sellerViewHolder.ckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBeanList.get(i).setCheck(sellerViewHolder.ckBox.isChecked());
                sellerThingsAdapter.selectOrRemoveAll(sellerViewHolder.ckBox.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataBeanList.size();
    }

    public class SellerViewHolder extends RecyclerView.ViewHolder {
        CheckBox ckBox;
        TextView textView;
        RecyclerView recyclerView;
        public SellerViewHolder(@NonNull View itemView) {
            super(itemView);
            ckBox = itemView.findViewById(R.id.checked_seller);
            textView = itemView.findViewById(R.id.item_text_sellerName);
            recyclerView = itemView.findViewById(R.id.item_recycler_shop);
        }
    }

    public void setList(List<CarBean.DataBean> list) {
        this.dataBeanList = list;
        notifyDataSetChanged();
    }

    private CarCallBackListener mCarCallBackListener;

    public void setListener(CarCallBackListener listener) {
        this.mCarCallBackListener = listener;
    }

    public interface CarCallBackListener {
        void callBack(List<CarBean.DataBean> list);
    }
}
