package com.joy.ep.myokhttptext.http;

import com.cjj.http.Http;
import com.cjj.listener.CallbackListener;
import com.joy.ep.myokhttptext.enity.GanHuo;
import com.joy.ep.myokhttptext.enity.NewBean;
import com.joy.ep.myokhttptext.enity.TestBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author   Joy
 * Date:  2016/2/14.
 * version:  V1.0
 * Description:
 */
public class AppDao {
    private static AppDao instance;
    String url = "http://222.177.210.200/supplier/news/getNewsList";
    String tag = "http://120.25.0.216/userfindmacth.json";
    String gank = "http://gank.avosapps.com/api/data/all/30/1";

    public static AppDao getInstance() {
        if (instance == null) {
            instance = new AppDao();
        }
        return instance;
    }


    private AppDao() {
    }

    public Map<String, String> createMap() {
        Map<String, String> map = new HashMap<>();
        return map;
    }


    public void getNewsList(CallbackListener<String> listener) {
        Map<String, String> map = createMap();
        map.put("newsTypeVal", "CC");
        Http.post(url, map, listener);
    }

    public void getNewsListBean(CallbackListener<JsonResult<NewBean>> listener) {
        Map<String, String> params = new HashMap<>();
        params.put("newsTypeVal", "CC");
        Http.post(url, params, listener);
    }

    public void userfindmacth(CallbackListener<TestBean> listener) {
        Map<String, String> params = new HashMap<>();
        params.put("pageNo", "1");
        Http.post(tag, params, listener);
    }

    public void userfindmacthS(CallbackListener<String> listener) {
        Map<String, String> params = new HashMap<>();
        params.put("pageNo", "1");
        Http.post(tag, params, listener);
    }

    public void gankIo(CallbackListener<List<GanHuo>> listener) {
        Http.get(gank, listener);
    }

}
