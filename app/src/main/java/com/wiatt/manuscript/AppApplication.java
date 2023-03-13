package com.wiatt.manuscript;

import com.wiatt.arouter.BaseApplication;

public class AppApplication extends BaseApplication {

    private static AppApplication mAppApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppApplication = this;
    }

    public static AppApplication getApplication(){
        return mAppApplication;
    }
}
