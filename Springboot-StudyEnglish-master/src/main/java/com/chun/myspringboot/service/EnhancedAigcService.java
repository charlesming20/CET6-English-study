package com.chun.myspringboot.service;

import com.google.gson.*;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 增强的AIGC服务 - 与现有学习功能集成
 * 用于为单词、书籍、例句等添加AI驱动的解释和扩展内容
 */
@Service
public class EnhancedAigcService {

    @Autowired
    private AigcService aigcService;

    @Value("${ai.gemini.api-key}")
    private String apiKey;

    @Value("${ai.gemini.endpoint}")
    private String endpoint;

    private final Gson gson = new Gson();

    /**
     * 为单词生成多语言定义和例句
     * 
     * @param word 英文单词
     * @param level 学习难度（beginner/intermediate/advanced）
     * @return 包含多种解释和例句的JSON
     */
    public String generateComprehensiveExplanation(String word, String level) throws Exception {
        String prompt = String.format(
            "为英文单词 '%s' 生成详细的学习资料（难度级别：%s），内容应包括：\n" +
            "1. 中英文定义\n" +
            "2. 词性和词根\n" +
            "3. 2-3个英文例句\n" +
            "4. 同义词\n" +
            "5. 反义词\n" +
            "6. 实用用法建议\n" +
            "请用JSON格式返回，包括 definition, examples, synonyms, antonyms, tips 字段。",
            word, level
        );
        return aigcService.generateContent(prompt);
    }

    /**
     * 生成听力练习脚本
     * 
     * @param topic 主题
     * @param difficulty 难度
     * @return 听力脚本
     */
    public String generateListeningScript(String topic, String difficulty) throws Exception {
        String prompt = String.format(
            "为英文学习者生成一份听力练习脚本（主题：%s，难度：%s），要求：\n" +
            "1. 100-150个单词\n" +
            "2. 使用简洁清晰的英文\n" +
            "3. 包含常见词汇\n" +
            "4. 配上中文翻译\n",
            topic, difficulty
        );
        return aigcService.generateContent(prompt);
    }

    /**
     * 生成阅读理解材料
     * 
     * @param topic 阅读主题
     * @param difficulty 难度等级
     * @return 阅读材料
     */
    public String generateReadingMaterial(String topic, String difficulty) throws Exception {
        String prompt = String.format(
            "为英文学习者创建一份阅读理解材料（主题：%s，难度：%s）\n" +
            "要求：\n" +
            "1. 200-300个单词的短文\n" +
            "2. 有趣且教育性\n" +
            "3. 包含3-5个理解问题\n" +
            "4. 标注关键词汇\n",
            topic, difficulty
        );
        return aigcService.generateContent(prompt);
    }

    /**
     * 生成写作提示和框架
     * 
     * @param essayType 作文类型（email/essay/story等）
     * @param topic 作文主题
     * @return 写作指南和框架
     */
    public String generateWritingGuide(String essayType, String topic) throws Exception {
        String prompt = String.format(
            "为英文学习者提供%s的写作指导（主题：%s）\n" +
            "包括：\n" +
            "1. 结构框架\n" +
            "2. 常用短语和表达\n" +
            "3. 示例段落\n" +
            "4. 避免常见错误\n" +
            "5. 检查清单\n",
            essayType, topic
        );
        return aigcService.generateContent(prompt);
    }

    /**
     * 批改文本并提供改进建议
     * 
     * @param originalText 原始文本
     * @param focusArea 关注领域（grammar/vocabulary/style等）
     * @return 批改意见和改进建议
     */
    public String provideWritingFeedback(String originalText, String focusArea) throws Exception {
        String prompt = String.format(
            "请批改以下英文文本，重点关注%s问题：\n\n%s\n\n" +
            "请提供：\n" +
            "1. 具体的错误指正\n" +
            "2. 改进的版本\n" +
            "3. 语法和词汇解释\n" +
            "4. 整体反馈\n",
            focusArea, originalText
        );
        return aigcService.generateContent(prompt);
    }

    /**
     * 生成发音指导（针对易发音错误的单词）
     * 
     * @param word 单词
     * @return 发音指导
     */
    public String providePronunciationGuide(String word) throws Exception {
        String prompt = String.format(
            "为英文单词 '%s' 提供发音指导。包括：\n" +
            "1. 国际音标（IPA）\n" +
            "2. 中文发音指导\n" +
            "3. 音节划分\n" +
            "4. 容易犯的错误\n" +
            "5. 练习建议\n",
            word
        );
        return aigcService.generateContent(prompt);
    }

    /**
     * 生成语法讲解
     * 
     * @param grammarTopic 语法主题（如 Present Perfect, Conditional等）
     * @return 详细的语法讲解
     */
    public String explainGrammar(String grammarTopic) throws Exception {
        String prompt = String.format(
            "为英文学习者详细讲解 '%s'，包括：\n" +
            "1. 定义和概念\n" +
            "2. 形成规则\n" +
            "3. 5个实际例句\n" +
            "4. 与类似语法的对比\n" +
            "5. 常见错误\n" +
            "6. 练习题（3-5题）\n",
            grammarTopic
        );
        return aigcService.generateContent(prompt);
    }

    /**
     * 生成口语对话练习
     * 
     * @param scenario 场景（restaurant/hotel/airport等）
     * @param level 难度
     * @return 对话脚本
     */
    public String generateConversationPractice(String scenario, String level) throws Exception {
        String prompt = String.format(
            "生成一份英文口语对话练习（场景：%s，难度：%s）\n" +
            "格式：\n" +
            "Speaker A: ...\n" +
            "Speaker B: ...\n" +
            "包含：\n" +
            "1. 实用的日常表达\n" +
            "2. 自然的对话流程\n" +
            "3. 关键短语标注\n" +
            "4. 中文翻译\n",
            scenario, level
        );
        return aigcService.generateContent(prompt);
    }

    /**
     * 生成测验题目
     * 
     * @param topic 测验主题
     * @param questionCount 题目数量
     * @param difficulty 难度
     * @return 测验题目
     */
    public String generateQuiz(String topic, int questionCount, String difficulty) throws Exception {
        String prompt = String.format(
            "为主题 '%s' 生成%d道英文题目（难度：%s）\n" +
            "格式：\n" +
            "Q1. [题目]\nA) [选项]\nB) [选项]\nC) [选项]\nD) [选项]\n\n" +
            "最后提供答案key。\n" +
            "题目类型可包括：选择题、填空、配对等\n",
            topic, questionCount, difficulty
        );
        return aigcService.generateContent(prompt);
    }
}
