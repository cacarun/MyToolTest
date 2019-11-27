package com.mytooltest.mylist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mytooltest.R;
import com.mytooltest.mylist.grouplist.GroupListActivity;
import com.mytooltest.mylist.grouprecycler.GroupRecyclerActivity;
import com.mytooltest.mylist.headerfooter.HeaderFooterListActivity;

public class MyListActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);

        findViewById(R.id.btn_group_list).setOnClickListener(this);
        findViewById(R.id.btn_group_recycler).setOnClickListener(this);
        findViewById(R.id.btn_header_footer_list).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.btn_group_list:

                this.startActivity(new Intent(this, GroupListActivity.class));
                break;
            case R.id.btn_group_recycler:

                this.startActivity(new Intent(this, GroupRecyclerActivity.class));
                break;
            case R.id.btn_header_footer_list:

                this.startActivity(new Intent(this, HeaderFooterListActivity.class));
                break;
        }

    }

}
