package com.chun.myspringboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * AI学习中心控制器
 * 处理AI学习相关的页面请求
 */
@Controller
public class AiStudyController {
    
    /**
     * 跳转到AI学习中心页面
     * @return AI学习中心页面
     */
    @RequestMapping("/ai-study")
    public String toAiStudyPage() {
        return "ai-study";
    }

    /**
     * 跳转到Gemini AI助手页面
     * @return AI助手页面
     */
    @RequestMapping("/aigc-assistant")
    public String toAigcAssistantPage() {
        return "aigc-assistant";
    }
}
