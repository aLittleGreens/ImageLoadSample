package com.ifreecomm.imageload.imageload.request;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.ifreecomm.imageload.imageload.ImageLoader;
import com.ifreecomm.imageload.imageload.config.DisplayConfig;
import com.ifreecomm.imageload.imageload.policy.LoadPolicy;
import com.ifreecomm.imageload.utils.ImageViewHelper;
import com.ifreecomm.imageload.utils.MD5Util;

import java.lang.ref.WeakReference;

/**
 * Created by IT小蔡 on 2018-9-6.
 */

public class BitmapRequest implements Comparable<BitmapRequest> {

    public String imgUrl;
    public DisplayConfig displayConfig;
    private final WeakReference<ImageView> mImageViewRef;
    public int serialNum;
    public boolean isCancel;
    public ImageLoader.ImageListener imageListener;
    public Bitmap.Config mBitmapConfig = Bitmap.Config.ARGB_8888;
    public boolean justCacheInMem;
    public String md5Imgurl;
    /**
     * 加载策略
     */
    LoadPolicy mLoadPolicy = ImageLoader.getInstance().getImageLoadConfig().getLoadPolicy();

    public BitmapRequest(ImageView imageView, String imgUrl, DisplayConfig displayConfig, ImageLoader.ImageListener listener) {
        mImageViewRef = new WeakReference<>(imageView);
        this.imgUrl = imgUrl;
        this.md5Imgurl = MD5Util.hashKeyForDisk(imgUrl);
        this.displayConfig = displayConfig;
        imageView.setTag(imgUrl);
        this.imageListener = listener;
    }

    public ImageView getImageView() {
        return mImageViewRef.get();
    }


    /**
     * 判断imageview的tag与uri是否相等
     *
     * @return
     */
    public boolean isImageViewTagValid() {
        return mImageViewRef.get() != null ? mImageViewRef.get().getTag().equals(imgUrl) : false;
    }

    public int getImageViewWidth() {
        return ImageViewHelper.getImageViewWidth(getImageView());
    }

    public int getImageViewHeight() {
        return ImageViewHelper.getImageViewHeight(getImageView());
    }


    @Override
    public int compareTo(@NonNull BitmapRequest another) {
        return mLoadPolicy.compare(this, another);
    }
}
