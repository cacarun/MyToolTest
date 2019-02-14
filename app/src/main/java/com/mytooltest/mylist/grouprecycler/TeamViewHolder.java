package com.mytooltest.mylist.grouprecycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mytooltest.R;

class TeamViewHolder extends RecyclerView.ViewHolder {
    private final TextView mTitleView;

    TeamViewHolder(View itemView) {
        super(itemView);
        mTitleView = itemView.findViewById(R.id.title);
    }

    void update(Team team) {
        mTitleView.setText(team.title);
    }
}
