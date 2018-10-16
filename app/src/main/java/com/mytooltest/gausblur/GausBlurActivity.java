package com.mytooltest.gausblur;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mytooltest.R;
import com.mytooltest.gausblur.util.BlurBitmap;
import com.mytooltest.gausblur.util.RenderScriptGaussianBlur;

public class GausBlurActivity extends AppCompatActivity {

    private static final String TAG = "GausBlurActivity";

    private ImageView imageView;
    private ImageView container;
    private LinearLayout layout;
    private TextView textViewProgress;
    private RenderScriptGaussianBlur blur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gausblur);

        imageView = (ImageView) findViewById(R.id.imageView);
        container = (ImageView) findViewById(R.id.container);

        container.setVisibility(View.GONE);

        layout = (LinearLayout) findViewById(R.id.layout);

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        textViewProgress = (TextView) findViewById(R.id.textViewProgress);

        Button btnDialog = (Button) findViewById(R.id.btn_dialog);
        blur = new RenderScriptGaussianBlur(GausBlurActivity.this);

        seekBar.setMax(25);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewProgress.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int radius = seekBar.getProgress();
                if (radius < 1) {
                    radius = 1;
                }
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_gaus_blur);
                imageView.setImageBitmap(blur.gaussianBlur(radius, bitmap));
            }
        });

        btnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                container.setVisibility(View.VISIBLE);

                layout.setDrawingCacheEnabled(true);
                layout.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);

                Bitmap bitmap = layout.getDrawingCache();

                container.setImageBitmap(blur.gaussianBlur(25, bitmap));

                AlertDialog dialog = new AlertDialog.Builder(GausBlurActivity.this).create();
                dialog.setTitle("Title");
                dialog.setMessage("Message");
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        container.setVisibility(View.GONE);
                    }
                });
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        container.setVisibility(View.GONE);
                    }
                });

                dialog.show();
            }
        });

        ///////////////////////

        Button btnDialog2 = (Button) findViewById(R.id.btn_dialog2);
        btnDialog2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBlurDialog();
            }
        });

    }

    /**
     * 1) 把dialog弹层铺满整个屏幕
     * 2) 通过应用内截图的方式获得弹层背后的图片bitmap
     * 3) 通过高斯模糊算法对截图进行模糊处理
     * 4) 把模糊后的图片bitmap设置成dialog的background
     *
     */
    private void showBlurDialog() {
        final Dialog dialog = new Dialog(GausBlurActivity.this, R.style.SquareEntranceDialogStyle);
        Window window = dialog.getWindow();
        Bitmap blurBg = null;
        if (window != null) {
            long startMs = System.currentTimeMillis();

            // 获取截图
            View activityView = getWindow().getDecorView();
            activityView.setDrawingCacheEnabled(true);
            activityView.destroyDrawingCache();
            activityView.buildDrawingCache();
            Bitmap bmp = activityView.getDrawingCache();
            Log.d(TAG, "getDrawingCache take away:" + (System.currentTimeMillis() - startMs) + "ms");

            // 模糊处理并保存
            blurBg = BlurBitmap.blur(GausBlurActivity.this, bmp);
            Log.d(TAG, "blur take away:" + (System.currentTimeMillis() - startMs) + "ms");

            // 设置成dialog的背景
            window.setBackgroundDrawable(new BitmapDrawable(getResources(), blurBg));
            bmp.recycle();
        }
        final Bitmap finalBlurBg = blurBg;
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                // 对话框取消时释放背景图bitmap
                if (finalBlurBg != null && !finalBlurBg.isRecycled()) {
                    finalBlurBg.recycle();
                }
            }
        });
        View layout = View.inflate(GausBlurActivity.this, R.layout.dialog_gaus_blur, null);
        dialog.setContentView(layout);
        dialog.show();
    }
}
