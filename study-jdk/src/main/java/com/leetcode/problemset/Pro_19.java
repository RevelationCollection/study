package com.leetcode.problemset;

import com.leetcode.problemset.mode.ListNode;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list/
 */
public class Pro_19 {

    public static void main(String[] args) {
//        for (int i = 1; i < 6; i++) {
//            int[] arr = new int[]{1,2,3,4,5,6};
//            ListNode node = ListNode.initNode(arr);
//            ListNode.print(removeNthFromEnd(node,i));
//        }
        int[] arr = new int[]{1};
        ListNode node = ListNode.initNode(arr);
        ListNode.print(removeNthFromEnd(node,1));
    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        //快慢指针
        if (head==null){
            return head;
        }
        ListNode fast = head;
        ListNode dump = new ListNode(0,head);
        ListNode slow = dump;
        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }
        while (fast!=null){
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return dump.next;
    }

    public static ListNode removeNthFromEnd3(ListNode head, int n) {
        //数组遍历
        if (head==null){
            return head;
        }
        int length =0;
        ListNode node = head;
        while (node!=null){
            node = node.next;
            length++;
        }
        ListNode dump = new ListNode(0,head);
        node = dump;
        for (int i = 1; i < length-n+1; i++) {
            node = node.next;
        }
        node.next = node.next.next;
        return dump.next;
    }
    public static ListNode removeNthFromEnd2(ListNode head, int n) {
        //确定索引查找
        if (head==null){
            return head;
        }
        List<ListNode> list = new ArrayList<>();
        ListNode node = head;
        while (node!=null){
            list.add(node);
            node = node.next;
        }
        int total = list.size();
        if (total==n){
            return head.next;
        }
        if (n>total){
            return head;
        }
        int prev = total - n -1 ;
        int next = total - n +1;
        ListNode prevNode = list.get(prev);
        if (next<total){
            ListNode nextNode = list.get(next);
            prevNode.next = nextNode;
        }else {
            prevNode.next = null;
        }
        return head;
    }



}
