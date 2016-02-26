package com.joy.ep.myokhttptext.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cjj.http.Http;
import com.joy.ep.myokhttptext.R;
import com.joy.ep.myokhttptext.enity.GanHuo;

import java.util.List;

/**
 * author   Joy
 * Date:  2016/2/26.
 * version:  V1.0
 * Description:
 */
public class Alladapter extends RecyclerView.Adapter<Alladapter.ViewHolder> {
    private List<GanHuo> ganHuos;
    private LayoutInflater mInflater;
    private Context mtx;


    public Alladapter(Context context, List<GanHuo> bean) {
        mtx = context;
        this.ganHuos = bean;
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.all_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final GanHuo ganHuo = ganHuos.get(position);
        if (!ganHuo.getType().equals("福利")) {
            holder.img.setVisibility(View.GONE);
            holder.tv.setText(Html.fromHtml("<font color=blue>(" + ganHuo.getType() + ")  </font>" +
                    "<a href=\"" + ganHuo.getUrl() + "\">" + ganHuo.getDesc() + "</a>"
                    + "<font color=red>[" + ganHuo.getWho() + "]</font>"));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(holder.itemView, ganHuo);
                    }
                }
            });
        } else {
            holder.tv.setVisibility(View.GONE);
            holder.img.setVisibility(View.VISIBLE);
            Http.showimg(mtx, holder.img, ganHuo.getUrl());
        }
    }

    public interface OnItemClickListener {

        void onItemClick(View v, GanHuo bean);
    }

    @Override
    public int getItemCount() {
        return ganHuos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;
        private ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.alltv);
            img = (ImageView) itemView.findViewById(R.id.allimg);
        }
    }
}
