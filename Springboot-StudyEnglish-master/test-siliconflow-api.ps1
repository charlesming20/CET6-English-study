# 测试魔塔社区 API 调用
$apiKey = "ms-04751b8a-631d-4875-9744-41c8213892df"
$endpoint = "https://api.siliconflow.cn/v1/chat/completions"

$headers = @{
    "Authorization" = "Bearer $apiKey"
    "Content-Type" = "application/json"
}

$body = @{
    model = "deepseek-ai/DeepSeek-V3"
    messages = @(
        @{
            role = "user"
            content = "你好，请简短地介绍一下你自己"
        }
    )
    temperature = 0.7
    max_tokens = 100
} | ConvertTo-Json -Depth 5

Write-Host "正在调用魔塔社区 API..." -ForegroundColor Yellow
Write-Host "Endpoint: $endpoint" -ForegroundColor Cyan
Write-Host "Model: deepseek-ai/DeepSeek-V3" -ForegroundColor Cyan
Write-Host ""

try {
    $response = Invoke-RestMethod -Uri $endpoint -Method POST -Headers $headers -Body $body -ErrorAction Stop
    
    Write-Host "✅ API 调用成功！" -ForegroundColor Green
    Write-Host ""
    Write-Host "完整响应:" -ForegroundColor Yellow
    $response | ConvertTo-Json -Depth 10
    Write-Host ""
    
    if ($response.choices -and $response.choices.Count -gt 0) {
        $content = $response.choices[0].message.content
        Write-Host "AI 回复:" -ForegroundColor Green
        Write-Host $content
    } else {
        Write-Host "⚠ 响应格式异常，未找到 choices" -ForegroundColor Red
    }
} catch {
    Write-Host "❌ API 调用失败！" -ForegroundColor Red
    Write-Host "错误信息: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host ""
    
    if ($_.Exception.Response) {
        $reader = New-Object System.IO.StreamReader($_.Exception.Response.GetResponseStream())
        $errorBody = $reader.ReadToEnd()
        Write-Host "错误详情:" -ForegroundColor Yellow
        Write-Host $errorBody
    }
}
