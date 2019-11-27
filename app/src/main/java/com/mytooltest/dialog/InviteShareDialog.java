package com.mytooltest.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.mytooltest.R;
import com.mytooltest.dialog.base.SuperDialog;
import com.mytooltest.util.FastClickUtil;
import com.mytooltest.util.Feedback;
import com.mytooltest.util.ToolUtil;


public class InviteShareDialog extends SuperDialog implements OnClickListener {

    private Activity activity;
    private Feedback feedback;


    public static final String PACKAGE_NAME_WHATSAPP = "com.whatsapp";
    public static final String PACKAGE_NAME_TWITTER = "com.twitter.android";
    public static final String PACKAGE_NAME_FACEBOOK = "com.facebook.katana";


    public static final int SHARE_DONE = 100;

    public InviteShareDialog(Activity activity, Feedback feedback) {
        super(activity, R.style.TranslucentFloatDialogWithAnim);
        this.activity = activity;
        this.feedback = feedback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_invite_share);

        initView();

    }

    public void initView() {
        findViewById(R.id.ll_whatsapp).setOnClickListener(this);
        findViewById(R.id.ll_twitter).setOnClickListener(this);
        findViewById(R.id.ll_facebook).setOnClickListener(this);
        findViewById(R.id.ll_more).setOnClickListener(this);

        findViewById(R.id.tv_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.tv_cancel) {

            dismiss();
            if (feedback != null) {

                feedback.onFeedback(R.id.tv_cancel);
            }
        } else {

            if (id == R.id.ll_more) {

                performCopyLink();
            } else if (id == R.id.ll_whatsapp) {

                performShare(100, PACKAGE_NAME_WHATSAPP);
            } else if (id == R.id.ll_twitter) {

                performShare(101, PACKAGE_NAME_TWITTER);
            } else if (id == R.id.ll_facebook) {

                performShare(102, PACKAGE_NAME_FACEBOOK);
            }

//            // 完成分享
//            dismiss();
            if (feedback != null) {

                feedback.onFeedback(SHARE_DONE);
            }
        }
    }

    private void performShare(int type, String packageName) {
        if (ToolUtil.isPackageInstalled(packageName, activity)) {

        } else {
            performCopyLink();
        }
    }

    private void performCopyLink() {
        if (FastClickUtil.isFastClick()) {
            return;
        }
    }

    @Override
    public void onBackPressed() {

    }
}