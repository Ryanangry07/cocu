import {createRouter, createWebHistory, RouteRecordRaw} from 'vue-router'
import Layout from '@/views/layout/index.vue'

const routes: Array<RouteRecordRaw> = [
    {
        path: '/login',
        name: 'login',
        component: () => import('@/views/auth/login.vue'),
        meta: {
            title: 'Login Page'
        }
    },
    {
        path: '/register',
        name: 'register',
        component: () => import('@/views/auth/register.vue'),
        meta: {
            title: 'Register Page'
        }
    },
    {
        path: '/forgetPwd',
        name: 'forgetPwd',
        component: () => import('@/views/auth/forgetPwd.vue'),
        meta: {
            title: 'Forget Password Page'
        }
    },
    {
        path: '/',
        component: Layout,
        redirect: '/home',
        children: [
            {
                path: '/home',
                name: 'home',
                component: () => import('@/views/home/home.vue'),
                meta: {
                    title: 'Home Page'
                }
            },
            {
                path: '/club',
                name: 'club',
                component: () => import('@/views/club/club.vue'),
                meta: {
                    title: 'Club Page'
                }
            },
            {
                path: '/clubDetails/:id',
                name: 'clubDetails',
                component: () => import('@/views/club/details.vue'),
                meta: {
                    title: 'Club Details Page'
                }
            },
            {
                path: '/activity',
                name: 'activity',
                component: () => import('@/views/activity/activity.vue'),
                meta: {
                    title: 'Activity Page'
                }
            },
            {
                path: '/activityDetails/:id',
                name: 'activityDetails',
                component: () => import('@/views/activity/details.vue'),
                meta: {
                    title: 'Activity Details Page'
                }
            },
            {
                path: '/news',
                name: 'news',
                component: () => import('@/views/news/news.vue'),
                meta: {
                    title: 'News Page'
                }
            },
            {
                path: '/notification',
                name: 'notification',
                component: () => import('@/views/notification/notification.vue'),
                meta: {
                    title: 'Notification Page'
                }
            },
            {
                path: '/notificationDetails/:id',
                name: 'notificationDetails',
                component: () => import('@/views/notification/details.vue'),
                meta: {
                    title: 'Notification Details Page'
                }
            },
            {
                path: '/profile',
                name: 'profile',
                component: () => import('@/views/profile/profile.vue'),
                meta: {
                    title: 'Profile'
                },
                redirect: '/profile/myProfile',
                children: [
                    {
                        path: '/profile/myProfile',
                        name: 'myProfile',
                        component: () => import('@/views/profile/myProfile.vue'),
                        meta: {
                            title: 'My Profile'
                        }
                    },
                    {
                        path: '/profile/myClub',
                        name: 'myClub',
                        component: () => import('@/views/profile/myClub.vue'),
                        meta: {
                            title: 'My Club'
                        }
                    },
                    {
                        path: '/profile/myActivity',
                        name: 'myActivity',
                        component: () => import('@/views/profile/myActivity.vue'),
                        meta: {
                            title: 'My Activity'
                        }
                    }
                ]
            }
        ]
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router