package io.github.phonechan.xman.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.phonechan.xman.R;
import io.github.phonechan.xman.base.BaseFragment;

/**
 * Created by chenfeng on 16/2/2.
 */
public class NewsFragment extends BaseFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        return view;

    }
}
