<template>
  <div class="container content">
    <div class="card">
      <div class="card-body">
        <slot></slot>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "ComCard",

}
</script>

<style scoped>

.content{
  margin-top: 15px;
}

</style>