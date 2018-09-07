package com.ifreecomm.imageload.imageload.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ifreecomm.imageload.imageload.request.BitmapRequest;
import com.ifreecomm.imageload.utils.BitmapDecoder;
import com.ifreecomm.imageload.utils.IOUtil;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by IT小蔡 on 2018-9-6.
 */

public class UrlLoader extends AbsLoader {
    private static final String TAG = "UrlLoader";

    @Override
    protected Bitmap onLoadImage(BitmapRequest bitmapRequest) {
        final String imageUrl = bitmapRequest.imgUrl;
        FileOutputStream fos = null;
        InputStream is = null;
        try {
            URL url = new URL(imageUrl);
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(conn.getInputStream());
            is.mark(is.available());//在该输入流中标记当前位置。 后续调用 reset 方法重新将流定位于最后标记位置

            final InputStream inputStream = is;
            BitmapDecoder bitmapDecoder = new BitmapDecoder() {

                @Override
                public Bitmap decodeBitmapWithOption(BitmapFactory.Options options) {
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
                    //
                    if (options.inJustDecodeBounds) {
                        try {
                            inputStream.reset();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        // 关闭流
                        conn.disconnect();
                    }
                    return bitmap;
                }
            };
            if (bitmapRequest.getImageViewWidth() <= 0 || bitmapRequest.getImageViewHeight() <= 0) {
                return BitmapFactory.decodeStream(inputStream);
            } else {
                return bitmapDecoder.decodeBitmap(bitmapRequest.getImageViewWidth(),
                        bitmapRequest.getImageViewHeight(), bitmapRequest.mBitmapConfig);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtil.closeQuietly(is);
            IOUtil.closeQuietly(fos);
        }

        return null;
    }

    private Bitmap decodeSampleSize(InputStream inputStream, BitmapRequest bitmapRequest) throws IOException {
        BufferedInputStream is = new BufferedInputStream(inputStream);
        is.mark(is.available());
        // decode image size
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        inputStream.mark(inputStream.available());//在该输入流中标记当前位置。 后续调用 reset 方法重新将流定位于最后标记位置
        BitmapFactory.decodeStream(is, null, options);
        is.reset();
        // 计算合理的缩放指数（2的整幂数）
        int width_tmp = options.outWidth, height_tmp = options.outHeight;
        int scale = 1;

        while (true) {
            if (width_tmp <= bitmapRequest.getImageViewWidth() || height_tmp <= bitmapRequest.getImageViewHeight()) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }
        // decode with inSampleSize
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        if (bitmapRequest.mBitmapConfig == null) {
            bitmapRequest.mBitmapConfig = Bitmap.Config.ARGB_8888;
        }
        options.inPreferredConfig = bitmapRequest.mBitmapConfig;
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
        is.close();
        return bitmap;
    }

}
