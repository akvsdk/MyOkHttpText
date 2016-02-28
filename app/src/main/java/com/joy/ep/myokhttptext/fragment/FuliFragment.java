package com.joy.ep.myokhttptext.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.listener.CallbackListener;
import com.joy.ep.myokhttptext.R;
import com.joy.ep.myokhttptext.adapter.Fuliadapter;
import com.joy.ep.myokhttptext.common.BaseFragment;
import com.joy.ep.myokhttptext.enity.GanHuo;
import com.joy.ep.myokhttptext.http.AppDao;
import com.joy.ep.myokhttptext.util.IntentUtils;

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

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_frg, container, false);
        rcy = (RecyclerView) view.findViewById(R.id.rcy);
        adapter = new Fuliadapter(getActivity(), ganHuos);
        rcy.setAdapter(adapter);
        rcy.setLayoutManager(new LinearLayoutManager(getActivity()));
//        SpacesItemDecoration decoration=new SpacesItemDecoration(16);
//        rcy.addItemDecoration(decoration);
        adapter.setOnItemClickListener(new Fuliadapter.OnItemClickListener() {

            @Override
            public void onItemClick(View v, GanHuo bean) {
                IntentUtils.intUrl(getContext(), bean.getUrl());
            }
        });
        getdate();
        return view;
    }


    public void getdate() {
        AppDao.getInstance().gankIo(1,1,new CallbackListener<List<GanHuo>>() {
            @Override
            public void onSuccess(List<GanHuo> result) {
                ganHuos.clear();
                ganHuos.addAll(result);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
