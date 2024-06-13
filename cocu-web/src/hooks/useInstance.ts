import { getCurrentInstance, ComponentInternalInstance } from 'vue'

export default function useInstance(){
    const { appContext,proxy } = getCurrentInstance() as ComponentInternalInstance
    // get global instance
    const global = appContext.config.globalProperties
    return{
        global,
        proxy
    }
}