<template>
  <div>
    <div style="text-align: center; margin: 16px">
      <h2>重置用户密码</h2>
      <p style="color: #777777; font-size: 14px">Hi~ 欢迎使用 Test Web</p>
    </div>
    <div style="margin-top: 20px; width: 100%">
      <el-steps :active="active" simple style="margin-top: 20px">
        <el-step title="验证邮箱" :icon="Message" />
        <el-step title="重置密码" :icon="Edit" />
      </el-steps>
    </div>

    <!-- 根据active显示 -->
    <div style="padding: 20px">
      <div v-if="active === 1">
        <el-form label-width="80px" :model="emailForm" ref="emailFormRef" :rules="emailRules">
          <el-form-item label="邮箱" prop="email">
            <el-input placeholder="请输入邮箱" v-model="emailForm.email" />
          </el-form-item>
          <el-form-item label="验证码" prop="code">
            <el-row :getter="10" style="width: 100%">
              <el-col :span="14">
                <el-input v-model="emailForm.code" type="text" placeholder="请输入验证码" />
              </el-col>
              <el-col :span="10" style="text-align: right">
                <el-button type="success"> 获取验证码 </el-button>
              </el-col>
            </el-row>
          </el-form-item>
        </el-form>
      </div>
      <div v-else>
        <el-form label-width="80px" :model="pwdForm" ref="pwdFormRef" :rules="pwdRules">
          <el-form-item label="新密码" prop="pwd">
            <el-input placeholder="请输入新密码" v-model="pwdForm.pwd" type="password" />
          </el-form-item>
          <el-form-item label="确认密码" prop="rePwd">
            <el-input placeholder="请再次输入新密码" v-model="pwdForm.rePwd" type="password" />
          </el-form-item>
        </el-form>
      </div>
    </div>

    <div style="display: flex; justify-content: space-between; padding: 0 20px">
      <div style="padding-top: 10px">
        <el-link @click="router.push({ name: 'weclome-login' })">
          <el-icon style="translate: 0 1px"><ArrowLeftBold /></el-icon>
          <span>返回登录</span>
        </el-link>
      </div>
      <div>
        <el-button type="success" style="margin-top: 12px" @click="nextSetp">
          {{ active === 1 ? '下一步' : '完成' }}
        </el-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { Edit, Message, ArrowLeftBold } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const active = ref(1)
const emailFormRef = ref(null)
const pwdFormRef = ref(null)
const emailForm = reactive({
  email: '',
  code: '',
})
const pwdForm = reactive({
  pwd: '',
  rePwd: '',
})

const emailRules = {
  email: [
    { required: true, message: '请输入邮箱' },
    { type: 'email', message: '请输入正确的邮箱' },
  ],
  code: [
    { required: true, message: '请输入验证码' },
    { min: 6, max: 6, message: '请输入6位验证码' },
  ],
}
const pwdRules = {
  pwd: [
    { required: true, message: '请输入新密码' },
    { min: 6, max: 20, message: '请输入6-20位密码' },
  ],
  rePwd: [
    { required: true, message: '请再次输入新密码' },
    { min: 6, max: 20, message: '请输入6-20位密码' },
    {
      validator: (rule, value, callback) => {
        if (value !== pwdForm.pwd) {
          callback(new Error('两次输入密码不一致'))
        } else {
          callback()
        }
      },
    },
  ],
}

const nextSetp = () => {
  if (active.value++ > 1) active.value = 1
}
</script>

<style scoped></style>
