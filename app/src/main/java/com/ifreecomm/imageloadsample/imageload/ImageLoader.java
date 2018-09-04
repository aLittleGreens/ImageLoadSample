package com.ifreecomm.imageloadsample.imageload;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by IT小蔡 on 2018-9-3.
 */

public class ImageLoader {
    private static final String TAG = "ImageLoader";
    private static ImageLoader instance = null;
    private Handler mUiHandler = new Handler(Looper.getMainLooper());
    private static Context mContext;
    //线程池，线程数量为CPU的数量
    ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    //    private final ImageMemoryCache mImageCache;
//    private final ImageDiskLruCache mImageDiskLruCache;
    private static int defaultlayoutId;
    private static int errorlayoutId;
    //    private final DoubleCache doubleCache;
//    private static boolean isUseDiskCache; //是否用磁盘缓存，默认是双缓存
    private ImageCache imageCache;

    public static void init(Context context) {
        mContext = context;
    }

    public static ImageLoader getInstance() {
        if (mContext == null) {
            throw new IllegalStateException("you must init ImageLoad by call init()");
        }
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
        defaultlayoutId = 0;
        errorlayoutId = 0;
    }

    private ImageLoader() {
//        doubleCache = new DoubleCache(mContext);
////        mImageCache = new ImageMemoryCache();
//        mImageDiskLruCache = new ImageDiskLruCache(mContext);
    }

    public ImageLoader setImageCache(ImageCache imageCache) {
        this.imageCache = imageCache;
        return this;
    }

    public void displayImage(final String url, final ImageView imageView) {
        Bitmap bitmap = null;
        if (imageCache == null) {
            Log.e(TAG, "imageCache == null");
            imageCache = DoubleCache.getInstance(mContext);
        }
        bitmap = imageCache.get(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        } else {
            if (defaultlayoutId != 0) {
                imageView.setImageResource(defaultlayoutId);
            }
            imageView.setTag(url);
            mExecutorService.submit(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = downloadBitmap(url);
                    if (bitmap == null) {
                        if (errorlayoutId != 0) {
                            updateImageView(imageView, errorlayoutId);
                        }
                        return;
                    }
                    if (imageView.getTag().equals(url)) {
                        updateImageView(imageView, bitmap);
                    }

                }
            });
        }
        return;
    }

    public ImageLoader setDefaultImg(int defaultlayoutId) {
        this.defaultlayoutId = defaultlayoutId;
        return this;
    }

    public ImageLoader setErrorImg(int errorlayoutId) {
        this.errorlayoutId = errorlayoutId;
        return this;
    }

//    public ImageLoader setUseDiskCache(boolean isUseDiskCache) {
//        this.isUseDiskCache = isUseDiskCache;
//        return this;
//    }

    public Bitmap downloadBitmap(String imageUrl) {

        Bitmap bitmap = null;
        try {
            Log.e(TAG, "网络请求加载");
            URL url = new URL(imageUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            imageCache.put(imageUrl, bitmap);//存入内存
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }


    private void updateImageView(final ImageView imageView, final Bitmap bitmap) {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bitmap);
            }
        });

    }

    private void updateImageView(final ImageView imageView, final int layoutId) {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                imageView.setImageResource(layoutId);
            }
        });

    }


}
