package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.util.LruCache;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * technology study practice
 */
public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    private Handler mHandler = new Handler();// 声明一个处理器对象
    private int mCount = 0;// 计数值
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mCount++;
            System.out.println(":> 当前计数值为：" + mCount);
            mHandler.postDelayed(this, 1000);
        }
    };

    private WeakHandler weakHandler = new WeakHandler(this);

    @Override
    public void onClick(View v) {
        String language = ((Button)v).getText().toString();
        lruCache.put(language, String.valueOf(System.currentTimeMillis()));

        String desc = "";
        // 读取 LRU 缓存在当前时刻下的快照映射
        Map<String, String> cache = lruCache.snapshot();
        for (Map.Entry<String, String> item : cache.entrySet()){
            desc = String.format("%s%s 最后一次更新时间为 %s\n", desc, item.getKey(), item.getValue());
        }
        tv_info.setText(desc);
    }

    private static class WeakHandler extends Handler{

        private static WeakReference<Main2Activity> mActivity;
        public WeakHandler(Main2Activity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mActivity.get() != null){

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // 从资源库里的图片文件获取图形对象
//        Drawable drawable = getResources().getDrawable(R.drawable.ic_launcher);

//        getPath();
//        getPubPriPath();

        // 自定义动画
//        counter();

//        reqSms();

//        lurDemo();

//        demo_canonical();

    }

    private void demo_canonical() {
        System.out.println(":> value = " + getPackageName() + ", " + getClass().getCanonicalName());
    }

    private LruCache<String, String> lruCache; // 声明一个最近最少使用算法的对象
    private TextView tv_info;
    private void lurDemo() {
        // 创建一个大小为 5 的 LRU 缓存
        lruCache = new LruCache<>(5);

        tv_info = findViewById(R.id.tv_info);

        findViewById(R.id.btn_java).setOnClickListener(this);
        findViewById(R.id.btn_android).setOnClickListener(this);
        findViewById(R.id.btn_c).setOnClickListener(this);

    }

    private void reqSms() {
        String[] pre = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
//        int hasPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
//        System.out.println(":> hasP: " + hasPermission);

        ActivityCompat.requestPermissions(this, pre, 0x10);
    }

    /**
     * 计数器
     */
    private void counter() {
        if (mHandler != null){
            mHandler.post(runnable);
        }
    }

    // 获取外部存储的公共空间和私有空间的路径
    private void getPubPriPath() {
        String publicPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .toString();
        // /storage/emulated/0/Download
        System.out.println(":) 系统的公共存储路径：" + publicPath);

        // 获取当前 app 的私有存储路径
        String privatePath = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString();
        // /storage/emulated/0/Android/data/com.example.myapplication/files/Download
        System.out.println(":) 当前 App 的私有存储路径位于：" + privatePath);

    }

    private void getPath() {
        File rootDic = Environment.getRootDirectory();
        // /system
        System.out.println(":) rootDic getAbsolutePath: " + rootDic.getAbsolutePath());

        File dataDic = Environment.getDataDirectory();
        // /data
        System.out.println(":) dataDic getAbsolutePath: " + dataDic.getAbsolutePath());

        File cacheDic = Environment.getDownloadCacheDirectory();
        // /data/cache
        System.out.println(":) cacheDic getAbsolutePath: " + cacheDic.getAbsolutePath());

        File externalStorageDic = Environment.getExternalStorageDirectory();
        // /storage/emulated/0
        System.out.println(":) externalStorageDic getAbsolutePath: " + externalStorageDic.getAbsolutePath());

        String externalStorageState = Environment.getExternalStorageState();
        System.out.println(":) externalStorageState: " + externalStorageState);

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event);
    }
}
