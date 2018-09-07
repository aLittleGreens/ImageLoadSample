# ImageLoadSample
Bitmap三级缓存，用面向对象六大原则书写，重点是理解设计模式

作者主要是以学习为主，并不是为了造轮子

## Download
Add the following dependencies in project's gradle.
```groovy
allprojects {
    repositories {
    ...
	maven { url 'https://jitpack.io' }
    }
}

dependencies {
 	implementation 'com.github.cai784921129:ImageLoadSample:1.1'
}

```

## Useage 
1. init The ImageLoad with ImageLoaderConfig
```java
public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoadConfig imageLoadConfig = new ImageLoadConfig().
                setLoadPolicy(new SerialPolicy()).	//设置队列顺序，默认是顺序
                setCache(DoubleCache.getInstance(this)).	//设置缓存策略，默认内存缓存
                setLoadingPlaceholder(R.drawable.loading).
                setNotFoundPlaceholder(R.drawable.not_found).
                setThreadCount(5);	//设置线程数量，默认cpu核数+1
        ImageLoader.getInstance().init(imageLoadConfig);
    }
}
```
## load bitmap
a、加载网络图片
```java
 ImageLoader.getInstance().
                setLoadingRedId(R.mipmap.ic_launcher).
                setErrorResId(R.mipmap.ic_launcher_round).
                displayImage(imageView,imgUrl);

```
b、加载sdcard图片（加载本地图片没有缓存，只是对bitmap做个2次采样）
```java
ImageLoader.getInstance().
                displayImage(imageView,"file://sdcard/xxx/image.jpg");
```
c、加载resID资源
```java
 ImageLoader.getInstance().
                displayImage(imageView, "res://"+R.mipmap.ic_launcher_round);
```

