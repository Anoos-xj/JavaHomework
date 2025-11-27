import axios from 'axios'
import { ref } from 'vue'

const BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080'

export const api = axios.create({
  baseURL: BASE_URL,
  timeout: 10000,
})

export function useApi() {
  const loading = ref(false)
  const error = ref(null)

  const post = async (url, data) => {
    loading.value = true
    error.value = null
    try {
      const res = await api.post(url, data)
      return res.data
    } catch (err) {
      error.value = err.response?.data?.message || err.message
      throw err
    } finally {
      loading.value = false
    }
  }

  const get = async (url, params) => {
    loading.value = true
    error.value = null
    try {
      const res = await api.get(url, { params })
      return res.data
    } catch (err) {
      error.value = err.response?.data?.message || err.message
      throw err
    } finally {
      loading.value = false
    }
  }

  return { post, get, loading, error }
}