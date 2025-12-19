package com.chun.myspringboot.service;

import com.google.gson.*;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 魔塔社区 DeepSeek AIGC服务 - OpenAI 兼容接口
 * 与JDK8兼容
 */
@Service
public class AigcService {

    @Value("${ai.siliconflow.api-key}")
    private String apiKey;

    @Value("${ai.siliconflow.endpoint}")
    private String endpoint;

    @Value("${ai.siliconflow.model:deepseek-ai/DeepSeek-V3}")
    private String model;

    private final Gson gson = new Gson();

    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();

    /**
     * 调用魔塔社区 DeepSeek API 生成文本回复
     *
     * @param userMessage 用户输入的消息
     * @return DeepSeek 的回复
     */
    public String generateContent(String userMessage) throws Exception {
        if (!isValidInput(userMessage)) {
            throw new IllegalArgumentException("输入内容不合法：为空或过长");
        }

        // 构建 OpenAI 兼容请求体
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("model", model);
        
        JsonArray messages = new JsonArray();
        JsonObject userMsg = new JsonObject();
        userMsg.addProperty("role", "user");
        userMsg.addProperty("content", userMessage);
        messages.add(userMsg);
        
        requestBody.add("messages", messages);
        requestBody.addProperty("temperature", 0.7);
        requestBody.addProperty("max_tokens", 2000);

        // 构建 HTTP 请求
        RequestBody body = RequestBody.create(
                requestBody.toString(),
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url(endpoint)
                .post(body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String errorBody = response.body() != null ? response.body().string() : "无响应体";
                throw new IOException("DeepSeek API 请求失败，状态码: " + response.code() + 
                        ", 响应: " + errorBody);
            }

            String responseBody = response.body().string();
            JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();

            // 提取生成的文本（OpenAI 兼容格式）
            if (jsonResponse.has("choices")) {
                JsonArray choices = jsonResponse.getAsJsonArray("choices");
                if (choices.size() > 0) {
                    JsonObject choice = choices.get(0).getAsJsonObject();
                    if (choice.has("message")) {
                        JsonObject message = choice.getAsJsonObject("message");
                        if (message.has("content")) {
                            return message.get("content").getAsString();
                        }
                    }
                }
            }

            throw new IOException("无法解析 DeepSeek API 响应: " + responseBody);
        }
    }

    /**
     * 用于学习场景的 API 调用 - 解释单词
     *
     * @param word 要解释的单词
     * @return 词义解释
     */
    public String explainWord(String word) throws Exception {
        String prompt = String.format("请用中文简明扼要地解释英文单词 '%s' 的含义，不超过50字。", word);
        return generateContent(prompt);
    }

    /**
     * 生成学习内容
     *
     * @param topic 学习主题
     * @return 生成的学习内容
     */
    public String generateLearningContent(String topic) throws Exception {
        String prompt = String.format("请为学习英语的学生生成关于 '%s' 的学习材料，包括定义、例句和使用建议，不超过200字。", topic);
        return generateContent(prompt);
    }

    /**
     * 检查用户输入是否合法（防止注入）
     *
     * @param input 用户输入
     * @return 是否合法
     */
    private boolean isValidInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }
        // 防止过长输入
        if (input.length() > 2000) {
            return false;
        }
        return true;
    }
}

