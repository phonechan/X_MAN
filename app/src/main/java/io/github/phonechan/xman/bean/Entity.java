package io.github.phonechan.xman.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by chenfeng on 16/2/2.
 */
public abstract class Entity implements Serializable {

    private static final long serialVersionUID = 2015050101L;

    abstract public void parse(JSONObject jsonObject) throws JSONException;

}
