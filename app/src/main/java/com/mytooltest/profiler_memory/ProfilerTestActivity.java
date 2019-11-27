package com.mytooltest.profiler_memory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mytooltest.R;

public class ProfilerTestActivity extends AppCompatActivity implements View.OnClickListener {

    private ListItem40MClass head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_click);


        findViewById(R.id.btn_click).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.btn_click) {
            if (head == null) {
                head = new ListItem40MClass();
            } else {
                ListItem40MClass tmp = head;
                while (tmp.next != null) {
                    tmp = tmp.next;
                }
                tmp.next = new ListItem40MClass();
            }
        }
    }


}
