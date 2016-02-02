package io.github.phonechan.xman.api;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.Locale;

import cz.msebera.android.httpclient.client.params.ClientPNames;

/**
 * Created by apple on 16/2/2.
 */
public class ApiHttpClient {

    private static String TAG = "ApiHttpClient";

    public static String API_URL = "http://op.juhe.cn/";

    public static AsyncHttpClient client;

    public ApiHttpClient() {
    }

    public static AsyncHttpClient getHttpClient() {
        return client;
    }

    public static void setHttpClient(AsyncHttpClient c) {
        client = c;
        client.addHeader("Accept-Language", Locale.getDefault().toString());
        client.addHeader("Connection", "Keep-Alive");
        client.getHttpClient().getParams()
                .setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);

    }

    public static void get(String partUrl, AsyncHttpResponseHandler handler) {
        client.get(getAbsoluteApiUrl(partUrl), handler);
        Log.i(TAG, new StringBuilder("GET ").append(partUrl).toString());
    }

    public static void get(String partUrl, RequestParams params,
                           AsyncHttpResponseHandler handler) {
        client.get(partUrl, params, handler);
//        client.get(getAbsoluteApiUrl(partUrl), params, handler);
        Log.i(TAG, new StringBuilder("GET ").append(partUrl).append("&")
                .append(params).toString());
    }

    public static String getAbsoluteApiUrl(String partUrl) {
        String url = partUrl;
        if (!partUrl.startsWith("http:") && !partUrl.startsWith("https:")) {
            url = String.format(API_URL, partUrl);
        }
        Log.d("BASE_CLIENT", "request:" + url);
        return url;
    }
}
