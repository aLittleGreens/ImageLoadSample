package com.ifreecomm.imageload.imageload.cache;

import android.content.Context;
import android.graphics.Bitmap;

import com.ifreecomm.imageload.imageload.request.BitmapRequest;

/**
 * Created by IT小蔡 on 2018-9-3.
 * 双缓存 先从内存中获取，获取不到再从磁盘获取
 */

public class DoubleCache implements ImageCache {

    private final ImageDiskLruCache mImageDiskLruCache;
    private final ImageMemoryCache mImageMemoryCache;

    private static DoubleCache instance = null;

    private DoubleCache(Context context) {
        mImageDiskLruCache = ImageDiskLruCache.getInstance(context);
        mImageMemoryCache = ImageMemoryCache.getInstance();
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
    public Bitmap get(BitmapRequest bitmapRequest) {
        Bitmap bitmap = mImageMemoryCache.get(bitmapRequest); // 内存缓存
        if (bitmap == null) {
            bitmap = mImageDiskLruCache.get(bitmapRequest); // 磁盘缓存
            if (bitmap != null) {
                mImageMemoryCache.put(bitmapRequest, bitmap); //如果在磁盘找到，缓存一份到内存
            }
        }
        return bitmap;
    }

    @Override
    public void put(BitmapRequest bitmapRequest, Bitmap bitmap) {
        mImageDiskLruCache.put(bitmapRequest, bitmap);
        mImageMemoryCache.put(bitmapRequest, bitmap);
    }

    @Override
    public void remove(BitmapRequest bitmapRequest) {
        mImageDiskLruCache.remove(bitmapRequest);
        mImageMemoryCache.remove(bitmapRequest);
    }

}
