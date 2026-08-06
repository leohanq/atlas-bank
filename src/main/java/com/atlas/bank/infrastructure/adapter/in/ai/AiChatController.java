package com.atlas.bank.infrastructure.adapter.in.ai;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
@Slf4j
public class AiChatController {

    private final ChatClient chatClient;

    @PostMapping("/chat")
    public String chat(@RequestBody String userMessage) {
        try {
            return chatClient.prompt()
                    .user(userMessage)
                    .call()
                    .content();
        } catch (Exception e) {
            log.error("Error while processing chat request: {}", e.getMessage(), e);
            return "Sorry, there was an error processing your request " + e.getMessage();
        }

    }

}
