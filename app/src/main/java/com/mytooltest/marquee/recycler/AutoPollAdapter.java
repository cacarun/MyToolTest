package com.mytooltest.marquee.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mytooltest.R;

import java.util.List;

/**
 * Created by jarvis on 2018/9/26.
 */

public class AutoPollAdapter extends RecyclerView.Adapter<AutoPollAdapter.BaseViewHolder> {
    private final List<String> mData;

    public AutoPollAdapter(List<String> list) {
        this.mData = list;
    }

    public void marqueeData() {

        if (mData == null || mData.size() == 0) {

            return;
        }

        String dd = mData.get(0);
        mData.remove(0);
        mData.add(dd);

        notifyItemRemoved(0);
//        notifyDataSetChanged();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_upmarquee_recycler_view, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
//        String data = mData.get(position % mData.size());
        String data = mData.get(position);
        holder.tv.setText(data);

//        final int pos = position;
//        holder.tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(holder.tv.getContext(), "Pos: " + pos, Toast.LENGTH_LONG).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
//        return Integer.MAX_VALUE;
        return mData.size();
    }

    class BaseViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        public BaseViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_content);
        }
    }
}
