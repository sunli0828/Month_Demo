package com.sunli.test1220.mvp.presenter;

import com.sunli.test1220.mvp.callback.ICallBack;
import com.sunli.test1220.mvp.model.IModelImpl;
import com.sunli.test1220.mvp.view.IView;

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
                iView.showResponseData(data);
            }

            @Override
            public void setDataFail(Exception e) {
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
