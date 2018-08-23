package com.mytooltest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.mytooltest.banner.BannerActivity;
import com.mytooltest.encryption.DTUploadCreditCardPhotoCmd;
import com.mytooltest.encryption.UploadCreditCardPhotoTask;
import com.mytooltest.tab.TabActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.btn_banner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, BannerActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        findViewById(R.id.btn_tab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, TabActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

//        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                uploadCreditCardPhoto();
//            }
//        });
//
//        SDCardUtil.createFile2SDCard();


//        String dtStart = "2018-04-20 10:00:30";
//
//        String dtEnd   = "2018-04-19 09:00:40";
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        try {
//            Date dateStart = format.parse(dtStart);
//            Date dateEnd = format.parse(dtEnd);
//
//            int gapDay = ToolsForTime.getGapCount(dateStart.getTime(), dateEnd.getTime());
//            System.out.println("gap day: " + gapDay);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }



    }

    private void compressImage() {

        byte[] mPhoto = null;



    }

    public void uploadCreditCardPhoto() {
        Log.i(TAG, "Credit Card Optimize, uploadCreditCardPhoto");


        DTUploadCreditCardPhotoCmd cmd = new DTUploadCreditCardPhotoCmd();

//        cmd.time = "" + System.currentTimeMillis();
        cmd.time = "1514298066966";
        String creditCardInfo = "411111XXXXXX1111" + "_" + cmd.time;
        String backInfo = creditCardInfo; // TODO jarvis base64

        cmd.appName = "TalkU";
        cmd.deviceId = "And.0e20f3310ada38cd9b6ece7d04f151ca.dttalk";
        cmd.userId = "7182012637234297";


        cmd.pstage = 2;
        cmd.numPart = "411111XXXXXX1111";
        cmd.backInfo = backInfo;
        cmd.fileUrl = "";

        // default
        cmd.fileType = "jpg";
        cmd.keyId = 0;

        String creditCardKey = String.format("%s_%s_%s_%s_%s.%s",
                cmd.userId, cmd.deviceId, cmd.keyId, cmd.time, "411111", cmd.fileType);
        cmd.creditCardKey = creditCardKey;

        UploadCreditCardPhotoTask task = new UploadCreditCardPhotoTask(cmd);
        task.execute();
    }






}
