package com.mytooltest.mylist.headerfooter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mytooltest.R;

import java.util.ArrayList;
import java.util.List;

public class HeaderFooterListActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "HeaderFooterList";

    private ListView listView;
    private MyListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_footer_list);

        listView = findViewById(R.id.list_view);

        List<String> lists = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            lists.add("我是数据" + i);
        }
        adapter = new MyListAdapter(this, lists);

        TextView headerView = new TextView(this);
        headerView.setText("我是 footer view");
        headerView.setBackgroundColor(Color.RED);

//        headerView.setOnClickListener(this);

        // 需要在 setAdapter 之前
//        listView.addHeaderView(headerView);
        listView.addFooterView(headerView);

        listView.setAdapter(adapter);

        Log.d(TAG, "初始 adapter：" + adapter.toString());
        Log.d(TAG, "添加了 footer，新获取的 adapter：" + listView.getAdapter().toString());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Log.d(TAG, "onItemClick position=" + position);

                // 添加了headerView之后，headerView是列表中的第一项，所以在
                // 处理点击事件时，需要根据位置信息，以及对应的数据进行处理的
                // 时候，需要将 position - 1，得到对应的数据信息，然后做处理

                // 这个能取到真实 item 数据
                String item = (String) parent.getAdapter().getItem(position);
                // 这个取不到真实 item 数据
//                String item = adapter.getItem(position);

                Toast.makeText(HeaderFooterListActivity.this, "position=" + position + " item=" + item, Toast.LENGTH_LONG).show();
            }
        });

    }


    class MyListAdapter extends BaseAdapter {

        private List<String> mLists;
        private Context mContext;

        public MyListAdapter(Context context, List<String> lists) {
            this.mContext = context;
            this.mLists = lists;
        }

        @Override
        public int getCount() {
            return mLists.size();
        }

        @Override
        public String getItem(int position) {
            return mLists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(mContext);
            textView.setText(mLists.get(position) + "我在 list view 中的位置是" + position);
            return textView;
        }
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        Log.d(TAG, "onClick...");
    }

}
