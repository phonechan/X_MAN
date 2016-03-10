package io.github.phonechan.xman.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by apple on 16/2/2.
 */
public class RobotResponse extends Entity {

    /**
     * reason : 成功的返回
     * result : {"code":100000,"text":"你好啊，希望你今天过的快乐"}
     * error_code : 0
     */

    private String reason;
    /**
     * code : 100000
     * text : 你好啊，希望你今天过的快乐
     */

    private ResultEntity result;
    private int error_code;

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public ResultEntity getResult() {
        return result;
    }

    public int getError_code() {
        return error_code;
    }

    public static class ResultEntity {
        private int code;
        private String text;

        public void setCode(int code) {
            this.code = code;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getCode() {
            return code;
        }

        public String getText() {
            return text;
        }

        @Override
        public String toString() {
            return "ResultEntity{" +
                    "code=" + code +
                    ", text='" + text + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "RobotResponse{" +
                "reason='" + reason + '\'' +
                ", result=" + result +
                ", error_code=" + error_code +
                '}';
    }

    @Override
    public void parse(JSONObject jsonObject) throws JSONException {

    }
}
