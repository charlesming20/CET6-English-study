package com.chun.myspringboot.controller;

import com.chun.myspringboot.service.AigcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Gemini AI学习助手控制器
 */
@RestController
@RequestMapping("/api/aigc")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AigcController {

    @Autowired
    private AigcService aigcService;

    /**
     * 通用对话接口
     *
     * @param request 包含 "message" 的请求体
     * @return 包含 "response" 的响应
     */
    @PostMapping("/chat")
    public ResponseEntity<Map<String, Object>> chat(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String message = request.getOrDefault("message", "");
            if (message.trim().isEmpty()) {
                response.put("success", false);
                response.put("error", "消息不能为空");
                return ResponseEntity.badRequest().body(response);
            }

            String reply = aigcService.generateContent(message);
            response.put("success", true);
            response.put("response", reply);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace(); // 打印完整堆栈到控制台
            response.put("success", false);
            response.put("error", "AI服务调用失败: " + e.getMessage());
            response.put("details", e.getClass().getSimpleName());
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * 单词解释接口
     *
     * @param request 包含 "word" 的请求体
     * @return 包含 "explanation" 的响应
     */
    @PostMapping("/explain-word")
    public ResponseEntity<Map<String, Object>> explainWord(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String word = request.getOrDefault("word", "");
            if (word.trim().isEmpty()) {
                response.put("success", false);
                response.put("error", "单词不能为空");
                return ResponseEntity.badRequest().body(response);
            }

            String explanation = aigcService.explainWord(word);
            response.put("success", true);
            response.put("word", word);
            response.put("explanation", explanation);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "解释失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * 生成学习内容接口
     *
     * @param request 包含 "topic" 的请求体
     * @return 包含 "content" 的响应
     */
    @PostMapping("/generate-learning")
    public ResponseEntity<Map<String, Object>> generateLearning(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String topic = request.getOrDefault("topic", "");
            if (topic.trim().isEmpty()) {
                response.put("success", false);
                response.put("error", "主题不能为空");
                return ResponseEntity.badRequest().body(response);
            }

            String content = aigcService.generateLearningContent(topic);
            response.put("success", true);
            response.put("topic", topic);
            response.put("content", content);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "生成失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * 健康检查接口
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "ok");
        response.put("service", "Gemini AI Assistant");
        return ResponseEntity.ok(response);
    }
}
