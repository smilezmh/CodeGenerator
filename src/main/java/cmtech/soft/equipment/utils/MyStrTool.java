package cmtech.soft.equipment.utils;

public class MyStrTool {
    public static boolean isNullOrEmpty(String str){
        if(str==null||str.trim().equals("")){
            return  true;
        }else {
            return false;
        }
    }
}
