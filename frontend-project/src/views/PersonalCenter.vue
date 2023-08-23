<template>
  <div style="padding: 50px; text-align: center">
    <el-button type="primary" style="margin: 0 20px" @click="userLogout"> 退出登录 </el-button>
  </div>
</template>

<script setup>
import { logout } from '../net'
import { useRouter } from 'vue-router'

const router = useRouter()

function userLogout() {
  logout(() => router.push('/'))
}
</script>

<style scoped></style>
