package com.leetcode.problemset;


import java.util.HashSet;
import java.util.Set;

/**
 * 给定一个链表，判断链表中是否有环。
 *
 * 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。 为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。注意：pos 不作为参数进行传递，仅仅是为了标识链表的实际情况。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/linked-list-cycle
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Pro_141 {
    public static void main(String[] args) {
        Pro_141 test = new Pro_141();
        ListNode node = test.getNode(new int[]{3, 2, 0, 4},-1);

//        System.out.println(hasCycle(node));
        System.out.println(hasCycle2(node));
    }

    public static boolean hasCycle(ListNode head) {
        if (head==null){
            return false;
        }
        //获取内存地址
        int ramIndex = System.identityHashCode(head);
        Set<Integer> cache = new HashSet<>(50);
        cache.add(ramIndex);
        while (head.next != null) {
            head = head.next;
            ramIndex = System.identityHashCode(head);
            if (cache.contains(ramIndex)){
                return true;
            }
            cache.add(ramIndex);
        }
        return false;
    }

    // 快慢指针
    public static boolean hasCycle2(ListNode head) {
        if (head==null || head.next==null){
            return false;
        }
        ListNode fast = head.next;
        ListNode slow = head;
        while (fast!=slow){
            if (fast ==null || fast.next==null){
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }

    public ListNode getNode(int[] arr,int pos){
        ListNode root = null;
        ListNode cycleNode = null;
        for (int i = 0; i < arr.length; i++) {
            ListNode newNode = new ListNode(arr[i]);
            if (i==pos){
                cycleNode = newNode;
            }
            if (root==null){
                root = newNode;
            }else {
                addNode(root,newNode);
            }
            if (i==arr.length-1){
                newNode.next = cycleNode;
            }
        }

        return root;
    }

    private void addNode(ListNode root, ListNode node){
        while (root.next != null) {
            root = root.next;
        }
        root.next = node;
    }

    private class ListNode {
      int val;
      ListNode next;
      ListNode(int x) {
          val = x;
          next = null;
      }
    }
}
