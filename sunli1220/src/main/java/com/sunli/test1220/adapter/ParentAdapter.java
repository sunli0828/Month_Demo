package com.sunli.test1220.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunli.test1220.R;
import com.sunli.test1220.bean.CarBean;

import java.util.ArrayList;
import java.util.List;

public class ParentAdapter extends RecyclerView.Adapter<ParentAdapter.ParentViewHolder> {

    private List<CarBean.DataBean> dataBeanList = new ArrayList<>();
    private Context dataContext;

    public ParentAdapter(Context dataContext) {
        this.dataContext = dataContext;
    }

    public void setDataBeanList(List<CarBean.DataBean> dataBeanList) {
        this.dataBeanList = dataBeanList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ParentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(dataContext, R.layout.adapter_item_parent, null);
        ParentViewHolder parentViewHolder = new ParentViewHolder(view);
        return parentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ParentViewHolder parentViewHolder, final int i) {
        parentViewHolder.sellerName.setText(dataBeanList.get(i).getSellerName());
        parentViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (parentCallbackListener != null) {
                    parentCallbackListener.parentCallbackListener(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataBeanList.size();
    }

    public class ParentViewHolder extends RecyclerView.ViewHolder {
        TextView sellerName;
        public ParentViewHolder(@NonNull View itemView) {
            super(itemView);
            sellerName = itemView.findViewById(R.id.item_parent_text);
        }
    }

    public interface ParentCallbackListener{
        void parentCallbackListener(int i);
    }

    ParentCallbackListener parentCallbackListener;

    public void setListener(ParentCallbackListener listener) {
        this.parentCallbackListener = listener;
    }
}
