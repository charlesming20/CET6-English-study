package com.chun.myspringboot.controller;

import com.chun.myspringboot.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * 六级英语学习控制器
 * 提供六级英语学习相关的HTTP接口，只支持调用Gemini API
 */
@Controller
@RequestMapping("/cet6")
public class Cet6Controller {
    
    @Autowired
    private GeminiService geminiService;
    
    /**
     * 生成六级词汇详细解释
     * @param word 要解释的单词
     * @return 包含词义、搭配、例句等的详细解释
     */
    @PostMapping("/vocab/explanation")
    @ResponseBody
    public String getVocabExplanation(@RequestParam String word) {
        try {
            return geminiService.generateVocabExplanation(word);
        } catch (IOException e) {
            return "{\"error\":\"词汇解释生成失败：" + e.getMessage() + "}";
        }
    }
    
    /**
     * 解析六级长难句
     * @param sentence 要解析的长难句
     * @return 包含语法结构、成分划分、翻译等的详细解析
     */
    @PostMapping("/sentence/parse")
    @ResponseBody
    public String parseComplexSentence(@RequestParam String sentence) {
        try {
            return geminiService.parseComplexSentence(sentence);
        } catch (IOException e) {
            return "{\"error\":\"长难句解析失败：" + e.getMessage() + "}";
        }
    }
    
    /**
     * 批改六级作文
     * @param essay 作文内容
     * @return 包含评分、修改建议、亮点分析等的批改结果
     */
    @PostMapping("/essay/correct")
    @ResponseBody
    public String correctEssay(@RequestParam String essay) {
        try {
            return geminiService.correctEssay(essay);
        } catch (IOException e) {
            return "{\"error\":\"作文批改失败：" + e.getMessage() + "}";
        }
    }
    
    /**
     * 生成六级翻译练习
     * @param chineseText 中文文本
     * @return 英文翻译和相关知识点
     */
    @PostMapping("/translation/generate")
    @ResponseBody
    public String generateTranslationPractice(@RequestParam String chineseText) {
        try {
            return geminiService.generateTranslationPractice(chineseText);
        } catch (IOException e) {
            return "{\"error\":\"翻译练习生成失败：" + e.getMessage() + "}";
        }
    }
}