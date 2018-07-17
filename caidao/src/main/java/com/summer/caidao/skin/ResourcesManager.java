package com.summer.caidao.skin;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

/**
 * Created by Summer on 2018/2/2.
 * <p>
 * 资源管理类
 */
class ResourcesManager {

    private Resources resources;
    private String mPkgName;
    private String mSuffix;

    ResourcesManager(Resources resources, String mPkgName, String suffix) {
        this.resources = resources;
        this.mPkgName = mPkgName;
        if (null == suffix) {
            suffix = "";
        }
        mSuffix = suffix;
    }

    Drawable getDrawableByResourceName(String name) {
        try {
            name = appendSuffix(name);
            return resources.getDrawable(resources.getIdentifier(name, "drawable", mPkgName));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    ColorStateList getColorByResourceName(String name) {
        try {
            name = appendSuffix(name);
            return resources.getColorStateList(resources.getIdentifier(name, "color", mPkgName));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String appendSuffix(String name) {
        if (!TextUtils.isEmpty(mSuffix)) {
            name += "_" + mSuffix;
        }

        return name;
    }
}
