package cmtech.soft.equipment.utils.commonUtil;

import cmtech.soft.equipment.utils.commonUtil.baseUtil.Cons;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class S extends StringUtils {
    /***
     * 默认分隔符 ,
     */
    public static final String SEPARATOR = ",";


    /***
     * 裁剪字符串，显示前部分+...
     * @param input
     * @return
     */
    public static String cut(String input, int cutLength){
        return substring(input, 0, cutLength);
    }

    /***
     * 将list拼接成string，默认分隔符:,
     * @param stringList
     * @return
     */
    public static String join(List<String> stringList){
        return StringUtils.join(stringList, SEPARATOR);
    }

    /***
     * 将list拼接成string，默认分隔符:,
     * @param stringArray
     * @return
     */
    public static String join(String[] stringArray){
        return StringUtils.join(stringArray, SEPARATOR);
    }

    /***
     * 按,拆分字符串
     * @param joinedStr
     * @return
     */
    public static String[] split(String joinedStr){
        if(joinedStr == null){
            return null;
        }
        return joinedStr.split(SEPARATOR);
    }

    /***
     * 转换为String数组（避免转型异常）
     * @param stringList
     * @return
     */
    public static String[] toStringArray(List<String> stringList){
        return stringList.toArray(new String[stringList.size()]);
    }

    /***
     * 转换成蛇形命名（用于Java属性转换为数据库列名）
     * @param camelCaseStrArray
     * @return
     */
    public static String[] toSnakeCase(String[] camelCaseStrArray){
        if(camelCaseStrArray == null){
            return null;
        }
        String[] snakeCaseArray = new String[camelCaseStrArray.length];
        for(int i=0; i<camelCaseStrArray.length; i++){
            snakeCaseArray[i] = toSnakeCase(camelCaseStrArray[i]);
        }
        return snakeCaseArray;
    }

    /***
     * 转换成蛇形命名（用于Java属性转换为数据库列名）
     * @param camelCaseStrArray
     * @return
     */
    public static List<String> toSnakeCase(List<String> camelCaseStrArray){
        if(camelCaseStrArray == null){
            return null;
        }
        List<String> snakeCaseList = new ArrayList<>(camelCaseStrArray.size());
        for(String camelCaseStr : camelCaseStrArray){
            snakeCaseList.add(toSnakeCase(camelCaseStr));
        }
        return snakeCaseList;
    }

    /***
     * 转换成小写蛇形命名（用于Java属性转换为小写数据库列名）
     * @param camelCaseStr
     * @return
     */
    public static String toSnakeCase(String camelCaseStr){
        if(V.isEmpty(camelCaseStr)){
            return null;
        }
        // 全小写
        if(camelCaseStr.toLowerCase().equals(camelCaseStr)){
            return camelCaseStr;
        }
        // 全大写直接return小写
        if(camelCaseStr.toUpperCase().equals(camelCaseStr)){
            return camelCaseStr.toLowerCase();
        }
        // 大小写混合，则遇“大写”转换为“_小写”
        char[] chars = camelCaseStr.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : chars){
            if(Character.isUpperCase(c)){
                if(sb.length() > 0){
                    sb.append(Cons.SEPARATOR_UNDERSCORE);
                }
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    /***
     * 转换成首字母小写的驼峰命名（用于数据库列名转换为Java属性）
     * @param snakeCaseStr
     * @return
     */
    public static String toLowerCaseCamel(String snakeCaseStr){
        if(V.isEmpty(snakeCaseStr)){
            return null;
        }
        // 不包含_
        if(!snakeCaseStr.contains(Cons.SEPARATOR_UNDERSCORE)){
            // 全大写直接return小写
            if(snakeCaseStr.toUpperCase().equals(snakeCaseStr)){
                return snakeCaseStr.toLowerCase();
            }
            // 其他return 首字母小写
            else{
                return uncapFirst(snakeCaseStr);
            }
        }
        // 包含_
        String result = null;
        String[] words = snakeCaseStr.split(Cons.SEPARATOR_UNDERSCORE);
        for(String word : words){
            if(V.notEmpty(word)){
                if(result == null){
                    result = word.toLowerCase();
                }
                else{
                    result += word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
                }
            }
        }
        return result;
    }

    /***
     * 转换为Long类型（判空，避免NPE）
     * @param strValue
     * @return
     */
    public static Long toLong(String strValue){
        return toLong(strValue, null);
    }

    /***
     * 转换为Long类型（判空，避免NPE）
     * @param strValue 字符类型值
     * @param defaultLong 默认值
     * @return
     */
    public static Long toLong(String strValue, Long defaultLong){
        if(V.isEmpty(strValue)){
            return defaultLong;
        }
        return Long.parseLong(strValue);
    }

    /***
     * 转换为Integer类型(判空，避免NPE)
     * @param strValue
     * @return
     */
    public static Integer toInt(String strValue){
        return toInt(strValue, null);
    }

    /***
     * 转换为Integer类型(判空，避免NPE)
     * @param strValue
     * @param defaultInt 默认值
     * @return
     */
    public static Integer toInt(String strValue, Integer defaultInt){
        if(V.isEmpty(strValue)){
            return defaultInt;
        }
        return Integer.parseInt(strValue);
    }

    /***
     * 将多个空格替换为一个
     * @param input
     * @return
     */
    public static String removeDuplicateBlank(String input){
        if(V.isEmpty(input)){
            return input;
        }
        return input.trim().replaceAll(" +", " ");
    }

    /***
     * 将object转换为字符串
     * @param o
     * @return
     */
    public static String valueOf(Object o) {
        if (o == null){
            return null;
        }
        return String.valueOf(o);
    }

    /***
     * 将首字母转为小写
     * @return
     */
    public static String uncapFirst(String input){
        if(input != null){
            return Character.toLowerCase(input.charAt(0)) + input.substring(1);
        }
        return null;
    }

    /***
     * 将首字母转为大写
     * @return
     */
    public static String capFirst(String input){
        if(input != null){
            return Character.toUpperCase(input.charAt(0)) + input.substring(1);
        }
        return null;
    }

}
