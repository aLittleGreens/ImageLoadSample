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
	        implementation 'com.github.cai784921129:ImageLoadSample:1.0'
	}

```

## Useage 
1. init The ImageLoad with ImageLoaderConfig
```java
public class BaseApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoader.init(this);
    }
}
```
2、load bitmap
```java
ImageLoader.getInstance().
                setImageCache(DoubleCache.getInstance(context)).
                setDefaultImg(R.mipmap.ic_launcher).
                setErrorImg(R.mipmap.ic_launcher_round).
                displayImage(url, imageView);

```
