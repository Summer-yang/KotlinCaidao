package com.summer.caidao.skin;

import android.content.Context;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Summer on 2018/2/2.
 * <p>
 * 获取支持换肤的属性
 */
public class SkinAttrSupport {

    public static List<SkinAttr> getSkinAttrs(AttributeSet attrs, Context context) {

        List<SkinAttr> mSkinAttrs = new ArrayList<>();
        SkinAttr skinAttr;
        SkinAttrType attrType;

        for (int i = 0, n = attrs.getAttributeCount(); i < n; i++) {

            String attrName = attrs.getAttributeName(i);
            String attrVal = attrs.getAttributeValue(i);

            if (attrVal.startsWith("@")) {
                int id = -1;

                try {
                    id = Integer.parseInt(attrVal.substring(1));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                if (-1 == id) {
                    continue;
                }

                String resName = context.getResources().getResourceEntryName(id);

                if (resName.startsWith(BaseSkinManager.SKIN_RES_START_WITH)) {
                    attrType = getSupportAttrType(attrName);
                    if (null == attrType) break;

                    skinAttr = new SkinAttr(resName, attrType);
                    mSkinAttrs.add(skinAttr);
                }
            }
        }
        return mSkinAttrs;
    }

    private static SkinAttrType getSupportAttrType(String attrName) {
        for (SkinAttrType attrType : SkinAttrType.values()) {
            if (attrType.getResType().equals(attrName)) {
                return attrType;
            }
        }
        return null;
    }

}
