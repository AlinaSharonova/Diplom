<template>
  <div id="app" class="register">
    <div class="modal">
      <h1 class="header"> Для входа в систему введите логин и пароль</h1>
      <input class="input" type="text" placeholder="Логин" v-model="login.login"/>
      <input class="input" type="password" placeholder="Пароль" v-model="login.password"/>
      <input class="btn" v-on:click="signIn()" type="button" value="Вход"/>
    </div>
  </div>
</template>

<script>
export default {
  name: "LoginForm",
  data() {
    return {
      login: {
        login: "",
        password: ""
      }
    }
  },
  methods: {
    signIn() {
      console.log('clicked')
      if (this.login.login === 'admin') {
        localStorage.setItem('admin', true)
      } else {
        localStorage.clear()
      }
      this.$router.push('/app').catch(() => {
      })
      console.log(this.$route.meta)
    }
  }
}
</script>

<style scoped>
.modal {
  height: 40%;
  width: 30%;
  position: absolute;
  top: 32%;
  left: 35%;
  background-color: #708FFF;
  opacity: 70%;
}

.input {
  background-color: white;
  width: 60%;
  height: 8%;
  margin-left: 20%;
  margin-top: 2%;
}

.header {
  padding-left: 10%;
  margin-bottom: 10%;
  padding-right: 10%;
  text-align: center;
  font-family: "Calibri Light", serif;
}

.btn {
  z-index: 2;
  position: absolute;
  bottom: 20%;
  left: 42%;
  width: 100px;
  height: 40px;
  background-color: white;

}

.register {
  z-index: 1;
  width: 100%;
  height: 100%;
  position: absolute;
  top: 0;
  left: 0;
  background-image: url("../assets/img.png");
  background-size: 100% auto;
}
</style>
