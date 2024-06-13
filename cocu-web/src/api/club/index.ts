import http from "@/http";

// get all club types
export const getClubTypeListAPI = ()=> {
    return http.get('/club-type/list')
}

// get entire club list
export const getClubListAPI = ()=>{
    return http.get('/club/list')
}

// get club list by type id
export const getClubListByTypeAPI = (typeId:string)=>{
    return http.get('/club/listByType', {typeId:typeId})
}

export const getClubByIdAPI = (param:string)=>{
    return http.get('/club/getById', {id:param})
}

export type ApplyJoinClubParam = {
    userId:string,
    clubId:string
}

export const applyJoinClubAPI = (param:ApplyJoinClubParam)=>{
    return http.post('/user-apply-club', param)
}

// get my club
export const getMyClubAPI = (userId:string)=>{
    return http.get('/club/myClub', {userId: userId})
}