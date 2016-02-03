package io.github.phonechan.xman.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.phonechan.xman.R;
import io.github.phonechan.xman.base.ListBaseAdapter;

/**
 * Created by chenfeng on 16/2/2.
 */
public class NewsAdapter extends ListBaseAdapter {


    public NewsAdapter() {

    }


    @Override
    protected View getRealView(int position, View convertView, ViewGroup parent) {


        ViewHolder vh;
        if (convertView == null || convertView.getTag() == null) {
            convertView = getLayoutInflater(parent.getContext()).inflate(
                    R.layout.list_cell_news, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        return super.getRealView(position, convertView, parent);
    }

    static class ViewHolder {

        @Bind(R.id.tv_title)
        TextView tv_title;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
