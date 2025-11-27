import { describe, it, expect, vi } from 'vitest'
import { ref } from 'vue'
import { flushPromises, mount } from '@vue/test-utils'
import ClientForm from '@/views/ClientForm.vue'

// 默认不拦截，单测中分别 doMock

describe('ClientForm', () => {
  it('renders all fields', () => {
    const wrapper = mount(ClientForm)
    expect(wrapper.find('input[placeholder="请输入姓名"]').exists()).toBe(true)
    expect(wrapper.find('input[placeholder="18位身份证号码"]').exists()).toBe(true)
    expect(wrapper.find('input[placeholder="如：北京"]').exists()).toBe(true)
    expect(wrapper.find('input[placeholder="如：上海"]').exists()).toBe(true)
  })

  it('shows validation errors when submit empty', async () => {
    const wrapper = mount(ClientForm)
    await wrapper.find('form').trigger('submit.prevent')
    await flushPromises()
    expect(wrapper.text()).toContain('请输入姓名')
    expect(wrapper.text()).toContain('身份证号码格式错误')
    expect(wrapper.text()).toContain('请输入出发城市')
    expect(wrapper.text()).toContain('请输入到达城市')
  })

  it('calls api post with valid data', async () => {
    const mockPost = vi.fn().mockResolvedValue({})
    vi.doMock('@/stores/api', () => ({
      useApi: () => ({ post: mockPost, loading: ref(false), error: ref(null) })
    }))
    const ClientFormLocal = (await import('@/views/ClientForm.vue')).default
    const wrapper = mount(ClientFormLocal)
    await wrapper.find('input[placeholder="请输入姓名"]').setValue('张三')
    await wrapper.find('input[placeholder="18位身份证号码"]').setValue('11010119900101001X')
    await wrapper.find('input[placeholder="如：北京"]').setValue('北京')
    await wrapper.find('input[placeholder="如：上海"]').setValue('上海')

    await wrapper.find('form').trigger('submit.prevent')
    await flushPromises()

    expect(mockPost).toHaveBeenCalledWith('/api/tickets', {
      name: '张三',
      idCard: '11010119900101001X',
      startCity: '北京',
      endCity: '上海'
    })
    expect(wrapper.text()).toContain('提交成功')
  })
})
