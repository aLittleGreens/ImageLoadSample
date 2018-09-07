package com.ifreecomm.imageload.imageload.config;

/**
 * Created by IT小蔡 on 2018-9-6.
 */

public class DisplayConfig {

    public int loadingResId = -1;
    public int failedResId = -1;

    enum Config {
        loading,
        failed
    }

    public DisplayConfig() {
    }

    public DisplayConfig(int loadingResId, int failedResId) {
        this.loadingResId = loadingResId;
        this.failedResId = failedResId;
    }

    public DisplayConfig(Config config, int configResId) {
        switch (config) {
            case failed:
                this.failedResId = configResId;
                break;
            case loading:
                this.loadingResId = configResId;
                break;
        }
    }

}
