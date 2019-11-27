package com.mytooltest.test;

public class TestAlgorithm {

    public static void main(String[] args) {

        // 无重复字符的最长子串 - 暴力法
//        System.out.println("Result=" + new Solution().lengthOfLongestSubstring("abcabc"));
        // 无重复字符的最长子串 - 滑动窗口
//        System.out.println("Result=" + new Solution().lengthOfLongestSubstring3("pwwkew"));


//        String inputNumber = "(417)567-0121";
//        String inputNumber = "(417) 567-0121";
        String inputNumber = "(417) 567-=0121.";

        String unFormatted = inputNumber.replaceAll("[^\\d]*", "");
        System.out.println(unFormatted);
    }

}
