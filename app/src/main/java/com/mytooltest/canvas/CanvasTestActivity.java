package com.mytooltest.canvas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mytooltest.R;

public class CanvasTestActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "CanvasTestActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas_test);

        findViewById(R.id.btn_poster).setOnClickListener(this);






    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {

            case R.id.btn_poster:

                this.startActivity(new Intent(this, PosterActivity.class));
                break;
        }

    }
}
