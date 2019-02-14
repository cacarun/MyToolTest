package com.mytooltest.mylist.grouprecycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mytooltest.R;

class MemberViewHolder extends RecyclerView.ViewHolder {

    private final TextView mNameView;
    private final TextView mMemoView;

    MemberViewHolder(View itemView) {
        super(itemView);
        mNameView = itemView.findViewById(R.id.name);
        mMemoView = itemView.findViewById(R.id.memo);
    }

    void update(Member member) {
        mNameView.setText(member.name);
        mMemoView.setText(member.memo);
    }
}
