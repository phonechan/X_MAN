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

    public static void getNewsHotwords(AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("key", AppConfig.KEY_NEWS);
        String newsUrl = "onebox/news/words";
        ApiHttpClient.get(newsUrl, params, handler);
    }

    public static void getNewsList(String q, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("q", q);
        params.put("key", AppConfig.KEY_NEWS);
        String newsUrl = "onebox/news/query";
        ApiHttpClient.get(newsUrl, params, handler);
    }
}
