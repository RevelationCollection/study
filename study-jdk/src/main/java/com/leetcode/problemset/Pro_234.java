package com.leetcode.problemset;

import com.leetcode.problemset.mode.ListNode;

import java.util.LinkedList;

/**
 * 1 2 2 1
 * 1 2 3 2 1
 * 1 2 3 4 3 2 1
 * 1 2 3 3 2 1
 * 回文链表
 */
public class Pro_234 {

    public static void main(String[] args) {

    }



    public static boolean isPalindrome(ListNode head) {
        //快慢指针+反转后半部分链表
        ListNode halfNode = getHalfNode(head);
        ListNode reveseNode = reverseNode(halfNode.next);
        while (reveseNode!=null && head!=null  ){
            if (head.val!=reveseNode.val){
                return false;
            }
            head = head.next;
            reveseNode = reveseNode.next;
        }
        return true;
    }

    private static ListNode reverseNode(ListNode head){
        ListNode prev = null;
        ListNode curr = head;
        while (curr!=null){
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return  prev;
    }

    private static ListNode getHalfNode(ListNode head){
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next !=null && fast.next.next!=null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public static boolean isPalindrome1(ListNode head) {
        //栈实现
        if (head==null){
            return false;
        }
        if (head.next==null){
            return true;
        }
        LinkedList<ListNode> stack = new LinkedList<>();
        ListNode node = head;
        while (node!=null){
            stack.add(node);
            node = node.next;
        }
        while (!stack.isEmpty()){
            ListNode first = stack.removeFirst();
            if (stack.isEmpty()){
                return true;
            }
            ListNode last = stack.removeLast();
            if (first.val!=last.val){
                return false;
            }
        }
        return  true;
    }


}
