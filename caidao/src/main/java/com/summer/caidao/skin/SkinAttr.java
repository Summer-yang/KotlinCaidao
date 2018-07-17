package com.summer.caidao.skin;

import android.view.View;

/**
 * Created by Summer on 2018/2/2.
 * 属性类
 */
public class SkinAttr {

    private String mResName;
    private SkinAttrType mType;

    SkinAttr(String resName, SkinAttrType type) {
        this.mResName = resName;
        this.mType = type;
    }

    public void apply(View view) {
        mType.apply(view, mResName);
    }

}
