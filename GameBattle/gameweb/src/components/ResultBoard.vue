<template>

  <div class="result-board">
    <div class="result" v-if="$store.state.battle.loser === 'AB'">
      DRAW
    </div>
    <div class="result" v-else-if="$store.state.battle.loser === 'A' && $store.state.battle.a_id === parseInt($store.state.user.id)">
      LOSE
    </div>
    <div class="result" v-else-if="$store.state.battle.loser === 'B' && $store.state.battle.b_id === parseInt($store.state.user.id)">
      LOSE
    </div>
    <div class="result" v-else>
      WIN
    </div>
    <div class="result-btn">
      <button  type="button" class="btn btn-primary btn-lg" @click="restart">再试一次!</button>

    </div>
  </div>

</template>

<script>
import {useStore} from "vuex";


export default {
  name: "ResultBoard",
  setup(){

    const store = useStore();
    const restart = () => {
      store.commit("updateStatus","matching");
      store.commit("updateLoser","none");
      store.commit("updateOpponent",{
        username: "opponent",
        headshot: "https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png",
      })

    }
    return{
      restart,

    }
  }
}
</script>

<style scoped>

div.result-board{
  height: 30vh;
  width: 25vw;
  background-color: rgba(50,50,50,0.2);
  position: absolute;
  top:33vh;
  left:37.5vw
}

div.result{
  text-align: center;
  color: rgba(36,171,242);
  font-weight: bolder;
  font-size: 60px;
  font-style: italic;
  padding-top: 5vh;
  font-family: "Bookman Old Style";
}

div.result-btn{
  padding-top: 3vh;
  text-align: center;

}

</style>