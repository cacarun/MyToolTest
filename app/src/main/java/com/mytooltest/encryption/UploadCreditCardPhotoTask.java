/**
 * UploadMyFacebookHeadPhotoTask.java
 * Copyright 2013 Dingtone Inc.
 *
 * All right reserved.
 *
 * Created on 2013-7-10上午9:49:50
 */

package com.mytooltest.encryption;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.mytooltest.util.SDCardUtil;
import com.mytooltest.util.ToolsForImage;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class UploadCreditCardPhotoTask extends AsyncTask<Void, Void, Void> {

    private static final String TAG = "Task";

    private DTUploadCreditCardPhotoCmd cmd;

    public UploadCreditCardPhotoTask(DTUploadCreditCardPhotoCmd cmd) {
        this.cmd = cmd;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        if (cmd != null) {

            try {
                Log.i(TAG, "UploadCreditCardPhotoTask fileUrl:" + cmd.fileUrl);
//                byte[] mPhoto = ToolsForImage.ReadStream(new FileInputStream(cmd.fileUrl));

//                String takePhotoPath = SDCardUtil.MEDIA_PATH + "test.jpg";
//                byte[] mPhoto = ToolsForImage.ReadStream(new FileInputStream(takePhotoPath));

                List<String> fileList = new ArrayList<>();
                fileList.add(SDCardUtil.MEDIA_PATH + "8.jpg"); // 6       6.3
                fileList.add(SDCardUtil.MEDIA_PATH + "25.jpg"); // 23     22.63
                fileList.add(SDCardUtil.MEDIA_PATH + "160.jpg"); // 156   155.97
                fileList.add(SDCardUtil.MEDIA_PATH + "442.jpg"); // 429   428.73
                fileList.add(SDCardUtil.MEDIA_PATH + "643.jpg"); // 625   624.70
                fileList.add(SDCardUtil.MEDIA_PATH + "36m.jpg"); // 3.4M  3.38m
                fileList.add(SDCardUtil.MEDIA_PATH + "37m.jpg"); // 3.5M  3.52m

                for (String file : fileList) {

                    Bitmap photoBitmap = ToolsForImage.GetCompressPicFromFile(file);
                    int size = ToolsForImage.sizeOf(photoBitmap);
                    Log.i(TAG, "Credit Card Optimize, 0000 Bitmap size:" + size);

                    if (photoBitmap != null) {
                        byte[] mPhoto = ToolsForImage.bitmap2BytesWithJPEG(photoBitmap);
                        Log.i(TAG, "Credit Card Optimize, 1111 mPhoto length:" + mPhoto.length);


                        String photoBase64 = new String(Base64.encode(mPhoto, Base64.NO_WRAP));
                        String encryptedText = DtUtil.encryptCreditCardPhotoAESData(photoBase64.getBytes(),
                                cmd.userId, cmd.deviceId, cmd.creditCardKey);
                        if (encryptedText != null && !encryptedText.equals("")) {

                            byte[] rsaPhotoBytes = RSACoder.encryptByPublicKey(encryptedText.getBytes(), Keys.PUBLIC_CREDIT_CARD_PHOTO);

                            cmd.fContent  = new String(Base64.encode(rsaPhotoBytes, Base64.NO_WRAP));

                            Log.d(TAG, "Credit Card Optimize, 22222 base64 length:" + cmd.fContent.getBytes().length);


                            File f = new File(file.substring(0, file.length() - 4) + "_compress44.log");
                            FileOutputStream fs = new FileOutputStream(f);
                            fs.write(cmd.fContent.getBytes());
                            fs.flush();
                            fs.close();
                        }
                    }
                }


//                byte[] mPhoto = "hello".getBytes();
//
//                if (mPhoto != null && mPhoto.length > 0) {
//
////                    DTLog.i(TAG, "UploadCreditCardPhotoTask mPhoto");
////                    cmd.fContent = DTBase64.encode(mPhoto, DTBase64.DEFAULT);
////                    DTLog.i(TAG, "UploadCreditCardPhotoTask cmd.fContent upload!!!");
////                    TpClient.getInstance().uploadCreditCardPhoto(cmd);
//
//                    /////////////  test ////////////
////                    byte[] bb = DtUtil.encryptCreditCardPhotoAESData("creditcard_jarvis001".getBytes(),
////                            "userId", "deviceId", "reqInfo.fileName");
////                    String test = new String(Base64.encode(bb, Base64.DEFAULT), "UTF-8");
////                    DTLog.i(TAG, "UploadCreditCardPhotoTask mPhoto test:" + test);
////
//                    File f = new File(SDCardUtil.SUPPORT_PATH + "creditcardfile.log");
//                    FileOutputStream fs = new FileOutputStream(f);
//                    fs.write(test.getBytes());
//                    fs.flush();
//                    fs.close();
//                    /////////////  test ////////////
//
//
//                    /**
//                     *
//                     * read byte[] ==> Base64 ==> AES ==> Base64;
//                     * ==> RSA ==> Base64 ==> URL encode
//                     *
//                     */
//                    String firstBase64 = new String(Base64.encode(mPhoto, Base64.NO_WRAP));
//
//                    byte[] aesPhotoBytes = DtUtil.encryptCreditCardPhotoAESData(firstBase64.getBytes(), cmd.userId,
//                            cmd.deviceId, cmd.creditCardKey);
//
//                    if (aesPhotoBytes != null && aesPhotoBytes.length > 0) {
//
//                        try {
//
////                            String encryptedText = new String(Base64.encode(aesPhotoBytes, Base64.NO_WRAP)); // "UTF-8"  1111
//
//
////                            encryptedText = new String(Base64.encode(encryptedText.getBytes(), Base64.DEFAULT));
////                            cmd.fContent = android.net.Uri.encode(encryptedText);
//
//                            Log.d(TAG, "cmd.userId:" + cmd.userId
//                                    + "\ncmd.deviceId:" + cmd.deviceId + "\ncmd.creditCardKey:" + cmd.creditCardKey);
////                            File f = new File(SDCardUtil.MEDIA_PATH + "creditcardfile.log");
////                            FileOutputStream fs = new FileOutputStream(f);
////                            fs.write(cmd.fContent.getBytes());
////                            fs.flush();
////                            fs.close();
//
////                            Log.d(TAG, "[AES Base64]:" + encryptedText);  // 1111
//
////                            byte[] rsaPhotoBytes = RSACoder.encryptByPublicKey(encryptedText.getBytes(), Keys.PUBLIC_CREDIT_CARD_PHOTO);   111
//                            byte[] rsaPhotoBytes = RSACoder.encryptByPublicKey(aesPhotoBytes, Keys.PUBLIC_CREDIT_CARD_PHOTO);
//
//                            Log.d(TAG, "[RSA]:" + rsaPhotoBytes);
//
//                            String bb = Coder.encryptBASE64(rsaPhotoBytes);
//                            Log.d(TAG, "[RSA Base64]:" + bb);
//
//                            cmd.fContent = android.net.Uri.encode(bb);
//                            Log.d(TAG, "[RSA Base64 encode]:" + cmd.fContent);
//
//
////                            cmd.fContent = Coder.encryptBASE64(rsaPhotoBytes);
//
//                            File f = new File(SDCardUtil.MEDIA_PATH + "creditCardFileTest.log");
//                            FileOutputStream fs = new FileOutputStream(f);
//                            fs.write(cmd.fContent.getBytes());
//                            fs.flush();
//                            fs.close();
//
//                            Log.i(TAG, "UploadCreditCardPhotoTask cmd.fContent upload!!!");
////                            TpClient.getInstance().uploadCreditCardPhoto(cmd);
//
////                            if (rsaPhotoBytes != null && rsaPhotoBytes.length > 0) {
////                                DTLog.i(TAG, "Credit Card Optimize, save encrypt photo file:" + rsaPhotoBytes.toString());
////                                File f = new File(SDCardUtil.SUPPORT_PATH + "creditcardfile.log");
////                                FileOutputStream fs = new FileOutputStream(f);
////                                fs.write(rsaPhotoBytes);
////                                fs.flush();
////                                fs.close();
////                            }
//
//                        } catch (Exception e) {
//                            Log.e(TAG, "UploadCreditCardPhotoTask exception2 e " + e.getMessage());
//                        }
//                    }
//
//                } else {
//
//                    Log.i(TAG, "UploadCreditCardPhotoTask can't get photo");
//                }
//            } catch (Exception e) {
//                Log.e(TAG, "UploadCreditCardPhotoTask exception e " + e.getMessage());
//            }
//        }
            } catch (Exception e) {

            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
    }
}
