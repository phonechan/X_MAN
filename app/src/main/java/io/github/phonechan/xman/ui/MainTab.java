package io.github.phonechan.xman.ui;

import io.github.phonechan.xman.R;
import io.github.phonechan.xman.fragment.ExploreFragment;
import io.github.phonechan.xman.fragment.MyFragment;
import io.github.phonechan.xman.fragment.NewsFragment;
import io.github.phonechan.xman.fragment.TweetFragment;

/**
 * Created by chenfeng on 16/2/2.
 */
public enum MainTab {

    NEWS(0, R.string.main_tab_name_news, R.mipmap.ic_launcher, NewsFragment.class),

    TWEET(1, R.string.main_tab_name_tweet, R.mipmap.ic_launcher, TweetFragment.class),

    EXPLORE(2, R.string.main_tab_name_explore, R.mipmap.ic_launcher, ExploreFragment.class),

    ME(3, R.string.main_tab_name_my, R.mipmap.ic_launcher, MyFragment.class);

    private int idx;
    private int resName;
    private int resIcon;
    private Class<?> clz;

    private MainTab(int idx, int resName, int resIcon, Class<?> clz) {
        this.idx = idx;
        this.resName = resName;
        this.resIcon = resIcon;
        this.clz = clz;
    }

    public int getIdx() {
        return idx;
    }

    public int getResName() {
        return resName;
    }

    public int getResIcon() {
        return resIcon;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public void setResName(int resName) {
        this.resName = resName;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }
}
