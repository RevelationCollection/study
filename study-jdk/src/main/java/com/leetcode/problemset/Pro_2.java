package com.leetcode.problemset;

/**
 * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
 *
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 *
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 *
 *
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/add-two-numbers
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Pro_2 {

    public static void main(String[] args) {
        Pro_2 test = new Pro_2();
        test1(test);
        test2(test);
        test3(test);
    }

    private static void test1(Pro_2 test){
        ListNode l1 = test.getNode(new int[]{2,4,3});
        ListNode l2= test.getNode(new int[]{5,6,4});
        ListNode listNode = test.addTwoNumbers(l1, l2);
        print(listNode);
    }
    private static void test2(Pro_2 test){
        ListNode l1 = test.getNode(new int[]{1,8});
        ListNode l2= test.getNode(new int[]{0});
        ListNode listNode = test.addTwoNumbers(l1, l2);
        print(listNode);
    }
    private static void test3(Pro_2 test){
        ListNode l1 = test.getNode(new int[]{9});
        ListNode l2= test.getNode(new int[]{1,9,9,9,9,9,9,9,9});
        ListNode listNode = test.addTwoNumbers(l1, l2);
        print(listNode);
    }

    private static void print(ListNode node){
        while (node!=null){
            System.out.print(node.val+",");
            node = node.next;
        }
        System.out.println();
    }

    public ListNode getNode(int[] arr){
        ListNode root = null;
        for (int i : arr) {
            ListNode newNode = new ListNode(i);
            if (root==null){
                root = newNode;
            }else {
                addNode(root,newNode);
            }
        }
        return root;
    }


    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode root = null;
        int more = 0;
        while (true){
            try {
                if (l1==null && l2==null){
                    break;
                }
                int sum = add(l1, l2) + more;
//                System.out.printf("l1:%s,l2:%s,more:%s,sum:%s",getValue(l1),getValue(l2),more,sum);
//                System.out.println();
                if (sum<10){
                    root = saveNode(root,sum);
                    more = 0;
                    continue;
                }
                int current = sum%10;
                root = saveNode(root,current);
                more = 1;
            }finally {
                l1 = l1==null?null:l1.next;
                l2 = l2==null?null:l2.next;
            }
        }
        if (more>0){
            root = saveNode(root,more);
        }
        return root;
    }


    private ListNode saveNode(ListNode root,int data){
        ListNode node = new ListNode(data);
        if (root==null) {
            return node ;
        }
        addNode(root,node);
        return root;
    }


    private void addNode(ListNode root,ListNode node){
        while (root.next != null) {
            root = root.next;
        }
        root.next = node;
    }

    private int getValue(ListNode node){
        if (node==null) {
            return 0;
        }
        return node.val;
    }

    private int add(ListNode node,ListNode newNode){
        return getValue(node) + getValue(newNode);
    }

    private ListNode addPreviousNode(ListNode node,ListNode newNode){
        newNode.next = node;
        return newNode;
    }


    private ListNode reverNode(ListNode node){
        ListNode fatherNode =node;
        ListNode rootNode = node;
        while (node.next != null) {
            ListNode next = node.next;
            ListNode childernNode = next.next;
            next.next = fatherNode;
            node.next = childernNode;
            rootNode = fatherNode;
            fatherNode = next;
            if (childernNode==null){
                fatherNode.next = rootNode;
                rootNode = fatherNode;
            }
        }
        return rootNode;
    }

    private class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

}

