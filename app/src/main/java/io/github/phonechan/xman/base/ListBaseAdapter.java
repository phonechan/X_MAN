package io.github.phonechan.xman.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.github.phonechan.xman.R;
import io.github.phonechan.xman.bean.Entity;
import io.github.phonechan.xman.util.StringUtils;
import io.github.phonechan.xman.util.TDevice;

/**
 * Created by chenfeng on 16/2/2.
 */
public class ListBaseAdapter<T extends Entity> extends BaseAdapter {


    public static final int STATE_EMPTY_ITEM = 0;
    public static final int STATE_LOAD_MORE = 1;
    public static final int STATE_NO_MORE = 2;
    public static final int STATE_NO_DATA = 3;
    public static final int STATE_LESS_ONE_PAGE = 4;
    public static final int STATE_NETWORK_ERROR = 5;
    public static final int STATE_OTHER = 6;

    protected int state = STATE_LESS_ONE_PAGE;

    protected int _loadmoreText;
    protected int _loadFinishText;
    protected int _noDateText;

    protected ArrayList<T> data = new ArrayList<T>();

    private LayoutInflater inflater;

    public ListBaseAdapter() {
        _loadmoreText = R.string.loading;
        _loadFinishText = R.string.loading_no_more;
        _noDateText = R.string.error_view_no_data;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return this.state;
    }

    public void setNoDataText(int noDataText) {
        this._noDateText = noDataText;
    }

    public ArrayList<T> getDatas() {
        return data == null ? (data = new ArrayList<T>()) : data;
    }

    public void setDatas(ArrayList<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addDatas(List<T> data) {
        if (data != null && data != null && !data.isEmpty()) {
            data.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void addItem(int pos, T obj) {
        if (data != null) {
            data.add(pos, obj);
        }
        notifyDataSetChanged();
    }

    public void removeItem(Object obj) {
        data.remove(obj);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    protected LayoutInflater getLayoutInflater(Context context) {

        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        return inflater;
    }


    @Override
    public int getCount() {
        switch (getState()) {
            case STATE_EMPTY_ITEM:
                return getDataSizePlus1();
            case STATE_NETWORK_ERROR:
            case STATE_LOAD_MORE:
                return getDataSizePlus1();
            case STATE_NO_DATA:
                return 1;
            case STATE_NO_MORE:
                return getDataSizePlus1();
            case STATE_LESS_ONE_PAGE:
                return getDataSize();
            default:
                break;
        }
        return getDataSize();
    }

    public int getDataSizePlus1() {
        if (hasFooterView()) {
            return getDataSize() + 1;
        }
        return getDataSize();
    }

    public int getDataSize() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        if (data.size() > position) {
            return data.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (position == getCount() - 1 && hasFooterView()) {// 最后一条

            if (getState() == STATE_LOAD_MORE
                    || getState() == STATE_NO_MORE
                    || state == STATE_EMPTY_ITEM
                    || getState() == STATE_NETWORK_ERROR) {

                this.mFooterView = (LinearLayout) LayoutInflater.from(
                        parent.getContext()).inflate(R.layout.list_cell_footer,
                        null);
                if (!loadMoreHasBg()) {
                    mFooterView.setBackgroundDrawable(null);
                }
                ProgressBar progress = (ProgressBar) mFooterView
                        .findViewById(R.id.progressbar);
                TextView text = (TextView) mFooterView.findViewById(R.id.text);
                switch (getState()) {
                    case STATE_LOAD_MORE:
                        setFooterViewLoading();
                        break;
                    case STATE_NO_MORE:
                        mFooterView.setVisibility(View.VISIBLE);
                        progress.setVisibility(View.GONE);
                        text.setVisibility(View.VISIBLE);
                        text.setText(_loadFinishText);
                        break;
                    case STATE_EMPTY_ITEM:
                        progress.setVisibility(View.GONE);
                        mFooterView.setVisibility(View.VISIBLE);
                        text.setText(_noDateText);
                        break;
                    case STATE_NETWORK_ERROR:
                        mFooterView.setVisibility(View.VISIBLE);
                        progress.setVisibility(View.GONE);
                        text.setVisibility(View.VISIBLE);
                        if (TDevice.hasInternet()) {
                            text.setText("加载出错了");
                        } else {
                            text.setText("没有可用的网络");
                        }
                        break;
                    default:
                        progress.setVisibility(View.GONE);
                        mFooterView.setVisibility(View.GONE);
                        text.setVisibility(View.GONE);
                        break;
                }
                return mFooterView;
            }
        }
        if (position < 0) {
            position = 0; // 若列表没有数据，是没有footview/headview的
        }
        return getRealView(position, convertView, parent);
    }

    protected View getRealView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    private LinearLayout mFooterView;

    protected boolean hasFooterView() {
        return true;
    }

    public View getFooterView() {
        return this.mFooterView;
    }

    public void setFooterViewLoading() {
        setFooterViewLoading("");
    }

    public void setFooterViewLoading(String loadMsg) {
        ProgressBar progress = (ProgressBar) mFooterView.findViewById(R.id.progressbar);
        TextView text = (TextView) mFooterView.findViewById(R.id.text);
        mFooterView.setVisibility(View.VISIBLE);
        progress.setVisibility(View.VISIBLE);
        text.setVisibility(View.VISIBLE);
        if (StringUtils.isEmpty(loadMsg)) {
            text.setText(_loadmoreText);
        } else {
            text.setText(loadMsg);
        }
    }

    protected boolean loadMoreHasBg() {
        return true;
    }


}
