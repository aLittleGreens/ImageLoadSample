package com.ifreecomm.imageloadsample;

import android.app.Application;

import com.ifreecomm.imageload.imageload.ImageLoader;
import com.ifreecomm.imageload.imageload.cache.DoubleCache;
import com.ifreecomm.imageload.imageload.config.ImageLoadConfig;
import com.ifreecomm.imageload.imageload.policy.SerialPolicy;

/**
 * Created by IT小蔡 on 2018-9-5.
 */

public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoadConfig imageLoadConfig = new ImageLoadConfig().
                setLoadPolicy(new SerialPolicy()).
                setCache(DoubleCache.getInstance(this)).
                setLoadingPlaceholder(R.drawable.loading).
                setNotFoundPlaceholder(R.drawable.not_found).
                setThreadCount(5);
        ImageLoader.getInstance().init(imageLoadConfig);
    }
}
