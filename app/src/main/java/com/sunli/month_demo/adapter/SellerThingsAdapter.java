package com.sunli.month_demo.adapter;

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
import com.sunli.month_demo.R;
import com.sunli.month_demo.bean.CarBean;

import java.util.ArrayList;
import java.util.List;

public class SellerThingsAdapter extends RecyclerView.Adapter<SellerThingsAdapter.ViewHolder> {
    private List<CarBean.DataBean.ListBean> mlist = new ArrayList<>();
    private Context context;

    public SellerThingsAdapter(Context context, List<CarBean.DataBean.ListBean> list) {
        this.context = context;
        this.mlist = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.adapter_item_seller_things, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        String url = mlist.get(i).getImages().split("\\|")[0].replace("https", "http");
        Glide.with(context).load(url).into(viewHolder.icon);
        viewHolder.title.setText(mlist.get(i).getTitle());
        viewHolder.price.setText(mlist.get(i).getPrice() + "");

        //根据记录状态改变勾选
        viewHolder.ckbox.setChecked(mlist.get(i).isCheck());
        viewHolder.ckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mlist.get(i).setCheck(isChecked);
                if (mShopCallBackListener != null) {
                    mShopCallBackListener.callBack();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, price;
        ImageView icon;
        CheckBox ckbox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_things_text_product_title);
            price = itemView.findViewById(R.id.item_things_text_product_price);
            icon = itemView.findViewById(R.id.item_things_icon_product);
            ckbox = itemView.findViewById(R.id.item_things_check_product);
        }
    }

    public void selectOrRemoveAll(boolean isSelectall) {
        for (CarBean.DataBean.ListBean listBean : mlist) {
            listBean.setCheck(isSelectall);
        }
        notifyDataSetChanged();
    }

    private ShopCallBackListener mShopCallBackListener;

    public void setListener(ShopCallBackListener listener) {
        this.mShopCallBackListener = listener;
    }

    public interface ShopCallBackListener {
        void callBack();
    }
}
