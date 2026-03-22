package com.example.nebulabyte.spring_ollama.Bean;

import org.springframework.ai.chroma.vectorstore.ChromaApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class AppConfig {

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean
    public ChromaApi chromaApi(){
        return new ChromaApi.Builder()
        .baseUrl("http://localhost:8000")
        .build();
    }

}
