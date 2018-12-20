package com.sunli.month_demo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sunli.month_demo.R;
import com.sunli.month_demo.bean.CarBean;

import java.util.ArrayList;
import java.util.List;

public class SellerAdapter extends RecyclerView.Adapter<SellerAdapter.ViewHolder> {
    private List<CarBean.DataBean> mlist ;
    private Context context;

    public SellerAdapter(Context context) {
        this.context = context;
        mlist=new ArrayList<>();
    }

    public void setList(List<CarBean.DataBean> list) {
        this.mlist = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SellerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = View.inflate(context, R.layout.adapter_item_seller, null);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SellerAdapter.ViewHolder holder, final int i) {
        holder.text_name.setText(mlist.get(i).getSellerName());
       /* final SellerThingsAdapter sellerThingsAdapter = new SellerThingsAdapter(context, mlist.get(i).getList());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        holder.recyclerView.setAdapter(sellerThingsAdapter);
*/
        holder.checkBox.setChecked(mlist.get(i).isCheck());

       /* sellerThingsAdapter.setListener(new SellerThingsAdapter.ShopCallBackListener() {
            @Override
            public void callBack() {
                if (mShopCallBackListener != null) {
                    mShopCallBackListener.callBack(mlist);
                }

                List<CarBean.DataBean.ListBean> listBeans = mlist.get(i).getList();
                boolean isAllChecked = true;
                for (CarBean.DataBean.ListBean bean : listBeans) {
                    if (!bean.isCheck()) {
                        isAllChecked = false;
                        break;
                    }
                }

                holder.checkBox.setChecked(isAllChecked);
                mlist.get(i).setCheck(isAllChecked);
            }
        });

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlist.get(i).setCheck(holder.checkBox.isChecked());
                sellerThingsAdapter.selectOrRemoveAll(holder.checkBox.isChecked());
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text_name;
        //RecyclerView recyclerView;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_name = itemView.findViewById(R.id.item_text_sellerName1);
            //recyclerView = itemView.findViewById(R.id.recycler_shop1);
            checkBox = itemView.findViewById(R.id.checked_seller1);
        }
    }

    private ShopCallBackListener mShopCallBackListener;

    public void setListener(ShopCallBackListener listener) {
        this.mShopCallBackListener = listener;
    }

    public interface ShopCallBackListener {
        void callBack(List<CarBean.DataBean> list);
    }
}
