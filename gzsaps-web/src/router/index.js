import Vue from 'vue'
import VueRouter from 'vue-router'

import User from '../views/user'

Vue.use(VueRouter)

const routes = [
	{
		path:'',
		redirect:'/user'
	},
	{
		path:'/user',
		component:User
	},
	{
		path:'/notice',
		component:() => import('../views/notice')
	},
	{
		path:'/information',
		component:() => import('../views/information')
	},
	{
		path:'/adoption',
		component:() => import('../views/adoption')
	},
	{
		path:'/project',
		component:() => import('../views/project')
	}
]

const router = new VueRouter({
	mode: 'history',
	base: process.env.BASE_URL,
	routes
})

export default router
