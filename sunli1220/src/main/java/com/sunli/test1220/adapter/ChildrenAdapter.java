package com.sunli.test1220.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sunli.test1220.R;
import com.sunli.test1220.bean.CarBean;

import java.util.List;

public class ChildrenAdapter extends RecyclerView.Adapter<ChildrenAdapter.ChilderenViewHolder> {

    private List<CarBean.DataBean.ListBean> listBeanList;
    private Context listContext;

    public ChildrenAdapter(Context listContext) {
        this.listContext = listContext;
    }

    public void setListBeanList(List<CarBean.DataBean.ListBean> listBeanList) {
        this.listBeanList = listBeanList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChilderenViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(listContext, R.layout.adapter_item_children, null);
        return new ChilderenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChilderenViewHolder childerenViewHolder, final int i) {
        String url = listBeanList.get(i).getImages().split("\\|")[0].replace("https", "http");
        Glide.with(listContext).load(url).into(childerenViewHolder.childrenIcon);

        childerenViewHolder.childrenTitle.setText(listBeanList.get(i).getTitle());
        childerenViewHolder.childrenPrice.setText(listBeanList.get(i).getPrice() + "");
        childerenViewHolder.chilerenNum.setText(listBeanList.get(i).getNum() + "");
    }

    @Override
    public int getItemCount() {
        return listBeanList.size();
    }

    public class ChilderenViewHolder extends RecyclerView.ViewHolder {
        ImageView childrenIcon;
        TextView childrenPrice, childrenTitle, chilerenNum;
        public ChilderenViewHolder(@NonNull View itemView) {
            super(itemView);
            childrenIcon = itemView.findViewById(R.id.item_children_icon);
            childrenTitle = itemView.findViewById(R.id.item_children_title);
            childrenPrice = itemView.findViewById(R.id.item_children_price);
            chilerenNum = itemView.findViewById(R.id.item_children_num);
        }
    }
}
