package com.mytooltest.mylist.grouprecycler;

import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lib.grouplist.GroupItemDecoration;
import com.lib.grouplist.GroupRecyclerAdapter;
import com.lib.grouplist.OnChildClickListener;
import com.lib.grouplist.OnGroupClickListener;
import com.mytooltest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 如果有多种分隔线的用 RecyclerView 方便
 *
 */
public class GroupRecyclerActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_group_recycler);

        List<Team> teams = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            List<Member> members = new ArrayList<>();
            String teamName = Character.toString((char) ((int) ('A') + i)) + "队";
            for (int j = 0; j < 2; j++) {
                members.add(new Member("姓名" + j, teamName + j));
            }
            teams.add(new Team(teamName, members));
        }

        final LayoutInflater layoutInflater = LayoutInflater.from(this);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final GroupRecyclerAdapter<Team, TeamViewHolder, MemberViewHolder> recyclerAdapter =
                new GroupRecyclerAdapter<Team, TeamViewHolder, MemberViewHolder>(teams) {
                    @Override
                    protected TeamViewHolder onCreateGroupViewHolder(ViewGroup parent) {
                        return new TeamViewHolder(layoutInflater.inflate(R.layout.group_recycler_my_item_header, parent, false));
                    }

                    @Override
                    protected MemberViewHolder onCreateChildViewHolder(ViewGroup parent) {
                        return new MemberViewHolder(layoutInflater.inflate(R.layout.group_recycler_my_item, parent, false));
                    }

                    @Override
                    protected void onBindGroupViewHolder(TeamViewHolder holder, int groupPosition) {
                        holder.update(getGroup(groupPosition));
                    }

                    @Override
                    protected void onBindChildViewHolder(MemberViewHolder holder, int groupPosition, int childPosition) {
                        holder.update(getGroup(groupPosition).members.get(childPosition));
                    }

                    @Override
                    protected int getChildCount(Team group) {
                        return group.members.size();
                    }
                };

        recyclerView.setAdapter(recyclerAdapter);

        GroupItemDecoration decoration = new GroupItemDecoration(recyclerAdapter);
        decoration.setFirstDividerEnabled(false);
        decoration.setGroupDivider(ResourcesCompat.getDrawable(getResources(), R.drawable.divider_height_16_dp, null));
        decoration.setTitleDivider(ResourcesCompat.getDrawable(getResources(), R.drawable.divider_height_1_px, null));
        decoration.setChildDivider(ResourcesCompat.getDrawable(getResources(), R.drawable.divider_height_1_left_16, null));
        recyclerView.addItemDecoration(decoration);

        recyclerAdapter.setOnGroupClickListener(new OnGroupClickListener() {
            @Override
            public void onGroupItemClick(View itemView, int groupPosition) {
                showToast(recyclerAdapter.getGroup(groupPosition).title);
            }
        });
        recyclerAdapter.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public void onChildClick(View itemView, int groupPosition, int childPosition) {
                Team team = recyclerAdapter.getGroup(groupPosition);
                showToast(team.title + ": " +team.members.get(childPosition).name);
            }
        });

    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
