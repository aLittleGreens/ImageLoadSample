/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2015 bboyfeiyu@gmail.com ( Mr.Simple )
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.ifreecomm.imageload.imageload.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;

import com.ifreecomm.imageload.imageload.request.BitmapRequest;
import com.ifreecomm.imageload.utils.BitmapDecoder;

import java.io.File;

/**
 * schema:// + 图片路径
 *  file://sdcard/xxx/image.jpg
 *  drawable://image
 *  http://xxx.image.jpg
 */
public class LocalLoader extends AbsLoader {

    @Override
    public Bitmap onLoadImage(BitmapRequest request) {
        final String imagePath = Uri.parse(request.imgUrl).getPath();
        final File imgFile = new File(imagePath);
        if (!imgFile.exists()) {
            return null;
        }

        // 从sd卡中加载的图片仅缓存到内存中,不做本地缓存
        request.justCacheInMem = true;

        // 加载图片
        BitmapDecoder decoder = new BitmapDecoder() {

            @Override
            public Bitmap decodeBitmapWithOption(Options options) {
                return BitmapFactory.decodeFile(imagePath, options);
            }
        };

        if (request.getImageViewWidth() <= 0 || request.getImageViewHeight() <= 0) {
            return BitmapFactory.decodeFile(imagePath);
        } else {
            return decoder.decodeBitmap(request.getImageViewWidth(),
                    request.getImageViewHeight(),request.mBitmapConfig);
        }
    }

}
