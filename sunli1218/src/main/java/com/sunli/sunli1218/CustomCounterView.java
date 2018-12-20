package com.sunli.sunli1218;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sunli.sunli1218.adapter.SellerThingsAdapter;
import com.sunli.sunli1218.bean.CarBean;

import java.util.ArrayList;
import java.util.List;

public class CustomCounterView extends RelativeLayout implements View.OnClickListener {

    private Context context;
    private EditText editCounts;

    public CustomCounterView(Context context) {
        super(context);
        init(context);
    }

    public CustomCounterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomCounterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        View view = View.inflate(context, R.layout.view_item_counts, null);
        ImageView reduceCounts = view.findViewById(R.id.reduce_counts);
        ImageView addCounts = view.findViewById(R.id.add_counts);
        editCounts = view.findViewById(R.id.edit_shop_counts);

        reduceCounts.setOnClickListener(this);
        addCounts.setOnClickListener(this);
        addView(view);

        editCounts.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private int num;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_counts:
                num ++;
                editCounts.setText(num + "");
                list.get(position).setNum(num);
                listener.callBack();
                sellerThingsAdapter.notifyItemChanged(position);
                break;
            case R.id.reduce_counts:
                if (num > 1) {
                    num --;
                } else {
                    Toast.makeText(context, "商品数量不能小于1", Toast.LENGTH_SHORT).show();
                }
                editCounts.setText(num + "");
                list.get(position).setNum(num);
                listener.callBack();
                sellerThingsAdapter.notifyItemChanged(position);
                break;
            default:
                break;
        }
    }

    private List<CarBean.DataBean.ListBean> list= new ArrayList<>();
    private int position;
    private SellerThingsAdapter sellerThingsAdapter;

    public void setData(SellerThingsAdapter sellerThingsAdapter, List<CarBean.DataBean.ListBean> list, int i) {
        this.list = list;
        this.sellerThingsAdapter = sellerThingsAdapter;
        position = i;
        num = list.get(i).getNum();
        editCounts.setText(num + "");
    }

    private CallBackListener listener;

    public void setOnCallBack(CallBackListener listener) {
        this.listener = listener;
    }

    public interface CallBackListener { void callBack(); }
}
