package com.sunli.month_demo.mvp.presenter;

import com.sunli.month_demo.mvp.callback.ICallBack;
import com.sunli.month_demo.mvp.model.IModelImpl;
import com.sunli.month_demo.mvp.view.IView;

import java.util.Map;

public class IPresenterImpl implements IPresenter {

    private IView iView;
    private IModelImpl iModel;

    public IPresenterImpl(IView iView) {
        this.iView = iView;
        iModel = new IModelImpl();
    }

    @Override
    public void startRequest(String urlStr, String params, Class clazz) {
        iModel.getResponseData(urlStr, params, clazz, new ICallBack() {
            @Override
            public void success(Object data) {
                iView.showResponseData(data);
            }

            @Override
            public void fail(Exception e) {
                iView.showResponseFail(e);
            }
        });
    }

    public void onDetach() {
        if (iModel != null) {
            iModel = null;
        }
        if (iView != null) {
            iView = null;
        }
    }
}
