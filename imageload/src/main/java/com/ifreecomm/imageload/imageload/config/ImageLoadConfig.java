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
    private ImageCache bitmapCache = ImageMemoryCache.getInstance();
    /**
     * 默认图配置
     */
    private DisplayConfig displayConfig = new DisplayConfig();
    /**
     * 加载顺序
     */
    private LoadPolicy loadPolicy = new SerialPolicy();

    private int threadCount = Runtime.getRuntime().availableProcessors() + 1;

    private ImageLoadConfig() {
    }

    public ImageCache getBitmapCache() {
        return bitmapCache;
    }

    public DisplayConfig getDisplayConfig() {
        return displayConfig;
    }

    public LoadPolicy getLoadPolicy() {
        return loadPolicy;
    }

    public void setLoadPolicy(LoadPolicy loadPolicy) {
        this.loadPolicy = loadPolicy;
    }

    public void setBitmapCache(ImageCache bitmapCache) {
        this.bitmapCache = bitmapCache;
    }

    public void setDisplayConfig(DisplayConfig displayConfig) {
        this.displayConfig = displayConfig;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public static class Builder {
        /**
         * 缓存策略
         */
        private ImageCache bitmapCache = ImageMemoryCache.getInstance();
        /**
         * 默认图配置
         */
        private DisplayConfig displayConfig = new DisplayConfig();
        /**
         * 加载顺序
         */
        private LoadPolicy loadPolicy = new SerialPolicy();

        private int threadCount = Runtime.getRuntime().availableProcessors() + 1;

        /**
         * @param count
         * @return
         */
        public Builder setThreadCount(int count) {
            threadCount = Math.max(1, count);
            return this;
        }

        public Builder setCache(ImageCache cache) {
            bitmapCache = cache;
            return this;
        }

        public Builder setLoadingPlaceholder(int resId) {
            displayConfig.loadingResId = resId;
            return this;
        }

        public Builder setNotFoundPlaceholder(int resId) {
            displayConfig.failedResId = resId;
            return this;
        }

        public Builder setLoadPolicy(LoadPolicy loadPolicy) {
            if (loadPolicy != null) {
                this.loadPolicy = loadPolicy;
            }
            return this;
        }

        public ImageLoadConfig create() {
            ImageLoadConfig imageLoadConfig = new ImageLoadConfig();
            imageLoadConfig.bitmapCache = bitmapCache;
            imageLoadConfig.displayConfig = displayConfig;
            imageLoadConfig.threadCount = threadCount;
            imageLoadConfig.loadPolicy = loadPolicy;
            return imageLoadConfig;
        }

    }

}
