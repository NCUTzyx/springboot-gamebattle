<template>
  <div ref="parent" class="play-info">
    <canvas ref="canvas" tabindex="0"></canvas>
  </div>
</template>

<script>
import {GameMap} from "@/assets/scripts/GameMap";
import { ref,onMounted } from "vue";
import {useStore} from "vuex";

export default {
  name: "GameInfo",
  setup:()=>{

    const store = useStore();
    let parent = ref('');
    let canvas = ref('');

    onMounted(() => {
      store.commit("updateGameObject",
          new GameMap(canvas.value.getContext('2d'),parent.value,store)
      )
    });


    return{
      parent,
      canvas,
    }

  }
}
</script>

<style scoped>

.play-info{
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}


</style>