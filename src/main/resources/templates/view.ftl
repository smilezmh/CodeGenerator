<template>
  <div class="container" style="width:99%;">
	<!--工具栏-->
	<el-card style="margin-left:1px;margin-right:1px;" :style="{height:toolbarHeight}">
		<div class="toolbar">
			<el-row>
				<el-button-group style="float: left">
					<el-button @click="drawer = true" icon="el-icon-notebook-2" type="primary" size="mini" style="margin-left: 10px;">
						展开查询条件
					</el-button>
					<kt-button :label="$t('action.search')" perms="sys:${entity}:view" type="primary" icon="el-icon-search" @click="findPage()" />
					<kt-button :label="$t('action.add')" perms="sys:${entity}:add" type="primary" icon="el-icon-plus" @click="handleAdd" />
					<el-button @click="resetFilters('filters')" type="primary" size="mini" icon="el-icon-refresh-left">
						重置搜索条件
					</el-button>
				</el-button-group>
				<el-upload style="float: right;padding-right: 25px"
						   :before-upload="handleBeforeUpload" :action="actionUrl()" multiple>
					<el-button size="mini" type="primary">点击上传excel</el-button>
				</el-upload>
			</el-row>
		</div>
	</el-card>

	  <el-drawer
			  :visible.sync="drawer"
			  direction="btt" size="55%" title="查询条件">
		  <!--工具栏-->
		  <div style="margin-bottom:10px;padding-left:30px;">
			  <el-form :inline="true" :model="filters" size="mini" label-width="100px" ref="filters"
					   label-position="left">
				  <el-col :span="8">
					  <el-form-item label="编码" prop="code">
						  <el-input v-model="filters.code" placeholder="编码"></el-input>
					  </el-form-item>
				  </el-col>
				  <el-col :span="8">
					  <el-form-item label="名称" prop="name">
						  <el-input v-model="filters.name" placeholder="名称"></el-input>
					  </el-form-item>
				  </el-col>
				  <el-col :span="8">
					  <el-form-item>
						  <el-button-group>
							  <kt-button :label="$t('action.search')" perms="sys:${entity}:view"
										 type="primary" @click="findPage()" icon="el-icon-search"/>
							  <kt-button :label="$t('action.add')" perms="sys:${entity}:add"
										 type="primary" @click="handleAdd" icon="el-icon-plus"/>
							  <el-button @click="exportExcel('filters')" type="primary" size="mini">
								  导出excel
							  </el-button>
							  <el-button @click="resetFilters('filters')" type="primary" size="mini" icon="el-icon-refresh-left">
								  重置搜索条件
							  </el-button>
						  </el-button-group>
					  </el-form-item>
				  </el-col>

			  </el-form>
		  </div>
	  </el-drawer>
	<!--表格内容栏 此表为主表，slaveButtonShow是否支持主从表，relatedId为关联的从表的外键 slaveUrl为axios的api模块名字
	如EquipmentSlaveInfo slaveHtmlUrl为跳转到从表的url如/equipment/equipmentslaveinfo slaveButtonShow是对应从表按
	钮是否显示，detailButtonShow为详情按钮是否显示，slaveAddButtonShow为从表增加数据按钮是否显示,rowSpanShow为是否显示
	详情卡片 spanMethod为向子组件传递的合并单元格方法，当合并单元格后建议rowSpanShow设置为false toolbarHeight为工具栏高度-->
	<kt-table permsEdit="sys:${entity}:edit" permsDelete="sys:${entity}:delete"  permsAdd="sys:${entity}:add" permsView="sys:${entity}:view"
              slaveUrl="slaveUrl" slaveHtmlUrl='/slaveHtmlUrl' relatedId="relatedId" :data="pageResult" :columns="columns" :pageRequest="pageRequest"
              :slaveButtonShow="false" :detailButtonShow="false" :slaveAddButtonShow="false" :rowSpanShow="true" :hasScope="false"
              @findPage="findPage" @handleEdit="handleEdit" @handleDelete="handleDelete" ref="ktTable" :toolbarHeight="toolbarHeight">
	</kt-table>
	<!--新增编辑界面-->
	<el-dialog :title="operation?'新增':'编辑'" width="50%" :visible.sync="editDialogVisible" :close-on-click-modal="false" v-dialogDrag>
		<el-form :model="dataForm" label-width="80px" :rules="dataFormRules" ref="dataForm" :size="size" :inline="false">
			<#list table.fields as field>
			<#if field.propertyType == "LocalDateTime">
			<el-form-item label="${field.comment}" prop="${field.propertyName}" v-if="true">
				<el-date-picker type="datetime" placeholder="请选择时间" v-model="dataForm.${field.propertyName}" style="width: 100%;">
				</el-date-picker>
			</el-form-item>
			<#elseif field.propertyName=="code">
			<el-form-item label="${field.comment}" prop="${field.propertyName}" v-if="true">
				<el-input v-model="dataForm.${field.propertyName}" auto-complete="off" suffix-icon="***" :disabled="codeEditFlag" style="width: 100%;"></el-input>
			</el-form-item>
			<#else>
			<el-form-item label="${field.comment}" prop="${field.propertyName}" v-if="true">
				<el-input v-model="dataForm.${field.propertyName}" auto-complete="off" suffix-icon="***" style="width: 100%;"></el-input>
			</el-form-item>
			</#if>
			</#list>
			<el-form-item label="上传图片">
				<el-upload style="float: left"
						   class="upload-demo"
						   :action="actionUrl()" :on-success="handleSuccess" :on-preview="handlePreview"
						   :before-upload="handleBeforeUpload"
						   :before-remove="handleRemove" list-type="picture" :data="uploadData"
						   drag
						   :file-list="fileList"
						   multiple>
					<i class="el-icon-upload"></i>
					<div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
					<div class="el-upload__tip" slot="tip">只能上传图片，且不超过10MB</div>
				</el-upload>
			</el-form-item>
		</el-form>
		<div slot="footer" class="dialog-footer">
			<el-button :size="size" @click.native="editDialogVisible = false">{{$t('action.cancel')}}</el-button>
			<el-button :size="size" type="primary" @click.native="submitForm" :loading="editLoading">{{$t('action.submit')}}</el-button>
		</div>
	</el-dialog>
  </div>
</template>

<script>
import Cookies from "js-cookie";
import KtTable from "@/views/Core/MainSlaveTable";
import KtButton from "@/views/Core/KtButton";
import { format } from "@/utils/datetime";
import {loadOaOptions,getCascaderList,getNowTime,hasValue,getTime,$export} from "@/utils/common";
import config from '@/http/config';

export default {
	components:{
			KtTable,
			KtButton
	},
	data() {
		return {
			filePath: "/application_data/files/cmmes_equipment/repair_imgs/",
			fileUrl: '',
			uploadData: {
				prefix: "",
				type: ""
			},
			fileList: [],
			toolbarHeight:300,
			spanArr: [], // 合并单元格，数组
			pos: 0, // 合并单元格，位置
			creater:"",
			createrId:0,
			modifierId:0,
			modifier:"",
			createTime:"",
			modifyTime:"",
			codeEditFlag:'disabled',
			drawer: false,
			size: 'mini',
			filters: {code: '',name:''},
			columns: [
			<#list table.fields as field>
				<#if field.propertyType == "LocalDateTime">
				{prop:"${field.propertyName}", label:"${field.comment}", minWidth:200, show:true,align:"center"},
				<#else>
				{prop:"${field.propertyName}", label:"${field.comment}", minWidth:100, show:true,align:"center"},
				</#if>
			</#list>
			],
			pageRequest: { current: 1, size: 20 },
			pageResult: {},
			operation: false, // true:新增, false:编辑
			editDialogVisible: false, // 新增编辑界面是否显示
			editLoading: false,
			dataFormRules: {
				code: [{required: true,message: '编码不能为空', trigger: 'blur'}],
				name: [{required: true,message: '名称不能为空',trigger: 'blur'}],
			},
			// 新增编辑界面数据
			dataForm: {
				<#list table.fields as field>
				${field.propertyName}: null,
				</#list>
				// srcUrl: null
			}
		}
	},
	methods: {

		// 获取分页数据
		findPage: function (data) {
			if (data != null) {
				this.pageRequest = data.pageRequest;
			}

			this.pageRequest.code=this.filters.code;
			this.pageRequest.name=this.filters.name;

			this.spanArr = [];
			this.$api.${entity}.findPage(this.pageRequest).then((res) => {
				this.pageResult = res.data;
				// spanRowsMerge(this,res)
			}).then(data != null ? data.callback : '')
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
			this.codeEditFlag=false;
			this.dataForm = {
			<#list table.fields as field>
				${field.propertyName}: null,
			</#list>
			}

			this.$nextTick(() => {
				this.$refs['dataForm'].resetFields();
			});

			this.dataForm.createrId=this.createrId;
			this.dataForm.creater=this.creater;
			this.dataForm.createTime=getNowTime();
			this.dataForm.modifyTime=getNowTime();
			// if (hasValue(this.$route.query.id)) {// 外键id，从表添加数据
			// 	this.dataForm.equipmentId=this.$route.query.id;
			// 	this.dataForm.equipmentCode=this.$route.query.code;
			// }
		},
		// 显示编辑界面
		handleEdit: function (params) {
			this.editDialogVisible = true;
			this.operation = false;
			this.codeEditFlag=true;

			this.$nextTick(() => {
				this.$refs['dataForm'].clearValidate();
			});

			this.dataForm = Object.assign({}, params.row);
			this.dataForm.modifierId=this.modifierId;
			this.dataForm.modifier=this.modifier;
			this.dataForm.modifyTime=getNowTime();

			if (params.row.srcUrl) {
				let srcUrl = params.row.srcUrl;

				let srcList = srcUrl.toString().split(",");
				this.fileList = [];
				srcList.forEach(item => {
					let fileName = item.substring(item.lastIndexOf('=') + 1);
				this.fileList.push({name: fileName, url: item})
				})
			}
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
					}).catch(() => {});
				}
			})
		},
		// 时间格式化
      	dateFormat: function (row, column, cellValue, index){
          	return format(row[column.property]);
      	},
		exportExcel(filters) {
			let condition = {

			};

			$export(config.bizurl + "<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>",condition,getNowTime().toString("yyyy-MM-dd"));
		},
		objectSpanMethod({row, column, rowIndex, columnIndex}) {// 合并单元格
			let that = this;

			if (columnIndex === 0) {
				const _row = that.spanArr[rowIndex];
				const _col = _row > 0 ? 1 : 0;
				return {
					rowspan: _row,
					colspan: _col
				}
			}
		},
		resetFilters(form) {
			this.$nextTick(() => {
				this.$refs[form].resetFields();
		})
		},
		actionUrl() { // 上传参数可以任意写，后边用handleBeforeUpload方法覆盖
                return "http://api.cmtech-soft-test.com/cmmes-base-service/feign/FileHelp/upload/"
        },
		handleBeforeUpload(file) {
			this.uploadData.prefix = this.dataForm.code;
			let fd = new FormData()
			fd.append('file', file)
			fd.append("prefix", this.uploadData.prefix)
			fd.append("path", this.filePath)
			this.$api.ecn.FileUpload(fd).then((res) => {})
         },
		handleSuccess(response, file, fileList) { // 增加list
			 file.name = this.dataForm.code + "-" + file.name;

			 if (response.code == '200') {
				 this.$message({message: '上传成功', type: 'success'});
                } else {
				 this.$message({message: '上传失败', type: 'warning'});}

			 let lenth = fileList.length;
			 let num = 0;

			 for (let i = 0; i < lenth; i++) {
				 if (file.name == fileList[i].name) {
					 num++;
				 }

				 if (num > 1) {
					 fileList.splice(i, 1) // 如果重复就更新数组，删掉重复
				 }
			 }

			 this.fileList = fileList;
			 this.savePicSrcUrl()
		 },
		handlePreview(file) {
			window.location.href = file.url// 在本页面下载
		},
		handleRemove(file, fileList) { // 删除文件，通过文件名
			this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
				confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
			}).then(() => {
				let postdata = {fileName: file.name, path: this.filePath}
				// 删除文件
				this.$api.ecn.FileDelete(postdata).then((res) => {
					if (res.code == '200') {
						this.$message({message: '删除成功', type: 'success'});
						let length = fileList.length;

						for (let i = 0; i < length; i++) {
							if (file.name == fileList[i].name) {
								fileList.splice(i, 1) // 如果删除成功就删掉文件列表
								break;
							}
						}

						this.fileList = fileList;
                            this.savePicSrcUrl()
                        } else {
                            this.$message({
                                message: '删除失败',
                                type: 'warning'
                            });
                            return false;
                        }
				})
			}).catch(() => {this.$message({type: 'info', message: '已取消删除'});});

			return false;
		},
		savePicSrcUrl(){

			if (this.fileList.length > 0) {
				this.dataForm.srcUrl = this.fileList.map(item => this.fileUrl + item.name).join(",");
			} else {
				this.dataForm.srcUrl = ''
			}

			let postData = {
				id: this.dataForm.id,
				srcUrl: this.dataForm.srcUrl
			}

			this.$api.${entity}.saveOne(postData).then((res) => {
				this.findPage();
			});
		}
	},
	mounted() {
		this.modifierId=Cookies.get('userId');
		this.modifier=Cookies.get('userName');
		this.createrId= Cookies.get('userId');
		this.creater=Cookies.get('userName');

		if (hasValue(this.$route.query.id)) {//此表作为从表 外键id
			this.editDialogVisible=true;
			this.operation=true;
			this.dataForm.equipmentId=this.$route.query.id; // 修改equipmentId为此表外键
			this.dataForm.equipmentCode=this.$route.query.code; // 修改equipmentCode为此表外键
		}

		switch (process.env.NODE_ENV) {
			case "development":
				this.fileUrl = "http://10.136.41.45:9020/FileHelp/downloadFiles?path=/Users/smilezmh/imgs/flowImg/&filename=";
				this.filePath = "/Users/smilezmh/imgs/flowImg/";
				break;
			case "production":
				this.fileUrl = "http://api.cmtech-soft-test.com/cmmes-base-service/feign/FileHelp/downloadFiles/?path=/application_data/files/cmmes_equipment/repair_imgs/&filename=";
				this.filePath = "/application_data/files/cmmes_***/***_imgs/";
				break;
			default:
				break;
		}
	},
	beforeRouteEnter(to,from,next){
		if(typeof to.query.hasNav!='undefined'&&to.query.hasNav=='false'){
			to.meta.keepAlive = false;
			next();
			return;
		};
		next()
	}
}

export function spanRowsMerge(that,res) {
	for (var i = 0; i < res.data.records.length; i++) {

		if (i === 0) {
			that.spanArr.push(1);
			that.pos = 0
		} else {
			// 判断当前元素与上一个元素是否相同
			if (res.data.records[i].projectNo === res.data.records[i - 1].projectNo) {//??改为要合并的字段
				that.spanArr[that.pos] += 1;
				that.spanArr.push(0);
			} else {
				that.spanArr.push(1);
				that.pos = i;
			}
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

	.el-form-item.is-required:not(.is-no-asterisk) > .el-form-item__label:before {
		display: none !important;
	}
</style>