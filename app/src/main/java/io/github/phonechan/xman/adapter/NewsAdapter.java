package io.github.phonechan.xman.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.phonechan.xman.AppContext;
import io.github.phonechan.xman.R;
import io.github.phonechan.xman.bean.Entity;
import io.github.phonechan.xman.bean.News;

/**
 * Created by chenfeng on 16/2/2.
 */
public class NewsAdapter extends BaseAdapter {

    private Context mContext;
    private LinkedList<Entity> mLinkedList;
    private LayoutInflater mInflater;

    ViewHolder holder = null;


    public void setmLinkedList(LinkedList<Entity> mLinkedList) {
        this.mLinkedList = mLinkedList;
    }

    public NewsAdapter(Context mContext, LinkedList<Entity> mLinkedList) {
        this.mContext = mContext;
        this.mLinkedList = mLinkedList;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mLinkedList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = mInflater.inflate(R.layout.list_item_news, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        News news = (News) mLinkedList.get(position);
        AppContext.showImage(news.getImg(), holder.ivNews);
        holder.tvNewsTitle.setText(news.getTitle());
        holder.tvNewsContent.setText(news.getFull_title());

        return convertView;
    }


    class ViewHolder {
        @Bind(R.id.iv_news)
        ImageView ivNews;
        @Bind(R.id.tv_news_title)
        TextView tvNewsTitle;
        @Bind(R.id.tv_news_content)
        TextView tvNewsContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
