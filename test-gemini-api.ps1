# Gemini API Key Test Script (PowerShell Version)

# Test Configuration
$apiKey = "AIzaSyDmB13t3TBQgzN0IxJuOrudZeldBJM5SdA"
$apiEndpoint = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent"
$testPrompt = "Please briefly explain the word 'hello'"

# Build Request Body
$requestBody = "{`"contents`": [{`"parts`": [{`"text`": `"$testPrompt`"}]}]}"

# Display Test Info
Write-Host "Testing Gemini API Key..."
Write-Host "API Endpoint: $apiEndpoint"
Write-Host "API Key: $apiKey"
Write-Host "Request Body: $requestBody"
Write-Host ""

# Send Request
try {
    $response = Invoke-RestMethod -Uri $apiEndpoint -Method POST -Headers @{
        "Content-Type" = "application/json"
        "Authorization" = "Bearer $apiKey"
    } -Body $requestBody
    
    # Display Response
    Write-Host "✅ API Call Successful!"
    Write-Host "Response Status: 200 OK"
    Write-Host "Response Content: $($response | ConvertTo-Json -Depth 3)"
    Write-Host ""
    
    # Validate Response Format
    if ($response -ne $null) {
        Write-Host "✅ Test Passed! API Key is valid."
        exit 0
    } else {
        Write-Host "⚠️ Invalid Response Format"
        exit 1
    }
    
} catch {
    # Handle Errors
    $errorMsg = $_.Exception.Message
    Write-Host "❌ API Call Failed!"
    Write-Host "Error Message: $errorMsg"
    Write-Host ""
    
    # Check Status Code
    if ($_.Exception.Response -ne $null) {
        $statusCode = $_.Exception.Response.StatusCode.value__
        Write-Host "Status Code: $statusCode"
        
        # Error Analysis
        switch ($statusCode) {
            401 {
                Write-Host "🔑 Error: Invalid API Key"
                Write-Host "Solution: Check if the API key is correct and not expired"
            }
            403 {
                Write-Host "🚫 Error: Insufficient Permissions"
                Write-Host "Solution: Ensure the API key has proper permissions"
            }
            429 {
                Write-Host "⏱️ Error: Too Many Requests"
                Write-Host "Solution: Check API quota or try later"
            }
            500 {
                Write-Host "💥 Error: Server Error"
                Write-Host "Solution: Try again later or contact support"
            }
            default {
                Write-Host "❓ Error: Unknown Error"
                Write-Host "Solution: Check request parameters"
            }
        }
    }
    
    exit 1
}