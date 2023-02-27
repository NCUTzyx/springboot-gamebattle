<template>
  <div class="play-field">
    <GameInfo/>
  </div>
</template>

<script>
import GameInfo from "@/components/GameView/GameInfo";

export default {
  name: "GameField",
  components:{
    GameInfo,
  }
}
</script>

<style scoped>
    .play-field{
      width: 62vw;
      height: 72vh;
      margin: 20px auto;
    }
</style>