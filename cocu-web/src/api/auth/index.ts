import http from "@/http"

// get verification code
export const getImageAPI = ()=> {
    return http.post("/imageCode");
}

// register param
export type Register = {
    username: string,
    mobile: string,
    email: string,
    password: string,
    age: string,
    gender: string,
    emailCode: string
}
// register API
export const registerAPI = (param:Register)=>{
    return http.post("/register", param)
}


// login type
export type Login = {
    email: string,
    password: string,
    imageCode: string
}
// login API
export const loginAPI = (param:Login)=>{
    return http.post("/login", param)
}


// forget password param
export type ForgetPwd = {
    email: string,
    newPwd: string,
    emailCode: string,
    imageCode: string
}
// forget password API
export const forgetPwdAPI = (param:ForgetPwd)=>{
    return http.post("/forgetPwd", param)
}

// forget password param
export type Auth = {
    email: string,
    newPwd: string,
    emailCode: string,
    imageCode: string
}
// send verification email API
export const sendTemplateMailAPI = (param:Auth)=>{
    return http.post("/sendTemplateMail", param)
}

// check email code API
export const checkEmailCodeAPI = (param:Auth)=>{
    return http.post("/checkEmailCode", param)
}


