package cmtech.soft.equipment.base.model.excelModel;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * <p>
 * 设备基础信息主表
 * </p>
 *
 * @author smilezmh
 * @since 2020-04-29
 */

@Data
@ContentRowHeight(20)
@HeadRowHeight(25)
@ApiModel(value="EquipmentBaseInfo excel对象", description="设备基础信息主表")
public class EquipmentBaseInfoExcel {

    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "序号")
    @ColumnWidth(10)
    private Integer xid;

    @ExcelProperty(value = "主键",index = 1)
    @ColumnWidth(30)
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ExcelProperty(value = "资产编号，财务,唯一可以作为业务主键",index = 2)
    @ColumnWidth(30)
    @ApiModelProperty(value = "资产编号，财务,唯一可以作为业务主键")
    private String code;

    @ExcelProperty(value = "名称",index = 3)
    @ColumnWidth(30)
    @ApiModelProperty(value = "名称")
    private String name;

    @ExcelProperty(value = "设备类型code,外链equipmen_type中的code",index = 4)
    @ColumnWidth(30)
    @ApiModelProperty(value = "设备类型code,外链equipmen_type中的code")
    private String typeCode;

    @ExcelProperty(value = "设备类型name,外链equipmen_type中的name",index = 5)
    @ColumnWidth(30)
    @ApiModelProperty(value = "设备类型name,外链equipmen_type中的name")
    private String typeName;

    @ExcelProperty(value = "位置编码,外链equipment_position中的code",index = 6)
    @ColumnWidth(30)
    @ApiModelProperty(value = "位置编码,外链equipment_position中的code")
    private String positionCode;

    @ExcelProperty(value = "位置,外链equipment_position中的name",index = 7)
    @ColumnWidth(30)
    @ApiModelProperty(value = "位置,外链equipment_position中的name")
    private String positionName;

    @ExcelProperty(value = "规格/型号",index = 8)
    @ColumnWidth(30)
    @ApiModelProperty(value = "规格/型号")
    private String specification;

    @ExcelProperty(value = "功率",index = 9)
    @ColumnWidth(30)
    @ApiModelProperty(value = "功率")
    private String power;

    @ExcelProperty(value = "计量单位（台 套）",index = 10)
    @ColumnWidth(30)
    @ApiModelProperty(value = "计量单位（台 套）")
    private String unit;

    @ExcelProperty(value = "成本中心（设备的）",index = 11)
    @ColumnWidth(30)
    @ApiModelProperty(value = "成本中心（设备的）")
    private String costCenterNo;

    @ExcelProperty(value = "采购成本（原始价值，某些角色财务可以看到）",index = 12)
    @ColumnWidth(30)
    @ApiModelProperty(value = "采购成本（原始价值，某些角色财务可以看到）")
    private String purchaseCost;

    @ExcelProperty(value = "资产净值（财务才可以看到）",index = 13)
    @ColumnWidth(30)
    @ApiModelProperty(value = "资产净值（财务才可以看到）")
    private String pureCost;

    @ExcelProperty(value = "采购订单号",index = 14)
    @ColumnWidth(30)
    @ApiModelProperty(value = "采购订单号")
    private String purchaseOrderNo;

    @ExcelProperty(value = "制造厂商",index = 15)
    @ColumnWidth(30)
    @ApiModelProperty(value = "制造厂商")
    private String manufacturer;

    @ExcelProperty(value = "制造日期",index = 16)
    @ColumnWidth(30)
    @ApiModelProperty(value = "制造日期")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date madeDate;

    @ExcelProperty(value = "启用日期",index = 17)
    @ColumnWidth(30)
    @ApiModelProperty(value = "启用日期")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date useDate;

    @ExcelProperty(value = "保修期（月）",index = 18)
    @ColumnWidth(30)
    @ApiModelProperty(value = "保修期（月）")
    private Integer guaranteeTime;

    @ExcelProperty(value = "保修电话，售后电话",index = 19)
    @ColumnWidth(30)
    @ApiModelProperty(value = "保修电话，售后电话")
    private String afterSaleServiceTel;

    @ExcelProperty(value = "出厂编号",index = 20)
    @ColumnWidth(30)
    @ApiModelProperty(value = "出厂编号")
    private String factoryNo;

    @ExcelProperty(value = "折旧年限，单位为年",index = 21)
    @ColumnWidth(30)
    @ApiModelProperty(value = "折旧年限，单位为年")
    private Integer depreciateTime;

    @ExcelProperty(value = "状态（已验收/未验收/报废）",index = 22)
    @ColumnWidth(30)
    @ApiModelProperty(value = "状态（已验收/未验收/报废）")
    private String status;

    @ExcelProperty(value = "资产挂账人（部门对应人）",index = 23)
    @ColumnWidth(30)
    @ApiModelProperty(value = "资产挂账人（部门对应人）")
    private String putAccountPerson;

    @ExcelProperty(value = "报废日期",index = 24)
    @ColumnWidth(30)
    @ApiModelProperty(value = "报废日期")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date discardDate;

    @ExcelProperty(value = "资产描述",index = 25)
    @ColumnWidth(30)
    @ApiModelProperty(value = "资产描述")
    private String equipmentDesc;

    @ExcelProperty(value = "照片存储位置",index = 26)
    @ColumnWidth(30)
    @ApiModelProperty(value = "照片存储位置")
    private String picUrl;

    @ExcelProperty(value = "创建人id",index = 27)
    @ColumnWidth(30)
    @ApiModelProperty(value = "创建人id")
    private Integer createrId;

    @ExcelProperty(value = "创建人",index = 28)
    @ColumnWidth(30)
    @ApiModelProperty(value = "创建人")
    private String creater;

    @ExcelProperty(value = "创建人id",index = 29)
    @ColumnWidth(30)
    @ApiModelProperty(value = "创建人id")
    private Integer modifierId;

    @ExcelProperty(value = "修改人",index = 30)
    @ColumnWidth(30)
    @ApiModelProperty(value = "修改人")
    private String modifier;

    @ExcelProperty(value = "创建时间",index = 31)
    @ColumnWidth(30)
    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ExcelProperty(value = "修改时间",index = 32)
    @ColumnWidth(30)
    @ApiModelProperty(value = "修改时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

    @ExcelProperty(value = "是否删除",index = 33)
    @ColumnWidth(30)
    @ApiModelProperty(value = "是否删除")
    private Boolean isDeleted;



}
