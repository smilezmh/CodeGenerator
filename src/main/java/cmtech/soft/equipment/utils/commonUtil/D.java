package cmtech.soft.equipment.utils.commonUtil;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class D {
    /***
     * 日期时间格式
     */
    public static final String FORMAT_DATE_y2M = "yyMM";
    public static final String FORMAT_DATE_y2Md = "yyMMdd";
    public static final String FORMAT_DATE_y4 = "yyyy";
    public static final String FORMAT_DATE_y4Md = "yyyyMMdd";
    public static final String FORMAT_DATE_Y4MD = "yyyy-MM-dd";
    public static final String FORMAT_TIMESTAMP = "yyMMddhhmmss";
    public static final String FORMAT_TIME_HHmm = "HH:mm";
    public static final String FORMAT_TIME_HHmmss = "HH:mm:ss";
    public static final String FORMAT_DATETIME_Y4MDHM = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_DATETIME_Y4MDHMS = "yyyy-MM-dd HH:mm:ss";
    /***
     * 星期
     */
    public static final String[] WEEK = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    /***
     * 模糊转换日期
     * @param dateString
     * @return
     */
    public static Date fuzzyConvert(String dateString){
        if(V.isEmpty(dateString)){
            return null;
        }
        // 清洗
        if(dateString.contains("-")){
        }
        else if(dateString.contains("月")){
            dateString = dateString.replaceAll("年", "-").replaceAll("月", "-").replaceAll("日", "").replaceAll("号", "");
        }
        else{
            dateString = dateString.replaceAll("\\/", "-").replaceAll("\\.", "-");
        }
        String[] parts = dateString.split(" ");
        String[] ymd = parts[0].split("-");
        if(ymd.length >= 3){
            if(ymd[0].length() == 2){
                ymd[0] = String.valueOf(Calendar.getInstance().get(Calendar.YEAR)).substring(0, 2) + ymd[0];
            }
            if(ymd[1].length() == 1){
                ymd[1] = "0" + ymd[1];
            }
            if(ymd[2].length() == 1){
                ymd[2] = "0" + ymd[2];
            }
        }
        parts[0] = S.join(ymd, "-");
        if(parts.length == 1){
            return D.convert2FormatDate(parts[0], D.FORMAT_DATE_Y4MD);
        }
        // 18:20:30:103
        String[] hmsArray = new String[3];
        String[] hms = parts[1].split(":");
        if(hms[0].length() == 1){
            hms[0] = "0" + hms[0];
        }
        hmsArray[0] = hms[0];
        if(hms.length >= 2){
            if(hms[1].length() == 1){
                hms[1] = "0" + hms[1];
            }
            hmsArray[1] = hms[1];
        }
        else{
            hmsArray[1] = "00";
        }
        if(hms.length >= 3){
            if(hms[2].length() == 1){
                hms[2] = "0" + hms[2];
            }
            hmsArray[2] = hms[2];
        }
        else{
            hmsArray[2] = "00";
        }
        parts[1] = S.join(hmsArray, ":");
        return D.convert2FormatDate(S.join(parts, " "), D.FORMAT_DATETIME_Y4MDHMS);
    }

    /***
     * 转换字符串为日期date
     * @param datetime
     * @param fmt
     * @return
     */
    public static Date convert2FormatDate(String datetime, String fmt){
        if (StringUtils.isBlank(datetime)){
            return null;
        }
        SimpleDateFormat format =  new SimpleDateFormat(fmt);
        try {
            Date date = format.parse(datetime);
            return date;
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
