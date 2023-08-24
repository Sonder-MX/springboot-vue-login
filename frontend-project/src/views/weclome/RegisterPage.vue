<template>
  <div style="text-align: center; padding: 18px">
    <div>
      <h1>用户注册</h1>
      <p style="color: #777777">Hi~ 欢迎使用 Test Web</p>
    </div>
    <div style="margin-top: 80px">
      <el-form :model="rgForm" :rules="rules" ref="registerFormRef">
        <el-form-item prop="username">
          <el-input v-model="rgForm.username" maxlength="10" type="text" placeholder="请输入用户名">
            <template #prefix>
              <el-icon> <User /> </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="rgForm.password"
            maxlength="20"
            type="password"
            placeholder="请输入密码">
            <template #prefix>
              <el-icon> <Lock /> </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="rePwd">
          <el-input
            v-model="rgForm.rePwd"
            maxlength="20"
            type="password"
            placeholder="请再次输入密码">
            <template #prefix>
              <el-icon> <Lock /> </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="email">
          <el-input v-model="rgForm.email" maxlength="20" type="email" placeholder="请输入邮箱">
            <template #prefix>
              <el-icon> <Message /> </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="code">
          <el-row :getter="10" style="width: 100%">
            <el-col :span="16">
              <el-input v-model="rgForm.code" maxlength="20" type="text" placeholder="请输入验证码">
                <template #prefix>
                  <el-icon> <Promotion /> </el-icon>
                </template>
              </el-input>
            </el-col>
            <el-col :span="8">
              <el-button type="success">获取验证码</el-button>
            </el-col>
          </el-row>
        </el-form-item>
      </el-form>
    </div>

    <div style="margin-top: 60px">
      <el-button style="width: 270px" type="primary" round>注 册</el-button>
    </div>

    <div style="margin-top: 50px">
      <el-divider>
        <span>已有账号？</span>
        <el-link style="translate: 0 -2px" @click="router.push('/')">立即登录</el-link>
      </el-divider>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { User, Lock, Message, Promotion } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const registerFormRef = ref()
const rgForm = reactive({
  username: '',
  password: '',
  rePwd: '',
  email: '',
  code: '',
})
const rules = {
  username: [{ required: true, message: '请输入用户名' }],
  password: [{ required: true, message: '请输入密码' }],
  rePwd: [{ required: true, message: '请再次输入密码' }],
  email: [{ required: true, message: '请输入邮箱' }],
  code: [{ required: true, message: '请输入验证码' }],
}
</script>

<style scoped></style>
