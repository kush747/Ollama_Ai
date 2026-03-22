package com.example.nebulabyte.spring_ollama.Services;
import java.util.List;
import java.util.Map;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.experimental.var;
import reactor.core.publisher.Flux;

@Service
public class ChatService {

    @Autowired
    private VectorStore vectorStore;

    private ChatClient chatClient;

    public ChatService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String chat(String query, String userId){

    //     String prompt = "tell me about virat kohli";
    //   var content =   chatClient
    //     .prompt()
    //     .user(prompt)
    //     .system("as a human in 2 line")
    //     .call()
    //     .chatResponse()
    //     .getMetadata();
    //     System.out.println(content);

    //     .content();  
        return chatClient
        .prompt()
        .advisors(advisorSpec->advisorSpec.param(ChatMemory.CONVERSATION_ID,userId))
        .system("you are an expert and answer only in one or two lines ")
        .user(query)
        .call()
        .content();
    }

    public String chatTemplate(){

       PromptTemplate strTemplate =  PromptTemplate.builder().template("who is {name}? tell me with an example {example}").build();
      //render the template
      String renderedMessage = strTemplate.render(
        Map.of(
            "name","Virat kohli",
            "example","any one achievements"
        )
      );

      Prompt prompt = new Prompt(renderedMessage);
      var content = chatClient
      .prompt(prompt)
      .call()
      .content();

      // some othe methods

       SystemPromptTemplate systemPromptTemplate = SystemPromptTemplate.builder()
    .template("You are very helfull and really expert in coding")
    .build();

   var message =  systemPromptTemplate.createMessage();

   var userPromptTemplate = PromptTemplate.builder().template("who is {name} explain in one line").build();
     var userMessage = userPromptTemplate.createMessage(
        Map.of(
            "name","Virat kohli"
            
        )
      );

      Prompt prompt2 = new Prompt(message,userMessage);
      var content2 = chatClient      .prompt(prompt2)
      .call()
      .content();
       return content;
    }

    public Flux<String> chatStream(String q) {
        return chatClient
        .prompt()
        .system("you are an expert and answer only in one or two lines ")
        .user(q)
        .stream()
        .content();
    }

   

    

    // vectore store add 
    // public void saveData(List<String> list){
    //     List<Document> documentList = list.stream().map(Document::new).toList();
    //     vectorStore.add(documentList);
    // }

    public void saveDocument(){
        List<Document> docs= List.of(
            new Document("The sky is blue and beautiful.", Map.of("category", "nature")),
            new Document("Java is a popular programming language.", Map.of("category", "tech")),
            new Document("Spring Boot makes backend development easy.", Map.of("category", "tech"))
        );
        vectorStore.add(docs);
        System.out.println("Documents added successfully!");


    }

    // search document

    public List<Document> search(String query){
        return vectorStore.similaritySearch(

            SearchRequest.builder()
            .query(query)
            .topK(4)
            .similarityThreshold(0.7)
            .build()
        );
    }

    

}
