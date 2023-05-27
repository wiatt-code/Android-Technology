package com.wiatt.manuscript;

import com.wiatt.arouter.BaseApplication;

import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.AutoSizeConfig;

public class AppApplication extends BaseApplication {

    private static AppApplication mAppApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppApplication = this;
        AutoSize.checkAndInit(this);
        AutoSizeConfig.getInstance()
                .setCustomFragment(true)
                .setBaseOnWidth(true)
                .setUseDeviceSize(false);
        AutoSizeConfig.getInstance().getUnitsManager()
                .setSupportDP(true)
                .setSupportSP(true);
    }

    public static AppApplication getApplication(){
        return mAppApplication;
    }
}
