package com.joy.ep.myokhttptext.http;

import com.cjj.http.Http;
import com.cjj.listener.CallbackListener;
import com.joy.ep.myokhttptext.enity.GanHuo;

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
        Http.post("url", map, listener);
    }

    public void gankIo(int index, int page, CallbackListener<List<GanHuo>> listener) {
        String type = "all";
        switch (index) {
            case 0:
                type = "all";
                break;
            case 1:
                type = "福利";
                break;
            case 2:
                type = "Android";
                break;
            case 3:
                type = "iOS";
                break;
            case 4:
                type = "休息视频";
                break;
            case 5:
                type = "拓展资源";
                break;
            case 6:
                type = "前端";
                break;
        }
        String gank = "http://gank.avosapps.com/api/data/" + type + "/30/" + page;
        Http.get(gank, listener);
    }

}
