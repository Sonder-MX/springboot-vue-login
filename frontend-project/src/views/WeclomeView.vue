<template>
  <div style="display: flex; width: 100vw; height: 100vh; overflow: hidden">
    <div style="flex: 1">
      <el-image
        style="width: 100%; height: 100%"
        fit="cover"
        src="https://ts1.cn.mm.bing.net/th/id/R-C.eec02321ea106169d757f427b98b358d?rik=%2bgK43uKTPrZbCw&riu=http%3a%2f%2f00.minipic.eastday.com%2f20170823%2f20170823152907_d41d8cd98f00b204e9800998ecf8427e_4.jpeg&ehk=FsISayQ5Gjp%2boHXA8OW7nhrZdn2JEzUKk3lfW%2br0P70%3d&risl=&pid=ImgRaw&r=0">
      </el-image>
    </div>
    <div class="weclome-title">
      <div style="font-size: 28px; font-weight: bold; margin-bottom: 10px">Weclome Test Web</div>
      <div>SpringBoot + Vue</div>
    </div>
    <div class="right-box">
      <router-view />
    </div>
  </div>
</template>

<script setup></script>

<style scoped>
.weclome-title {
  position: absolute;
  bottom: 40px;
  left: 40px;
  color: #fff;
  text-shadow: 0 0 10px #eee;
}

.right-box {
  width: 400px;
  z-index: 1;
  background-color: #fff;
}
</style>
