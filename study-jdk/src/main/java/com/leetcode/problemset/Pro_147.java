package com.leetcode.problemset;

import com.leetcode.problemset.mode.ListNode;

import java.util.LinkedList;

public class Pro_147 {

    public static void main(String[] args) {
        ListNode node = ListNode.initNode(new int[]{4,2,1,3});
        node = insertionSortList(node);
        ListNode.print(node);
    }

    private static ListNode insertionSortList(ListNode head) {
        ListNode current = head.next;
        ListNode newHead = head;
        newHead.next = null;
        while (current!=null){

        }
        return head;
    }
    private static ListNode insertionSortList1(ListNode head) {
        if (head==null) return head;
        LinkedList<ListNode> stack = new LinkedList<>();
        while (head!=null){
            addNode(head,stack);
            head = head.next;
        }
        ListNode newHead = stack.peek();
        ListNode current = stack.poll();
        while (!stack.isEmpty()){
            current.next = stack.poll();
            current = current.next;
        }
        current.next = null;
        return newHead;
    }

    private static void addNode(ListNode currentNode,LinkedList<ListNode> stack){
        LinkedList<ListNode> temp = new LinkedList<>();
        while (!stack.isEmpty()){
            if(stack.peek().val>currentNode.val){
                break;
            }else {
                temp.push(stack.poll()); }
        }
        stack.push(currentNode);
        while (!temp.isEmpty()){
            stack.push(temp.poll());
        }
    }
}
