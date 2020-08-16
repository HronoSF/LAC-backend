package com.github.hronosf.services.impl;

import com.github.hronosf.enums.Constants;
import com.github.hronosf.services.DocumentGenerationService;
import com.github.hronosf.util.Util;
import de.phip1611.Docx4JSRUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.docx4j.Docx4J;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
@Service
public class DocumentGenerationServiceImpl implements DocumentGenerationService {

    private Map<String, WordprocessingMLPackage> loadedTemplates;

    @SneakyThrows
    @PostConstruct
    public void loadTemplatesCreateTargetFolder() {
        loadedTemplates = new HashMap<>();

        // load to cache all docx-templates:
        Arrays.stream(new PathMatchingResourcePatternResolver().getResources("templates/*.docx"))
                .forEach(resource -> {
                    WordprocessingMLPackage mlPackage = null;
                    try {
                        mlPackage = WordprocessingMLPackage
                                .load(this.getClass().getResourceAsStream("/templates/" + resource.getFilename()));
                    } catch (Docx4JException e) {
                        log.error("Failed to load mlPackage for docx file", e);
                    }

                    log.debug("Loaded {} file to templates", resource.getFilename());
                    loadedTemplates.put(StringUtils.substringBefore(resource.getFilename(), "."), mlPackage);
                });

        // create folder to store generated files:
        Files.createDirectories(Constants.PATH.getValue());
    }

    @Override
    @SneakyThrows
    public String generatePretrialAppeal(Map<String, String> mappings) {
        // fill generate docx with user data:
        final String filledDocx = fillUserDataAndGenerateDocx(mappings, loadedTemplates.get("pretrial"));

        // generate file name:
        String pdf = Util.buildFileName(mappings.get("CONSUMER"), ".pdf");

        // convert docx to pdf:
        try (InputStream is = new FileInputStream(filledDocx);
             OutputStream out = new FileOutputStream(new File(pdf))) {

            // 1) Load DOCX into XWPFDocument
            XWPFDocument document = new XWPFDocument(is);

            // 2) Convert XWPFDocument to Pdf
            fr.opensagres.poi.xwpf.converter.pdf.PdfConverter.getInstance().convert(document, out, null);
        } finally {
            // delete generated docx:
            Files.delete(Paths.get(filledDocx));
        }
        log.debug("Generated {} file", pdf);

        return pdf;
    }

    @Override
    @SneakyThrows
    public String generatePostInventory(Map<String, String> mappings) {
        return fillUserDataAndGenerateDocx(mappings, loadedTemplates.get("post_inventory"));
    }

    private String fillUserDataAndGenerateDocx(Map<String, String> mappings, WordprocessingMLPackage wordMLPackage)
            throws Docx4JException, IOException {
        // replace variables in template:
        Docx4JSRUtil.searchAndReplace(wordMLPackage, mappings);

        // paths to files:
        String docx = Util.buildFileName(mappings.get("SELLER"), ".docx");

        // save ready docx file:
        Docx4J.save(wordMLPackage, new FileOutputStream(docx));

        log.debug("Generated {} file", docx);
        return docx;
    }
}
