package com.ifreecomm.imageload.imageload.policy;

import com.ifreecomm.imageload.imageload.request.BitmapRequest;

/**
 * Created by IT小蔡 on 2018-9-6.
 */

public interface LoadPolicy {
    int compare(BitmapRequest request1, BitmapRequest request2);
}
