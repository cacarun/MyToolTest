package com.mytooltest.mylist.grouplist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mytooltest.R;

import java.util.ArrayList;

public class GroupListActivity extends AppCompatActivity implements View.OnClickListener {

    private CategoryAdapter mCustomBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_group_list);

        ListView listView = findViewById(R.id.list_item);

        // 数据
        ArrayList<Category> listData = getData();

        mCustomBaseAdapter = new CategoryAdapter(getBaseContext(), listData);

        // 适配器与ListView绑定
        listView.setAdapter(mCustomBaseAdapter);

        listView.setOnItemClickListener(new ItemClickListener());

    }

    private class ItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Toast.makeText(getBaseContext(),  (String)mCustomBaseAdapter.getItem(position),
                    Toast.LENGTH_SHORT).show();
        }

    }


    /**
     * 创建测试数据
     */
    private ArrayList<Category> getData() {

        ArrayList<Category> listData = new ArrayList<>();

        Category categoryOne = new Category("路人甲");
        categoryOne.addItem("马三立");
        categoryOne.addItem("赵本山");
        categoryOne.addItem("郭德纲");
        categoryOne.addItem("周立波");

        Category categoryTwo = new Category("事件乙");
        categoryTwo.addItem("**贪污");
        categoryTwo.addItem("**照门");

        Category categoryThree = new Category("书籍丙");
        categoryThree.addItem("10天学会***");
        categoryThree.addItem("**大全");
        categoryThree.addItem("**秘籍");
        categoryThree.addItem("**宝典");
        categoryThree.addItem("10天学会***");
        categoryThree.addItem("10天学会***");
        categoryThree.addItem("10天学会***");
        categoryThree.addItem("10天学会***");

        Category categoryFour = new Category("书籍丙");
        categoryFour.addItem("河南");
        categoryFour.addItem("天津");
        categoryFour.addItem("北京");
        categoryFour.addItem("上海");
        categoryFour.addItem("广州");
        categoryFour.addItem("湖北");
        categoryFour.addItem("重庆");
        categoryFour.addItem("山东");
        categoryFour.addItem("陕西");

        listData.add(categoryOne);
        listData.add(categoryTwo);
        listData.add(categoryThree);
        listData.add(categoryFour);

        return listData;
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

    }

}
