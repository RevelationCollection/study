package com.leetcode.problemset;

import com.algorithm.sort.tree.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Pro_144 {

    public static void main(String[] args) {

    }

    public static List<Integer> preorderTraversal(TreeNode root) {
        TreeNode node = root;
        LinkedList<TreeNode> queue = new LinkedList<>();
        List<Integer> list = new ArrayList<>();
        while (node!=null || !queue.isEmpty()){
            if (node!=null){
                list.add(node.val);
                if (node.right!=null){
                    queue.push(node.right);
                }
                node = node.left;
            }else {
                node = queue.pop();
            }
        }
        return list;
    }
    public static List<Integer> preorderTraversal1(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        preorder(root,list);
        return list;
    }

    private static void preorder(TreeNode node,List<Integer> list){
        if (node!=null){
            list.add(node.val);
            preorder(node.left,list);
            preorder(node.right,list);
        }
        return ;
    }
}
