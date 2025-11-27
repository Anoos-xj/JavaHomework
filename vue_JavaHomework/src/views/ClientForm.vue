<template>
  <div class="max-w-xl mx-auto p-6 bg-white rounded shadow">
    <h1 class="text-2xl font-bold mb-4">火车售票记录提交</h1>

    <form @submit.prevent="handleSubmit" class="space-y-4">
      <div>
        <label class="block text-sm font-medium mb-1">乘客姓名</label>
        <input
          v-model="form.name"
          type="text"
          placeholder="请输入姓名"
          class="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
        <p v-if="errors.name" class="text-red-500 text-sm mt-1">{{ errors.name }}</p>
      </div>

      <div>
        <label class="block text-sm font-medium mb-1">身份证号码</label>
        <input
          v-model="form.idCard"
          type="text"
          placeholder="18位身份证号码"
          class="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
        <p v-if="errors.idCard" class="text-red-500 text-sm mt-1">{{ errors.idCard }}</p>
      </div>

      <div>
        <label class="block text-sm font-medium mb-1">出发城市</label>
        <input
          v-model="form.startCity"
          type="text"
          placeholder="如：北京"
          class="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
        <p v-if="errors.startCity" class="text-red-500 text-sm mt-1">{{ errors.startCity }}</p>
      </div>

      <div>
        <label class="block text-sm font-medium mb-1">到达城市</label>
        <input
          v-model="form.endCity"
          type="text"
          placeholder="如：上海"
          class="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
        <p v-if="errors.endCity" class="text-red-500 text-sm mt-1">{{ errors.endCity }}</p>
      </div>

      <button
        type="submit"
        :disabled="loading"
        class="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700 disabled:opacity-50"
      >
        {{ loading ? '提交中...' : '提交' }}
      </button>

      <p v-if="error" class="text-red-500 text-center">{{ error }}</p>
      <p v-if="success" class="text-green-600 text-center">{{ success }}</p>
    </form>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'

const loading = ref(false)
const error = ref(null)
const success = ref('')

const form = reactive({
  name: '',
  idCard: '',
  startCity: '',
  endCity: ''
})

const errors = reactive({
  name: '',
  idCard: '',
  startCity: '',
  endCity: ''
})

function validate() {
  let valid = true
  // 重置
  Object.keys(errors).forEach(k => errors[k] = '')

  if (!form.name.trim()) {
    errors.name = '请输入姓名'
    valid = false
  }
  const idRegex = /^[1-9]\d{5}(18|19|20)\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])\d{3}[\dX]$/i
  if (!idRegex.test(form.idCard.trim())) {
    errors.idCard = '身份证号码格式错误'
    valid = false
  }
  if (!form.startCity.trim()) {
    errors.startCity = '请输入出发城市'
    valid = false
  }
  if (!form.endCity.trim()) {
    errors.endCity = '请输入到达城市'
    valid = false
  }
  return valid
}

async function handleSubmit() {
  if (!validate()) return
  success.value = ''
  try {
    loading.value = true
    error.value = null
    const apiMod = await import('@/stores/api')
    const { post } = apiMod.useApi()
    await post('/api/tickets', {
      name: form.name,
      idCard: form.idCard,
      startCity: form.startCity,
      endCity: form.endCity
    })
    success.value = '提交成功'
    Object.keys(form).forEach(k => form[k] = '')
  } catch (e) {
    error.value = e?.response?.data?.message || e?.message || '提交失败'
  } finally {
    loading.value = false
  }
}
</script>