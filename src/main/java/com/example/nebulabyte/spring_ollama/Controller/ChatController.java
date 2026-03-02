package com.example.nebulabyte.spring_ollama.Controller;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ChatController {


    private ChatClient openAiChatClient;
    private ChatClient ollamaChatClient;

    public ChatController(
       @Qualifier("openAiChatClient") ChatClient openAiChatClient,
         @Qualifier("ollamaChatClient") ChatClient ollamaChatClient) {


        this.openAiChatClient = openAiChatClient;
        this.ollamaChatClient = ollamaChatClient;
    }

    @GetMapping("/chat")
    public ResponseEntity<String> chat(
        @RequestParam(value="q",required = true) String q
    ){

        var response = ollamaChatClient.prompt(q).call().content();
        return ResponseEntity.ok(response);

    }

}
