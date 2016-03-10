package io.github.phonechan.xman;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import io.github.phonechan.xman.api.ApiHttpClient;
import io.github.phonechan.xman.base.BaseApplication;

/**
 * Created by apple on 16/2/1.
 */
public class AppContext extends BaseApplication {

    private static final String TAG = "AppContext";

    private static AppContext instance;

    private static Gson gson;

    public static Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    /**
     * 获得当前app运行的AppContext
     *
     * @return
     */
    public static AppContext getInstance() {
        return instance;
    }

    /**
     * 获取App安装包信息
     *
     * @return
     */
    public PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null)
            info = new PackageInfo();
        return info;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init();
    }

    private void init() {

        SpeechUtility.createUtility(instance, SpeechConstant.APPID + "=" + AppConfig.APP_ID_XUNFEI);

        // 初始化网络请求
        AsyncHttpClient client = new AsyncHttpClient();
        PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
        client.setCookieStore(myCookieStore);
        ApiHttpClient.setHttpClient(client);

        initImageLoader();
    }

    private void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(getApplicationContext())
                .threadPoolSize(4)
                        // 线程池内加载的数量
                .threadPriority(Thread.MIN_PRIORITY + 3)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                .memoryCache(new WeakMemoryCache())
                        // You can pass your own memory cache
                        // implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)
                .discCacheSize(50 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                        // 将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(100)
                        // 缓存的文件数量
                        // .discCache(new UnlimitedDiscCache(cacheDir))
                        // 自定义缓存路径
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(
                        new BaseImageDownloader(getApplicationContext(), 10 * 1000,
                                30 * 1000))// connectTimeout (5 s), readTimeout
                        // (30 s)超时时间
                .build();// 开始构建

        ImageLoader.getInstance().init(config);

    }


    public static void showImage(String imageUri, ImageView imageView) {
        ImageLoader.getInstance().displayImage(imageUri, imageView);
    }


}
