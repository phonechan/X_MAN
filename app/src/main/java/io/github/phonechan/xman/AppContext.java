package io.github.phonechan.xman;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;

import io.github.phonechan.xman.api.ApiHttpClient;
import io.github.phonechan.xman.base.BaseApplication;

/**
 * Created by apple on 16/2/1.
 */
public class AppContext extends BaseApplication {

    private static final String TAG = "AppContext";

    private static AppContext instance;


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
    }


}
