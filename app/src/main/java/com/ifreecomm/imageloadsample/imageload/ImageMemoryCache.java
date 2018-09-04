package com.ifreecomm.imageloadsample.imageload;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import com.ifreecomm.imageloadsample.utils.MD5Util;

/**
 * Created by IT小蔡 on 2018-9-3.
 * 处理图片缓存
 */

public class ImageMemoryCache implements ImageCache {

    private static final String TAG = "ImageMemoryCache";
    private LruCache<String, Bitmap> mImageCache;

    private static ImageMemoryCache instance = null;

    private ImageMemoryCache() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);    //计算可用的最大内存
        Log.e(TAG, "maxMemory:" + maxMemory);
        // 取1/4内存作为缓存
        int cacheSize = maxMemory / 4;
        //计算每张图片的大小
        mImageCache = new LruCache<String, Bitmap>(cacheSize) {

            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                int bitmapSize = bitmap.getByteCount() / 1024;
                Log.e(TAG, "bitmapSize:" + bitmapSize);
                return bitmapSize;//计算每张图片的大小
            }
        };
    }

    public static ImageMemoryCache getInstance() {
        if (instance == null) {
            synchronized (ImageMemoryCache.class) {
                if (instance == null) {
                    instance = new ImageMemoryCache();
                }
            }
        }
        return instance;
    }

    @Override
    public Bitmap get(String imgUrl) {
        String key = MD5Util.hashKeyForDisk(imgUrl);
        Bitmap bitmap = mImageCache.get(key);
        if(bitmap != null){
            Log.e(TAG,"内存中加载");
        }
        return bitmap;
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        String key = MD5Util.hashKeyForDisk(url);
        mImageCache.put(key, bitmap);
    }

}
