import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useTicketStore = defineStore('ticket', () => {
  const list = ref([])
  const loading = ref(false)
  const error = ref(null)

  async function load() {
    loading.value = true
    error.value = null
    const apiMod = await import('@/stores/api')
    const { get } = apiMod.useApi()
    try {
      const data = await get('/api/tickets')
      list.value = data || []
    } catch (e) {
      error.value = e?.response?.data?.message || e?.message || String(e)
    } finally {
      loading.value = false
    }
  }

  return { list, loading, error, load }
})