<template>
  <div class="max-w-5xl mx-auto p-6 bg-white rounded shadow">
    <div class="flex items-center justify-between mb-4">
      <h1 class="text-2xl font-bold">售票记录管理</h1>
      <button
        @click="load"
        :disabled="loading"
        class="px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700 disabled:opacity-50"
      >
        {{ loading ? '加载中...' : '刷新' }}
      </button>
    </div>

    <p v-if="error" class="text-red-500 mb-4">{{ error }}</p>

    <div class="overflow-x-auto">
      <table class="min-w-full border-collapse border border-gray-300">
        <thead>
          <tr class="bg-gray-100">
            <th class="border border-gray-300 px-4 py-2 text-left">#</th>
            <th class="border border-gray-300 px-4 py-2 text-left">姓名</th>
            <th class="border border-gray-300 px-4 py-2 text-left">身份证号码</th>
            <th class="border border-gray-300 px-4 py-2 text-left">出发城市</th>
            <th class="border border-gray-300 px-4 py-2 text-left">到达城市</th>
            <th class="border border-gray-300 px-4 py-2 text-left">提交时间</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(t, idx) in list" :key="t.id" class="hover:bg-gray-50">
            <td class="border border-gray-300 px-4 py-2">{{ idx + 1 }}</td>
            <td class="border border-gray-300 px-4 py-2">{{ t.name }}</td>
            <td class="border border-gray-300 px-4 py-2">{{ t.idCard }}</td>
            <td class="border border-gray-300 px-4 py-2">{{ t.startCity }}</td>
            <td class="border border-gray-300 px-4 py-2">{{ t.endCity }}</td>
            <td class="border border-gray-300 px-4 py-2">{{ new Date(t.createdAt).toLocaleString() }}</td>
          </tr>
          <tr v-if="list.length === 0">
            <td colspan="6" class="text-center text-gray-500 border border-gray-300 px-4 py-8">
              暂无记录
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { onMounted } from 'vue'
import { storeToRefs } from 'pinia'
import { useTicketStore } from '@/stores/ticket'

const store = useTicketStore()
const { list, loading, error } = storeToRefs(store)
const { load } = store

onMounted(() => load())
</script>