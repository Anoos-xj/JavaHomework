# 部署说明

## 1. 环境要求
- Node.js ≥ 20
- 后端服务已启动并暴露接口：
  - POST `/api/tickets` （提交记录）
  - GET  `/api/tickets` （获取记录列表）

## 2. 安装依赖
```bash
cd vue_JavaHomework
npm install
```

## 3. 本地开发
```bash
npm run dev
```
默认访问 http://localhost:5173

## 4. 构建生产包
```bash
npm run build
```
输出目录：`dist/`

## 5. 预览构建结果
```bash
npm run preview
```

## 6. 部署到静态服务器
将 `dist/` 目录内容上传至任意静态文件服务器（nginx、Apache、Vercel、Netlify 等）。

示例 nginx 配置：
```nginx
server {
  listen 80;
  server_name your-domain.com;
  root /path/to/dist;
  index index.html;
  location / {
    try_files $uri $uri/ /index.html;
  }
  location /api/ {
    proxy_pass http://localhost:8080/; # 后端地址
  }
}
```

## 7. 环境变量
如需自定义后端地址，可在构建前创建 `.env` 文件：
```
VITE_API_BASE_URL=https://your-api.com
```

## 8. 运行测试
```bash
npm run test
```

## 9. 代码检查与格式化
```bash
npm run lint
npm run format
```