package com.joy.ep.myokhttptext.listener;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by cjj on 2015/8/25.
 */
public abstract class CallbackListener<T> extends BaseListener<T> {

    public Type type;

    public CallbackListener() {
        type = getSuperclassTypeParameter(getClass());
    }

    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }


    @Override
    public void onError(Exception e) {

    }

    @Override
    public void onFailure(String e) {

    }

    @Override
    public abstract void onSuccess(T result);

    @Override
    public void onStringResult(String result) {

    }

    @Override
    public void onDownloadFinish(String path) {

    }

    @Override
    public void onDownloadProgress(int progress) {

    }


}
