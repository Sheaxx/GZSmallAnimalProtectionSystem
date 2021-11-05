<template>
  <div id="login">
    <div class="container">
      <div class="form signup">
        <form action="" class="form">
          <h2 class="formTitle">注册</h2>
          <input
            type="text"
            placeholder="用户名"
            v-model="signInForm.username"
          />
          <input type="text" placeholder="密码" v-model="signInForm.password" />
          <button @click.prevent="signIn">Sign In</button>
        </form>
      </div>

      <div class="form signin">
        <form action="" class="form">
          <h2 class="formTitle">登录</h2>
          <input
            type="text"
            placeholder="用户名"
            v-model="signUpForm.username"
          />
          <input
            type="password"
            placeholder="密码"
            v-model="signUpForm.password"
          />
          <button @click.prevent="signUp">Sign Up</button>
        </form>
      </div>

      <div class="cover">
        <div class="overlay">
          <div class="slide left">
            <button @click="toSignIn">Sign Up</button>
          </div>
          <div class="slide right">
            <button @click="toSignUp">Sign In</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      signUpForm: {
        username: "",
        password: "",
      },
      signInForm: {
        username: "",
        password: "",
      },
    };
  },
  methods: {
    toSignIn() {
      const container = document.querySelector(".container");
      container.classList.remove("switch");
    },
    toSignUp() {
      const container = document.querySelector(".container");
      container.classList.add("switch");
    },
    signIn() {
      if (this.signInForm.username == "" && this.signInForm.password == "") {
        this.$message("请输入用户名和密码");
      } else if (this.signInForm.username == "") {
        this.$message("请输入用户名");
      } else if (this.signInForm.password == "") {
        this.$message("请输入密码");
      } else {
        let that = this;
        let obj = {};
        obj.username = this.signInForm.username;
        obj.password = this.signInForm.password;
        obj.realname = "";
        obj.tel = "";
        obj.address = "";
        obj.role = 0;
        this.$ajax
          .post("http://localhost:8081/user/signIn", obj)
          .then(function (res) {
            if (res.data == "success") {
              that.$message.success("注册成功，请登录");
            } else {
              that.$message.error("该用户名已被注册");
            }
          });
      }
    },
    signUp() {
      if (this.signUpForm.username == "" && this.signUpForm.password == "") {
        this.$message("请输入用户名和密码");
      } else if (this.signUpForm.username == "") {
        this.$message("请输入用户名");
      } else if (this.signUpForm.password == "") {
        this.$message("请输入密码");
      } else {
        let that = this;
        this.$ajax
          .get(
            "http://localhost:8081/user/signUp/" +
              this.signUpForm.username +
              "/" +
              this.signUpForm.password
          )
          .then(function (res) {
            if (res.data) {
              window.localStorage.setItem("username",that.signUpForm.username);
              window.localStorage.setItem("password",that.signUpForm.password);
              window.localStorage.setItem("realname",res.data.realname);
              window.localStorage.setItem("address",res.data.address);
              window.localStorage.setItem("tel",res.data.tel);
              window.localStorage.setItem("role",res.data.role);
              that.$router.replace("/user");
              that.$message({
                message: "登录成功",
                type: "success",
              });
            } else {
              that.$message.error("用户名或密码错误");
            }
          })
          .catch((err) => console.log(err));
      }
    },
  },
  created() {
    console.log(window.localStorage.getItem("username"));
  }
  
};
</script>

<style>
#login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: url("../assets/image/background.png") no-repeat fixed center;
  background-size: cover;
}
#login .container {
  position: relative;
  width: 60vw;
  height: 70vh;
  background: #fcfaf1;
  box-shadow: 0 1rem 1.5rem rgba(0, 0, 0, 0.3),
    0 0.5rem 0.5rem rgba(0, 0, 0, 0.2);
  border-radius: 1rem;
  overflow: hidden;
}
#login .form {
  position: absolute;
  top: 0;
  height: 100%;
  transition: all 0.5s ease-in-out;
}
.signup {
  left: 0;
  width: 50%;
  z-index: 1;
  opacity: 0;
}
.signin {
  left: 0;
  width: 50%;
  z-index: 2;
  opacity: 1;
}
.container.switch .signup {
  transform: translateX(100%);
  z-index: 5;
  opacity: 1;
}
.container.switch .signin {
  transform: translateX(100%);
  opacity: 0;
}
.cover {
  position: absolute;
  left: 50%;
  top: 0;
  width: 50%;
  height: 100%;
  overflow: hidden;
  transform: translateX(0);
  transition: all 0.5s ease-in-out;
  z-index: 999;
}
.overlay {
  position: absolute;
  top: 0;
  left: -100%;
  width: 200%;
  height: 100%;
  background: url("../assets/image/background.png") no-repeat fixed center;
  background-size: cover;
  transition: all 0.5s ease-in-out;
}
.container.switch .cover {
  transform: translateX(-100%);
}
.container.switch .overlay {
  transform: translateX(50%);
}
.slide {
  position: absolute;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 50%;
  transition: all 0.5s ease-in-out;
}
.left {
  transform: translateX(-20%);
}
.right {
  transform: translateX(0);
  right: 0;
}
.container.switch .left {
  transform: translateX(0);
}
.container.switch .right {
  transform: translateX(20%);
}
.form {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100%;
}
#login input {
  width: 100%;
  margin: 0.5rem 0;
  padding: 1rem;
  border: none;
}
.formTitle {
  font-weight: 300;
  margin-bottom: 1rem;
  font-size: 2rem;
}
#login button {
  outline: none;
  border: none;
  padding: 1rem 3rem;
  margin-top: 1.5rem;
  background-image: -webkit-linear-gradient(40deg, #ff7f41 0%, #fbd04f 70%);
  border-radius: 1rem;
  color: #fcfaf1;
  font-size: 1rem;
  font-weight: 600;
  transition: transform 0.1s ease-in-out;
}
#login button:active {
  transform: scale(0.95);
}
</style>