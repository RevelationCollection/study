package com.leetcode.problemset;

import com.algorithm.sort.tree.TreeNode;

import java.util.LinkedList;
import java.util.List;

public class Pro_129 {

    public static void main(String[] args) {

    }

    public int sumNumbers(TreeNode root) {
        List<Integer>  data = new LinkedList<>();
        preTraverse(root,0,data);
        int ans = 0;
        for (Integer val : data) {
            ans+=val;
        }
        return ans;
    }

    private void preTraverse(TreeNode node,int preVal,List<Integer> data){
        if (node==null){
            return;
        }
        int ans = node.val + preVal*10;
        if (node.right==null && node.left==null){
            data.add(ans);
            return;
        }
        preTraverse(node.right,ans,data);
        preTraverse(node.left,ans,data);
    }
}
