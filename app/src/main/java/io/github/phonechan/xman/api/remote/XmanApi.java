package io.github.phonechan.xman.api.remote;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import io.github.phonechan.xman.AppConfig;
import io.github.phonechan.xman.api.ApiHttpClient;

/**
 * Created by apple on 16/2/2.
 */
public class XmanApi {

    public static void getRobotSay(String info, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("info", info);
        params.put("key", AppConfig.KEY_ROBOT);
        String robotUrl = "robot/index";
        ApiHttpClient.get(robotUrl, params, handler);
    }
}
