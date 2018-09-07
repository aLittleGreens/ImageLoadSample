package com.ifreecomm.imageloadsample;

import android.app.Application;

import com.ifreecomm.imageload.imageload.ImageLoader;
import com.ifreecomm.imageload.imageload.cache.DoubleCache;
import com.ifreecomm.imageload.imageload.config.ImageLoadConfig;
import com.ifreecomm.imageload.imageload.policy.ReversePolicy;

/**
 * Created by IT小蔡 on 2018-9-5.
 */

public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoadConfig imageLoadConfig = new ImageLoadConfig.Builder().
                setLoadPolicy(new ReversePolicy()).    //设置队列顺序，默认是顺序
                setCache(DoubleCache.getInstance(this)).    //设置缓存策略，默认内存缓存
                setLoadingPlaceholder(R.drawable.loading).
                setNotFoundPlaceholder(R.drawable.not_found).
                setThreadCount(5).  //设置线程数量，默认cpu核数+1
                create();
        ImageLoader.getInstance().init(imageLoadConfig);
    }
}
