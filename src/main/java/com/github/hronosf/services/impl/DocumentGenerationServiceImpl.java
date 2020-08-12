package com.github.hronosf.services.impl;

import com.github.hronosf.services.DocumentGenerationService;
import de.phip1611.Docx4JSRUtil;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.docx4j.Docx4J;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.*;
import java.util.Map;
import java.util.UUID;


@Service
public class DocumentGenerationServiceImpl implements DocumentGenerationService {

    private static final Path PATH = Paths.get(System.getProperty("user.home") + "/Desktop/generatedDocuments");

    @SneakyThrows
    @PostConstruct
    public void createDirectory() {
        Files.createDirectories(PATH);
    }

    @Override
    @SneakyThrows
    public FileInputStream generatePretrialDocument(Map<String, String> mappings) {
        // get docx template:
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
                .load(this.getClass().getResourceAsStream("/templates/pretrial.docx"));

        // replace variables in template:
        Docx4JSRUtil.searchAndReplace(wordMLPackage, mappings);

        // paths to files:
        String docx = commonPathPart() + ".docx";
        String pdf = commonPathPart() + ".pdf";

        // save ready docx file:
        Docx4J.save(wordMLPackage, new FileOutputStream(docx));

        try (InputStream is = new FileInputStream(docx);
             OutputStream out = new FileOutputStream(new File(pdf))) {

            // 1) Load DOCX into XWPFDocument
            XWPFDocument document = new XWPFDocument(is);

            // 2) Convert XWPFDocument to Pdf
            fr.opensagres.poi.xwpf.converter.pdf.PdfConverter.getInstance().convert(document, out, null);
        } finally {
            // delete generated docx:
            Files.delete(Paths.get(docx));
        }
        return new FileInputStream(pdf);
    }

    @Async
    @SneakyThrows
    @Scheduled(cron = "0 0/5 * * * ?")
    public void cleanReportFolder() {
        FileUtils.cleanDirectory(PATH.toFile());
    }

    private String commonPathPart() {
        return PATH.toAbsolutePath().toString() + File.separator + UUID.randomUUID().toString().substring(0, 8);
    }
}
