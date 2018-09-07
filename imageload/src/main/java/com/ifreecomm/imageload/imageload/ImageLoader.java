package com.ifreecomm.imageload.imageload;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.ifreecomm.imageload.imageload.config.DisplayConfig;
import com.ifreecomm.imageload.imageload.config.ImageLoadConfig;
import com.ifreecomm.imageload.imageload.policy.SerialPolicy;
import com.ifreecomm.imageload.imageload.queue.RequestQueue;
import com.ifreecomm.imageload.imageload.request.BitmapRequest;

/**
 * Created by IT小蔡 on 2018-9-6.
 */

public class ImageLoader {

    private static ImageLoader instance = null;
    private ImageLoadConfig imageLoadConfig;
    private static int loadingResId;
    private static int failedResId;
    private RequestQueue mRequestQueue;

    private ImageLoader() {
    }

    public static ImageLoader getInstance() {
        if (instance == null) {
            synchronized (ImageLoader.class) {
                if (instance == null) {
                    instance = new ImageLoader();
                }
            }
        }
        reBack();
        return instance;
    }

    private static void reBack() {
        loadingResId = -1;
        failedResId = -1;
    }

    public void init(ImageLoadConfig imageLoadConfig) {
        this.imageLoadConfig = imageLoadConfig;
        checkConfig();
        this.mRequestQueue = new RequestQueue(imageLoadConfig.getThreadCount());
        mRequestQueue.start();
    }

    private void checkConfig() {
        if (imageLoadConfig == null) {
            throw new RuntimeException(
                    "The config of ImageLoader is Null, please call the init(ImageLoaderConfig config) method to initialize");
        }
        if (mRequestQueue != null) {
            throw new RuntimeException(
                    "you already called the init(ImageLoaderConfig config)");
        }

        if (imageLoadConfig.getLoadPolicy() == null) {
            imageLoadConfig.setLoadPolicy(new SerialPolicy());
        }

    }

    public ImageLoadConfig getImageLoadConfig() {
        return imageLoadConfig;
    }

    public void displayImage(ImageView imageView, String url) {
        displayImage(imageView, url, null);
    }

    public void displayImage(ImageView imageView, String url, ImageListener listener) {

        DisplayConfig displayConfig = null;
        if (loadingResId != -1) {
            displayConfig = displayConfig == null ? new DisplayConfig() : displayConfig;
            displayConfig.loadingResId = loadingResId;
        }
        if (failedResId != -1) {
            displayConfig = displayConfig == null ? new DisplayConfig() : displayConfig;
            displayConfig.failedResId = failedResId;
        }
        BitmapRequest request = new BitmapRequest(imageView, url, displayConfig, listener);

        // 加载的配置对象,如果没有设置则使用ImageLoader的配置
        request.displayConfig = request.displayConfig != null ? request.displayConfig
                : imageLoadConfig.getDisplayConfig();
        // 添加对队列中
        mRequestQueue.addRequest(request);
    }

    public ImageLoader setLoadingRedId(int loadingResId) {
        this.loadingResId = loadingResId;
        return this;
    }

    public ImageLoader setErrorResId(int failedResId) {
        this.failedResId = failedResId;
        return this;
    }

    /**
     * 图片加载Listener
     *
     * @author mrsimple
     */
    public static interface ImageListener {
        void onComplete(ImageView imageView, Bitmap bitmap, String uri);
    }

}
