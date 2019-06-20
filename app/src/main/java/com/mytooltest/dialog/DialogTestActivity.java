package com.mytooltest.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mytooltest.R;
import com.mytooltest.util.Feedback;

public class DialogTestActivity extends AppCompatActivity implements View.OnClickListener {

    private Dialog dialog;

    private Feedback feedback = new Feedback() {
        @Override
        public void onFeedback(int viewId) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_test);

        findViewById(R.id.btn_test).setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.btn_test) {

//            GuideRemindDialog dialog = new GuideRemindDialog(this, feedback);
//            dialog.show();


//            dialog = new InviteShareDialog(this, feedback);
//            dialog.show();
//            ToolsForImage.getWindowAttributes(dialog, this); // 宽度 match


//            dialog = new ProgressRunningDialog(this, feedback);
//            dialog.show();
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    ((ProgressRunningDialog) dialog).finishProgress(feedback);
//                }
//            }, 3000);


//            dialog = new FlipperDialog(this, feedback);
//            dialog.show();


            dialog = new NumberRunningDialog(this, "100", "≈ 10.00 USD");
            dialog.show();

        }

    }

}
