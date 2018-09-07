package com.ifreecomm.imageload.imageload.cache;

import android.graphics.Bitmap;

import com.ifreecomm.imageload.imageload.request.BitmapRequest;

/**
 * Created by IT小蔡 on 2018-9-4.
 */

public interface ImageCache {

    Bitmap get(BitmapRequest bitmapRequest);

    void put(BitmapRequest bitmapRequest, Bitmap bitmap);

    void remove(BitmapRequest bitmapRequest);
}
