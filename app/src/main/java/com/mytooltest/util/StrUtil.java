package com.mytooltest.util;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.ParcelableSpan;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class StrUtil {


    public static int size(List dataList) {
        if (dataList != null && dataList.size() > 0) {
            return dataList.size();
        }

        return 0;
    }

    public static int size(Map dataList) {
        if (dataList != null && dataList.size() > 0) {
            return dataList.size();
        }

        return 0;
    }

    public static SpannableString getSpannableString(String targetStr, String originStr,
                                                     List<ParcelableSpan> spanList, int spanType) {
        if (TextUtils.isEmpty(targetStr) || TextUtils.isEmpty(originStr)) {
            return null;
        }

        if (spanList == null || spanList.size() == 0) {
            return null;
        }

        SpannableString spanString = new SpannableString(originStr);

        int startIndex = originStr.indexOf(targetStr);
        if (startIndex >= 0) {
            int endIndex = startIndex + targetStr.length();

            for (ParcelableSpan spanItem : spanList) {
                spanString.setSpan(spanItem, startIndex, endIndex, spanType);
            }
        }

        return spanString;
    }

    /**
     * 尾部匹配
     *
     * @param targetStr
     * @param originStr
     * @param spanList
     * @param spanType
     * @return
     */
    public static SpannableString getSpannableStringMatchTail(String targetStr, String originStr,
                                                              List<ParcelableSpan> spanList, int spanType) {
        if (TextUtils.isEmpty(targetStr) || TextUtils.isEmpty(originStr)) {
            return null;
        }

        if (spanList == null || spanList.size() == 0) {
            return null;
        }

        SpannableString spanString = new SpannableString(originStr);

        int startIndex = originStr.lastIndexOf(targetStr);
        if (startIndex >= 0) {
            int endIndex = startIndex + targetStr.length();

            for (ParcelableSpan spanItem : spanList) {
                spanString.setSpan(spanItem, startIndex, endIndex, spanType);
            }
        }

        return spanString;
    }

    public static SpannableString getClickableSpannableString(String targetStr, String originStr,
                                                              List<ParcelableSpan> spanList,
                                                              ClickableSpan clickableSpan,
                                                              int spanType) {
        SpannableString spanString = getSpannableString(targetStr, originStr, spanList, spanType);

        if (spanString != null) {
            int startIndex = originStr.indexOf(targetStr);
            if (startIndex >= 0) {
                int endIndex = startIndex + targetStr.length();

                spanString.setSpan(clickableSpan, startIndex, endIndex, spanType);
            }
        }

        return spanString;
    }

    public static SpannableString getFormatSpannableString(Activity activity, String priceStr,
                                                           String priceStrFormat, int colorResourceId,
                                                           float proportion) {
        if (colorResourceId == 0 || proportion == 0) {
            return null;
        }

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(activity, colorResourceId));
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(proportion);

        List<ParcelableSpan> spanList = new ArrayList<>();
        spanList.add(colorSpan);
        spanList.add(sizeSpan);

        return getSpannableString(priceStr, priceStrFormat, spanList, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
    }

    /**
     *
     * @param context
     * @param str
     * 这个是用来着色的字符串
     * @param strFormat
     * 这个是长的原始字符串,包含被着色的字符串的大字符串
     * @param colorRes
     * 颜色资源的id,如R.color.red
     * @return
     * 着色后的SpannableString
     */
    public static SpannableString getSpannableString(Context context, String str, String strFormat, int colorRes) {
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(context, colorRes));

        List<ParcelableSpan> spanList = new ArrayList<>();
        spanList.add(colorSpan);

        return getSpannableString(str, strFormat, spanList, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
    }


    /**
     * 字符串多个子字符串需要着色
     *
     * @param context
     * @param originalString
     * @param colorRes
     * @param subStrArray
     * @return
     */
    public static SpannableString getSpannableString(Context context, String originalString, int colorRes, String... subStrArray) {

        SpannableString spanString = new SpannableString(originalString);

        Map<Integer, Integer> indexMap = new HashMap<>();

        for (String str : subStrArray) {
            int index = originalString.indexOf(str);
            indexMap.put(index, index + str.length());
        }

        for (int k : indexMap.keySet()) {
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(context, colorRes));
            spanString.setSpan(colorSpan, k, indexMap.get(k), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }

        return spanString;

    }

    /**
     * 字符串多个子字符串需要着色和字体改变
     *
     * @param context
     * @param originalString
     * @param textSize
     * @param colorRes
     * @param subStrArray
     * @return
     */
    public static SpannableString getSpannableString(Context context, String originalString, int textSize, int colorRes, String... subStrArray) {

        SpannableString spanString = new SpannableString(originalString);

        Map<Integer, Integer> indexMap = new HashMap<>();

        for (String str : subStrArray) {
            int index = originalString.indexOf(str);
            indexMap.put(index, index + str.length());
        }

        for (int k : indexMap.keySet()) {

            ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(context, colorRes));
            spanString.setSpan(colorSpan, k, indexMap.get(k), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

            AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(textSize, true);
            spanString.setSpan(sizeSpan, k, indexMap.get(k), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }

        return spanString;

    }

    /**
     * 小数点后两位缩小
     *
     * @param tokenNum
     * @param proportion
     * @return
     */
    public static SpannableString getPriceValueSpanString(double tokenNum, float proportion) {
        // 格式化两位小数
        String num = new DecimalFormat("0.00").format(tokenNum);
        String tokenIntPart = num;
        if (num.contains(".")) {
            tokenIntPart = num.split("\\.")[1];
        }

        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(proportion);

        List<ParcelableSpan> spanList = new ArrayList<>();
        spanList.add(sizeSpan);

        return getSpannableStringMatchTail(tokenIntPart, num, spanList, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

    }

    /**
     * 小数点后两位缩小
     *
     * @param tokenNum
     * @param formatStr
     * @param proportion
     * @return
     */
    public static SpannableString getPriceValueSpanString(double tokenNum, String formatStr, float proportion) {
        // 格式化两位小数
        String num = new DecimalFormat("0.00").format(tokenNum);
        String tokenIntPart = num;
        if (num.contains(".")) {
            tokenIntPart = num.split("\\.")[1];
        }

        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(proportion);

        List<ParcelableSpan> spanList = new ArrayList<>();
        spanList.add(sizeSpan);

        return getSpannableStringMatchTail(tokenIntPart, formatStr, spanList, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

    }

    public static String randomFromList(List<String> valueList) {
        int valueSize = size(valueList);
        if (valueSize > 0) {

            Random rand = new Random();
            int n = rand.nextInt(valueSize);
            return valueList.get(n);
        }

        return null;
    }

    public static File randomFileFromList(List<File> valueList) {
        int valueSize = size(valueList);
        if (valueSize > 0) {

            Random rand = new Random();
            int n = rand.nextInt(valueSize);
            return valueList.get(n);
        }

        return null;
    }

    public static String getStringFromList(List<String> strList) {
        if (size(strList) == 0) {
            return "";
        }

        String listString = "";
        for (String s : strList) {

            listString += s + "\t";
        }

        return listString;
    }

    public static String valueOf(double value) {
        return String.valueOf(value).replaceAll("0+?$", "").replaceAll("[.]$", "");
    }
}
