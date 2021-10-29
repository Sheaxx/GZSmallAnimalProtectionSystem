import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    user: {
      username: "",
      password: "",
      realname: "",
      tel: "",
      address: "",
      role: null
    }
  },
  getters: {},
  mutations: {
    setUser(state, user) {
      state.user.username = user.username;
      state.user.password = user.password;
      state.user.realname = user.realname;
      state.user.tel = user.tel;
      state.user.address = user.address;
      state.user.role = user.role;
    }
  },
  actions: {},
  modules: {}
})
