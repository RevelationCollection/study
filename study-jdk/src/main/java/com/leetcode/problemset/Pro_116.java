package com.leetcode.problemset;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *  给定一个完美二叉树，其所有叶子节点都在同一层，每个父节点都有两个子节点。二叉树定义如下：
 *
 */
public class Pro_116 {
    public static void main(String[] args) {
        Pro_116 test = new Pro_116();

    }

    public Node connect(Node root) {

        return root;
    }

    private Node third(Node root){
        if (root==null){
            return root;
        }
        Node cur = root;
        while (cur!=null){
            Node temp = new Node(0);
            Node pre = temp;
            while (cur!=null && cur.left!=null){
                //拼凑当前节点的下一个节点
                pre.next = cur.left;
                cur.left.next = cur.right;
                pre = cur.right;
                //因为当前节点的顺序已经倍上一级指定，直接获取下一个节点
                cur = cur.next;
            }
            cur = temp.next;
        }
        return root;
    }

    private Node second(Node root){
        //BFS
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()){
            int level = queue.size();
            Node pre = null;
            for (int i = 0; i < level; i++) {
                //从队列的最左边，头部取出第一个节点
                Node node = queue.poll();
                if (pre!=null) {
                    pre.next = node;
                }
                pre = node;
                //保存下一级的节点
                if (node.left!=null){
                    queue.add(node.left);
                }
                if (node.right!=null) {
                    queue.add(node.right);
                }
            }
        }
        return root;
    }

    private Node first(Node root){
        List<List<Node>> list = new LinkedList<>();
        postOrderTracerRecursion(root,0,list);
        for (List<Node> nodeList : list) {
            for (int i = 1; i < nodeList.size(); i++) {
                Node preNode = nodeList.get(i - 1);
                Node node = nodeList.get(i);
                preNode.next = node;
            }
        }
        return root;
    }

    private void dfs(Node curr, Node next) {
        if (curr == null)
            return;
        curr.next = next;
        dfs(curr.left, curr.right);
        dfs(curr.right, curr.next == null ? null : curr.next.left);
    }

    private void postOrderTracerRecursion(Node node,int i,List<List<Node>> list){
        if (node==null){
            return;
        }
        if (i+1 >list.size()){
            list.add(new LinkedList<>());
        }
        List<Node> nodes = list.get(i);
        if (nodes==null){
            nodes = new ArrayList<>();
            list.add(nodes);
        }
        nodes.add(node);
        postOrderTracerRecursion(node.left,i+1,list);
        postOrderTracerRecursion(node.right,i+1,list);
    }


    private class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    };
}
