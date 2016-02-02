package io.github.phonechan.xman.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import io.github.phonechan.xman.R;
import io.github.phonechan.xman.adapter.NewsAdapter;
import io.github.phonechan.xman.api.remote.XmanApi;
import io.github.phonechan.xman.base.BaseFragment;
import io.github.phonechan.xman.base.BaseListFragment;
import io.github.phonechan.xman.base.ListBaseAdapter;
import io.github.phonechan.xman.bean.News;
import io.github.phonechan.xman.log.LogUtil;

/**
 * Created by chenfeng on 16/2/2.
 */
public class NewsFragment extends BaseListFragment<News> {

    private static final String TAG = "NewsFragment";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        return view;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        XmanApi.getNewsList("小伙以死要彩礼", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                LogUtil.i(TAG, response.toString());

            }
        });
    }

    @Override
    protected ListBaseAdapter<News> getListAdapter() {
        return new NewsAdapter();
    }
}
