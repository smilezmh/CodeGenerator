<template>
  <div class="container" style="width:99%;margin-top:-25px;">
	<!--工具栏-->
	<div class="toolbar" style="float:left;padding-top:10px;padding-left:15px;">
		<el-form :inline="true" :model="filters" :size="size">
			<el-form-item>
				<el-input v-model="filters.label" placeholder="名称"></el-input>
			</el-form-item>
			<el-form-item>
				<kt-button :label="$t('action.search')" perms="sys:${entity}:view" type="primary" @click="findPage(null)"/>
			</el-form-item>
			<el-form-item>
				<kt-button :label="$t('action.add')" perms="sys:${entity}:add" type="primary" @click="handleAdd" />
			</el-form-item>
		</el-form>
	</div>
	<!--表格内容栏-->
	<kt-table permsEdit="sys:${entity}:edit" permsDelete="sys:${entity}:delete"
		:data="pageResult" :columns="columns" height="700px"
		@findPage="findPage" @handleEdit="handleEdit" @handleDelete="handleDelete">
	</kt-table>
	<!--新增编辑界面-->
	<el-dialog :title="operation?'新增':'编辑'" width="90%" :visible.sync="editDialogVisible" :close-on-click-modal="false">
		<el-form :model="dataForm" label-width="80px" :rules="dataFormRules" ref="dataForm" :size="size" :inline="true">
			<#list table.fields as field>
			<el-form-item label="${field.comment}" prop="${field.propertyName}">
				<el-input v-model="dataForm.${field.propertyName}" auto-complete="off"></el-input>
			</el-form-item>
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
import {loadOaOptions,getCascaderList,getNowTime,hasValue} from "@/utils/common";

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
				{prop:"${field.propertyName}", label:"${field.comment}", minWidth:100},
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
		findPage: function (data) {
			if(data !== null) {
				this.pageRequest = data.pageRequest
			}
			//this.pageRequest.columnFilters = {label: {name:'label', value:this.filters.label}}
			this.$api.${entity}.findPage(this.pageRequest).then((res) => {
				this.pageResult = res.data;
			})
		},
		// 批量删除
		handleDelete: function (data) {
			let postData=[]

			if(hasValue(data)&&hasValue(data.params)&&hasValue(data.params.length)&&data.params.length>0){
				for(let i=0;i<data.params.length;i++){
					postData.push({
						id:data.params[i].id,
						isDeleted:true
					})
				}
			}

			this.$api.EquipmentBaseInfo.save(postData).then(data != null ? data.callback : '')
		},
		// 显示新增界面
		handleAdd: function () {
			this.editDialogVisible = true
			this.operation = true
			this.dataForm = {
			<#list table.fields as field>
				${field.propertyName}: null,
			</#list>
			}
		},
		// 显示编辑界面
		handleEdit: function (params) {
			this.editDialogVisible = true
			this.operation = false
			this.dataForm = Object.assign({}, params.row)
		},
		// 编辑
		submitForm: function () {
			this.$refs.dataForm.validate((valid) => {
				if (valid) {
					this.$confirm('确认提交吗？', '提示', {}).then(() => {
						this.editLoading = true;
						let params = Object.assign({}, this.dataForm);
						this.$api.${entity}.save(params).then((res) => {
							if(res.code == 200) {
								this.$message({ message: '操作成功', type: 'success' })
							} else {
								this.$message({message: '操作失败, ' + res.msg, type: 'error'})
							}
							this.editLoading = false;
							this.$refs['dataForm'].resetFields();
							this.editDialogVisible = false;
							this.findPage();
						})
					})
				}
			})
		},
		// 时间格式化
      	dateFormat: function (row, column, cellValue, index){
          	return format(row[column.property]);
      	}
	},
	mounted() {
	}
}
</script>

<style scoped>

</style>