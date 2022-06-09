import VueRouter from 'vue-router';


export const router = new VueRouter({
    mode: 'history',
    routes: [
        {
            path: '/',
            component: () => import('@/components/LoginForm')
        },
        {
            path: '/app',
            component: () => import('@/components/ComponentRequests')
        },
        {
            path:'/create',
            component: () => import('@/components/CreateRequest')
        }
    ]
});