package com.joy.ep.myokhttptext.listener;

/**
 * Created by Administrator on 2015/8/25.
 */
public abstract class BaseListener<T> {
    public abstract void  onError(Exception e);
    public abstract void  onFailure(String e);
    public abstract void  onSuccess(T result);
    public abstract void  onStringResult(String result);
    public abstract void  onDownloadFinish(String path);//下载完成
    public abstract void  onDownloadProgress(int progress);//下载进度
}
