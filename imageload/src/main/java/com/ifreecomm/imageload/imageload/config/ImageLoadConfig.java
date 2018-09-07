package com.ifreecomm.imageload.imageload.config;

import com.ifreecomm.imageload.imageload.cache.ImageCache;
import com.ifreecomm.imageload.imageload.cache.ImageMemoryCache;
import com.ifreecomm.imageload.imageload.policy.LoadPolicy;
import com.ifreecomm.imageload.imageload.policy.SerialPolicy;

/**
 * Created by IT小蔡 on 2018-9-6.
 */

public class ImageLoadConfig {
    /**
     * 缓存策略
     */
    public ImageCache bitmapCache = ImageMemoryCache.getInstance();
    /**
     * 默认图配置
     */
    public DisplayConfig displayConfig = new DisplayConfig();
    /**
     * 加载顺序
     */
    public LoadPolicy loadPolicy = new SerialPolicy();

    public int threadCount = Runtime.getRuntime().availableProcessors() + 1;


    public DisplayConfig getDisplayConfig() {
        return displayConfig;
    }


    /**
     * @param count
     * @return
     */
    public ImageLoadConfig setThreadCount(int count) {
        threadCount = Math.max(1, count);
        return this;
    }

    public ImageLoadConfig setCache(ImageCache cache) {
        bitmapCache = cache;
        return this;
    }

    public ImageLoadConfig setLoadingPlaceholder(int resId) {
        displayConfig.loadingResId = resId;
        return this;
    }

    public ImageLoadConfig setNotFoundPlaceholder(int resId) {
        displayConfig.failedResId = resId;
        return this;
    }

    public LoadPolicy getLoadPolicy() {
        return loadPolicy;
    }

    public ImageLoadConfig setLoadPolicy(LoadPolicy loadPolicy) {
        if (loadPolicy != null) {
            this.loadPolicy = loadPolicy;
        }
        return this;
    }

}
