package com.chun.myspringboot.service;

import java.io.IOException;

/**
 * Gemini API服务接口
 * 用于调用Gemini API，提供六级英语学习相关功能
 */
public interface GeminiService {
    
    /**
     * 生成六级词汇的详细解释
     * @param word 要解释的单词
     * @return 包含词义、搭配、例句等的详细解释
     * @throws IOException HTTP请求异常
     */
    String generateVocabExplanation(String word) throws IOException;
    
    /**
     * 解析六级长难句
     * @param sentence 要解析的长难句
     * @return 包含语法结构、成分划分、翻译等的详细解析
     * @throws IOException HTTP请求异常
     */
    String parseComplexSentence(String sentence) throws IOException;
    
    /**
     * 批改六级作文
     * @param essay 作文内容
     * @return 包含评分、修改建议、亮点分析等的批改结果
     * @throws IOException HTTP请求异常
     */
    String correctEssay(String essay) throws IOException;
    
    /**
     * 生成六级翻译练习
     * @param chineseText 中文文本
     * @return 英文翻译和相关知识点
     * @throws IOException HTTP请求异常
     */
    String generateTranslationPractice(String chineseText) throws IOException;
}