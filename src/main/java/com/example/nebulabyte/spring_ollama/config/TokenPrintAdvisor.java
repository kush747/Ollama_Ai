package com.example.nebulabyte.spring_ollama.config;

import org.slf4j.Logger;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;

import reactor.core.publisher.Flux;

public class TokenPrintAdvisor implements CallAdvisor,StreamAdvisor{

    Logger logger = org.slf4j.LoggerFactory.getLogger(TokenPrintAdvisor.class);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public Flux<ChatClientResponse> adviseStream(ChatClientRequest chatClientRequest,
            StreamAdvisorChain streamAdvisorChain) {

             Flux<ChatClientResponse> flux =  streamAdvisorChain.nextStream(chatClientRequest);
       return flux;
    }

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {

        logger.info("first advisor is classed : {}");
        logger.info("request: {}", chatClientRequest.prompt().getContents());

        ChatClientResponse chatClientResponse = callAdvisorChain.nextCall(chatClientRequest);
            logger.info("response received in advisor : {}");

            logger.info("response: {}", chatClientResponse.chatResponse().getResult().getOutput().getText());
            logger.info("total token comsumed : {}", chatClientResponse.chatResponse().getMetadata().getUsage().getTotalTokens());


        return chatClientResponse;
    }

}
