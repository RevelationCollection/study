package com.algorithm.sort;

import java.util.*;

/**
 * 桶排序
 * 桶排序是计数排序的升级版。它利用了函数的映射关系，高效与否的关键就在于这个映射函数的确定。
 *
 * 9.1 算法描述
 * 人为设置一个BucketSize，作为每个桶所能放置多少个不同数值（例如当BucketSize==5时，该桶可以存放｛1,2,3,4,5｝这几种数字，但是容量不限，即可以存放100个3）；
 * 遍历输入数据，并且把数据一个一个放到对应的桶里去；
 * 对每个不是空的桶进行排序，可以使用其它排序方法，也可以递归使用桶排序；
 * 从不是空的桶里把排好序的数据拼接起来。
 */
public class BucketSort {

    public static int[] bucketSort(int[] array){
        return sort(array);
    }

    private static int[] sort(int[] array){
        int min=array[0],max = min;
        for (int i = 0; i < array.length; i++) {
            int value = array[i];
            if (min>value){
                min = value;
            }
            if (max<value){
                max = value;
            }
        }
        //映射规则，f(x) = x/10 -c , c= min/10 ,每10个间隔一个桶
        int c = min/10;
        //桶的数量= (max-min)/10 + 1
        int bucketCount = (max-min)/10+1;
        //准备好槽
        List<List<Integer>> bucketArr = new ArrayList<>(bucketCount);
        for (int i = 0; i < bucketCount; i++) {
            bucketArr.add(new LinkedList<>());
        }
        for (int i = 0; i < array.length; i++) {
            int val = array[i];
            //获得对应桶的位置
            int slot = val/10-c;
            bucketArr.get(slot).add(val);
        }
        for (int i = 0,index=0; i < bucketCount; i++) {
            List<Integer> link = bucketArr.get(i);
            if (link==null ||link.isEmpty()){
                continue;
            }
            //对桶内数据进行排序
            sortList(link);
            Iterator<Integer> iterator = link.iterator();
            do {
                Integer next = iterator.next();
                if (next==null){
                    break;
                }
                array[index++] = next;
            }while (iterator.hasNext());
        }
//        for (int i = 0; i < bucketCount; i++) {
//            if (bucketSize == 1) { // 如果带排序数组中有重复数字时  感谢 @见风任然是风 朋友指出错误
//                for (int j = 0; j < bucketArr.get(i).size(); j++)
//                    resultArr.add(bucketArr.get(i).get(j));
//            } else {
//                if (bucketCount == 1)
//                    bucketSize--;
//                ArrayList<Integer> temp = BucketSort(bucketArr.get(i), bucketSize);
//                for (int j = 0; j < temp.size(); j++)
//                    resultArr.add(temp.get(j));
//            }
//        }
        return array;
    }

    private static <T extends Comparable<? super T>>List<T> sortList(List<T> list){
        Collections.sort(list);
        Arrays.sort(new int[]{1});
        return list;
    }

}
