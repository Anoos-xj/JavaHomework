# 前端系统测试报告

## 测试目的和范围
- 验证客户端表单输入、校验与提交功能完整性
- 验证服务端记录页数据获取与展示的正确性
- 验证与后端接口的交互：POST `/api/tickets` 与 GET `/api/tickets`
- 覆盖常见输入场景、边界值与异常网络情况

## 测试环境配置
- 操作系统：Windows（PowerShell）
- Node.js：>= 20
- 依赖安装：`npm install --cache .\node_cache --no-audit --no-fund`
- 前端开发服务器：`npm run dev`（http://localhost:5173/）
- 后端服务：Java HTTP Server（http://localhost:8080/）
- 环境变量：`VITE_API_BASE_URL=http://localhost:8080`

## 测试用例设计
- 方法：黑盒测试、边界值分析、等价类划分
- 等价类：
  - 合法身份证（18位，校验位可为数字或X）
  - 非法身份证（长度不符、日期不合法）
  - 合法/非法城市名（空/仅空格/正常汉字/包含特殊字符）
- 边界值：
  - 姓名最短/最长（1字符/64字符）
  - 城市名最短/最长（1字符/64字符）
  - 身份证固定18位
  - 提交空值
- 异常网络：后端未启动、接口超时、接口返回错误

## 测试步骤详细说明
1. 启动后端（HTTP）：
   - 终端执行：
     - 编译：`javac -cp .;lib\mysql-connector-j-8.3.0.jar test\test\HttpServerApp.java test\test\DatabaseHelper.java test\test\ServerFileHelper.java test\test\Ticket.java`
     - 运行：`java -cp .;lib\mysql-connector-j-8.3.0.jar test.HttpServerApp`
   - 预期：控制台打印 `HTTP Server started at http://localhost:8080`
2. 启动前端：`npm run dev`，访问 `http://localhost:5173/`
3. 客户端表单页（/）：
   - 输入合法数据并提交，观察成功提示
   - 输入空值/非法身份证，观察错误提示
4. 服务端记录页（/server）：
   - 点击刷新，检查表格是否展示数据库中的数据
   - 验证列对齐与字段完整性
5. 单元测试（Vitest）：
   - 运行：`npm run test`
   - 覆盖：表单渲染与校验、记录页渲染与刷新

## 预期结果与实际结果对比
- 表单合法提交：预期显示“提交成功”，后端返回 `{ ok: true }`；实际符合
- 表单非法提交：预期显示具体错误信息；实际符合
- 记录页刷新：预期显示数据库中最新记录，时间格式ISO/本地化；实际符合（后端启动前会出现“暂无记录”或网络错误）
- 单元测试：预期核心用例通过；实际在未Mock网络时会出现 `Network Error`，Mock后用例通过

## 问题记录和分析
- 问题：`Network Error`（前端提交/测试环境）
  - 原因：后端HTTP未启动或基础URL错误（默认指向 `http://localhost:8080`）；测试环境使用 jsdom 时真实网络不可用
  - 解决：
    - 启动 Java HTTP 服务（`HttpServerApp`）或将 Vite 代理到后端
    - 在 Vitest 中对 `useApi` 或 axios 进行 Mock，避免真实网络请求
- 问题：PowerShell 执行策略导致 `npm`/`npx` 无法运行
  - 解决：`Set-ExecutionPolicy -Scope CurrentUser -ExecutionPolicy RemoteSigned`
- 问题：`vite` 未找到
  - 解决：安装依赖并使用本地缓存：`npm install --cache .\node_cache --no-audit --no-fund`

## 测试结论和建议
- 结论：当前前端功能符合设计要求；与后端交互在后端HTTP服务就绪时工作正常
- 建议：
  - 在生产环境通过 Nginx 反向代理 `/api/*` 到后端，提高稳定性与安全性
  - 为前端添加请求超时与重试策略；对表单增加更细致的校验提示
  - 扩充测试用例，增加压力测试（并发提交）、回归测试（修改后确保现有功能不受影响）

## 可复用测试脚本和用例
- npm脚本：`npm run test`、`npm run dev`、`npm run build`
- Vitest用例：`src/views/__tests__/ClientForm.test.js`、`src/views/__tests__/ServerRecords.test.js`
- 后端脚本：`HttpServerApp` 的编译与运行命令（见“测试步骤”）

## 问题跟踪清单及解决方案
- [x] Vite未找到 → 安装依赖、修正缓存路径
- [x] PowerShell脚本限制 → 修改执行策略
- [x] Network Error（前端） → 启动后端HTTP服务或配置代理；测试中Mock网络
- [ ] 单元测试网络Mock稳定性 → 后续完善测试隔离（Mock axios 全局）
