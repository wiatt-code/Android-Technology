package com.wiatt.manuscript.pluginable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wiatt.manuscript.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

public class reflectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflection);
        System.out.println("1111, from app!!!");
        ARouter.getInstance().build("/plugin/MainActivity").navigation();

        File apk = new File(getCacheDir() + "/plugin.apk");
        try (Source source = Okio.source(getAssets().open("apk/plugin-debug.apk"));
             BufferedSink sink = Okio.buffer(Okio.sink(apk))){
            sink.writeAll(source);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        DexClassLoader classLoader = new DexClassLoader(apk.getPath(),
                getCacheDir().getPath(), null, null);
        try {
            Class utilsClass = classLoader.loadClass("com.wiatt.plugin.utils.Utils");
            Constructor utilsConstructor = utilsClass.getDeclaredConstructors()[0];
            utilsConstructor.setAccessible(true);
            Object utils= utilsConstructor.newInstance();
            Method shoutMethod = utilsClass.getDeclaredMethod("shout");
            shoutMethod.setAccessible(true);
            shoutMethod.invoke(utils);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}