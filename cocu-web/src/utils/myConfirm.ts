import { ElMessageBox } from "element-plus"

export default function myConfirm(text:string){
    return new Promise((resolve, reject)=>{
        ElMessageBox.confirm(
            text,
            'System Notification',
            {
                confirmButtonText: 'Confirm',
                cancelButtonText: 'Cancel',
                type: 'warning'
            }
        ).then(()=>{
            resolve(true)
        }).catch(()=>{
            reject(false)
        })
    }).catch(()=>{
        return false
    })
    
}