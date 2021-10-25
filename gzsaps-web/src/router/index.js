import Vue from 'vue'
import VueRouter from 'vue-router'

import User from '../views/user'
import Index from '../views/index'

Vue.use(VueRouter)

const routes = [
	{
		path: '',
		redirect: '/user'
	},
	{
		path: '/index',
		component: Index,
		children: [
			{
				path:'/user',
				component:User
			},
			{
				path: '/notice',
				component: () => import('../views/notice')
			},
			{
				path: '/information',
				component: () => import('../views/information')
			},
			{
				path: '/adoption',
				component: () => import('../views/adoption')
			},
			{
				path: '/project',
				component: () => import('../views/project')
			}
		]
	},
	{
		path: '/login',
		component: () => import('../views/login')
	}
]

const router = new VueRouter({
	mode: 'history',
	base: process.env.BASE_URL,
	routes
})

export default router
