package com.joy.ep.myokhttptext.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.view.ViewGroup.LayoutParams;

import com.cjj.http.Http;
import com.joy.ep.myokhttptext.R;
import com.joy.ep.myokhttptext.enity.GanHuo;

import java.util.ArrayList;
import java.util.List;

/**
 * author   Joy
 * Date:  2016/2/26.
 * version:  V1.0
 * Description:
 */
public class Fuliadapter extends RecyclerView.Adapter<Fuliadapter.ViewHolder> {
    private List<GanHuo> ganHuos;
    private LayoutInflater mInflater;
    private Context mtx;
    private List<Integer> mHeights;


    public Fuliadapter(Context context, List<GanHuo> bean) {
        mtx = context;
        mInflater = LayoutInflater.from(context);
        this.ganHuos = bean;
        mHeights = new ArrayList<Integer>();
        for (int i = 0; i < ganHuos.size(); i++) {
            mHeights.add((int) (100 + Math.random() * 400));
        }
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fuli_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final GanHuo ganHuo = ganHuos.get(position);
        LayoutParams lp = holder.img.getLayoutParams();
        if (mHeights.size() != 0) {
            lp.height = mHeights.get(position);
            holder.img.setLayoutParams(lp);
        }

        Http.showimg(mtx, holder.img, ganHuo.getUrl());
    }

    public interface OnItemClickListener {

        void onItemClick(View v, GanHuo bean);
    }

    @Override
    public int getItemCount() {
        return ganHuos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.allimg);
        }
    }

}