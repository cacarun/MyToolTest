package com.mytooltest.canvas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;

import com.mytooltest.R;
import com.mytooltest.canvas.path.WaveLoadingView;
import com.mytooltest.canvas.path.WaveLoadingView2;

public class CanvasTestActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "CanvasTestActivity";


    private WaveLoadingView mWaveLoadingView;
    private WaveLoadingView2 mWaveLoadingView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas_test);

        findViewById(R.id.btn_poster).setOnClickListener(this);




        // 水晶球波浪进度条 1
        mWaveLoadingView = findViewById(R.id.waveLoading);
        SeekBar seekBar1 = findViewById(R.id.seekBar);
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mWaveLoadingView.setPercent(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // 水晶球波浪进度条 2
        mWaveLoadingView2 = findViewById(R.id.wave_ball_progress_act_view);
        SeekBar seekBar2 = findViewById(R.id.seekBar2);
        seekBar2.setMax(100);
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mWaveLoadingView2.startProgress(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



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
