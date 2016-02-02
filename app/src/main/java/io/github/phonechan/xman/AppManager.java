package io.github.phonechan.xman;

import android.app.Activity;
import android.content.Context;
import android.os.Process;

import java.util.Stack;

/**
 * Created by apple on 16/2/1.
 */
public class AppManager {

    private static Stack<Activity> activityStack;

    private static AppManager instance;

    private AppManager() {

    }

    /**
     * 单例
     *
     * @return
     */
    public static AppManager getInstance() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }


    /**
     * 添加Activity到堆栈
     *
     * @param activity
     */
    public void addActivity(Activity activity) {

        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity
     *
     * @return
     */
    public Activity currentActivity() {

        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity
     */
    public void finishActivity() {

        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     *
     * @param activity
     */
    public void finishActivity(Activity activity) {

        if (activity != null && !activity.isFinishing()) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     *
     * @param cls
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                break;
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {

        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();

    }

    /**
     * 获取指定的Activity
     *
     * @param cls
     * @return
     */
    public Activity getActivity(Class<?> cls) {
        if (activityStack != null) {
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    return activity;
                }
            }
        }
        return null;
    }

    /**
     * 退出应用
     *
     * @param context
     */
    public void appExit(Context context) {
        try {
            finishAllActivity();
            android.os.Process.killProcess(Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
