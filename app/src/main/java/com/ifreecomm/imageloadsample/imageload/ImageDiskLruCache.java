package com.ifreecomm.imageloadsample.imageload;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.ifreecomm.imageloadsample.disklrucache.DiskLruCache;
import com.ifreecomm.imageloadsample.utils.MD5Util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Created by IT小蔡 on 2018-9-3.
 * 控制硬盘缓存
 */

public class ImageDiskLruCache implements ImageCache{
    private static final String TAG = "ImageDiskLruCache";
    private DiskLruCache mDiskLruCache;
    private int mDiskCacheSize = 100 * 1024 * 1024;  //kb

    private static ImageDiskLruCache instance = null;

    private ImageDiskLruCache(Context context) {
        File cacheDir = getDiskCacheDir(context, "image");
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }

        /**
         *  第一个参数指定的是数据的缓存地址，
         *  第二个参数指定当前应用程序的版本号，
         *  第三个参数指定同一个key可以对应多少个缓存文件，基本都是传1，
         *  第四个参数指定最多可以缓存多少字节的数据
         */
        try {
            mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(context), 1, mDiskCacheSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ImageDiskLruCache getInstance(Context context) {
        if (instance == null) {
            synchronized (ImageDiskLruCache.class) {
                if (instance == null) {
                    instance = new ImageDiskLruCache(context);
                }
            }
        }
        return instance;
    }


    /**
     * 1、首先判断外部缓存如果这个手机没有SD卡，或者SD正好被移除了的情况，则缓存目录=context.getCacheDir().getPath()，即存到 /data/data/package_name/cache 这个文件系统目录下；
     * 2、反之缓存目录=context.getExternalCacheDir().getPath()，即存到 /storage/emulated/0/Android/data/package_name/cache 这个外部存储目录中，
     * PS：外部存储可以分为两种：一种如上面这种路径 (/storage/emulated/0/Android/data/package_name/cache)， 当应用卸载后，存储数据也会被删除，
     * 另外一种是永久存储，即使应用被卸载，存储的数据依然存在，存储路径如：/storage/emulated/0/mDiskCache，可以通过Environment.getExternalStorageDirectory().getAbsolutePath() + "/mDiskCache" 来获得路径。
     */
    private File getDiskCacheDir(Context context, String uniqueName) {
        final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()
                ? context.getExternalCacheDir().getPath()
                : context.getCacheDir().getPath();
        return new File(cachePath + File.separator + uniqueName);
    }

    @Override
    public void put(String address, Bitmap bitmap) {
        if (address == null || bitmap == null) {
            return;
        }
        OutputStream outputStream = null;
        if (mDiskLruCache != null) {
            String key = MD5Util.hashKeyForDisk(address);
            try {
                //通过key值来获得一个Snapshot，如果Snapshot存在，则移动到LRU队列的头部来，
                // 通过Snapshot可以得到一个输入流InputStream
                DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
                if (snapshot == null) {
                    //通过key可以获得一个DiskLruCache.Editor，通过Editor可以得到一个输出流，进而缓存到本地存储上
                    DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                    if (editor != null) {
                        outputStream = editor.newOutputStream(0);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                        editor.commit();
                        outputStream.close();
                        mDiskLruCache.flush();//	强制缓冲文件保存到文件系统
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (outputStream != null) {
                        outputStream.close();
                        outputStream = null;
                    }
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    public Bitmap get(String imageUrl) {
        Bitmap bitmap = null;
        String key = MD5Util.hashKeyForDisk(imageUrl);
        try {
            DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);
            if (snapShot != null) {
                InputStream inputStream = snapShot.getInputStream(0);
                bitmap = BitmapFactory.decodeStream(inputStream);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        if(bitmap != null){
            Log.e(TAG,"磁盘中加载");
        }
        return bitmap;

    }

    public boolean remove(String imageUrl) {
        String key = MD5Util.hashKeyForDisk(imageUrl);
        try {
            return mDiskLruCache.remove(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 每当版本号改变，缓存路径下存储的所有数据都会被清除掉
     *
     * @param context
     * @return 获取应用的版本号
     */
    public int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;

    }

}
