package com.atlas.bank.infrastructure.config;

import com.atlas.bank.infrastructure.adapter.in.ai.AtlasBankTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiAgentConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, AtlasBankTools atlasBankTools) {
        return builder.
                defaultSystem("/no_think You are a helpful assistant for Atlas Bank. You can help users transfer money between accounts and get account details.")
                .defaultTools(atlasBankTools)
                .build();
    }
}
