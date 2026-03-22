package com.example.nebulabyte.spring_ollama.Controller;
import java.util.List;

import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.nebulabyte.spring_ollama.Services.ChatService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping
public class ChatController {


    // private ChatClient openAiChatClient;
    // private ChatClient ollamaChatClient;

    // public ChatController(
    //    @Qualifier("openAiChatClient") ChatClient openAiChatClient,
    //      @Qualifier("ollamaChatClient") ChatClient ollamaChatClient) {


    //     this.openAiChatClient = openAiChatClient;
    //     this.ollamaChatClient = ollamaChatClient;
    // }

   // private ChatClient chatClient;
    private ChatService chatService;

    // public ChatController(ChatClient.Builder builder) {
    //     this.chatClient = builder.build();
    // }

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }


    @GetMapping("/chat")
    public ResponseEntity<String> chat(
        @RequestParam(value="q",required = true) String q,
        @RequestHeader("userId") String userId
    ){

        var response = chatService.chat(q,userId);
        return ResponseEntity.ok(response);

    }
    //controller for chatTemplate in chatservice
   @GetMapping("/template")
    public String chatTemplate() {
        return chatService.chatTemplate();
    }

   @GetMapping(value = "/stream")
public Flux<String> streamChat(@RequestParam String q) {
    return chatService.chatStream(q);
}



// add document 

@PostMapping("/add")
    public String add() {
        chatService.saveDocument();
        return "Documents added!";
    }

    @GetMapping("/search")
    public List<Document> search(@RequestParam String query) {
        return chatService.search(query);
    }

}
