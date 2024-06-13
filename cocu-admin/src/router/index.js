import VueRouter from "vue-router";

const routes = [
    {
        path: '/',
        name: 'index',
        component: () => import('../components/IndexContainer.vue')
    },
    {
        path: '/register',
        name: 'register',
        component: () => import('../components/RegisterModule.vue')
    },
    {
        path: '/index',
        name: 'index',
        component: () => import('../components/IndexContainer.vue'),
        children: [
            {
                path: '/home',
                name: 'home',
                meta: {
                    title: 'Home'
                },
                component: () => import('../components/HomeModule.vue')
            },
            {
                path: '/profile',
                name: 'profile',
                component: () => import('../components/user/UserProfile.vue')
            },
            /*{
                path: '/admin',
                name: 'admin',
                meta: {
                    title: 'Admin Management'
                },
                component: () => import('../components/admin/AdminManage.vue')
            },
            {
                path: '/user',
                name: 'user',
                meta: {
                    title: 'User Management'
                },
                component: () => import('../components/user/UserManage.vue')
            }*/
        ]
    }
]

const router = new VueRouter({
    mode: 'history',
    routes
})


const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}

export function resetRouter(){
    router.matcher = new VueRouter({
        mode: 'history',
        routes: []
    }).matcher
}

export default router;