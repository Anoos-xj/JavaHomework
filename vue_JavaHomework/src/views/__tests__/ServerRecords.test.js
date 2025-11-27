import { describe, it, expect, vi } from 'vitest'
import { ref } from 'vue'
import { flushPromises, mount } from '@vue/test-utils'
import { createPinia, setActivePinia } from 'pinia'
import ServerRecords from '@/views/ServerRecords.vue'
import { useTicketStore } from '@/stores/ticket'

// 默认不拦截，单测中分别 doMock

describe('ServerRecords', () => {
  beforeEach(() => {
    setActivePinia(createPinia())
  })

  it('renders table headers', async () => {
    vi.doMock('@/stores/api', () => ({
      useApi: () => ({
        get: vi.fn(async () => [
          { id: 1, name: '张三', idCard: '11010119900101001X', startCity: '北京', endCity: '上海', createdAt: '2025-11-27T12:00:00Z' }
        ]),
        loading: ref(false),
        error: ref(null)
      })
    }))
    const ServerRecordsLocal = (await import('@/views/ServerRecords.vue')).default
    const wrapper = mount(ServerRecordsLocal)
    expect(wrapper.text()).toContain('姓名')
    expect(wrapper.text()).toContain('身份证号码')
    expect(wrapper.text()).toContain('出发城市')
    expect(wrapper.text()).toContain('到达城市')
    expect(wrapper.text()).toContain('提交时间')
  })

  it('loads and displays records', async () => {
    vi.doMock('@/stores/api', () => ({
      useApi: () => ({
        get: vi.fn(async () => [
          { id: 1, name: '张三', idCard: '11010119900101001X', startCity: '北京', endCity: '上海', createdAt: '2025-11-27T12:00:00Z' }
        ]),
        loading: ref(false),
        error: ref(null)
      })
    }))
    const ServerRecordsLocal = (await import('@/views/ServerRecords.vue')).default
    const wrapper = mount(ServerRecordsLocal)
    await flushPromises()
    expect(wrapper.text()).toContain('张三')
    expect(wrapper.text()).toContain('北京')
    expect(wrapper.text()).toContain('上海')
  })

  it('shows empty message when no data', async () => {
    vi.doMock('@/stores/api', () => ({
      useApi: () => ({ get: vi.fn(async () => []), loading: ref(false), error: ref(null) })
    }))
    const ServerRecordsLocal = (await import('@/views/ServerRecords.vue')).default
    const wrapper = mount(ServerRecordsLocal)
    await flushPromises()
    expect(wrapper.text()).toContain('暂无记录')
  })
})
