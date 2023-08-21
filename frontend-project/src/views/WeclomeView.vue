<template>
  <div style="display: flex; width: 100vw; height: 100vh; overflow: hidden">
    <div style="flex: 1"></div>
    <div class="right-box"></div>
    <!-- <router-view /> -->
  </div>
</template>

<script setup></script>

<style scoped>
.right-box {
  width: 400px;
  z-index: 1;
  background-color: #fff;
}
</style>
