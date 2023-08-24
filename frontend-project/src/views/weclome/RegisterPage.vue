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
          <el-input v-model="rgForm.email" type="email" placeholder="请输入邮箱">
            <template #prefix>
              <el-icon> <Message /> </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="code">
          <el-row :getter="10" style="width: 100%">
            <el-col :span="16">
              <el-input v-model="rgForm.code" type="text" placeholder="请输入验证码">
                <template #prefix>
                  <el-icon> <Promotion /> </el-icon>
                </template>
              </el-input>
            </el-col>
            <el-col :span="8">
              <el-button type="success" :disabled="codeTime > 0" @click="askCode">
                {{ codeTime > 0 ? `${codeTime}秒后重新获取` : '获取验证码' }}
              </el-button>
            </el-col>
          </el-row>
        </el-form-item>
      </el-form>
    </div>

    <div style="margin-top: 60px">
      <el-button style="width: 270px" type="primary" round @click="userRegister">注 册</el-button>
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
import { get, post } from '../../net'
import { ElMessage } from 'element-plus'

const router = useRouter()
const registerFormRef = ref()
const codeTime = ref(0)
const rgForm = reactive({
  username: '',
  password: '',
  rePwd: '',
  email: '',
  code: '',
})

// 验证用户名
const validataUserName = (rule, value, callback) => {
  if (!value) {
    return callback(new Error('请输入用户名'))
  }
  const reg = /^[\u4E00-\u9FA5A-Za-z0-9_-]{2,20}$/
  if (!reg.test(value)) {
    return callback(new Error('用户名至少2位，由大小写字母、数字、中文、下划线和减号组成'))
  }
  callback()
}

// 验证密码
const validataRePwd = (rule, value, callback) => {
  if (!value) {
    return callback(new Error('请再次输入密码'))
  }
  if (value !== rgForm.password) {
    return callback(new Error('两次输入密码不一致'))
  }
  callback()
}

const rules = {
  username: [{ validator: validataUserName, trigger: ['blur', 'change'] }],
  password: [
    { required: true, message: '请输入密码' },
    { min: 6, max: 20, message: '密码长度在 6 到 20 个字符', trigger: 'blur' },
  ],
  rePwd: [{ validator: validataRePwd, trigger: 'blur' }],
  email: [
    { required: true, message: '请输入邮箱' },
    { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] },
  ],
  code: [{ required: true, message: '请输入验证码' }],
}

// 发送验证码
function askCode() {
  // 验证邮箱是否合适
  if (/^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/.test(rgForm.email)) {
    get(
      `/api/auth/ask-code?email=${rgForm.email}&type=register`,
      () => {
        codeTime.value = 60
        setInterval(() => {
          if (codeTime.value > 0) {
            codeTime.value--
          }
        }, 1000)
        ElMessage.success('验证码已发送至邮箱，请注意查收')
      },
      () => {
        ElMessage.error('验证码发送失败，请稍后重试')
        codeTime.value = 0
      }
    )
  } else {
    ElMessage.warning('请输入正确的邮箱地址')
  }
}

function userRegister() {
  registerFormRef.value.validate((isValid) => {
    if (isValid) {
      post('/api/auth/register', { ...rgForm }, () => {
        ElMessage.success('注册成功')
        router.push('/')
      })
    } else {
      ElMessage.warning('请检查输入是否正确')
    }
  })
}
</script>

<style scoped></style>
