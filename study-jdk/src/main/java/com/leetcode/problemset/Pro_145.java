package com.leetcode.problemset;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个二叉树，返回它的 后序 遍历。
 */
public class Pro_145 {

    public static void main(String[] args) {
        TreeNode three = new TreeNode(3, null, null);
        TreeNode two = new TreeNode(2,three,null);
        TreeNode root = new TreeNode(1,null,two);
        List<Integer> list = postorderTraversal(root);
        System.out.println(list);
    }

    public static List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        saveNode(root,list);
        return list;
    }

    private static void saveNode(TreeNode node,List<Integer> list){
        if (node==null){
            return;
        }
        saveNode(node.left,list);
        saveNode(node.right,list);
        list.add(node.val);
    }


    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
  }
}
