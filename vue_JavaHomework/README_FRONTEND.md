# Vue 3 前端系统开发说明

## 项目结构
```
vue_JavaHomework/
├── public/
├── src/
│   ├── components/       # 通用组件（可选扩展）
│   ├── router/           # 路由配置
│   ├── stores/           # Pinia 状态管理
│   ├── views/            # 页面级组件
│   ├── App.vue           # 根组件
│   ├── main.js           # 入口文件
│   └── style.css         # Tailwind 引入
├── package.json
├── tailwind.config.js
├── postcss.config.js
└── vite.config.js
```

## 核心实现
1. **客户端前端** (`/src/views/ClientForm.vue`)
   - 提供姓名、身份证号、出发城市、到达城市输入
   - 前端表单验证（非空 + 身份证正则）
   - 提交按钮与加载/错误/成功状态
   - 使用 `useApi` 组合式函数统一处理请求

2. **服务端前端** (`/src/views/ServerRecords.vue`)
   - 表格展示所有售票记录
   - 顶部刷新按钮，支持重新拉取
   - 使用 `useTicketStore` Pinia Store 管理数据

3. **状态与请求**
   - `stores/api.js`：封装 axios，提供 `post`/`get` 及 `loading`/`error` 状态
   - `stores/ticket.js`：Pinia Store，负责拉取记录列表
   - 环境变量 `VITE_API_BASE_URL` 配置后端地址（默认 `http://localhost:8080`）

4. **路由与导航**
   - `/` → 客户端表单页面
   - `/server` → 服务端记录管理页面
   - 顶部导航栏使用 `<RouterLink>`，响应式布局

5. **UI & 样式**
   - Tailwind CSS 提供原子化样式
   - 表单与表格均适配移动端（`max-w-*` + `overflow-x-auto`）
   - 按钮禁用态、错误提示、加载中状态完整

## 使用说明
```bash
cd vue_JavaHomework
npm install        # 安装依赖
npm run dev        # 本地开发
npm run build      # 生产构建
npm run preview    # 本地预览构建产物
```

## 后端对接
- 客户端 POST `/api/tickets`  { name, idCard, startCity, endCity }
- 服务端 GET  `/api/tickets`  返回数组（含 id, createdAt 等字段）
- 统一错误格式：`{ message: string }`

## 代码规范
- 使用 Vue 3 `<script setup>` + Composition API
- 组件名 PascalCase，文件名与组件名一致
- 组合式函数以 `use*` 命名（如 `useApi`）
- props / emits 显式声明（本示例暂无 props）
- 组件注释采用 `<!-- 说明 -->` 或 `// 行内注释`