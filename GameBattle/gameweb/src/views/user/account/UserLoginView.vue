<template>
  <ComCard v-if="!$store.state.user.is_info">
    <div class="row justify-content-md-center">
      <div class="col-3">
        <form @submit.prevent="login">
          <div class="mb-3">
            <label for="username" class="form-label">用户名</label>
            <input v-model="username" type="text" class="form-control" id="username" placeholder="请输入用户名">
          </div>
          <div class="mb-3">
            <label for="password" class="form-label">密&nbsp;&nbsp; 码</label>
            <input v-model="password" type="password" class="form-control" id="password" placeholder="请输入密码">
          </div>
          <div class="message">{{message}}</div>
          <button type="submit" class="btn btn-primary">提交</button>
        </form>

        <div @click="qq_login" style="text-align: center; margin-top: 15px;">
          <img height="30"
               style="cursor: pointer;"
               src="https://wiki.connect.qq.com/wp-content/uploads/2013/10/03_qq_symbol-1-250x300.png"
          />
          <br>
          <span style="font-size: 16px;font-weight: bolder;font-family: 华文楷体">QQ一键登录</span>
        </div>
      </div>
    </div>

  </ComCard>
</template>

<script>
import ComCard from "@/components/ComCard";
import {useStore} from "vuex";
import {ref} from "vue";
import router from "@/router";
import $ from "jquery";

export default {
  name: "UserLoginView",
  components: {
    ComCard
  },
  setup(){
    const store = useStore();
    let username = ref('');
    let password = ref('');
    let message = ref('');

    const jwt_token = localStorage.getItem("jwt_token");
    if (jwt_token != null){
      //更新 token
      store.commit("updateToken",jwt_token);
      //请求信息，如果token有效
      store.dispatch("getInfo",{
        success(){
          router.push({ name: "rootIndex" });
          store.commit("updateIsInfo",false);
        },
        error(){
          store.commit("updateIsInfo",false);
        },
      })
    }else {
      store.commit("updateIsInfo",false);
    }

    const login=()=> {
      store.dispatch("login",{
        username: username.value,
        password: password.value,
        success(){
          store.dispatch("getInfo",{
              success(){
                router.push({name: 'rootIndex' });
                // console.log(resp);
                // console.log(store.state.user);
              }
          });
        },
        error(){
          message.value = "用户名或密码错误"
        }
      })
    };
    const qq_login = () => {
      $.ajax({
        url: "https://app3817.acapp.acwing.com.cn/api/user/account/qq/apply_code",
        type: "GET",
        success: resp => {
          if (resp.result === "success") {
            window.location.replace(resp.apply_code_url);
          }
        }
      })
    };

    return{
      username,
      password,
      message,
      login,
      qq_login
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