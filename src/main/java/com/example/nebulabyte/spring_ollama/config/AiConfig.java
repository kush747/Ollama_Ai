 package com.example.nebulabyte.spring_ollama.config;
import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.context.annotation.Bean;
// import org.springframework.ai.chat.client.ChatClient;
// import org.springframework.ai.ollama.OllamaChatModel;
// import org.springframework.ai.openai.OpenAiChatModel;
// import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
 public class AiConfig {

//     @Bean(name="ollamaChatClient")
//     public ChatClient ollamaChatModel(OllamaChatModel chatModel){
//         return ChatClient.builder(chatModel).build();



//     }

//     @Bean(name="openAiChatClient")
//     public ChatClient openAiChatModel(OpenAiChatModel chatModel){
//         return ChatClient.builder(chatModel).build();

//     }

        @Bean
        public ChatClient chatClient(ChatClient.Builder builder,ChatMemory chatMemory){


          MessageChatMemoryAdvisor messageChatMemoryAdvisor =   MessageChatMemoryAdvisor.builder(chatMemory).build();




            return builder
            .defaultAdvisors(messageChatMemoryAdvisor,new TokenPrintAdvisor(),new SafeGuardAdvisor(List.of("games")))
            .defaultSystem("you are very helfull assistant and very good in coding")
            .defaultOptions(OllamaChatOptions.builder()
            .model("gemma2:2b")
            .temperature(0.3)
            
        .build())
            
            .build();
        }


        @Bean
        public ChatMemory chatMemory(JdbcChatMemoryRepository jdbcChatMemoryRepository){
            return MessageWindowChatMemory.builder()
            .chatMemoryRepository(jdbcChatMemoryRepository)
            .maxMessages(10)
            .build();
        }

 }
