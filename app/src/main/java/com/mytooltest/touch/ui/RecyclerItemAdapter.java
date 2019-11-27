package com.mytooltest.touch.ui;

import android.content.Context;
import android.widget.TextView;

import com.mytooltest.R;
import com.mytooltest.base.BaseRecyclerAdapter;
import com.mytooltest.base.BaseRecyclerHolder;

import java.util.List;

public class RecyclerItemAdapter extends BaseRecyclerAdapter<String> {

    public RecyclerItemAdapter(Context context, List<String> datas) {
        super(context, R.layout.item_recycler, datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, String item, int position) {
        TextView tv = holder.getView(R.id.tv);
        String s = mDatas.get(position);
        tv.setText(s);
    }
}
