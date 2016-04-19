package com.joy.ep.myokhttptext.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.cjj.listener.CallbackListener;
import com.joy.ep.myokhttptext.R;
import com.joy.ep.myokhttptext.adapter.Alladapter;
import com.joy.ep.myokhttptext.common.BaseFragment;
import com.joy.ep.myokhttptext.enity.GanHuo;
import com.joy.ep.myokhttptext.http.AppDao;
import com.joy.ep.myokhttptext.util.IntentUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * author   Joy
 * Date:  2016/2/26.
 * version:  V1.0
 * Description:
 */
public class AllFragment extends BaseFragment {

    private List<GanHuo> ganHuos = new ArrayList<>();
    private RecyclerView rcy;
    private Alladapter adapter;


    private int page = 1;
    private MaterialRefreshLayout refresh;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_frg, container, false);
        rcy = (RecyclerView) view.findViewById(R.id.rcy);
//        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
//        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);

        adapter = new Alladapter(getActivity(), ganHuos);
        rcy.setAdapter(adapter);
        refresh = (MaterialRefreshLayout) view.findViewById(R.id.refresh);
        rcy.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.setOnItemClickListener(new Alladapter.OnItemClickListener() {

            @Override
            public void onItemClick(View v, GanHuo bean) {
                IntentUtils.intUrl(getContext(), bean.getUrl());
            }
        });
        refresh.setLoadMore(true);
        refresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                page++;
                getdate(page);
                refresh.finishRefreshLoadMore();

            }

            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                getdate(1);
                refresh.finishRefresh();
            }
        });
        getdate(1);
        return view;
    }

    public void getdate(int pag) {
        AppDao.getInstance().gankIo(0, pag, new CallbackListener<List<GanHuo>>() {
            @Override
            public void onSuccess(List<GanHuo> result) {
                if (page == 1) {
                    ganHuos.clear();
                }
                ganHuos.addAll(result);
                adapter.notifyDataSetChanged();
            }
        });
    }
}

