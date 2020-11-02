package com.leetcode.problemset;

/**
 * . 两两交换链表中的节点
 * 给定一个链表，两两交换其中相邻的节点，并返回交换后的链表。
 *
 * 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
 *
 * 示例:
 * 给定 1->2->3->4, 你应该返回 2->1->4->3.
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/swap-nodes-in-pairs
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Pro_24 {

    public static void main(String[] args) {
        Pro_24 test = new Pro_24();
        int[] arr = new int[]{1,2,3,4,5};
        ListNode listNode = test.initNode(arr);
        test.print(listNode);
        listNode = test.swapPairs(listNode);
        test.print(listNode);
    }

    public ListNode swapPairs(ListNode head) {
        return swapList(head,null);
    }

    private ListNode swapList(ListNode node,ListNode preNode){
        if (node==null|| node.next==null){
            return node;
        }
        ListNode one = node;
        ListNode two = node.next;
        ListNode next = two.next;
        two.next = one;
        one.next = next;
        if (preNode!=null){
            preNode.next = two;
        }
        swapList(next,one);
        return two;
    }

    private ListNode initNode(int[] arr){
        ListNode root = new ListNode(arr[0]);
        ListNode node = root;
        for (int i = 1; i < arr.length; i++) {
            node.next = new ListNode(arr[i]);
            node = node.next;
        }
        return root;
    }
    private void print(ListNode node){
        while (node!=null){
            System.out.print(node.val+",");
            node = node.next;
        }
        System.out.println();
    }

    private class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
}
