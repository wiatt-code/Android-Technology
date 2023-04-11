package com.wiatt.arouter;

import android.app.Application;

import com.alibaba.android.arouter.BuildConfig;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wiatt.engine.EngineApplication;

public class BaseApplication extends EngineApplication {

    private static BaseApplication mBaseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mBaseApplication = this;
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }
    public static BaseApplication getApplication(){
        System.out.println("mMyApplication");
        if (mBaseApplication == null) {
            System.out.println("mMyApplication == null");
        }
        return mBaseApplication;
    }
}
