package com.jamin.neeeerdplayer.utils;

/**
 * Created by jamin on 16-4-4.
 */
public class StringUtils {

    public static final String EMPTY = "";

    /**
     * 查看一个字符是否为空
     *
     * @param s 需要检查的字条
     * @return 是否为空
     */
    public static boolean isEmpty(String s) {
        if (s == null || s.trim().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isEmpty(CharSequence charSequence) {
        return isEmpty(charSequence.toString());
    }


    public static int getWordCount(String s) {

        s = s.replaceAll("[^\\x00-\\xff]", "**");
        int length = s.length();
        return length;
    }


    public static String notNull(String s){
        if(s==null){
            return EMPTY;
        }
        return s;
    }
    /**
     * 生成中间省略的字符串
     * 这个方法是为了使得字符串过长的时候进行中间截取成 XXX..XXX.DOC 为什么不用系统的ellipse=middle，使用这个属性在某种命名的文件下会导致系统崩溃，这是Android系统的bug.
     * @param content 字符串长度
     * @param max 允许字符串最大值
     * @param allowChineseCount 允许中文最大值
     * @param start 省略开始于
     * @param end  省略结束于
     * @return
     */
    public static String middleEllipse(String content, int max, int allowChineseCount, int start, int end) {
        Integer index = 0;
        int chineseCount = 0;
        for (int i = 0; i < content.length(); i++) {
            String retContent = content.substring(i, i + 1);
            // 生成一个Pattern,同时编译一个正则表达式
            boolean isChina = retContent.matches("[\u4E00-\u9FA5]");
            if (isChina) {
                index = index + 2;
                chineseCount ++;
            } else {
                index = index + 1;
            }
        }

        if (index < max) {
            return content;
        }
        StringBuffer sb = new StringBuffer();
        if (chineseCount > allowChineseCount) {
            sb.append(content.substring(0, start + 2));
        } else {
            sb.append(content.substring(0, start + 5));
        }
        sb.append("...");
        sb.append(content.substring(content.length()- end, content.length()));
        return sb.toString();
    }

}
