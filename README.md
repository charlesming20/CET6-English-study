# Spring Boot 英语学习平台

一个基于Spring Boot开发的智能英语学习平台，集成了AI学习助手，为用户提供全方位的英语学习服务。

## 📚 项目介绍

该平台旨在为英语学习者提供一个综合性的学习环境，结合了传统学习资源与AI智能辅助功能。用户可以通过平台进行单词学习、听力练习、书籍阅读，同时利用AI学习助手获得智能辅导。

## 🛠️ 技术栈

| 技术 | 版本 | 用途 |
| ---- | ---- | ---- |
| Spring Boot | 2.2.6 | 后端框架 |
| MyBatis | 2.1.2 | ORM框架 |
| MySQL | - | 数据库 |
| Thymeleaf | - | 模板引擎 |
| OkHttp | 4.11.0 | HTTP客户端 |
| Gson | 2.10.1 | JSON处理 |
| Lombok | - | 代码简化 |
| Bootstrap | - | 前端框架 |
| AI模型 | DeepSeek-V3 | 智能学习助手 |

## 🎯 核心功能

### 1. AI学习助手

AI学习助手是平台的核心功能，提供6个智能学习模块：

- **💬 智能对话**：与AI进行英语学习相关的对话，解答任何英语问题
- **📚 词汇详解**：输入英文单词，获取详细解释、搭配、例句等
- **📝 长难句分析**：解析句子结构、成分划分、提供翻译
- **✍️ 作文批改**：提供评分、修改建议、亮点分析
- **🌐 翻译练习**：中文转英文翻译及知识点讲解
- **✨ 学习内容生成**：根据主题生成学习材料

### 2. 传统学习功能

- **单词学习**：支持单词收藏、学习、记忆、未记忆等功能
- **听力练习**：提供CET4/CET6听力材料
- **书籍阅读**：支持在线阅读英语书籍
- **通知系统**：发布学习通知和资讯

### 3. 管理员后台

- 用户管理
- 单词管理
- 书籍管理
- 通知管理

## 📁 项目结构

```
Springboot-StudyEnglish-master/
├── src/
│   ├── main/
│   │   ├── java/com/chun/myspringboot/
│   │   │   ├── config/          # 配置类
│   │   │   ├── controller/      # 控制器
│   │   │   │   └── admin/       # 管理员控制器
│   │   │   ├── mapper/          # Mapper接口
│   │   │   ├── pojo/            # 实体类
│   │   │   ├── service/         # 服务层
│   │   │   └── util/            # 工具类
│   │   └── resources/
│   │       ├── mybatis/mapper/  # MyBatis映射文件
│   │       ├── public/          # 静态资源
│   │       ├── static/          # 静态文件
│   │       ├── templates/       # Thymeleaf模板
│   │       └── application.yml  # 配置文件
│   └── test/                    # 测试代码
├── StudyEnglish.sql             # 数据库脚本
├── pom.xml                      # Maven依赖
├── start.bat                    # Windows启动脚本
└── start.sh                     # Linux启动脚本
```

## 🚀 快速开始

### 1. 环境准备

- JDK 1.8+
- Maven 3.6+
- MySQL 5.7+

### 2. 安装步骤

1. **下载并解压项目**
   - 下载项目压缩包
   - 解压到本地目录

2. **导入IDEA**
   - 打开IDEA，选择"Open"，找到解压后的项目目录
   - 等待Maven自动加载依赖

3. **导入数据库**
   - 打开SQLyog或其他数据库工具，登录MySQL
   - 打开项目根目录下的 `StudyEnglish.sql` 文件
   - 复制所有SQL语句并执行，该脚本会自动创建数据库和表结构

4. **配置数据库连接和AI API**
   - 在IDEA中打开 `src/main/resources/application.yml` 文件
   - 修改数据库连接信息为自己的MySQL配置：
     ```yaml
     spring:
       datasource:
         url: jdbc:mysql://localhost:3306/studywords?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC
         username: 你的MySQL用户名
         password: 你的MySQL密码
     ```
   - AI API配置（可选，默认已配置）：
     ```yaml
     # AI API配置
     ai:
       # 使用魔塔社区 DeepSeek 模型
       provider: siliconflow
       
       # 魔塔社区 API 配置
       siliconflow:
         api-key: 你的魔塔社区API密钥
         endpoint: https://api.siliconflow.cn/v1/chat/completions
         model: deepseek-ai/DeepSeek-V3
       
       # Gemini API 配置（兼容原有功能）
       gemini:
         api-key: 你的魔塔社区API密钥
         endpoint: https://api.siliconflow.cn/v1/chat/completions
     ```

5. **启动项目**
   - 在IDEA中找到 `MyspringbootApplication.java` 主类
   - 右键点击，选择"Run MyspringbootApplication"
   - 或使用脚本启动：
     - Windows：双击 `start.bat` 脚本
     - Linux：执行 `chmod +x start.sh && ./start.sh`

6. **访问项目**
   打开浏览器，访问以下地址：
   ```
   # 主页面
   http://localhost:8080
   
   # AI学习助手页面
   http://localhost:8080/aigc-assistant
   ```

## 📖 使用指南

### AI学习助手使用

1. **访问AI学习助手**：登录平台后，点击"AI学习"或直接访问 `http://localhost:8080/aigc-assistant`

2. **选择功能模块**：
   - 智能对话：输入任何英语相关问题
   - 词汇详解：输入英文单词获取详细解释
   - 长难句分析：输入复杂句子进行解析
   - 作文批改：粘贴英文作文获得批改建议
   - 翻译练习：输入中文文本获得英文翻译
   - 学习内容生成：输入主题生成学习材料

3. **获取AI响应**：点击对应按钮，等待AI生成响应结果

### 传统学习功能使用

1. **单词学习**：
   - 选择"单词学习"模块
   - 浏览单词列表，点击"学习"进行学习
   - 可以收藏单词到个人收藏夹

2. **听力练习**：
   - 选择"听力练习"模块
   - 选择CET4/CET6听力材料
   - 播放音频进行练习

3. **书籍阅读**：
   - 选择"书籍阅读"模块
   - 浏览书籍列表，点击"阅读"进行在线阅读

## 🔧 开发说明

### 项目编译

```bash
cd Springboot-StudyEnglish-master
mvn clean compile
```

### 项目打包

```bash
mvn clean package -DskipTests
```

### 运行测试

```bash
mvn test
```

## 📝 功能扩展

### 添加新的AI功能模块

1. 在 `AiStudyController` 中添加新的请求映射
2. 在 `service` 层实现新功能的业务逻辑
3. 在 `aigc-assistant.html` 中添加新的标签页和表单
4. 在JavaScript中添加新的请求处理函数

### 添加新的学习资源

1. 在数据库中添加相应的数据
2. 在 `mapper` 层添加新的查询方法
3. 在 `service` 层实现业务逻辑
4. 在控制器中添加新的请求映射
5. 在前端模板中添加展示页面

## 🤝 贡献指南

欢迎大家对项目进行贡献，贡献方式包括：

- 提交Issue报告bug或提出新功能建议
- 提交Pull Request修复bug或实现新功能
- 改进文档

## 📄 许可证

本项目采用MIT许可证，详情请查看LICENSE文件。

## 🎉 感谢

感谢所有为项目做出贡献的开发者和用户！

---

**项目口号**："让AI助力你的英语学习之旅！"
