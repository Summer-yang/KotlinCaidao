package com.summer.caidao.skin;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Summer on 2018/2/2.
 * <p>
 * 换肤类型
 */
public enum SkinAttrType {

    BACKGROUND("background") {
        @Override
        public void apply(View view, String resName) {
            Drawable drawable = getResourcesManager().getDrawableByResourceName(resName);
            if (null != drawable) {
                view.setBackground(drawable);
            }
        }
    },
    SRC("src") {
        @Override
        public void apply(View view, String resName) {
            Drawable drawable = getResourcesManager().getDrawableByResourceName(resName);
            if (null != drawable) {
                if (view instanceof ImageView) {
                    ImageView imageView = (ImageView) view;
                    imageView.setImageDrawable(drawable);
                }
            }
        }
    },
    TEXT_COLOR("textColor") {
        @Override
        public void apply(View view, String resName) {
            ColorStateList colorStateList = getResourcesManager().getColorByResourceName(resName);
            if (null != colorStateList) {
                if (view instanceof TextView) {
                    TextView textView = (TextView) view;
                    textView.setTextColor(colorStateList);
                }
            }
        }
    };

    private String resType;

    SkinAttrType(String resType) {
        this.resType = resType;
    }

    public String getResType() {
        return resType;
    }

    public abstract void apply(View view, String resName);

    public ResourcesManager getResourcesManager() {
        return BaseSkinManager.newInstance().getResourcesManager();
    }
}
