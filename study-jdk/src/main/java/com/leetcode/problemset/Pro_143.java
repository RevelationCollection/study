package com.leetcode.problemset;

import com.leetcode.problemset.mode.ListNode;

import java.util.LinkedList;

public class Pro_143 {

    public static void main(String[] args) {
        int[] arr = new int[]{1,2};
        ListNode root = ListNode.initNode(arr);
//        reorderList(root);
        ListNode.print(middleNode(root));
//        arr = new int[]{1,2,3,4,5};
//        root = ListNode.initNode(arr);
//        reorderList(root);
//        ListNode.print(root);
    }

    public static ListNode middleNode(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while(slow!=null && fast!=null &&fast.next!=null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public static void reorderList(ListNode head) {
        //栈应用
        if (head==null){
            return;
        }
        LinkedList<ListNode> stack = new LinkedList<>();
        ListNode node = head;
        while (node!=null){
            //压栈
            stack.push(node);
            node = node.next;
        }
        node = head;
        while (node!=null){
            //出栈
            ListNode last = stack.poll();
            ListNode next = node.next;
            if (node==last){
                node.next = null;
                break;
            }
            node.next = last;
            if (last==next ){
                next.next = null;
                break;
            }else if(last!=null){
                last.next = next;
            }else {
                break;
            }
            node = next;
        }
    }
}
