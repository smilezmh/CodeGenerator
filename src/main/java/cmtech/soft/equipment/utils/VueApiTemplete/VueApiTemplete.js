import axios from '../../../equipmentaxios'


// 分页查询
export const findPage = (data) => {
    return axios({
        url: '/base/equipment-base-info/getPageByContition',
        method: 'post',
        data
    })
}

// 分页查询
export const saveOne = (data) => {
    return axios({
        url: '/base/equipment-base-info/saveOrUpdateWithIdReturnBack',
        method: 'post',
        data
    })
}

export const save = (data) => {
    return axios({
        url: '/base/equipment-base-info/saveOrUpdataBath',
        method: 'post',
        data
    })
}
