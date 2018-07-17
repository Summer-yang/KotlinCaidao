package com.summer.caidao.skin;

/**
 * Created by Summer on 2018/2/2.
 * <p>
 * 换肤过程中监听
 */
public interface SkinChangingListener {
    void onStart();

    void onError(Exception e);

    void onComplete();

    DefaultSkinChangingListener DEFAULT_LISTENER = new DefaultSkinChangingListener();

    class DefaultSkinChangingListener implements SkinChangingListener {

        @Override
        public void onStart() {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onComplete() {

        }
    }
}
