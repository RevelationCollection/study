package com.leetcode.problemset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 给定仅有小写字母组成的字符串数组 A，返回列表中的每个字符串中都显示的全部字符（包括重复字符）组成的列表。例如，如果一个字符在每个字符串中出现 3 次，但不是 4 次，则需要在最终答案中包含该字符 3 次。
 *
 * 你可以按任意顺序返回答案。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/find-common-characters
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Pro_1002 {

    public static void main(String[] args) {
        String[] A = new String[] {"bella","label","roller"};
        System.out.println(commonChars(A));
        A = new String[] {"cool","lock","cook"};
        A = new String[] {"cool","lock","cook"};
        System.out.println(commonChars(A));
    }

    public static List<String> commonChars(String[] A) {
        List<String> list = new ArrayList<>();
        if (A==null){
            return list;
        }
        Map<Character,Integer> cache = null;
        for (int i = 0; i < A.length; i++) {
            Map<Character,Integer> tmp = new HashMap<>();
            String str = A[i];
            char[] chars = str.toCharArray();
            for (char c : chars) {
                Integer count = tmp.get(c);
                count = count ==null?1:count+1;
                if (cache!=null){
                    Integer cacheCount = cache.get(c);
                    if (cacheCount==null){
                        continue;
                    }
                    if (count>cacheCount){
                        continue;
                    }
                }
                tmp.put(c,count);
            }
            if (cache == null){
                cache = tmp;
                continue;
            }
            cache = tmp;
        }
        for (Map.Entry<Character, Integer> entry : cache.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                list.add(entry.getKey().toString());
            }
        }
        return list;
    }

}
