# 火车售票记录与查看系统使用说明

## 准备
- 安装并启动 MySQL，确保本地端口为 `3306`。
- 将 `lib/mysql-connector-j-8.3.0.jar` 加入编译与运行的类路径。
- 配置 `db.properties`：
  - `url=jdbc:mysql://localhost:3306/`
  - `database=train_tickets`
  - `user=你的用户名`
  - `password=你的密码`
- 初始化数据库：在 MySQL 客户端执行 `sql/create_tickets.sql`。

## 编译
```powershell
javac -cp .;lib/mysql-connector-j-8.3.0.jar test\test\*.java
```

## 启动服务器
```powershell
java -cp .;lib/mysql-connector-j-8.3.0.jar test.MultiThreadServer
```

## 准备客户端文件
- 在项目根目录创建以下文件，内容为逗号分隔：`姓名,身份证号码,出发城市,到达城市`
  - `client1_tickets.txt`
  - `client2_tickets.txt`
  - `client3_tickets.txt`
  - `client4_tickets.txt`

示例：
```
张三,110101199001010011,北京,上海
李四,320101199501012222,南京,杭州
```

## 并发演示（4个客户端）
在不同终端或后台并行启动：
```powershell
java -cp .;lib/mysql-connector-j-8.3.0.jar test.FileClient1
java -cp .;lib/mysql-connector-j-8.3.0.jar test.FileClient2
java -cp .;lib/mysql-connector-j-8.3.0.jar test.FileClient3
java -cp .;lib/mysql-connector-j-8.3.0.jar test.FileClient4
```

每条记录将作为独立短连接发送到服务器，服务器追加写入 `server_all_records.txt` 并同步写入数据库 `tickets`。

## 显示数据
- 文件数据显示：
```powershell
java -cp .;lib/mysql-connector-j-8.3.0.jar test.DisplayServerFile
```
- 数据库数据显示：
```powershell
java -cp .;lib/mysql-connector-j-8.3.0.jar test.DisplayDatabase
```

## 常见问题
- 数据库连接失败：检查 `db.properties` 的 `user/password` 与 MySQL 服务状态；可先运行 `DBTest` 验证连接。
- 端口占用：如 `8888` 被占用，请关闭占用进程或修改服务器端口并同步更新客户端。
