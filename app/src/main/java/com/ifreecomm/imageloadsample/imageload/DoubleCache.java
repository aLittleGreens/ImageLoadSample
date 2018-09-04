package com.ifreecomm.imageloadsample.imageload;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by IT小蔡 on 2018-9-3.
 * 双缓存 先从内存中获取，获取不到再从磁盘获取
 */

public class DoubleCache {

    private final ImageDiskLruCache imageDiskLruCache;
    private final ImageMemoryCache imageCache;

    public DoubleCache(Context context) {
        imageDiskLruCache = new ImageDiskLruCache(context);
        imageCache = new ImageMemoryCache();
    }

    public Bitmap get(String imgUrl) {
        Bitmap bitmap = imageCache.get(imgUrl); // 内存缓存
        if (bitmap == null) {
            bitmap = imageDiskLruCache.get(imgUrl); // 磁盘缓存
        }
        return bitmap;
    }

    public void put(String imgUrl, Bitmap bitmap) {
        imageDiskLruCache.put(imgUrl, bitmap);
        imageCache.put(imgUrl, bitmap);
    }

}
