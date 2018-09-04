package com.ifreecomm.imageloadsample.imageload;

import android.graphics.Bitmap;

/**
 * Created by IT小蔡 on 2018-9-4.
 */

public interface ImageCache {

    Bitmap get(String imgUrl);

    void put(String imgUrl, Bitmap bitmap);
}
