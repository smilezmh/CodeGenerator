import axios from '../../../axios'

// 分页查询
export const findPage = (data) => {
    return axios({
        url: '<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>/getPageByContition',
        method: 'post',
        data
    },"biz")
}

// 无分页查询
export const findList = (data) => {
    return axios({
        url: '<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>/getListByQueryModel',
        method: 'post',
        data
    },"biz")
}

// 带id返回值的插入或者更新方法
export const saveOne = (data) => {
    return axios({
        url: '<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>/saveOrUpdateWithIdReturnBack',
        method: 'post',
        data
    },"biz")
}

// 根据查询条件查询数据是否存在
export const isExistsByQueryModel = (data) => {
    return axios({
        url: '<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>/isExistsByQueryModel',
        method: 'post',
        data
    },"biz")
}


// 根据条件串表分页查询信息
export const getTablesPageByContition = (data) => {
    return axios({
        url: '<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>/getTablesPageByContition',
        method: 'post',
        data
    },"biz")
}

// 根据条件串表不分页查询list
export const getTablesByContition = (data) => {
    return axios({
        url: '<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>/getTablesByContition',
        method: 'post',
        data
    },"biz")
}

// 根据更新条件更新了几条数据
export const updateByQueryModel = (data) => {
    return axios({
        url: '<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>/updateByQueryModel',
        method: 'post',
        data
    },"biz")
}

// 批量修改或插入
export const save = (data) => {
    return axios({
        url: '<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>/saveOrUpdataBath',
        method: 'post',
        data
    },"biz")
}

// 上传
export const upload = (data) => {
    return axios({
        url: '<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>/upload',
        method: 'post',
        data
    },"biz")
}

// 下载
export const download = (data) => {
    return axios({
        url: '<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>/download',
        method: 'post',
        data
    },"biz")
}


// 根据条件查询list任意一个实体信息
export const getRandomOneByQueryModel = (data) => {
    return axios({
        url: '<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>/getRandomOneByQueryModel',
        method: 'post',
        data
    },"biz")
}

// 根据条件查询list第一个实体信息
export const getFirstOneByQueryModel = (data) => {
	return axios({
		url: '<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>/getFirstOneByQueryModel',
        method: 'post',
        data
    },"biz")
}

// 批量插入带有业务主键判断是否重复判断
export const insertBatchWithCodeRepeatCheck = (data) => {
    return axios({
        url: '<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>/insertBatchWithCodeRepeatCheck',
        method: 'post',
        data
    },"biz")
}

// 生成单号
export const generateNo = (params) => {
    return axios({
        url: '<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>/generateNo',
        method: 'get',
        params
    },"biz")
}

// 根据业务主键code查询单条数据
export const getEntityByCode = (params) => {
    return axios({
        url: '<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>/getEntityByCode',
        method: 'get',
        params
    },"biz")
}


// 根据主键id查询单条数据
export const getEntityById = (params) => {
    return axios({
        url: '<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>/getEntityById',
        method: 'get',
        params
    },"biz")
}

// 根据id懒加载查找下级（下一级）
export const getSubList = (params) => {
    return axios({
        url: '/base/product-location/getSubList',
        method: 'get',
        params
    },"biz")
}

// 根据条件查找树结构，非懒加载方式
export const getListHasChildrenByContition = (data) => {
    return axios({
        url: '/base/product-location/getListHasChildrenByContition',
        method: 'post',
        data
    },"biz")
}

