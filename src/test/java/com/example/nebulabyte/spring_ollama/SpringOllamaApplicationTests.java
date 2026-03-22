package com.example.nebulabyte.spring_ollama;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.nebulabyte.spring_ollama.Services.ChatService;

@SpringBootTest
class SpringOllamaApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private ChatService chatService;
	@Test
	void testTemplate(){

		System.out.println("test template");
		var output = this.chatService.chatTemplate();
		System.out.println(output);
	}

}
