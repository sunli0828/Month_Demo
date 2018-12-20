package com.sunli.sunli1218.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sunli.sunli1218.CustomCounterView;
import com.sunli.sunli1218.R;
import com.sunli.sunli1218.bean.CarBean;

import java.util.ArrayList;
import java.util.List;

public class SellerThingsAdapter extends RecyclerView.Adapter<SellerThingsAdapter.SellerThingsViewHolder> {

    private List<CarBean.DataBean.ListBean> listBeanList = new ArrayList<>();
    private Context listContext;

    public SellerThingsAdapter(List<CarBean.DataBean.ListBean> listBeanList, Context listContext) {
        this.listBeanList = listBeanList;
        this.listContext = listContext;
    }

    @NonNull
    @Override
    public SellerThingsAdapter.SellerThingsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(listContext, R.layout.adapter_item_seller_things, null);
        SellerThingsViewHolder sellerThingsViewHolder = new SellerThingsViewHolder(view);
        return sellerThingsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SellerThingsAdapter.SellerThingsViewHolder sellerThingsViewHolder, final int i) {
        String url = listBeanList.get(i).getImages().split("\\|")[0].replace("https", "http");
        Glide.with(listContext).load(url).into(sellerThingsViewHolder.icon_things);
        sellerThingsViewHolder.text_things_title.setText(listBeanList.get(i).getTitle());
        sellerThingsViewHolder.text_things_price.setText(listBeanList.get(i).getPrice() + "");

        sellerThingsViewHolder.ckBox_things.setChecked(listBeanList.get(i).isCheck());

        sellerThingsViewHolder.ckBox_things.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listBeanList.get(i).setCheck(isChecked);

                if (mCarCallBackListener != null) {
                    mCarCallBackListener.callBack();
                }
            }
        });

        sellerThingsViewHolder.customCounterView.setData(this, listBeanList, i);

        sellerThingsViewHolder.customCounterView.setOnCallBack(new CustomCounterView.CallBackListener() {
            @Override
            public void callBack() {
                if (mCarCallBackListener != null) {
                    mCarCallBackListener.callBack();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBeanList.size();
    }

    public class SellerThingsViewHolder extends RecyclerView.ViewHolder {
        CheckBox ckBox_things;
        ImageView icon_things;
        TextView text_things_title;
        TextView text_things_price;
        private final CustomCounterView customCounterView;

        public SellerThingsViewHolder(@NonNull View itemView) {
            super(itemView);
            ckBox_things = itemView.findViewById(R.id.item_things_check_product);
            icon_things = itemView.findViewById(R.id.item_things_icon_product);
            text_things_title = itemView.findViewById(R.id.item_things_text_product_title);
            text_things_price = itemView.findViewById(R.id.item_things_text_product_price);
            customCounterView = itemView.findViewById(R.id.custom_things_counter);
        }
    }

    public void selectOrRemoveAll(boolean isSelectedAll) {
        for (CarBean.DataBean.ListBean listBean : listBeanList) {
            listBean.setCheck(isSelectedAll);
        }
        notifyDataSetChanged();
    }

    private CarCallBackListener mCarCallBackListener;

    public void setListener(CarCallBackListener listener) {
        this.mCarCallBackListener = listener;
    }

    public interface CarCallBackListener {
        void callBack();
    }
}
