package com.callgenie.demospringaisnapshot.controller;


import com.callgenie.demospringaisnapshot.service.MyPdfReaderService;
import com.callgenie.demospringaisnapshot.service.MyTikaDocumentReader;
import com.callgenie.demospringaisnapshot.service.OpenAiService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ChatController {


    private final MyTikaDocumentReader myTikaDocumentReader;
    private final MyPdfReaderService myPdfReaderService;
    private final OpenAiService openAiService;
    private final VectorStore vectorStore;

    public ChatController(MyTikaDocumentReader myTikaDocumentReader, MyPdfReaderService myPdfReaderService, OpenAiService openAiService, VectorStore vectorStore) {
        this.myTikaDocumentReader = myTikaDocumentReader;
        this.myPdfReaderService = myPdfReaderService;
        this.openAiService = openAiService;
        this.vectorStore = vectorStore;
    }

    @PostMapping("/")
    public List<Document> hello(@RequestBody String text) {
        try {
            return vectorStore.similaritySearch(SearchRequest.query(text));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/initPdf")
    public String initPdf() {
        try {
            return myPdfReaderService.splitAndStore();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping("/initTika")
    public String initTika() {
        try {
            return myTikaDocumentReader.splitAndStore();
        }catch (Exception e){
            return e.getMessage();
        }
    }

    @PostMapping("/chat")
    public String aiChat(@RequestBody String text){
        return openAiService.getResponse(text);
    }
}
