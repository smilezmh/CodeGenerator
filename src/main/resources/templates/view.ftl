<template>
  <div class="container" style="width:99%;">
	<!--工具栏-->
	<div class="toolbar" style="padding-top:10px;">
		<el-form :inline="true" :model="filters" :size="size">
			<el-form-item>
				<el-input v-model="filters.label" placeholder="名称"></el-input>
			</el-form-item>
			<el-form-item>
				<kt-button :label="$t('action.search')" perms="sys:${entity}:view" type="primary" @click="findPage()"/>
			</el-form-item>
			<el-form-item>
				<kt-button :label="$t('action.add')" perms="sys:${entity}:add" type="primary" @click="handleAdd" />
			</el-form-item>
		</el-form>
	</div>
	<!--表格内容栏 此表为主表，slaveButtonShow是否支持主从表，relatedId为关联的从表的外键 slaveUrl为axios的api模块名字
	如EquipmentSlaveInfo slaveHtmlUrl为跳转到从表的url如/equipment/equipmentslaveinfo slaveButtonShow是对应从表按
	钮是否显示，detailButtonShow为详情按钮是否显示，slaveAddButtonShow为从表增加数据按钮是否显示-->
	<kt-table permsEdit="sys:${entity}:edit" permsDelete="sys:${entity}:delete" slaveUrl="slaveUrl" slaveHtmlUrl='/slaveHtmlUrl' relatedId="relatedId"
		:data="pageResult" :columns="columns" :pageRequest="pageRequest" :slaveButtonShow="false" :detailButtonShow="false" :slaveAddButtonShow="false"
		@findPage="findPage" @handleEdit="handleEdit" @handleDelete="handleDelete">
	</kt-table>
	<!--新增编辑界面-->
	<el-dialog :title="operation?'新增':'编辑'" width="50%" :visible.sync="editDialogVisible" :close-on-click-modal="false">
		<el-form :model="dataForm" label-width="80px" :rules="dataFormRules" ref="dataForm" :size="size" :inline="false">
			<#list table.fields as field>
			<#if field.propertyType == "LocalDateTime">
			<el-form-item label="${field.comment}" prop="${field.propertyName}" v-if="true">
				<el-date-picker type="datetime" placeholder="请选择"${field.propertyName} v-model="dataForm.${field.propertyName}" style="width: 100%;">
				</el-date-picker>
			</el-form-item>
			<#else>
			<el-form-item label="${field.comment}" prop="${field.propertyName}" v-if="true">
				<el-input v-model="dataForm.${field.propertyName}" auto-complete="off" suffix-icon="***"></el-input>
			</el-form-item>
			</#if>
			</#list>
		</el-form>
		<div slot="footer" class="dialog-footer">
			<el-button :size="size" @click.native="editDialogVisible = false">{{$t('action.cancel')}}</el-button>
			<el-button :size="size" type="primary" @click.native="submitForm" :loading="editLoading">{{$t('action.submit')}}</el-button>
		</div>
	</el-dialog>
  </div>
</template>

<script>
import KtTable from "@/views/Core/MyKtTable";
import KtButton from "@/views/Core/KtButton";
import { format } from "@/utils/datetime";
import {loadOaOptions,getCascaderList,getNowTime,hasValue,getTime} from "@/utils/common";

export default {
	components:{
			KtTable,
			KtButton
	},
	data() {
		return {
			size: 'small',
			filters: {
				label: ''
			},
			columns: [
			<#list table.fields as field>
				<#if field.propertyType == "LocalDateTime">
				{prop:"${field.propertyName}", label:"${field.comment}", minWidth:200, show:true},
				<#else>
				{prop:"${field.propertyName}", label:"${field.comment}", minWidth:100, show:true},
				</#if>
			</#list>
			],
			pageRequest: { current: 1, size: 20 },
			pageResult: {},
			operation: false, // true:新增, false:编辑
			editDialogVisible: false, // 新增编辑界面是否显示
			editLoading: false,
			dataFormRules: {
				label: [
					{ required: true, message: '请输入名称', trigger: 'blur' }
				]
			},
			// 新增编辑界面数据
			dataForm: {
				<#list table.fields as field>
				${field.propertyName}: null,
				</#list>
			}
		}
	},
	methods: {
		// 获取分页数据
		findPage: function () {
			//this.pageRequest.columnFilters = {label: {name:'label', value:this.filters.label}}
			this.$api.${entity}.findPage(this.pageRequest).then((res) => {
				this.pageResult = res.data;
			})
		},
		// 批量删除
		handleDelete: function (data) {
			let postData=[];

			if(hasValue(data)&&hasValue(data.params)&&hasValue(data.params.length)&&data.params.length>0){
				for(let i=0;i<data.params.length;i++){
					postData.push({
						id:data.params[i].id,
						isDeleted:true
					});
				}
			}

			this.$api.${entity}.save(postData).then(data != null ? data.callback : '');
		},
		// 显示新增界面
		handleAdd: function () {
			this.editDialogVisible = true;
			this.operation = true;
			this.dataForm = {
			<#list table.fields as field>
				${field.propertyName}: null,
			</#list>
			// if (hasValue(this.$route.query.id)) {// 外键id，从表添加数据
			// 	this.dataForm.equipmentId=this.$route.query.id;
			// 	this.dataForm.equipmentCode=this.$route.query.code;
			// }
			}
		},
		// 显示编辑界面
		handleEdit: function (params) {
			this.editDialogVisible = true;
			this.operation = false;
			this.dataForm = Object.assign({}, params.row);
		},
		// 编辑
		submitForm: function () {
			this.$refs.dataForm.validate((valid) => {
				if (valid) {
					this.$confirm('确认提交吗？', '提示', {}).then(() => {
						this.editLoading = true;
						let params = Object.assign({}, this.dataForm);
						<#list table.fields as field>
						<#if field.propertyType == "LocalDateTime">
						if(hasValue(this.dataForm.${field.propertyName})){ // 日期处理
							params.${field.propertyName}=getTime(this.dataForm.${field.propertyName})
						}
						</#if>
						</#list>
						this.$api.${entity}.saveOne(params).then((res) => {
							if(res.code == '200') {
								this.$message({ message: '操作成功', type: 'success' })
							} else {
								this.$message({message: '操作失败, ' + res.msg, type: 'error'})
							}
							this.editLoading = false;
							this.$refs['dataForm'].resetFields();
							this.editDialogVisible = false;
							this.findPage();
						});
					});
				}
			})
		},
		// 时间格式化
      	dateFormat: function (row, column, cellValue, index){
          	return format(row[column.property]);
      	}
	},
	mounted() {
		if (hasValue(this.$route.query.id)) {//此表作为从表 外键id
			this.editDialogVisible=true;
			this.operation=true;
			this.dataForm.equipmentId=this.$route.query.id; // 修改equipmentId为此表外键
			this.dataForm.equipmentCode=this.$route.query.code; // 修改equipmentCode为此表外键
		}
	}
}
</script>

<style lang="scss">
	.el-form-item label:after {
		content: "";
		display: inline-block;
		width: 100%;
	}
	.el-form-item__label {
		text-align: justify;
		height: 25px;
	}
</style>