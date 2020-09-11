package cmtech.soft.equipment.utils;

import cmtech.soft.equipment.utils.Aop.InterceptAction;
import com.bestvike.linq.Linq;
import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.*;

public class MyStrTool {

    public static String getStringByList(List list, String regex) {
        String str = "";

        if (!MyListUtil.isNullOrEmpty(list)) {
            str = String.join(regex, list);
        }

        return str;
    }

    public static List getListByString(String str, String regex) {
        List list = null;

        if (!isNullOrEmpty(str)) {
            list = Arrays.asList(str.split(regex));
        }

        return list;
    }

    /**
     * 根据数据库数据自动生成新单号
     *
     * @param prefix  前缀 如PO
     * @param maps    数据库所有数据集合
     * @param colName 行名字
     * @param lastNum 最后保留几位如PR2001保留4位
     * @return 根据数据库数据自动生成新单号
     */
    @InterceptAction("获取数据库的单号")
    public static String getNo(String prefix, List<Map<String, Object>> maps, String colName, int lastNum) {
        String no = prefix;
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        no = prefix + df.format(new Date());// 固定格式
        List<String> strs = new ArrayList<String>();// 数据库已有单号集合

        String prefixNo=no;
        List<String> finalStrs=new ArrayList<>() ;
        Optional.ofNullable(maps).ifPresent(
                u -> {
                    u.forEach(
                            map -> Optional.ofNullable(map.get(colName)).ifPresent(x -> finalStrs.add((String) x))
                    );
                }
        );

        strs= Linq.of(finalStrs).where(x->x.startsWith(prefixNo)).toList();
        int[] array = getSupplyAndLastValidNum(strs, lastNum);
        int supplyNum = array[0];
        int num = array[1];

        if (supplyNum == -1) {
            return "超过系统生成单号范围！";
        }
//        int num=0; // 要截取的最后几位数字
//        String biggest=findBiggestStr(strs);
//
//        if(!isNullOrEmpty(biggest)){
//            // 最后几位数字
//            num=Integer.parseInt(biggest.substring(biggest.length() - lastNum));
//        }
//
//        int supplyNum=lastNum-1;//  在num+1的基础上补0的个数，如果是lastNum位，最多补lastNum-1个0，如果4位，最多3个0
//
//        // 看加几个0
//        while(Math.pow(10, lastNum)>num+1){
//            lastNum--;
//        }
//
//        if(lastNum==supplyNum+1){// 上边while没执行就退出
//            return "超过系统生成单号范围！";
//        }
//
//        supplyNum=supplyNum-lastNum;// 补多少个0

        int i = 0;

        while (i < supplyNum) {
            no = no + "0";
            i++;
        }

        no = no + (num + 1);

        return no;
    }

    /**
     * 根据数据库数据自动生成新单号
     *
     * @param prefix  前缀 如PO
     * @param maps    数据库所有数据集合
     * @param colName 行名字
     * @param lastNum 最后保留几位如PR2001保留4位
     * @param isRelatedToDate 是否和日期相关
     * @return 根据数据库数据自动生成新单号
     */
    @InterceptAction("获取数据库的单号")
    public static String getNo(String prefix, List<Map<String, Object>> maps, String colName, int lastNum,Boolean isRelatedToDate) {
        String no = prefix;

        if(isRelatedToDate!=null&&isRelatedToDate.booleanValue()){
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
            no = prefix + df.format(new Date());// 固定格式
        }

        List<String> strs = new ArrayList<String>();// 数据库已有单号集合

        String prefixNo=no;
        List<String> finalStrs=new ArrayList<>() ;
        Optional.ofNullable(maps).ifPresent(
                u -> {
                    u.forEach(
                            map -> Optional.ofNullable(map.get(colName)).ifPresent(x -> finalStrs.add((String) x))
                    );
                }
        );

        strs= Linq.of(finalStrs).where(x->x.startsWith(prefixNo)).toList();
        int[] array = getSupplyAndLastValidNum(strs, lastNum);
        int supplyNum = array[0];
        int num = array[1];

        if (supplyNum == -1) {
            return "超过系统生成单号范围！";
        }
//        int num=0; // 要截取的最后几位数字
//        String biggest=findBiggestStr(strs);
//
//        if(!isNullOrEmpty(biggest)){
//            // 最后几位数字
//            num=Integer.parseInt(biggest.substring(biggest.length() - lastNum));
//        }
//
//        int supplyNum=lastNum-1;//  在num+1的基础上补0的个数，如果是lastNum位，最多补lastNum-1个0，如果4位，最多3个0
//
//        // 看加几个0
//        while(Math.pow(10, lastNum)>num+1){
//            lastNum--;
//        }
//
//        if(lastNum==supplyNum+1){// 上边while没执行就退出
//            return "超过系统生成单号范围！";
//        }
//
//        supplyNum=supplyNum-lastNum;// 补多少个0

        int i = 0;

        while (i < supplyNum) {
            no = no + "0";
            i++;
        }

        no = no + (num + 1);

        return no;
    }

    /**
     * 获取要补足（用0）的位数，最后几位有效数字
     *
     * @param strs    数据库已有单号集合
     * @param lastNum 最后保留的位数
     * @return 第一个是补足的位数，第二个是截取的最后几位有效数字
     */
    private static int[] getSupplyAndLastValidNum(List<String> strs, int lastNum) {
        int[] intAarray = new int[2];
        int num = 0; // 要截取的最后几位数字
        String biggest = findBiggestStr(strs);

        if (!isNullOrEmpty(biggest)) {
            // 最后几位数字
            num = Integer.parseInt(biggest.substring(biggest.length() - lastNum));
        }

        int supplyNum = lastNum - 1;//  在num+1的基础上补0的个数，如果是lastNum位，最多补lastNum-1个0，如果4位，最多3个0

        // 看加几个0
        while (Math.pow(10, lastNum) > num + 1) {
            lastNum--;
        }

        if (lastNum == supplyNum + 1) {// 上边while没执行就退出
            intAarray[0] = -1;
        }

        supplyNum = supplyNum - lastNum;// 补多少个0
        intAarray[0] = supplyNum;
        intAarray[1] = num;
        return intAarray;
    }

    /**
     * 根据数据库数据该列数据自动生成新单号
     *
     * @param prefix  前缀 如PO
     * @param strs    数据库该列所有数据集合
     * @param lastNum 最后保留几位
     * @return 根据数据库数据自动生成新单号
     */
    @InterceptAction("获取数据库的单号")
    public static String getNo(String prefix, List<String> strs, int lastNum) {
        String no = prefix;
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        no = prefix + df.format(new Date());// 固定格式

        String prefixNo=no;
        strs= Linq.of(strs).where(x->x.startsWith(prefixNo)).toList();
        int num = 0;
        String biggest = findBiggestStr(strs);

        if (!isNullOrEmpty(biggest)) {
            // 最后几位数字
            num = Integer.parseInt(biggest.substring(biggest.length() - lastNum));
        }

        int supplyNum = lastNum - 1;//  在num+1的基础上补0的个数，如果是lastNum位，最多补lastNum-1个0，如果4位，最多3个0

        // 看加几个0
        while (Math.pow(10, lastNum) > num + 1) {
            lastNum--;
        }

        if (lastNum == supplyNum + 1) {// 上边while没执行就退出
            return "超过系统生成单号范围！";
        }

        supplyNum = supplyNum - lastNum;// 补多少个0

        int i = 0;

        while (i < supplyNum) {
            no = no + "0";
            i++;
        }

        no = no + (num + 1);

        return no;
    }

    /**
     * 根据数据库数据该列数据自动生成新单号
     *
     * @param prefix  前缀 如PO
     * @param strs    数据库该列所有数据集合
     * @param lastNum 最后保留几位
     * @param isRelatedToDate 是否和日期相关
     * @return 根据数据库数据自动生成新单号
     */
    @InterceptAction("获取数据库的单号")
    public static String getNo(String prefix, List<String> strs, int lastNum,Boolean isRelatedToDate) {
        String no = prefix;

        if(isRelatedToDate){
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
            no = prefix + df.format(new Date());// 固定格式
        }

        String prefixNo=no;
        strs= Linq.of(strs).where(x->x.startsWith(prefixNo)).toList();
        int num = 0;
        String biggest = findBiggestStr(strs);

        if (!isNullOrEmpty(biggest)) {
            // 最后几位数字
            num = Integer.parseInt(biggest.substring(biggest.length() - lastNum));
        }

        int supplyNum = lastNum - 1;//  在num+1的基础上补0的个数，如果是lastNum位，最多补lastNum-1个0，如果4位，最多3个0

        // 看加几个0
        while (Math.pow(10, lastNum) > num + 1) {
            lastNum--;
        }

        if (lastNum == supplyNum + 1) {// 上边while没执行就退出
            return "超过系统生成单号范围！";
        }

        supplyNum = supplyNum - lastNum;// 补多少个0

        int i = 0;

        while (i < supplyNum) {
            no = no + "0";
            i++;
        }

        no = no + (num + 1);

        return no;
    }

    /**
     * 找出一个集合最大的字符串
     *
     * @param strs 字符串集合
     * @return 找出一个集合最大的字符串
     */
    public static String findBiggestStr(List<String> strs) {
        String biggest = "";
        // 找出最大的
        if (strs != null && strs.size() > 0) {
            biggest = strs.get(0);

            for (String s : strs) {
                if (compareFlag(s, biggest)) {
                    biggest = s;
                }
            }
        }

        return biggest;
    }

    /**
     * 字符串是否为空或者null
     *
     * @param str
     * @return
     */
    public static boolean isNullOrEmpty(String str) {
        if (str == null || (str != null && str.trim().equals("")) || str.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 为null时返回"",否则返回正常值
     *
     * @param str 字符串
     * @return 为null时返回"",否则返回正常值
     */
    public static String $(Object str) {
        // 第一种写法（使用了匿名内部类）
//        Supplier<String> stringSupplier= new Supplier<String>() {
//            @Override
//            public String get() {
//                return "";
//            }
//        };
//        return Optional.ofNullable(str).orElseGet(stringSupplier);

        // 第二种写法
        // this::getNullStr和Supplier<String>类型相同，那么这个函数就是Supplier<String>类（接口）
        // 这个方法重写了Supplier中的 get方法（用匿名内部类实现），签名get找到getNullStr,最终执行的是 getNullStr方法
        // 静态函数内部也必须是静态成员变量
        return Optional.ofNullable((String)str).orElseGet(MyStrTool::getNullStr);
    }

    private static String getNullStr() {// 这个方法重写了Supplier中的 get方法，最终执行的是 getNullStr方法
        return "";
    }

    /**
     * 拼成sql in 的语句  select * from table where col in('1' ,'2')
     *
     * @param list
     * @return sql in 的语句
     */
    public static String getInsqlByList(List<String> list) {
        if (list == null && list.isEmpty()) {
            return "null or empty";
        }

        String str = "";
        StringBuilder sb = new StringBuilder();

        for (String s : list) {
            if (!isNullOrEmpty(s)) {
                sb.append("'" + s.trim() + "'");
                sb.append(",");
            }
        }
        return sb.substring(0, str.length() - 1);
    }

    /**
     * s1是否比s2大
     *
     * @param s1
     * @param s2
     * @return
     */
    private static boolean compareFlag(String s1, String s2) {
        boolean flag = true;

        if (s1.toLowerCase().trim().compareTo(s2.toLowerCase().trim()) < 0) {
            flag = false;
        }

        return flag;
    }

    /**
     * 返回当前时间的字符串 yyyy-MM-dd HH:mm:ss形式字符串
     *
     * @return 返回当前时间的字符串 yyyy-MM-dd HH:mm:ss形式字符串
     */
    private static String getNowTimeWithHms() {
        return DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 返回当前时间的字符串 yyyy-MM-dd形式字符串
     *
     * @return 返回当前时间的字符串 yyyy-MM-dd形式字符串
     */
    private static String getNowDate() {
        return DateTime.now().toString("yyyy-MM-dd");
    }

    /**
     * 给定字符串返回当前时间的字符串 yyyy-MM-dd HH:mm:ss形式字符串
     *
     * @return 返回当前时间的字符串 yyyy-MM-dd HH:mm:ss形式字符串
     */
    private static String getTimeWithHms(Object str) {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formater.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return formater.format(str);
    }

    /**
     * 给定字符串返回当前时间的字符串 yyyy-MM-dd 形式字符串
     *
     * @return 返回当前时间的字符串 yyyy-MM-dd 形式字符串
     */
    private static String getDate(Object str) {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        formater.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return formater.format(str);
    }

    /**
     * (中文或英文)逗号分隔的字符转成list  结合addAll方法可以将一个list集合的 list
     *
     * @param str (中文或英文)逗号分隔的字符
     * @return list
     */
    public static List<String> getListForStrComma(String str) {
        List<String> list = Arrays.asList(
                Optional.ofNullable(str.replace("，", ",").replace("\n", "").split(",")
                ).orElseGet(() -> new String[]{""}));
        list.forEach(x -> x.trim());
        return list;
    }

    /**
     * 获取某个符号字符串第几次出现的index
     *
     * @param times  第几次
     * @param str    字符串
     * @param symbol 符号
     * @return 获取某个符号字符串第几次出现的index
     */
    public static int getIndexOfSymbolInStr(int times, String str, String symbol) {

        if (times == 1) {
            return str.indexOf(symbol);
        } else {
            return getNextIndexSymbolInStr(getIndexOfSymbolInStr(times - 1, str, symbol), str, symbol);
        }
    }

    /**
     * 获取下次某个字符出现的index
     *
     * @param lastTimesIndex 上次的index
     * @param str            字符串
     * @param symbol         符号
     * @return 获取下次某个字符出现的index
     */
    public static int getNextIndexSymbolInStr(int lastTimesIndex, String str, String symbol) {
        return str.indexOf(symbol, lastTimesIndex + 1);
    }

    //首字母转小写
    public static String toLowerCaseFirstOne(String s)
    {
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }
    //首字母转大写
    public static String toUpperCaseFirstOne(String s)
    {
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }
}

/**
 * Optinal ：https://blog.csdn.net/zjhred/article/details/84976734
 * // 为空抛出错误
 * //  Optional.ofNullable(str).orElseThrow(()->new Exception("blabla"));
 * <p>
 * // 用optional不用判断对象是不是为空了
 * //  String city = Optional.ofNullable(user).map(u-> u.getName()).get();
 * <p>
 * if(user!=null){
 * if(user.getAddress()!=null){
 * Address address = user.getAddress();
 * if(address.getCity()!=null){
 * return address.getCity();
 * }
 * }
 * }else{
 *   throw new Excpetion("取值错误");
 * }
 *
 * =>
 * //Optional.ofNullable(user)
 * //                   .map(u-> u.getAddress())
 * //                   .map(a->a.getCity())
 * //                   .orElseThrow(()->new Exception("取指错误"));
 * <p>
 * //if(user!=null){dosomething(user)};=>Optional.ofNullable(user).ifPresent(u->{dosomething(u);});
 * <p>
 * <p>
 * public User getUser(User user) throws Exception{
 * if(user!=null){
 * String name = user.getName();
 * if("zhangsan".equals(name)){
 * return user;
 * }
 * }else{
 * user = new User();
 * user.setName("zhangsan");
 * return user;
 * }
 * }
 * =>
 * public User getUser(User user) {
 * return Optional.ofNullable(user)
 * .filter(u->"zhangsan".equals(u.getName()))
 * .orElseGet(()-> {
 * User user1 = new User();
 * user1.setName("zhangsan");
 * return user1;
 * });
 * }
 **/


