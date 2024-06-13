import Vue from 'vue';
import Vuex from 'vuex';
import router,{resetRouter} from "../router";
import createPersistedState from 'vuex-persistedstate'
Vue.use(Vuex)

function addNewRoute(menuList) {
    let routes = router.options.routes
    console.log(routes)
    routes.forEach(routeItem=> {
        if(routeItem.path=="/index"){
            menuList.forEach(menu => {
                let childRoute =  {
                    path: '/' + menu.menuClick,
                    name: menu.menuName,
                    meta: {
                        title: menu.menuName
                    },
                    component:()=>import('../components/'+menu.menuComponent)
                }

                routeItem.children.push(childRoute)
            })
        }
    })

    resetRouter()
    router.addRoutes(routes)
}

export default new Vuex.Store({
    state: {
        menuList: []
    },
    mutations: {
        setMenu(state,menuList) {
            state.menuList = menuList

            // generate route dynamic
            addNewRoute(menuList)
        },
        setMenuAfterRefresh(state, menuList){
            addNewRoute(menuList)
        }
    },
    getters: {
        getMenu(state) {
            return state.menuList
        }
    },
    plugins: [createPersistedState()]
})