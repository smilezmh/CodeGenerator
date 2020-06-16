package cmtech.soft.equipment.utils;

/**
 * 自定义基础扩展返回码
 */
public interface HttpStatusBase {
    // 主键重复
    public static final int Major_Key_Reapete_ = 208;
    // 参数为空
    public static final int Parameter_Not_Null = 209;
    // 参数类型不正确
    public static final int Parameter_Type_Not_Correct = 210;
    // 参数个数不正确
    public static final int Parameter_Num_Not_Correct = 211;
    // get方法返回data 不为Null但是空，但是list为空
    public static final int Return_Not_Null_But_List_Empty = 212;
    // get方法返回分页对象 不为Null但是空，但是list为空
    public static final int Return_Page_Not_Null_But_Empty = 213;
    // get方法返回data 不为Null但是空，但是实体为New对象
    public static final int Return_Not_Null_But_Entity_Empty = 214;
    // get方法返回为data Null
    public static final int Return_Null = 215;
    // 插入失败
    public static final int Insert_Fail = 216;
    // 更新插入失败
    public static final int Update_Fail = 217;
    // 更新或者插入失败
    public static final int Insert_Or_Update_Fail = 218;
    // 删除失败
    public static final int Delete_Fail = 219;
    // 获取文件失败
    public static final int Get_File_Fail = 220;
    // 删除文件失败
    public static final int Delete_File_Fail = 221;
    // 上传文件失败
    public static final int Upload_File_Fail = 222;
    // 未知失败
    public static final int Unknown_Fail = 299;
}
