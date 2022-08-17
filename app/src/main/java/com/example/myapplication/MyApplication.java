package com.example.myapplication;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

public class MyApplication extends Application {

    public static Context applicationContext;

    private static MyApplication myApplication;// 声明当前应用的静态实例

    // 利用单例模式获取当前应用的唯一实例
    public static MyApplication getInstance(){
        return myApplication;
    }

    // 在App启动时调用
    @Override
    public void onCreate() {
        super.onCreate();

        applicationContext = getApplicationContext();
        myApplication = this;

        boolean useFresco = true;

//        IImageFetcher.InitConfig config = new IImageFetcher.InitConfig();
//        config.setBmpConfig(Bitmap.Config.ARGB_8888);
//        ImageLoader.init(applicationContext,
//                useFresco ? ImageLoader.Type.FRESCO : ImageLoader.Type.GLIDE, config);


//        Fresco.initialize(applicationContext);
    }


//    在App退出时调用（按字面意思）
    // 现在很明确了，onTerminate方法就是个摆设，中看不中用。如果想在App退出前做资源回收操作，那么千万不要放在onTerminate方法中
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    // 在低内存时调用
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    // 在配置改变时调用，例如从竖屏变为横屏
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
