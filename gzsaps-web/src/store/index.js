import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    username:"aaa",
    role:2
  },
  getters:{
    getUsername(){
      return state.username;
    }
  },
  mutations: {},
  actions: {},
  modules: {}
})
