package com.mytooltest.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Created by jarvis on 2018/10/25.
 */

public class InviteUtil {

    public static void shareToSNS(Context context, String title, String text) {

        if (context == null) {

            return;
        }

        if (text == null || text.isEmpty()) {

            return;
        }

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");

        context.startActivity(Intent.createChooser(sendIntent, title));
    }

    public static void shareToSNS(Context context, String title, String text, String filePath) {

        Uri fileUri = Uri.fromFile(new File(filePath));

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("image/jpeg");

        context.startActivity(Intent.createChooser(sendIntent, title));
    }
}
