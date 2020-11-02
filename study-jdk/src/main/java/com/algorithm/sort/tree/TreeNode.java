package com.algorithm.sort.tree;


public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;
    public TreeNode(int x) { val = x; }

    public static TreeNode initRoot(int[] arr){
        if (arr==null||arr.length<1){
            return null;
        }
        TreeNode root = new TreeNode(arr[0]);
        if (arr.length<2){
            return root;
        }
        for (int i = 1; i < arr.length; i++) {
            addNode(root,arr[i]);
        }
        return root;
    }

    public static void addNode(TreeNode node, int val){
        if (node==null){
            return;
        }
        if (node.val > val){
            if (node.left==null){
                node.left = new TreeNode(val);
            }else {
                addNode(node.left,val);
            }
            return;
        }
        if (node.right ==null){
            node.right = new TreeNode(val);
            return;
        }
        addNode(node.right,val);
    }
}