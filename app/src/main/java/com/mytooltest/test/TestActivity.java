package com.mytooltest.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.mytooltest.R;
import com.mytooltest.util.DeviceUtil;
import com.mytooltest.view.verifycode.VerifyCodeView;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    VerifyCodeView verifyCodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test);
        setContentView(R.layout.layout_test_sw);

//        findViewById(R.id.btn_test_rect).setOnClickListener(this);

//        findViewById(R.id.btn_test_inflate).setOnClickListener(this);
//
//
//        verifyCodeView = findViewById(R.id.verify_code_view);
//        verifyCodeView.setInputCompleteListener(new VerifyCodeView.InputCompleteListener() {
//            @Override
//            public void inputComplete() {
//                Toast.makeText(TestActivity.this, "inputComplete: " + verifyCodeView.getEditContent(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void invalidContent() {
//
//            }
//        });

//        initDatas();
//        listView = (ListView) findViewById(R.id.list_view);
//        animalAdapter = new AnimalAdapter(this, datas);
//        listView.setAdapter(animalAdapter);
    }

    private ListView listView;
    private AnimalAdapter animalAdapter;
    private List<Animal> datas = new ArrayList<>();

    private void initDatas() {
        Animal animal0 = new Animal("兔八哥", 1);
        Animal animal1 = new Animal("眼镜蛇", 2);
        Animal animal2 = new Animal("小金鱼", 3);
        Animal animal3 = new Animal("千里马", 4);
        Animal animal4 = new Animal("米老鼠", 5);
        Animal animal5 = new Animal("大国宝", 6);
        datas.add(animal0);
        datas.add(animal1);
        datas.add(animal2);
        datas.add(animal3);
        datas.add(animal4);
        datas.add(animal5);
    }
    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.btn_test_rect) {

//            animalAdapter.flag = 222;

//            animalAdapter.getData().clear();
//            animalAdapter.getData().addAll(datas);

//            animalAdapter.notifyDataSetChanged();

            DeviceUtil.getDeviceId();

        }
//        else if (id == R.id.btn_test_inflate) {
//            startActivity(new Intent(this, Test_Inflate_Activity.class));
//        }

    }

    private void testme() {

    }

}
