package com.summer.caidao.skin;

import android.view.View;

import java.util.List;

/**
 * Created by Summer on 2018/2/2.
 * <p>
 * 换肤view对象
 */
public class SkinView {

    private View mView;
    private List<SkinAttr> mAttrs;


    public SkinView(View mView, List<SkinAttr> attrs) {
        if (null == mView || null == attrs) {
            return;
        }
        this.mView = mView;
        this.mAttrs = attrs;
    }

    public void apply() {
        for (SkinAttr attr : mAttrs) {
            attr.apply(mView);
        }
    }
}
