import http from '@/http'

export type PageParam={
    pageSize:number,
    pageNum:number,
    total:number
}

// get page
export const getActivityListPageAPI = (pageParam:PageParam)=>{
    return http.post("/activity/listPage", pageParam)
}

// get by id
export const getActivityByIdAPI = (param:string)=>{
    return http.get('/activity/getById', {id:param})
}

export type ApplyJoinActivityParam = {
    userId:string,
    activityId:string
}

// apply for join activity
export const applyJoinActivityAPI = (param:ApplyJoinActivityParam)=>{
    return http.post('/user-apply-activity', param)
}

// get my activity
export const getMyActivityAPI = (userId:string)=>{
    return http.get('/activity/myActivity', {userId: userId})
}