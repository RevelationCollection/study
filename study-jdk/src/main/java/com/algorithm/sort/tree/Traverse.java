package com.algorithm.sort.tree;

import java.util.LinkedList;

/**
 * 二叉搜索树遍历
 * 根节点、左子树、右子树
 * 前序遍历：根节点->左子树->右子树
 * 中序遍历：左子树->根节点->右子树
 * 后续遍历：左子树->右节点->根子树
 *
 *
 * 看到关于二叉树的问题，首先要想到关于二叉树的一些常见遍历方式，
 * 对于二叉树的遍历有
 *
 * 前序遍历
 * 中序遍历
 * 后续遍历
 * 深度优先搜索（DFS）
 * 宽度优先搜索（BFS)
 *
 * 作者：sdwwld
 * 链接：https://leetcode-cn.com/problems/populating-next-right-pointers-in-each-node/solution/bfshe-di-gui-zui-hou-liang-chong-ji-bai-liao-100-2/
 * 来源：力扣（LeetCode）
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */
public class Traverse {

    public static void main(String[] args) throws Exception {
        int[] arr = new int[]{5,4,3,8,1,2,9,7,6};
        arr = new int[]{20,15,31,54,66,65,73,16};
        TreeNode root = TreeNode.initRoot(arr);
        System.out.println("深度优先遍历:");
        System.out.println("前序遍历:");
        // 深度优先 - dfs  Depth First Search
        //前序遍历 - 递归
        preOrderTraverseRecursion(root);
        System.out.println();
        //前序遍历 - 循环
        preOrderTraverseCycle(root);
        System.out.println("\r\n中序遍历:");
        //中序遍历 - 递归
        postOrderTracerRecursion(root);
        System.out.println();
        //中序遍历 - 循环
        postOrderTracerCycle(root);
        System.out.println("\r\n后序遍历:");
        //后序遍历 - 递归
        afterOrderTracerRecursion(root);
        //后序遍历 - 循环
        System.out.println();
        afterOrderTracerCycle(root);
        System.out.println("\r\n宽度优先遍历:");
        //宽度优先-bfs
        breadthFirstSearch(root);
    }


    //宽度优先 层级遍历
    private static void breadthFirstSearch(TreeNode root){
        if (root==null){
            return;
        }
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root); // addLast
        while (!queue.isEmpty()){
            //poll方法相当于移除队列头部的元素
            TreeNode node = queue.poll();
            print(node);
            if (node.left!=null){
                queue.add(node.left);
            }
            if (node.right!=null){
                queue.add(node.right);
            }
        }
    }

    /** 后序遍历 - 循环 */
    public static void afterOrderTracerCycle(TreeNode root){
        LinkedList<TreeNode> queue = new LinkedList<>();
        TreeNode node = root;
        TreeNode pre = null;
        while (node!=null || !queue.isEmpty()){
            if(node!=null){
                queue.push(node);
                node = node.left;
            }else {
                // 已经访问到最左的节点了
                node = queue.poll();
                if (node==root){
                }else {
                    print(node);
                }
                node = node.right;
            }
        }
        print(root);
    }


    /** 后序遍历 - 递归 */
    public static void afterOrderTracerRecursion(TreeNode node){
        if (node!=null){
            postOrderTracerRecursion(node.left);
            postOrderTracerRecursion(node.right);
            print(node);
        }
    }
    /** 中序遍历 - 递归 */
    public static void postOrderTracerRecursion(TreeNode node){
        if (node!=null){
            postOrderTracerRecursion(node.left);
            print(node);
            postOrderTracerRecursion(node.right);
        }
    }

    public static void postOrderTracerCycle(TreeNode node){
        LinkedList<TreeNode> stack = new LinkedList<>();
        while (node!=null || !stack.isEmpty()){
            if (node!=null){
                stack.addFirst(node); //push
                node = node.left;
            }else {
                node = stack.removeFirst(); //poll
                print(node);
                node = node.right;
            }
        }
    }
    
    /** 前序遍历 - 递归 */
    public static void preOrderTraverseRecursion(TreeNode node){
        if (node!=null){
            print(node);
            preOrderTraverseRecursion(node.left);
            preOrderTraverseRecursion(node.right);
        }
    }

    /** 前序遍历 - 循环 */
    public static void preOrderTraverseCycle(TreeNode node) throws InterruptedException {
        LinkedList<TreeNode> stack = new LinkedList<>();
        //push,pop 放到最前面 addFirst，取第一个 removeFirst 压栈
        while (node!=null || !stack.isEmpty()){
            if (node!=null){
                print(node);
                stack.push(node);
                node = node.left;
            }else {
                node = stack.pop();
                node = node.right;
            }
        }
    }

    private static void print(TreeNode node){
        System.out.print(node.val+",");
    }
}
