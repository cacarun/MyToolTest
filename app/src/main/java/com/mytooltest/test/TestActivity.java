package com.mytooltest.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.mytooltest.R;
import com.mytooltest.view.verifycode.VerifyCodeView;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    VerifyCodeView verifyCodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        findViewById(R.id.btn_test_rect).setOnClickListener(this);
        findViewById(R.id.btn_test_inflate).setOnClickListener(this);


        verifyCodeView = findViewById(R.id.verify_code_view);
        verifyCodeView.setInputCompleteListener(new VerifyCodeView.InputCompleteListener() {
            @Override
            public void inputComplete() {
                Toast.makeText(TestActivity.this, "inputComplete: " + verifyCodeView.getEditContent(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void invalidContent() {

            }
        });
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.btn_test_rect) {
            startActivity(new Intent(this, Test_Rect_Activity.class));
        } else if (id == R.id.btn_test_inflate) {
            startActivity(new Intent(this, Test_Inflate_Activity.class));
        }

    }


}
