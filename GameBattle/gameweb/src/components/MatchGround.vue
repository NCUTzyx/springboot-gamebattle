<template>
    <div class="matchGround">
        <div class="row">
          <div class="col-4">
            <div class="user-headshot">
              <img :src="$store.state.user.headshot" alt="头像">
            </div>
            <div class="user-username">
              {{$store.state.user.username}}
            </div>
          </div>
          <div class="col-4">
            <div class="robot-select">
              <select v-model="select_robot" class="from-select" aria-label="Default select example">
                <option value="-1" selected>亲自上阵</option>
                <option v-for="robot in robots" :key="robot.id" :value="robot.id">
                  {{robot.name}}
                </option>
              </select>
            </div>
          </div>
          <div class="col-4">
            <div class="user-headshot">
              <img :src="$store.state.battle.opponent_headshot" alt="头像">
            </div>
            <div class="user-username">
              {{$store.state.battle.opponent_username}}
            </div>
          </div>
          <div class="col-12" style="text-align: center; padding-top: 13vh">
            <button type="button" class="btn btn-success btn-lg" @click="click_match">{{match_btn}}</button>
          </div>
        </div>

    </div>
</template>

<script>
import {ref} from "vue";
import {useStore} from "vuex";
import $ from "jquery";

export default {
  name: "MatchGround",
  setup(){
    const store = useStore();
    let match_btn = ref("开始匹配");
    let robots = ref([]);
    let select_robot = ref('-1');

    const click_match = () =>{
      if (match_btn.value === "开始匹配"){
        match_btn.value = "取消匹配";
        store.state.battle.socket.send(JSON.stringify({
          event: "start-matching",
          robot_id: select_robot.value,
        }));
      }else {
        match_btn.value = "开始匹配";
        store.state.battle.socket.send(JSON.stringify({
          event: "stop-matching",
        }));
      }
    };
    const refresh_list =()=>{
      $.ajax({
        url: "https://app3817.acapp.acwing.com.cn/api/user/robot/getlist",
        type: "GET",
        headers: {
          Authorization: "Bearer " + store.state.user.token,
        },
        success(resp){
          robots.value = resp;
        }
      })
    };

    refresh_list();  //从云端获取robot

    return{
      match_btn,
      click_match,
      robots,
      select_robot,
    }
  }
}
</script>

<style scoped>

div.matchGround{
  width: 62vw;
  height: 72vh;
  margin: 20px auto;
  background-color: rgba(50,50,50,0.4);
}

div.user-headshot{
  text-align: center;
  padding-top: 12vh;
}
div.user-headshot > img{
  border-radius: 50%;
  width: 20vh;
}

div.user-username{
  text-align: center;
  font-size: 24px;
  font-weight: bolder;
  color: wheat;
  padding-top: 2vh;
}

div.robot-select{
  padding-top: 21vh;
  font-size: 24px;
}

div.robot-select > select {
  width: 60%;
  margin-left: 4vw;
  background: rgba(200,200,200,0.8);
}


</style>