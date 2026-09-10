# Nova Platform Cloud

Nova 管理平台的后端项目，基于 Spring Boot 和 Spring Cloud 构建，提供登录认证、用户、角色、菜单权限、组织及任务记录管理等功能。配套前端为 `nova-platform-web`。

## 技术栈

- Java 17、Maven
- Spring Boot 3、Spring Cloud、Spring Cloud Alibaba
- Nacos：服务注册与配置管理
- PostgreSQL、MyBatis-Flex：数据存储与访问
- Redis、Sa-Token：缓存与登录认证

## 项目结构

```text
nova-platform-cloud/
├── nova-auth       # 登录认证与权限资源
├── nova-system     # 系统管理业务
├── nova-common     # 公共工具、数据模型与数据库配置
├── nova-gateway    # 网关模块（预留）
├── nova-ai         # AI 模块（预留）
└── docs            # 数据库脚本、Nacos 配置及接口示例
```

## 启动

1. 准备 Java 17、Maven、PostgreSQL、Redis 和 Nacos。
2. 参考 [docs/init](docs/init) 初始化数据库，并导入、调整 Nacos 配置。建表脚本引用的序列需提前创建，管理员账号与角色需自行初始化。
3. 在项目根目录构建：

默认开发环境的 Nacos 地址为 `127.0.0.1:8888`，命名空间 ID 为 `dev`。使用随附 Nacos 配置时，认证服务地址为 `http://localhost:8867/auth`，系统服务地址为 `http://localhost:8866/system`；实际地址以运行配置为准。

数据库连接、Redis、密钥等配置需替换为自己的环境值。接口调试示例见 [docs/test](docs/test)，登录密码需按当前实现加密后提交。
