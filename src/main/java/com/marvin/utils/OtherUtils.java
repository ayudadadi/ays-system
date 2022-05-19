package com.marvin.utils;

/**
 * @author Marvin
 * @Description com.marvin.utils
 * @create 2021-12-13 14:31
 */
public class OtherUtils {
    /**
     * 删除字符串中空格及空格中的内容
     * @param context
     * @return
     */
    public static String clearBracket(String context) {
        // 修改原来的逻辑，防止右括号出现在左括号前面的位置
        int head = context.indexOf('('); // 标记第一个使用左括号的位置
        if (head == -1) {
            return context; // 如果context中不存在括号，什么也不做，直接跑到函数底端返回初值str
        } else {
            int next = head + 1; // 从head+1起检查每个字符
            int count = 1; // 记录括号情况
            do {
                if (context.charAt(next) == '(')
                    count++;
                else if (context.charAt(next) == ')')
                    count--;
                next++; // 更新即将读取的下一个字符的位置
                if (count == 0) // 已经找到匹配的括号
                {
                    String temp = context.substring(head, next); // 将两括号之间的内容及括号提取到temp中
                    context = context.replace(temp, ""); // 用空内容替换，复制给context
                    head = context.indexOf('('); // 找寻下一个左括号
                    next = head + 1; // 标记下一个左括号后的字符位置
                    count = 1; // count的值还原成1
                }
            } while (head != -1); // 如果在该段落中找不到左括号了，就终止循环
        }
        return context.trim(); // 返回更新后的context
    }

    /**
     * 以,为分隔符，返回最大长度为maxLength的字符串
     * @param str       原字符串
     * @param maxLength 最大长度
     * @return
     */
    public static String getAppropriateText(String str, int maxLength) {
        if (str.length() > maxLength) {
            String maxStr = str.substring(0, maxLength);
            int last = maxStr.lastIndexOf(",");
            String newStr = maxStr.substring(0, last);
            return newStr;
        }
        return str;
    }
}
