<template>
  <div style="text-align: center; padding: 10px">
    <div>
      <h1>用户登录</h1>
      <p style="color: #777777">Hi~ 欢迎使用 Test Web</p>
    </div>
    <div style="margin-top: 80px">
      <el-form v-model="form">
        <el-form-item>
          <el-input v-model="form.username" maxlength="10" type="text" placeholder="请输入用户名">
            <template #prefix>
              <el-icon> <User /> </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.password" maxlength="20" type="password" placeholder="请输入密码">
            <template #prefix>
              <el-icon> <Lock /> </el-icon>
            </template>
          </el-input>
        </el-form-item>

        <el-row>
          <el-col :span="12" style="text-align: left">
            <el-form-item>
              <el-checkbox v-model="form.remember">记住我</el-checkbox>
            </el-form-item>
          </el-col>
          <el-col :span="12" style="text-align: right">
            <el-link>忘记密码?</el-link>
          </el-col>
        </el-row>
      </el-form>
    </div>

    <div style="margin-top: 40px">
      <el-button style="width: 270px" type="primary" round>登 录</el-button>
    </div>

    <el-divider>
      <span style="font-size: 12px">没有账号？</span>
    </el-divider>
    <el-button style="width: 270px" type="success" round plain>立即注册</el-button>
  </div>
</template>

<script setup>
import { User, Lock } from '@element-plus/icons-vue'
import { reactive } from 'vue'

const form = reactive({
  username: '',
  password: '',
  remember: false,
})
</script>

<style scoped></style>
