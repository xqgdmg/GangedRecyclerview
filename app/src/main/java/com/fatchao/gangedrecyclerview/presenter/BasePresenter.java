package com.fatchao.gangedrecyclerview.presenter;

import com.fatchao.gangedrecyclerview.other.ViewCallBack;

public abstract class BasePresenter {

    protected ViewCallBack mViewCallBack;

    public void add(ViewCallBack viewCallBack) {
        this.mViewCallBack = viewCallBack;
    }

    public void remove() {
        this.mViewCallBack = null;
    }

    protected abstract void getData();
}
