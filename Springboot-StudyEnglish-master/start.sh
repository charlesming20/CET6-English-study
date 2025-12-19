#!/bin/bash
# 针对 Linux/Mac 用户的启动脚本

cd "$(dirname "$0")"

echo "================================"
echo "Spring Boot AI学习助手 启动脚本"
echo "================================"
echo ""

# 检查 Java
if ! command -v java &> /dev/null; then
    echo "❌ 未找到 Java！请先安装 JDK 8+"
    exit 1
fi

# 编译
echo "[1/3] 编译项目..."
mvn compile -q
if [ $? -ne 0 ]; then
    echo "❌ 编译失败！"
    exit 1
fi
echo "✅ 编译成功"

echo ""
echo "[2/3] 启动 Spring Boot..."
echo ""
echo "访问地址: http://localhost:8080/aigc-assistant"
echo ""
echo "按 Ctrl+C 停止服务"
echo ""

# 启动应用
mvn exec:java -Dexec.mainClass=com.chun.myspringboot.MyspringbootApplication
