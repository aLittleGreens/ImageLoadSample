package com.ifreecomm.imageload.imageload.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.ifreecomm.imageload.imageload.request.BitmapRequest;
import com.ifreecomm.imageload.utils.BitmapDecoder;

/**
 * Created by IT小蔡 on 2018-9-6.
 * "res://"+R.mipmap.ic_launcher_round
 */

public class ResLoader extends AbsLoader {
    private static final String TAG = "ResLoader";

    @Override
    protected Bitmap onLoadImage(final BitmapRequest request) {
        final int resId = Integer.parseInt(parseSchema(request.imgUrl));

        // 从sd卡中加载的图片仅缓存到内存中,不做本地缓存
        request.justCacheInMem = true;

        // 加载图片
        BitmapDecoder decoder = new BitmapDecoder() {

            @Override
            public Bitmap decodeBitmapWithOption(BitmapFactory.Options options) {
                return BitmapFactory.decodeResource(request.getImageView().getResources(), resId);
            }
        };

        if (request.getImageViewWidth() <= 0 || request.getImageViewHeight() <= 0) {
            Log.e(TAG,"000");
            return BitmapFactory.decodeResource(request.getImageView().getResources(), resId);
        } else {
            return decoder.decodeBitmap(request.getImageViewWidth(),
                    request.getImageViewHeight(), request.mBitmapConfig);
        }

    }

    private String parseSchema(String uri) {
        if (uri.contains("://")) {
            return uri.split("://")[1];
        } else {
            Log.e(TAG, "### wrong scheme, image uri is : " + uri);
        }

        return "";
    }

}
