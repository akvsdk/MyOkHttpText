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
import com.joy.ep.myokhttptext.adapter.Fuliadapter;
import com.joy.ep.myokhttptext.common.BaseFragment;
import com.joy.ep.myokhttptext.enity.GanHuo;
import com.joy.ep.myokhttptext.http.AppDao;
import com.joy.ep.myokhttptext.util.IntentUtils;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

/**
 * author  Joy
 * Date:  2016/2/27 0027.
 * version:  V1.0
 * Description:
 */
public class FuliFragment extends BaseFragment {
    private List<GanHuo> ganHuos = new ArrayList<>();
    private RecyclerView rcy;
    private Fuliadapter adapter;

    private int page = 1;
    private MaterialRefreshLayout refresh;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_frg, container, false);
        rcy = (RecyclerView) view.findViewById(R.id.rcy);
        adapter = new Fuliadapter(getActivity(), ganHuos);
        rcy.setAdapter(adapter);
        rcy.setLayoutManager(new LinearLayoutManager(getActivity()));
        refresh = (MaterialRefreshLayout) view.findViewById(R.id.refresh);
        rcy.setLayoutManager(new LinearLayoutManager(getActivity()));
        refresh.setLoadMore(true);

        refresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                getdate(1);
                refresh.finishRefresh();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                page++;
                getdate(page);
                refresh.finishRefreshLoadMore();
            }
        });

        adapter.setOnItemClickListener(new Fuliadapter.OnItemClickListener() {

            @Override
            public void onItemClick(View v, GanHuo bean) {
                IntentUtils.intUrl(getContext(), bean.getUrl());
            }
        });
        getdate(1);
        return view;
    }


    public void getdate(int pag) {
        AppDao.getInstance().gankIo(1, pag, new CallbackListener<List<GanHuo>>() {
            @Override
            public void onStringResult(String result) {
                super.onStringResult(result);
                KLog.json(result);
                KLog.e(result);
            }

            @Override
            public void onSuccess(List<GanHuo> result) {
                if (page == 1) {
                    ganHuos.clear();
                }
                KLog.w(result);
                ganHuos.addAll(result);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
