import { createRouter, createWebHistory } from 'vue-router';
import BattleIndex from "@/views/battle/BattleIndex";
import ErrorIndex from "@/views/error/ErrorIndex";
import PeakIndex from "@/views/peak/PeakIndex";
import ReviewIndex from "@/views/review/ReviewIndex";
import MyBattleIndex from "@/views/user/battle/MyBattleIndex";
import UserRegisterView from "@/views/user/account/UserRegisterView";
import UserLoginView from "@/views/user/account/UserLoginView";
import ReviewContent from "@/views/review/ReviewContent";
import store from "@/store/index";
import UserLoginQQView from "@/views/user/account/UserLoginQQView";

const routes = [

  {
    path: "/",
    name: "rootIndex",
    redirect: "/battle",
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/battle",
    name: "battleIndex",
    component: BattleIndex,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/404",
    name: "errorIndex",
    component: ErrorIndex,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/peak",
    name: "peakIndex",
    component: PeakIndex,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/review",
    name: "reviewIndex",
    component: ReviewIndex,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/review/:reviewId/",
    name: "reviewContent",
    component: ReviewContent ,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/mybattle",
    name: "myBattleIndex",
    component: MyBattleIndex,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/user/account/qq/receive_code",
    name: "user_account_qq_receive_code",
    component: UserLoginQQView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/login",
    name: "userLoginView",
    component: UserLoginView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/register",
    name: "userRegisterView",
    component: UserRegisterView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/:catchAll(.*)",
    name: "restIndex",
    redirect: "/404",
  },

]

const router = createRouter({
  history: createWebHistory(),
  routes
})

//启用router之前调用的函数
router.beforeEach((to,from,next) => {
  //需要授权 并且 没有登录
  if (to.meta.requestAuth && !store.state.user.is_login){
    next({name: "userLoginView"});
  }else {
    //默认跳转
    next();
  }

});

export default router
