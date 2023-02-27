<template>
  <nav class="navbar navbar-expand-lg navbar-dark text-bg-dark">
    <div class="container">
      <router-link class="navbar-brand" style="font-family: 'Satisfy',cursive;font-size: 26px;color: lightblue; margin-right: 20px" :to="{name:'rootIndex'}">Game-Battle</router-link>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarText">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <router-link :class="view === 'battleIndex' ? 'nav-link active': 'nav-link'" style="font-size: 20px; margin: 0 20px" aria-current="page" :to="{name: 'battleIndex'}">开始游戏</router-link>
          </li>
          <li class="nav-item">
            <router-link :class="view === 'reviewIndex' ? 'nav-link active': 'nav-link'" style="font-size: 20px; margin: 0 20px" :to="{name: 'reviewIndex'}">对局回顾</router-link>
          </li>
          <li class="nav-item">
            <router-link :class="view === 'peakIndex' ? 'nav-link active': 'nav-link'" style="font-size: 20px;margin: 0 20px" :to="{name:'peakIndex'}">巅峰榜</router-link>
          </li>
        </ul>

        <ul class="navbar-nav mb-2 mb-lg-0" v-if="$store.state.user.is_login">
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" style="font-family: 华文楷体; font-size: 20px"  href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
              {{ $store.state.user.username }}
            </a>
            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
              <li><router-link class="dropdown-item" :to="{name:'myBattleIndex'}">我的对战</router-link></li>
              <li><hr class="dropdown-divider"></li>
              <li><a class="dropdown-item" href="#" @click="logout">退出</a></li>
            </ul>
          </li>
        </ul>


        <ul class="navbar-nav mb-2 mb-lg-0" v-else-if="!$store.state.user.is_info">
          <li class="nav-item">
            <router-link class="nav-link" style="font-size: 18px;" :to="{name:'userLoginView'}" role="button" >
              登录
            </router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" style="font-size: 18px;" :to="{name:'userRegisterView'}" role="button">
              注册
            </router-link>
          </li>
        </ul>

      </div>
    </div>
  </nav>

</template>

<script>
import {useRoute} from "vue-router";
import {computed} from "vue";
import {useStore} from "vuex";
export default {
  name: "NavBar",
  setup:() =>{
    let route = useRoute();
    let view = computed(()=> route.name);
    let store = useStore();
    const logout = () => {
      store.dispatch("logout");
    }
    return{
      view,
      logout
    }
  }
}
</script>

<style scoped>


</style>