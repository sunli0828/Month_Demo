package com.sunli.sunli1218.mvp.presenter;

import com.sunli.sunli1218.mvp.callback.ICallBack;
import com.sunli.sunli1218.mvp.model.IModelImpl;
import com.sunli.sunli1218.mvp.view.IView;

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
            public void setData(Object data) {
                iView.success(data);
            }

            @Override
            public void fail(Exception e) {
                iView.fail(e);
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
