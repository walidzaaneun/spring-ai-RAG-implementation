package com.callgenie.demospringaisnapshot.service;

import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.ParagraphPdfDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class MyPdfReaderService {
    private final VectorStore vectorStore;
    @Value("classpath:/docs/code_penal.pdf")
    private Resource codePdf;

    public MyPdfReaderService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public String splitAndStore() throws Exception{
        //var pdfReader = new ParagraphPdfDocumentReader(codePdf);  this require a pdf with TOC (table of content) either use TikaDocumentReader or PagePdfDocumentReader
        var pdfReader = new PagePdfDocumentReader(codePdf);
        TextSplitter textSplitter = new TokenTextSplitter();
        vectorStore.accept(textSplitter.apply(pdfReader.get()));
        return "done";
    }
}
