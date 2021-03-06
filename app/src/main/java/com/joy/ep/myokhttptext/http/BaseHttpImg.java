package com.joy.ep.myokhttptext.http;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.joy.ep.myokhttptext.listener.CallbackListener;
import com.joy.ep.myokhttptext.util.GenericsUtils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.Set;

/**
 * Created by cjj on 2015/8/25.
 */
public class BaseHttpImg<T> {
    private static final String TAG = "BaseHttp";
    private Gson mGson;
    private OkHttpClient mOkHttpClient;
    private Handler mHandler;
    private static BaseHttpImg mBaseHttp;

    private static final String STATUS = "status";
    private static final String TNGOU = "tngou";

    protected BaseHttpImg() {
        mGson = new Gson();
        mOkHttpClient = new OkHttpClient();
        mHandler = new Handler(Looper.getMainLooper());
        //cookie enabled
        mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
    }

    protected static BaseHttpImg getInstance() {
        if (mBaseHttp == null) {
            mBaseHttp = new BaseHttpImg();
        }
        return mBaseHttp;
    }


    protected void baseGet(String url, CallbackListener<T> listener) {
        Request request = getBaseRequest(url);
        doRequest(request, listener);
    }

    protected OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    protected void basePost(String url, Map<String, String> params, CallbackListener<T> listener) {
        if (params == null) {
            baseGet(url, listener);
            return;
        }
        FormEncodingBuilder builder = new FormEncodingBuilder();
        Set<Map.Entry<String, String>> entrySet = params.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            builder.add(entry.getKey(), entry.getValue());
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .tag(url)
                .build();
        doRequest(request, listener);
    }

    protected void baseDownload(final String url, final String savePath, final CallbackListener<T> listener) {
        Request request = getBaseRequest(url);
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                if (isListenerNotNull(listener)) listener.onError(e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                InputStream is = response.body().byteStream();
                startDownload(url, savePath, is, listener);
            }
        });
    }

    protected void baseDisplayImage(T t, ImageView imageView, String imgUrl, int placeholderId, int errorId) {
        if (t instanceof Activity) {
            Glide.with((Activity) t).load(imgUrl).placeholder(placeholderId).error(errorId).into(imageView);
        } else if (t instanceof Fragment) {
            Glide.with((Fragment) t).load(imgUrl).placeholder(placeholderId).error(errorId).into(imageView);
        } else if (t instanceof Context) {
            Glide.with((Context) t).load(imgUrl).placeholder(placeholderId).error(errorId).into(imageView);
        } else {
            throw new RuntimeException("the t must be context or activity or fragment");
        }
    }

    private void startDownload(String urlPath, String savePath, InputStream is, CallbackListener<T> listener) {
        FileOutputStream fos = null;
        try {
            // 判断SD卡是否存在，并且是否具有读写权限
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                // 获得存储卡的路径
//                String sdpath = Environment.getExternalStorageDirectory().toString();
                System.out.println("savePath-------->" + savePath);
                File file = new File(savePath, getFileNameByUrl(urlPath));
                // 判断文件目录是否存在
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
                fos = new FileOutputStream(file);
                // 缓存
                byte buf[] = new byte[1024 * 2];
                int len = 0;
                int progress = 0;
                while ((len = is.read(buf)) != -1) {
                    progress += len;
                    if (isListenerNotNull(listener)) listener.onDownloadProgress(progress);
                    fos.write(buf, 0, len);
                }
                if (isListenerNotNull(listener)) listener.onDownloadFinish(file.getAbsolutePath());
                fos.flush();
            } else {
                Log.i(TAG, "no find sdcard or sdcard no permission");
            }
        } catch (MalformedURLException e) {
            if (isListenerNotNull(listener)) listener.onError(e);
        } catch (IOException e) {
            if (isListenerNotNull(listener)) listener.onError(e);
        } finally {
            try {
                if (is != null) is.close();
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取Url名称
     *
     * @param strUrl
     * @return
     */
    public static String getFileNameByUrl(String strUrl) {
        if (!TextUtils.isEmpty(strUrl)) {
            try {
                return strUrl.substring(strUrl.lastIndexOf("/") + 1, strUrl.length());
            } catch (IndexOutOfBoundsException e) {
                return strUrl;
            }
        } else {
            return strUrl;
        }
    }

    protected Request getBaseRequest(String url) {
        Request request = new Request.Builder().url(url).tag(url).build();
        return request;
    }

    protected void doRequest(Request request, final CallbackListener<T> listener) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onError(e);
                    }
                });

            }

            @Override
            public void onResponse(Response response) throws IOException {
                JSONObject jsonObject = null;
                final String result = response.body().string();
                Log.i(TAG, "结果：" + result);

                try {
                    jsonObject = new JSONObject(result);

                    if (jsonObject.isNull(STATUS)) {
                        listener.onFailure("error key not exists!!");
                        return;
                    }
                    //判断后台返回结果，true表示失败，false表示成功，失败则返回错误回调并结束请求

                    if (!jsonObject.getBoolean(STATUS)) {
                        listener.onFailure("request failure!!");
                        return;
                    }

                    //判断results字段是否存在，不存在返回时报回调并结束请求
                    if (jsonObject.isNull(TNGOU)) {
                        listener.onFailure("results key not exists!!");
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final String results = jsonObject.optString("tngou");
                if (isListenerNotNull(listener)) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onStringResult(results);
                            try {
                                Class<T> clazz = GenericsUtils.getSuperClassGenricType(listener.getClass());
                                if (clazz == String.class) {        //字符串
                                    if (isListenerNotNull(listener))
                                        listener.onSuccess((T) results);
                                } else {//Object
                                    if (isListenerNotNull(listener))
                                        listener.onSuccess((T) new Gson().fromJson(results, listener.type));
                                }
                            } catch (Exception e) {
                                Log.i(TAG, "出错", e);
                                if (isListenerNotNull(listener))
                                    listener.onError(e);
                            }
                        }
                    });
                }
            }
        });
    }

    public Boolean isListenerNotNull(CallbackListener<T> listener) {
        if (listener != null) {
            return true;
        }
        return false;
    }


}
