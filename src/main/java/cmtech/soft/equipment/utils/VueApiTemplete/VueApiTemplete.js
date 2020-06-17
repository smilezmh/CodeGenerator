import axios from '../../../axios'

// 分页查询
export const findPage = (data) => {
    return axios({
        url: '/base/science-mold-test-edition/getPageByContition',
        method: 'post',
        data
    },"biz")
}

// 无分页查询
export const findList = (data) => {
    return axios({
        url: '/base/science-mold-test-edition/getListByQueryModel',
        method: 'post',
        data
    },"biz")
}

export const saveOne = (data) => {
    return axios({
        url: '/base/science-mold-test-edition/saveOrUpdateWithIdReturnBack',
        method: 'post',
        data
    },"biz")
}

export const save = (data) => {
    return axios({
        url: '/base/science-mold-test-edition/saveOrUpdataBath',
        method: 'post',
        data
    },"biz")
}

// 上传
export const upload = (data) => {
    return axios({
        url: '/base/science-mold-test-edition/upload',
        method: 'post',
        data
    },"biz")
}

// 下载
export const download = (data) => {
    return axios({
        url: '/base/science-mold-test-edition/download',
        method: 'post',
        data
    },"biz")
}


export const getRandomOneByQueryModel = (data) => {
    return axios({
        url: '/base/science-mold-test-edition/getRandomOneByQueryModel',
        method: 'post',
        data
    },"biz")
}

export const saveOrUpdateWithIdReturnBack = (data) => {
    return axios({
        url: '/base/science-mold-test-edition/saveOrUpdateWithIdReturnBack',
        method: 'post',
        data
    },"biz")
}

export const generateNo = (params) => {
    return axios({
        url: '/base/science-mold-test-edition/generateNo',
        method: 'get',
        params
    },"biz")
}




