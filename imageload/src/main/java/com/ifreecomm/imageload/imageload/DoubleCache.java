package com.ifreecomm.imageload.imageload;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by IT小蔡 on 2018-9-3.
 * 双缓存 先从内存中获取，获取不到再从磁盘获取
 */

public class DoubleCache implements ImageCache {

    private final ImageDiskLruCache imageDiskLruCache;
    private final ImageMemoryCache imageCache;

    private static DoubleCache instance = null;

    private DoubleCache(Context context) {
        imageDiskLruCache = ImageDiskLruCache.getInstance(context);
        imageCache = ImageMemoryCache.getInstance();
    }

    public static DoubleCache getInstance(Context context) {
        if (instance == null) {
            synchronized (DoubleCache.class) {
                if (instance == null) {
                    instance = new DoubleCache(context);
                }
            }
        }
        return instance;
    }

    @Override
    public Bitmap get(String imgUrl) {
        Bitmap bitmap = imageCache.get(imgUrl); // 内存缓存
        if (bitmap == null) {
            bitmap = imageDiskLruCache.get(imgUrl); // 磁盘缓存
            if (bitmap != null) {
                imageCache.put(imgUrl, bitmap); //如果在磁盘找到，缓存一份到内存
            }
        }
        return bitmap;
    }

    @Override
    public void put(String imgUrl, Bitmap bitmap) {
        imageDiskLruCache.put(imgUrl, bitmap);
        imageCache.put(imgUrl, bitmap);
    }

}
