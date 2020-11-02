package com.leetcode.problemset;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个二叉搜索树的根节点 root，返回树中任意两节点的差的最小值。
 */
public class Pro_530 {

    public static void main(String[] args) {
        Pro_530 test = new Pro_530();
        int[] arr = new int[]{90,69,49,89,52};
        TreeNode root = test.initRoot(arr);
        System.out.println(test.minDiffInBST(root));
    }

    private int minDiffInBST(TreeNode root) {
        if (root==null) return -1;
        dfs(root);
        return res;
    }

    private TreeNode pre = null;

    private int res = Integer.MAX_VALUE;

    private void dfs(TreeNode node){
        if(node == null) return;
        dfs(node.left);
        if(pre != null){
            res = Math.min(node.val-pre.val,res);   //记录最小
        }
        pre = node;
        dfs(node.right);
    }

    private TreeNode initRoot(int[] arr){
        TreeNode root = null;
        for (int i = 0; i < arr.length; i++) {
            if (root==null){
                root = new TreeNode(arr[i]);
                continue;
            }
            addNode(root,arr[i]);
        }
        return root;
    }

    private void addNode(TreeNode root,int val){
        if (root.val > val){
            if (root.left==null){
                root.left = new TreeNode(val);
            }else {
                addNode(root.left,val);
            }
            return;
        }
        if (root.right ==null){
            root.right = new TreeNode(val);
            return;
        }
        addNode(root.right,val);
    }

    private class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    /**
     *
     * ababcbacadefegdehijhklij
     *
     * "ababcbaca", "defegde", "hijhklij"。
     *
     * ababcbahcadefegdehijhklij
     */
    public static class Pro_763 {

        public static void main(String[] args) {
            System.out.println(partitionLabels("ababcbacadefegdehijhklij"));
        }

        public static List<Integer> partitionLabels(String S) {
            if (S==null){
                return null;
            }
            List<Integer> list = new ArrayList<>();
            int length = S.length();
            //存放每个字母最后出现的位置
            int[] wordsIndex = new int[26];
            for (int i = 0; i < length; i++) {
                wordsIndex[S.charAt(i)-'a']= i;
            }
            int i = 0 ,pre = 0;
            while (i < length){
                int lastIndex = wordsIndex[S.charAt(i)-'a'];
                while (i<=lastIndex){
                    int l = wordsIndex[S.charAt(i)-'a'];
                    lastIndex = Math.max(lastIndex, l);;
                    i++;
                }
                list.add(lastIndex - pre+1);
                pre = lastIndex+1;
            }
            return list;
        }
    }
}
