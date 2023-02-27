<template>
  <ComCard>

    <div class="row justify-content-md-center">
      <div class="col-3">
        <form @submit.prevent="register">
          <div class="mb-3">
            <label for="username" class="form-label">用户名</label>
            <input v-model="username" type="text" class="form-control" id="username" placeholder="请输入用户名">
          </div>
          <div class="mb-3">
            <label for="password" class="form-label">密&nbsp;&nbsp; 码</label>
            <input v-model="password" type="password" class="form-control" id="password" placeholder="请输入密码">
          </div>
          <div class="mb-3">
            <label for="confirmPassword" class="form-label">确认密码</label>
            <input v-model="confirmPassword" type="password" class="form-control" id="confirmPassword" placeholder="请再次输入密码">
          </div>
          <div class="message">{{message}}</div>
          <button type="submit" class="btn btn-primary">提交</button>
        </form>
      </div>
    </div>

  </ComCard>
</template>

<script>
import ComCard from "@/components/ComCard";
import {ref} from "vue";
import $ from "jquery";
import router from "@/router";

export default {
  name: "UserRegisterView",
  components: {ComCard},
  setup(){

     let username = ref('');
    let password = ref('');
    let message = ref('');
    let confirmPassword = ref('');

    const register =()=>{
      $.ajax({
        url: "https://app3817.acapp.acwing.com.cn/api/user/account/register",
        type: "POST",
        data: {
          username: username.value,
          password: password.value,
          confirmPassword: confirmPassword.value,
        },
        success(resp){

          if (resp.message === "success"){
            router.push({name: "userLoginView"});
          }else {
            message.value = resp.message;
          }
        },
        error(resp){
          console.log(resp);
        }
      });

    }

    return{
      username,
      password,
      confirmPassword,
      message,
      register
    }
  }
}
</script>

<style scoped>

button{
  width: 100%;
}

.message{
  color: red;
}

</style>