package com.ifreecomm.imageloadsample;

import android.app.Application;

import com.ifreecomm.imageload.imageload.ImageLoader;

/**
 * Created by IT小蔡 on 2018-9-5.
 */

public class BaseApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoader.init(this);
    }
}
