import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    user: {
      username: "abc",
      password: "",
      realname: "",
      tel: "",
      address: "",
      role: 2
    }
  },
  getters: {},
  mutations: {
    setUser(state, user) {
      Object.assign(state.user,user);
    },
    exit(state) {
      for(let item in state.user) {
        state.user[item] = ""
      }
    }
  },
  actions: {},
  modules: {}
})
