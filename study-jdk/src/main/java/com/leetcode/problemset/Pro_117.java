package com.leetcode.problemset;

/**
 * 给定一个二叉树。
 * https://leetcode-cn.com/problems/populating-next-right-pointers-in-each-node-ii/
 */
public class Pro_117 {

    public static void main(String[] args) {

    }

    public Node connect(Node root) {
        if (root==null || (root.left==null&&root==null)){
            return root;
        }
        Node cur = root;
        while (cur!=null){
            Node temp = new Node(0);
            Node pre = temp;
            while (cur!=null){
                if (cur.left!=null){
                    pre.next = cur.left;
                    pre = pre.next;
                }
                if (cur.right!=null){
                    pre.next = cur.right;
                    pre = pre.next;
                }
                cur = cur.next;
            }
            cur = temp.next;
        }
        return root;
    }

    private class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }
}
