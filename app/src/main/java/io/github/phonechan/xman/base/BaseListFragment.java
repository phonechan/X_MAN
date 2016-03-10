package io.github.phonechan.xman.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.phonechan.xman.R;
import io.github.phonechan.xman.bean.Entity;
import io.github.phonechan.xman.log.LogUtil;

/**
 * Created by chenfeng on 16/2/2.
 */
public abstract class BaseListFragment<T extends Entity> extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        AdapterView.OnItemClickListener, AbsListView.OnScrollListener {

    private final static String TAG = BaseListFragment.class.getSimpleName();

    public static final int STATE_NONE = 0;
    public static final int STATE_REFRESH = 1;
    public static final int STATE_LOADMORE = 2;
    public static final int STATE_NOMORE = 3;
    public static final int STATE_PRESSNONE = 4;// 正在下拉但还没有到刷新的状态
    public static int mState = STATE_NONE;

    protected int mCurrentPage = 0;

    @Bind(R.id.swiperefreshlayout)
    protected SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.listview)
    protected ListView listView;

    protected ListBaseAdapter<T> adapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pull_refresh_listview;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LogUtil.i(TAG, "+++onCreateView+++");

        View view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        LogUtil.i(TAG, "+++onViewCreated+++");
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();
//        onRefresh();
    }

    private void initView() {
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(
                R.color.swiperefresh_color1,
                R.color.swiperefresh_color2,
                R.color.swiperefresh_color3,
                R.color.swiperefresh_color4);

        listView.setOnItemClickListener(this);
        listView.setOnScrollListener(this);
        if (adapter != null) {
            listView.setAdapter(adapter);
        } else {
            adapter = getListAdapter();
            listView.setAdapter(adapter);
            if (requestDataIfViewCreated()) {
                mState = STATE_NONE;
                sendRequestData();
            } else {
            }
        }
    }

    protected abstract ListBaseAdapter<T> getListAdapter();

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onRefresh() {
        if (mState == STATE_REFRESH) {
            return;
        }
        // 设置顶部正在刷新
        listView.setSelection(0);
        setSwipeRefreshLoadingState();
        mCurrentPage = 0;
        mState = STATE_REFRESH;
        sendRequestData();

    }

    protected boolean requestDataIfViewCreated() {
        return true;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        if (adapter == null || adapter.getCount() == 0) {
            return;
        }
        // 数据已经全部加载，或数据为空时，或正在加载，不处理滚动事件
        if (mState == STATE_LOADMORE || mState == STATE_REFRESH) {
            return;
        }
        // 判断是否滚动到底部
        boolean scrollEnd = false;
        try {
            if (view.getPositionForView(adapter.getFooterView()) == view
                    .getLastVisiblePosition())
                scrollEnd = true;
        } catch (Exception e) {
            scrollEnd = false;
        }

        if (mState == STATE_NONE && scrollEnd) {
            if (adapter.getState() == ListBaseAdapter.STATE_LOAD_MORE
                    || adapter.getState() == ListBaseAdapter.STATE_NETWORK_ERROR) {
                mCurrentPage++;
                mState = STATE_LOADMORE;
                sendRequestData();
                adapter.setFooterViewLoading();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


    }

    protected void sendRequestData() {
    }

    /**
     * 设置顶部正在加载的状态
     */
    protected void setSwipeRefreshLoadingState() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(true);
            // 防止多次重复刷新
            swipeRefreshLayout.setEnabled(false);
        }
    }

    /**
     * 设置顶部加载完毕的状态
     */
    protected void setSwipeRefreshLoadedState() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
            swipeRefreshLayout.setEnabled(true);
        }
    }
}
