<template>
  <div></div>
</template>

<script>
import router from "@/router/index";
import {useStore} from "vuex";
import {useRoute} from "vue-router";
import $ from 'jquery'
export default {
  name: "UserLoginQQView",
  setup(){
    const myRoute = useRoute();
    const store = useStore();
    $.ajax({
      url: "https://app3817.acapp.acwing.com.cn/api/user/account/qq/receive_code",
      type: "GET",
      data: {
        code: myRoute.query.code,
        state: myRoute.query.state,
      },
      success: resp => {
        if (resp.result === "success") {
          localStorage.setItem("jwt_token", resp.jwt_token);
          store.commit("updateToken", resp.jwt_token);
          router.push({ name: "rootIndex" });
          store.commit("updateIsInfo",false);
        } else {
          router.push({name: "userLoginView"});
        }
      }
    })
  }
}
</script>

<style scoped>
</style>