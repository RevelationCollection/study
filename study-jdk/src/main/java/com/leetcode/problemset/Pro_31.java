package com.leetcode.problemset;

import com.leetcode.problemset.mode.ListNode;

public class Pro_31 {
    public static void main(String[] args) {
        ListNode node = ListNode.initNode(new int[]{1, 2, 3, 4, 5});
        ListNode.print(oddEvenList(node));
        node = ListNode.initNode(new int[]{2,1,3,5,6,4,7});
        ListNode.print(oddEvenList(node));
    }

    public static ListNode oddEvenList(ListNode head) {
        if (head==null || head.next==null){
            return head;
        }
        ListNode odd = head ,oddCurrent = new ListNode(-1);
        ListNode even = head.next,evenCurrent = new ListNode(-2);
        ListNode currNode = head;
        int i = 1;
        while (currNode!=null){
            if (i++%2==0){
                evenCurrent.next = currNode;
                evenCurrent = evenCurrent.next;
            }else {
                oddCurrent.next = currNode;
                oddCurrent = oddCurrent.next;
            }
            currNode = currNode.next;
        }
        evenCurrent.next = null;
        oddCurrent.next = even;
        return odd;
    }

}
