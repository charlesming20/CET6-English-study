@echo off
REM Spring Boot 启动脚本
REM 用法: 直接双击运行，或在命令行执行 start.bat

setlocal enabledelayedexpansion

echo ================================
echo Spring Boot AI学习助手 启动脚本
echo ================================

REM 切换到项目目录
cd /d "%~dp0"

echo.
echo [1/3] 编译项目...
call mvn compile -q
if errorlevel 1 (
    echo ❌ 编译失败！
    pause
    exit /b 1
)
echo ✅ 编译成功

echo.
echo [2/3] 启动 Spring Boot...
echo.
echo 访问地址: http://localhost:8080/aigc-assistant
echo.
echo 按 Ctrl+C 停止服务
echo.

REM 使用 Maven exec 插件启动（推荐）
mvn org.codehaus.mojo:exec-maven-plugin:3.0.0:java -Dexec.mainClass="com.chun.myspringboot.MyspringbootApplication"

pause
