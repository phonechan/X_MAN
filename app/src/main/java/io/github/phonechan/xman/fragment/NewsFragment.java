package io.github.phonechan.xman.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import io.github.phonechan.xman.R;
import io.github.phonechan.xman.adapter.NewsAdapter;
import io.github.phonechan.xman.api.remote.XmanApi;
import io.github.phonechan.xman.bean.Entity;
import io.github.phonechan.xman.bean.News;
import io.github.phonechan.xman.log.LogUtil;

/**
 * Created by chenfeng on 16/2/2.
 */
public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "NewsFragment";
    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;

    private LinkedList<Entity> mLinkedList = new LinkedList<>();
    private NewsAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        ButterKnife.bind(this, view);
        mAdapter = new NewsAdapter(getActivity(), mLinkedList);
        listview.setAdapter(mAdapter);
        swiperefreshlayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        swiperefreshlayout.setOnRefreshListener(this);

        return view;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swiperefreshlayout.setProgressViewOffset(false, 0, 30);
        swiperefreshlayout.setRefreshing(true);
        this.onRefresh();
    }

    @Override
    public void onRefresh() {


        XmanApi.getNewsList("小区价格抵机场", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                LogUtil.i(TAG, response.toString());

                try {
                    JSONArray jsonArray = response.getJSONArray("result");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        News news = new News();
                        news.parse(jsonArray.getJSONObject(i));
                        LogUtil.i(TAG, "news : " + news);
                        mLinkedList.add(news);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                handler.sendEmptyMessage(0);

            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private MyHandler handler = new MyHandler();

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    swiperefreshlayout.setRefreshing(false);
                    mAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    }
}
