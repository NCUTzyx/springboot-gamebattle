<template>

  <GameField v-if="$store.state.battle.status === 'playing' "/>
  <MatchGround v-if="$store.state.battle.status === 'matching' "/>
  <ResultBoard v-if="$store.state.battle.loser !== 'none' "/>
  <div class="user-color" v-if="$store.state.battle.status === 'playing' && parseInt($store.state.user.id) === parseInt($store.state.battle.a_id)" style="color: lightcoral">红方</div>
  <div class="user-color" v-if="$store.state.battle.status === 'playing' && parseInt($store.state.user.id) === parseInt($store.state.battle.b_id)" style="color: lightblue">蓝方</div>
</template>

<script>
import GameField from "@/components/GameView/GameField";
import MatchGround from "@/components/MatchGround";
import { onMounted, onUnmounted } from "vue";
import { useStore } from "vuex";
import ResultBoard from "@/components/ResultBoard";

export default {
  name: "BattleIndex",
  components:{
    MatchGround,
    GameField,
    ResultBoard,
  },
  setup(){

    const store = useStore();
    const socketUrl = `wss://app3817.acapp.acwing.com.cn/websocket/${store.state.user.token}`;

    store.commit("updateLoser","none");
    store.commit("updateIsReview",false);

    let socket = null;
    //组件被挂载的时候 加载完界面
    onMounted(() => {

      store.commit("updateOpponent",{
        username: "opponent",
        headshot: "https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png",
      })

      socket = new WebSocket(socketUrl);

      //成功建立连接
      socket.onopen = () => {
        console.log("connected!");
        store.commit("updateSocket",socket);
      }

      //接收到信息
      socket.onmessage = msg => {
        const data = JSON.parse(msg.data);
        if (data.event === "start-matching"){   //匹配成功
          store.commit("updateOpponent",{
            username: data.opponent_username,
            headshot: data.opponent_headshot,
          });
          setTimeout(()=>{
            store.commit("updateStatus","playing");
          },200);
          store.commit("updateGame", data.game);

        } else if (data.event === "move") {
          console.log(data);
          const gameMap = store.state.battle.gameObject;
          const [snake0,snake1] = gameMap.snakes;
          snake0.set_direction(data.a_move);
          snake1.set_direction(data.b_move);

        } else if (data.event === "result") {
          console.log(data)
          const game = store.state.battle.gameObject;
          const [snake0,snake1] = game.snakes;
          //蛇死亡
          if (data.loser === "AB" || data.loser === "A") {
            snake0.status = 2;
          }
          if (data.loser === "AB" || data.loser === "B") {
            snake1.status = 2;
          }
          store.commit("updateLoser",data.loser);

        }
      };

      //关闭时候
      socket.onclose = () => {
        console.log("disconnected!");
        store.commit("updateStatus","matching");
      }
    });

    //组件被卸载的时候 关闭界面
    onUnmounted(() =>{

      //断开连接
      socket.close();
    });
  },

}
</script>

<style scoped>

div.user-color {
  text-align: center;
  font-size: 40px;
  font-family: 华文楷体;
  font-weight: bolder;
}

</style>