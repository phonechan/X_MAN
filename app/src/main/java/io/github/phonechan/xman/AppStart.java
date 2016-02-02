package io.github.phonechan.xman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import io.github.phonechan.xman.api.remote.XmanApi;
import io.github.phonechan.xman.bean.RobotResponse;
import io.github.phonechan.xman.bean.VoiceResponse;
import io.github.phonechan.xman.ui.MainActivity;

public class AppStart extends Activity {

    private static final String TAG = "AppStart";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //防止第三方跳入出现双实例
        Activity activity = AppManager.getInstance().getActivity(MainActivity.class);
        if (activity != null && !activity.isFinishing()) {
            finish();
        }

        final View view = View.inflate(this, R.layout.app_start, null);


        //设置渐变启动屏
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.5f, 1.0f);
        alphaAnimation.setDuration(800);
        view.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                redirectTo();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        setContentView(view);


    }

    /**
     * 跳转到...
     */
    private void redirectTo() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}
