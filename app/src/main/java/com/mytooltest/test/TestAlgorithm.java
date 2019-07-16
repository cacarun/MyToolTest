package com.mytooltest.test;

import com.mytooltest.algorithm.Solution;

public class TestAlgorithm {

    public static void main(String[] args) {

        // 无重复字符的最长子串 - 暴力法
//        System.out.println("Result=" + new Solution().lengthOfLongestSubstring("abcabc"));
        // 无重复字符的最长子串 - 滑动窗口
        System.out.println("Result=" + new Solution().lengthOfLongestSubstring3("pwwkew"));
    }

}
