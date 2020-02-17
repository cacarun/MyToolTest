package com.mytooltest.algorithm;

import java.util.LinkedList;

public class TestAlgorithmMain {

    public static void main(String[] args) {


        // 打印菱形
//        printDiamond(9);

        // 约瑟夫环问题
//        int index = josephRingMethod(5, 4,3);
//        System.out.println("最后剩下" + index + "号");



        System.out.println("result=");

//        int[] arr = new int[] {2, 2, 1, 6, 3, 0};
//        printTest(arr);
//        for (int i : arr) {
//            System.out.print(i + " ");
//        }

//        String test = "123";
//        int n = test.length();
//        for (int i = 0; i < n; i++) {
//
//            for (int j = i + 1; j <= n; j++) {
//
//                String str = "";
//                for (int index = i; index < j; index++) {
//                    str = str + test.charAt(index);
//                }
//                System.out.println(str);
//
//            }
//
//        }








    }


    class TreeNode {
        int value;
        TreeNode left;
        TreeNode right;
        TreeNode(int value) {
            this.value = value;
        }
    }

    // 翻转二叉树
    private TreeNode revNode(TreeNode root) {
        if (root == null) {
            return null;
        }

        TreeNode left = revNode(root.left);
        TreeNode right = revNode(root.right);

        root.left = right;
        root.right = left;
        return root;
    }

    private void printNode(TreeNode root) {
        if (root == null) {
            return;
        }

        System.out.printf("" + root.value);

        printNode(root.left);
        
        printNode(root.right);
    }

    private static void printTest(int[] arr) {

        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 -i; j++) {
                if (arr[j] > arr[j+1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = tmp;
                }
            }
        }

    }




    /**
     * 打印菱形
     *
     *     *
     *    ***
     *   *****
     *  *******
     * *********
     *  *******
     *   *****
     *    ***
     *     *
     *
     * 解析：将菱形分成上下两个三角形，分析每行空格数和星号个数的关系
     *
     */
    private static void printDiamond(int num) {

        int n = (num + 1) / 2; // 上半部分行数

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n - i; j++) {
                System.out.print(" ");
            }
            for (int j = 1; j <= 2 * i - 1; j++) {
                System.out.print("*");
            }
            System.out.println();
        }

        for (int i = n - 1; i > 0; i--) {
            for (int j = 1; j <= n - i; j++) {
                System.out.print(" ");
            }
            for (int j = 1; j <= 2 * i - 1; j++) {
                System.out.print("*");
            }
            System.out.println();
        }

    }

    /**
     * 算法之约瑟夫环问题：有n个人排成一列或是一圈，从编号为k的人开始报数，数到m的那个人出列，最后一个人的编号是多少？
     *
     * @param n
     * @param m
     * @return
     */
    private static int josephRingMethod(int n, int k, int m) {

        LinkedList<Integer> linkedList = new LinkedList();
        for (int i = 1; i <= n; i++) {
            linkedList.add(i);
        }

        int index = (m - 1) + (k - 1);  //初始化，index指向第一个出列的人
        while (linkedList.size() != 0) {
            System.out.println(linkedList);
            //用 **索引 % 链表长度** 进行取余操作，避免下标越界
            index %= linkedList.size();

            //出列
            System.out.println(linkedList.get(index) + "号出列");
            linkedList.remove(index);

            //返回最后剩下的人的编号
            if (linkedList.size() == 1) {
                return linkedList.get(0);
            }
            //新的索引就要从出列的人重新开始数，再次数到m-1
            index += m - 1;
        }

        return -1;
    }


}
