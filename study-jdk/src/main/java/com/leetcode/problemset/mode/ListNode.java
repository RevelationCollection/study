package com.leetcode.problemset.mode;

public class ListNode {
    public int val;
    public ListNode next;
    public ListNode() {}
    public ListNode(int val) { this.val = val; }
    public ListNode(int val, ListNode next) { this.val = val; this.next = next; }

    public static ListNode saveNode(ListNode root, int data){
        ListNode node = new ListNode(data);
        if (root==null) {
            return node ;
        }
        addNode(root,node);
        return root;
    }

    public static ListNode initNode(int[] arr){
        ListNode root = new ListNode(arr[0]);
        ListNode node = root;
        for (int i = 1; i < arr.length; i++) {
            node.next = new ListNode(arr[i]);
            node = node.next;
        }
        return root;
    }

    public static void print(ListNode node){
        while (node!=null){
            System.out.print(node.val+",");
            node = node.next;
        }
        System.out.println();
    }


    public static void addNode(ListNode root, ListNode node){
        while (root.next != null) {
            root = root.next;
        }
        root.next = node;
    }

    public static int getValue(ListNode node){
        if (node==null) {
            return 0;
        }
        return node.val;
    }

}
