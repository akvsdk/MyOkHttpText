package com.joy.ep.myokhttptext.http;

import com.joy.ep.myokhttptext.util.ConverUtil;

/**
 * author   Joy
 * Date:  2016/2/17.
 * version:  V1.0
 * Description:
 */
public class JSONModel {
    private String classType = getClass().getName();

    public <T extends Object> T fromBean(String jsonStr) {
        return ConverUtil.jsonToBean(jsonStr, classType);
    }


    public <T extends Object> T fromArray(String jsonStr) {
        return ConverUtil.jsonToBeanList(jsonStr, classType);

    }

    public <T extends Object> T from(String jsonStr) {
        return ConverUtil.json2b(jsonStr, classType);

    }
}
