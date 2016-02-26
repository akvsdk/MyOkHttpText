package com.joy.ep.myokhttptext.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.listener.CallbackListener;
import com.joy.ep.myokhttptext.R;
import com.joy.ep.myokhttptext.adapter.Alladapter;
import com.joy.ep.myokhttptext.base.BaseFragment;
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

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_frg, container, false);
        rcy = (RecyclerView) view.findViewById(R.id.rcy);
        adapter = new Alladapter(getActivity(), ganHuos);
        rcy.setAdapter(adapter);
        rcy.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.setOnItemClickListener(new Alladapter.OnItemClickListener() {

            @Override
            public void onItemClick(View v, GanHuo bean) {
                IntentUtils.intUrl(getContext(), bean.getUrl());
            }
        });
        getdate();
        return view;
    }


    public void getdate() {
        AppDao.getInstance().gankIo(new CallbackListener<List<GanHuo>>() {
            @Override
            public void onSuccess(List<GanHuo> result) {
                ganHuos.clear();
                ganHuos.addAll(result);
                adapter.notifyDataSetChanged();
            }
        });
    }
}

