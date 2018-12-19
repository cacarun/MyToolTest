package com.mytooltest.progress.view;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mytooltest.R;

import java.lang.ref.WeakReference;
import java.util.Locale;

/**
 * 条纹进度条
 *
 */

public class RoundCornerProgressDialog extends DialogFragment {
    private static final String TAG = "RPDialog";
    public static final String F_TAG = "f_tag_ChangeUserProgressDialog";
    private int mDialogWid = -1;
    private int mDialogHeight = -1;
    private TextView mPercentTv;
    private RoundCornerImageView mProgressIv;
    private ImageView mBotIv;

    private ImageView mIvHead;
    private RelativeLayout mRlProgressLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_round_corner_progress, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPercentTv = view.findViewById(R.id.percent_tv);
        mProgressIv = view.findViewById(R.id.p_cover_iv);
        mBotIv = view.findViewById(R.id.p_bot_iv);
        mProgressIv.setRadiusDp(20);

        mRlProgressLayout = view.findViewById(R.id.progress_layout);
        mIvHead = view.findViewById(R.id.iv_head);
    }


    @Override
    public void onStart() {
        super.onStart();

        // 调整dialog的宽高
        Window window = getDialog().getWindow();
        if (null != window) {
            WindowManager.LayoutParams windowParams = window.getAttributes();
            windowParams.dimAmount = 0.0f;
            window.setAttributes(windowParams);
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            if (mDialogWid < 0) {
                mDialogHeight = (int) (dm.heightPixels * 0.4);
                mDialogWid = (int) (dm.widthPixels * 0.86);
            }
            window.setLayout(mDialogWid, mDialogHeight);
        }

        // 进度条
        handler.sendEmptyMessage(MSG_PROGRESS_GO);

        // 打断
        handler.postDelayed(soonRunnable, MILLIS_3000);

    }

    private static final int PROGRESS_MAX = 100;
    private static final int MSG_PROGRESS_GO = 1;

    private static final long MILLIS_3000 = 3000;
    private static final long MILLIS_5000 = 5000;
    private static final long MILLIS_500 = 500;

    private ValueAnimator valueAnimator;

    private Handler handler = new LoopHandler(this);

    private static class LoopHandler extends Handler {

        WeakReference<RoundCornerProgressDialog> wSwitcher;

        LoopHandler(RoundCornerProgressDialog switcher) {
            wSwitcher = new WeakReference<RoundCornerProgressDialog>(switcher);
        }

        @Override
        public void handleMessage(Message msg) {
            RoundCornerProgressDialog switcher = wSwitcher.get();
            if (switcher == null) {
                return;
            }

            Log.d(TAG, "handleMessage: " + msg.what);
            if (msg.what == MSG_PROGRESS_GO) {
                switcher.updatePercent(0, PROGRESS_MAX, MILLIS_5000);
            }
        }
    }

    private Runnable soonRunnable = new Runnable() {
        @Override
        public void run() {

            int percent = (int) valueAnimator.getAnimatedValue();

            Log.d(TAG, "runnableCode called... current percent: " + percent);

            // 进度条
            valueAnimator.cancel();
            if (percent < PROGRESS_MAX) {

//                valueAnimator.end();
                valueAnimator = null;
                updatePercent(percent, PROGRESS_MAX, MILLIS_500);
            }

        }
    };

    public void updatePercent(int from, int to, long duration) {

        valueAnimator = ValueAnimator.ofInt(from, to);
        valueAnimator.setDuration(duration);
        valueAnimator.start();

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                // 当前动画值，即为当前宽度比例值
                int percent = (int) animator.getAnimatedValue();
                Log.d(TAG, "valueAnimator addUpdateListener percent: " + percent);

                mPercentTv.setText(String.format(Locale.CHINA, "%2d%%", percent));

                float percentFloat = percent * 1.0f / PROGRESS_MAX;
                final int ivWidth = mBotIv.getWidth();
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mProgressIv.getLayoutParams();
                int marginEnd = (int) ((1 - percentFloat) * ivWidth);
                lp.width = ivWidth - marginEnd;
                mProgressIv.setLayoutParams(lp);

                RelativeLayout.LayoutParams headParams = (RelativeLayout.LayoutParams) mIvHead.getLayoutParams();
                float leftPosition = (mRlProgressLayout.getWidth() / PROGRESS_MAX) * percent + mRlProgressLayout.getLeft() - mIvHead.getWidth() / 2f;

                Log.d(TAG, "valueAnimator addUpdateListener head leftPosition: " + leftPosition);

                headParams.leftMargin = (int) Math.ceil(leftPosition);
//                mIvHead.setLayoutParams(headParams);
                mIvHead.requestLayout();
            }
        });

    }

}
