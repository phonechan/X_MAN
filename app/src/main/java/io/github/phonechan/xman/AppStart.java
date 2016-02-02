package io.github.phonechan.xman;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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

public class AppStart extends Activity {

    private static final String TAG = "AppStart";


    @Bind(R.id.btn_start)
    Button btn_start;

    @Bind(R.id.tv_words)
    TextView tv_words;

    private SpeechRecognizer mIat;


    //听写监听器    
    private RecognizerListener mRecoListener = new RecognizerListener() {
        //听写结果回调接口(返回Json格式结果，用户可参见附录12.1)；    
        //一般情况下会通过onResults接口多次返回结果，完整的识别内容是多次结果的累加；    
        //关于解析Json的代码可参见MscDemo中JsonParser类；    
        //isLast等于true时会话结束。    
        public void onResult(RecognizerResult results, boolean isLast) {
            Log.d("Result:", results.getResultString());
        }

        //会话发生错误回调接口    
        public void onError(SpeechError error) {
            error.getPlainDescription(true);//获取错误码描述
        }

        @Override
        public void onVolumeChanged(int i, byte[] bytes) {

        }

        //开始录音    
        public void onBeginOfSpeech() {
        }

        //音量值0~30    
        public void onVolumeChanged(int volume) {
        }

        //结束录音    
        public void onEndOfSpeech() {
        }

        //扩展用接口    
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_start);

        ButterKnife.bind(this);

        //1.创建SpeechRecognizer对象，第二个参数：本地听写时传InitListener    
        mIat = SpeechRecognizer.createRecognizer(this, null);
        //2.设置听写参数，详见《科大讯飞MSC API手册(Android)》SpeechConstant类    
        mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");
        //3.开始听写   
        mIat.startListening(mRecoListener);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick(R.id.btn_start)
    public void start() {

        //1.创建RecognizerDialog对象
        RecognizerDialog mDialog = new RecognizerDialog(this, new mInitListener());
        //2.设置accent、language等参数
        mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin"); //若要将UI控件用于语义理解,必须添加以下参数设置,设置之后onResult回调返回将是语义理解 //结果
        // mDialog.setParameter("asr_sch", "1");
        // mDialog.setParameter("nlp_version", "2.0");
        //3.设置回调接口 
        mDialog.setListener(new mRecognizerDialogListener());
        //4.显示dialog,接收语音输入
        mDialog.show();
    }

    class mInitListener implements InitListener {
        @Override
        public void onInit(int i) {
            Log.i(TAG, "onInit : i = " + i);
        }
    }

    String s = "";
    class mRecognizerDialogListener implements RecognizerDialogListener {
        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {

            Gson gson = new Gson();
            VoiceResponse voiceResponse = gson.fromJson(recognizerResult.getResultString(), VoiceResponse.class);
            for (VoiceResponse.WsEntity wsEntity : voiceResponse.getWs()) {
                for (VoiceResponse.WsEntity.CwEntity cwEntity : wsEntity.getCw()) {
                    s += cwEntity.getW();
                }
            }
            Log.i(TAG, "msg : " + s);

            if (b) {
                XmanApi.getRobotSay(s, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.i(TAG, "response : " + response);
                        Gson gson = new Gson();
                        RobotResponse robotResponse = gson.fromJson(response.toString(), RobotResponse.class);
                        tv_words.setText(robotResponse.toString());
                        s = "";
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.i(TAG, "errorResponse : " + errorResponse);
                    }
                });
            }

        }

        @Override
        public void onError(SpeechError speechError) {

            Log.i(TAG, "onError : speechError = " + speechError);
        }
    }


}
