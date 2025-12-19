package com.chun.myspringboot.service.Impl;

import com.chun.myspringboot.service.GeminiService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Gemini API服务实现类
 * 用于调用Gemini API，提供六级英语学习相关功能
 */
@Service
public class GeminiServiceImpl implements GeminiService {
    
    @Value("${ai.gemini.api-key}")
    private String apiKey;
    
    @Value("${ai.gemini.endpoint}")
    private String endpoint;
    
    // 使用Jackson库解析JSON响应
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    // 日志记录器
    private final Logger logger = LoggerFactory.getLogger(GeminiServiceImpl.class);
    
    @Override
    public String generateVocabExplanation(String word) throws IOException {
        String prompt = "请详细解释以下英语单词，包括音标、词性、常见释义、搭配用法和例句，适合英语六级学习者：" + word;
        return callGeminiApi(prompt);
    }
    
    @Override
    public String parseComplexSentence(String sentence) throws IOException {
        String prompt = "请分析以下复杂英语句子的结构、语法要点和中文含义，适合英语六级学习者：" + sentence;
        return callGeminiApi(prompt);
    }
    
    @Override
    public String correctEssay(String essay) throws IOException {
        String prompt = "请批改以下英语作文，指出语法错误、表达问题和改进建议，适合英语六级学习者：" + essay;
        return callGeminiApi(prompt);
    }
    
    @Override
    public String generateTranslationPractice(String chineseText) throws IOException {
        String prompt = "请将以下中文句子翻译成地道的英语，适合英语六级学习者：" + chineseText;
        return callGeminiApi(prompt);
    }
    
    /**
     * 调用Gemini API的通用方法，使用JDK 8自带的HttpURLConnection
     * 现已改为 OpenAI 兼容格式，支持硅基流动等服务
     * @param prompt 提示词
     * @return 转换为前端期望格式的API响应内容
     * @throws IOException 如果API调用失败
     */
    private String callGeminiApi(String prompt) throws IOException {
        // 构建 OpenAI 兼容格式的请求体（支持硅基流动）
        String requestBody = "{\"model\": \"deepseek-ai/DeepSeek-V3\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt.replace("\"", "\\\"") + "\"}], \"max_tokens\": 2000}";
        
        // 创建URL对象
        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        
        // 设置请求方法
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        
        // 设置请求头
        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        connection.setRequestProperty("Authorization", "Bearer " + apiKey);
        connection.setRequestProperty("Content-Length", Integer.toString(requestBody.getBytes(StandardCharsets.UTF_8).length));
        
        // 发送请求
        try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
            outputStream.write(requestBody.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        }
        
        // 获取响应
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder responseBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBuilder.append(line);
                }
                String geminiResponse = responseBuilder.toString();
                logger.info("Gemini API Response: {}", geminiResponse);
                
                // 将Gemini API响应转换为前端期望的格式
                return convertGeminiResponseToFrontendFormat(geminiResponse);
            }
        } else {
            // 读取错误响应
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
                StringBuilder errorResponse = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    errorResponse.append(line);
                }
                String errorMessage = "Gemini API call failed: " + responseCode + " " + connection.getResponseMessage() + ". Error: " + errorResponse.toString();
                logger.error(errorMessage);
                throw new IOException(errorMessage);
            }
        }
    }
    
    /**
     * 将 API 响应转换为前端期望的格式
     * OpenAI 兼容格式响应：{"choices": [{"message": {"content": "生成的内容"}}]}
     * 前端期望格式：{"choices": [{"message": {"content": "生成的内容"}}]}
     * @param geminiResponse API的原始响应
     * @return 转换为前端期望格式的响应
     * @throws IOException 如果JSON解析失败
     */
    private String convertGeminiResponseToFrontendFormat(String geminiResponse) throws IOException {
        // 解析 API 响应（OpenAI 兼容格式）
        JsonNode rootNode = objectMapper.readTree(geminiResponse);
        
        // 提取生成的内容
        String generatedContent = "";
        try {
            JsonNode choices = rootNode.path("choices");
            if (choices.isArray() && choices.size() > 0) {
                JsonNode firstChoice = choices.get(0);
                JsonNode message = firstChoice.path("message");
                generatedContent = message.path("content").asText("未生成有效内容");
            }
        } catch (Exception e) {
            generatedContent = "解析API响应失败：" + e.getMessage();
            logger.error("解析API响应失败：{}", e.getMessage());
        }
        
        // 构建前端期望的格式（与原响应格式相同）
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("id", "chat-" + System.currentTimeMillis());
        responseMap.put("object", "chat.completion");
        responseMap.put("created", System.currentTimeMillis() / 1000);
        responseMap.put("model", "deepseek-v3");
        
        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("role", "assistant");
        messageMap.put("content", generatedContent);
        
        Map<String, Object> choiceMap = new HashMap<>();
        choiceMap.put("index", 0);
        choiceMap.put("message", messageMap);
        choiceMap.put("finish_reason", "stop");
        
        Map<String, Integer> usageMap = new HashMap<>();
        usageMap.put("prompt_tokens", 10);
        usageMap.put("completion_tokens", 200);
        usageMap.put("total_tokens", 210);
        
        responseMap.put("choices", new Object[]{choiceMap});
        responseMap.put("usage", usageMap);
        
        // 转换为JSON字符串
        return objectMapper.writeValueAsString(responseMap);
    }
}