package com.summer.caidao.skin;

import android.app.Application;
import android.content.res.AssetManager;
import android.content.res.Resources;

import com.summer.caidao.shared.preferences.CaiDaoSharedPreferences;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Summer on 2018/2/2.
 * <p>
 * 换肤工具类
 * <p>
 * 使用须知,资源文件必须以 BaseUtilConstant.SKIN_RES_START_WITH 指定字符串开头
 * 目前支持替换的有
 * 1.background -> 统一使用drawable
 * 2.text color ->字体颜色
 * 3.image src  ->image图片
 */
public class BaseSkinManager {

    // 换肤配置
    public static final String SKIN_RES_START_WITH = "skin_";//需要换肤的资源前缀(可改变)
    public static final String SP_KEY_SKIN_PATH = "sp_key_skin_path";//插件式换肤,资源apk存放路径
    public static final String SP_KEY_SKIN_PACKAGE = "sp_key_skin_package";//插件式换肤,资源apk包名
    public static final String SP_KEY_SKIN_SUFFIX = "sp_key_skin_suffix";//本地资源换肤,资源后缀

    private Application mContext;

    private static BaseSkinManager mInstance;
    private ResourcesManager mResourcesManager;

    private List<SkinChangedListener> mListeners = new ArrayList<>();

    private Map<SkinChangedListener, List<SkinView>> mSkinViewMaps = new HashMap<>();

    private String mCurrentPath;
    private String mCurrentPkg;

    // 应用内换肤,后缀
    private String mSuffix;

    private BaseSkinManager() {
    }

    public static BaseSkinManager newInstance() {
        if (null == mInstance) {
            synchronized (BaseSkinManager.class) {
                if (null == mInstance) {
                    mInstance = new BaseSkinManager();
                }
            }
        }
        return mInstance;
    }

    public void init(Application context) {
        mContext = context;
        try {
            String skin_path = CaiDaoSharedPreferences.Companion.get(mContext).getString(SP_KEY_SKIN_PATH);
            String skin_package = CaiDaoSharedPreferences.Companion.get(mContext).getString(SP_KEY_SKIN_PACKAGE);
            mSuffix = CaiDaoSharedPreferences.Companion.get(mContext).getString(SP_KEY_SKIN_SUFFIX);

            File file = new File(skin_path);
            if (file.exists()) {
                loadPlugin(skin_path, skin_package);
            }

        } catch (Exception e) {
            e.printStackTrace();
            CaiDaoSharedPreferences.Companion.get(mContext).remove(SP_KEY_SKIN_PATH);
            CaiDaoSharedPreferences.Companion.get(mContext).remove(SP_KEY_SKIN_PACKAGE);
        }

    }

    private void loadPlugin(String skinPluginPath, String skinPluginPkg) {
        try {

            if (skinPluginPath.equals(mCurrentPath) && skinPluginPkg.equals(mCurrentPkg)) return;

            AssetManager assetManager = AssetManager.class.newInstance();
            Method method = assetManager.getClass().getMethod("addAssetPath", String.class);
            method.invoke(assetManager, skinPluginPath);

            Resources superResource = mContext.getResources();

            Resources resources = new Resources(assetManager, superResource.getDisplayMetrics(), superResource.getConfiguration());

            mResourcesManager = new ResourcesManager(resources, skinPluginPkg, null);

            mCurrentPath = skinPluginPath;
            mCurrentPkg = skinPluginPkg;


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ResourcesManager getResourcesManager() {

        if (!userPlugin()) {
            return new ResourcesManager(mContext.getResources(),
                    mContext.getPackageName(), mSuffix);
        }

        return mResourcesManager;
    }

    public List<SkinView> getSkinViews(SkinChangedListener listener) {
        return mSkinViewMaps.get(listener);
    }


    public void addSkinView(SkinChangedListener listener, List<SkinView> skinViews) {
        mSkinViewMaps.put(listener, skinViews);
    }

    public void registerListener(SkinChangedListener listener) {
        mListeners.add(listener);
    }

    public void unRegisterListener(SkinChangedListener listener) {
        mListeners.remove(listener);
        mSkinViewMaps.remove(listener);
    }

    /**
     * App内资源换肤
     *
     * @param suffix 资源后缀
     */
    public void changeSkin(String suffix) {
        cleanPluginInfo();

        mSuffix = suffix;
        CaiDaoSharedPreferences.Companion.get(mContext).put(SP_KEY_SKIN_SUFFIX, suffix);
        notifyChangedListener();
    }

    /**
     * 重置资源信息
     */
    private void cleanPluginInfo() {
        mCurrentPath = null;
        mCurrentPkg = null;
        mSuffix = null;

        CaiDaoSharedPreferences.Companion.get(mContext).remove(SP_KEY_SKIN_PATH);
        CaiDaoSharedPreferences.Companion.get(mContext).remove(SP_KEY_SKIN_PACKAGE);
    }

    /**
     * 插件式换肤
     *
     * @param skinPluginPath       插件资源apk存放路径
     * @param skinPluginPkg        插件资源apk包名
     * @param skinChangingListener 监听
     */
    public void changeSkin(String skinPluginPath, String skinPluginPkg, SkinChangingListener skinChangingListener) {
        if (null == skinChangingListener) {
            skinChangingListener = SkinChangingListener.DEFAULT_LISTENER;
        }

        final SkinChangingListener finalListener = skinChangingListener;

        finalListener.onStart();


        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            try {
                loadPlugin(skinPluginPath, skinPluginPkg);
            } catch (Exception e) {
                e.printStackTrace();
                finalListener.onError(e);
                emitter.onNext(-1);
                emitter.onComplete();
            }
            emitter.onNext(0);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer code) {
                        if (code == -1) {
                            finalListener.onError(new Exception("换肤异常"));
                            return;
                        }
                        try {
                            notifyChangedListener();
                            finalListener.onComplete();

                            updatePluginInfo(skinPluginPath, skinPluginPkg);

                        } catch (Exception e) {
                            e.printStackTrace();
                            finalListener.onError(e);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


//        new AsyncTask<Void, Void, Integer>() {
//
//            @Override
//            protected Integer doInBackground(Void... voids) {
//
//                try {
//                    loadPlugin(skinPluginPath, skinPluginPkg);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    finalListener.onError(e);
//                    return -1;
//                }
//
//                return 0;
//            }
//
//            @Override
//            protected void onPostExecute(Integer code) {
//                if (code == -1) {
//                    finalListener.onError(new Exception("换肤异常"));
//                    return;
//                }
//                try {
//                    notifyChangedListener();
//                    finalListener.onComplete();
//
//                    updatePluginInfo(skinPluginPath, skinPluginPkg);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    finalListener.onError(e);
//                }
//            }
//        }.execute();

    }

    private void notifyChangedListener() {
        for (SkinChangedListener skinChangedListener : mListeners) {
            skinChange(skinChangedListener);
            skinChangedListener.onSkinChanged();
        }
    }

    public void skinChange(SkinChangedListener skinChangedListener) {
        List<SkinView> skinViews = mSkinViewMaps.get(skinChangedListener);
        for (SkinView view : skinViews) {
            view.apply();
        }
    }

    private void updatePluginInfo(String path, String pkg) {
        CaiDaoSharedPreferences.Companion.get(mContext).put(SP_KEY_SKIN_PATH, path);
        CaiDaoSharedPreferences.Companion.get(mContext).put(SP_KEY_SKIN_PACKAGE, pkg);
    }

    public boolean needChangeSkin() {
        return userPlugin() || !isTrimEmpty(mSuffix);
    }

    private boolean userPlugin() {
        return !isTrimEmpty(mCurrentPath);
    }

    /**
     * 判断字符串是否为 null 或全为空格
     *
     * @param s 待校验字符串
     * @return {@code true}: null 或全空格<br> {@code false}: 不为 null 且不全空格
     */
    private boolean isTrimEmpty(final String s) {
        return (s == null || s.trim().length() == 0);
    }

}
