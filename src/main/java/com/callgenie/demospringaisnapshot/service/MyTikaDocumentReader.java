package com.callgenie.demospringaisnapshot.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyTikaDocumentReader{

    private final Resource resource;
    private final VectorStore vectorStore;

    MyTikaDocumentReader(@Value("classpath:/docs/CURSOS_FREEDOM.xlsx")
                         Resource resource, VectorStore vectorStore) {
        this.resource = resource;
        this.vectorStore = vectorStore;
    }

    List<Document> loadText() {
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(this.resource);
        return tikaDocumentReader.read();
    }

    public String splitAndStore() throws Exception {
        TokenTextSplitter splitter = new TokenTextSplitter();
        vectorStore.accept(splitter.apply(loadText()));
        return "done";
    }
}