package com.github.hronosf.services.impl;

import com.github.hronosf.dto.PostInventoryRequestDTO;
import com.github.hronosf.dto.PreTrialAppealRequestDTO;
import com.github.hronosf.dto.enums.Constants;
import com.github.hronosf.services.DocumentGenerationService;
import de.phip1611.Docx4JSRUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.docx4j.Docx4J;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.joda.time.format.DateTimeFormat;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
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
    public String generatePretrialAppeal(PreTrialAppealRequestDTO request) {
        Map<String, String> mappings = new HashMap<>();

        // seller's data:
        mappings.put("SELLER", request.getSellerName());

        mappings.put("INN", request.getSellerINN());
        mappings.put("SELADR", request.getSellerAddress());

        // consumer's data:
        final String consumerFullName = String.format("%s %s %s"
                , request.getLastName()
                , request.getFirstName()
                , request.getMiddleName() == null ? StringUtils.EMPTY : request.getMiddleName());

        mappings.put("CONSUMER", consumerFullName);
        mappings.put("CONADR", request.getAddress());

        mappings.put("BANKNAME", request.getConsumerBankName());
        mappings.put("BIK", request.getConsumerBankBik());
        mappings.put("CORRACC", request.getConsumerBankCorrAcc());
        mappings.put("CONSACC", request.getCustomerAccountNumber());

        // purchase data:
        mappings.put("PURCHDATA",
                DateTimeFormat.forPattern("dd.MM.yyyy")
                        .print(
                                DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                                        .parseDateTime(request.getPurchaseData()
                                        )
                        )
        );

        mappings.put("PRODUCT", request.getProductName());

        // date:
        mappings.put("CLAIMDATA", new SimpleDateFormat("dd.MM.yyyy").format(new Date()));

        // fill generate docx with user data:
        final String filledDocx = fillUserDataAndGenerateDocx(mappings, loadedTemplates.get("pretrial"));

        // generate file name:
        String pdf = buildFileName(mappings.get("CONSUMER"), "pre-trial-appeal", ".pdf");

        // convert docx to pdf:
        try (InputStream is = new FileInputStream(filledDocx);
             OutputStream out = new FileOutputStream(pdf)) {

            // 1) Load DOCX into XWPFDocument
            XWPFDocument document = new XWPFDocument(is);

            // 2) Convert XWPFDocument to Pdf
            fr.opensagres.poi.xwpf.converter.pdf.PdfConverter.getInstance().convert(document, out, null);
        }

        log.debug("Generated {} file", pdf);

        return pdf;
    }

    @Override
    @SneakyThrows
    public String generatePostInventory(PostInventoryRequestDTO request) {
        Map<String, String> mappings = new HashMap<>();

        // seller's data:
        mappings.put("CONSUMER", request.getConsumerName());
        mappings.put("SELLER", request.getSellerName());
        mappings.put("SELADR", request.getSellerAddress());

        return fillUserDataAndGenerateDocx(mappings, loadedTemplates.get("post_inventory"));
    }

    private String fillUserDataAndGenerateDocx(Map<String, String> mappings, WordprocessingMLPackage wordMLPackage)
            throws Docx4JException, IOException {
        // replace variables in template:
        Docx4JSRUtil.searchAndReplace(wordMLPackage, mappings);

        // paths to files:
        String docx = buildFileName(mappings.get("CONSUMER"), "post-inventory", ".docx");

        // save ready docx file:
        Docx4J.save(wordMLPackage, new FileOutputStream(docx));

        log.debug("Generated {} file", docx);

        return docx;
    }

    @SuppressWarnings("java:S5361")
    public String buildFileName(String name, String type, String extension) {
        return new String(
                String.format("%s%s%s_%s_%s%s"
                        , Constants.PATH.getValue().toAbsolutePath().toString()
                        , File.separator
                        , new String(name.replaceAll(StringUtils.SPACE, "-").getBytes(), Charset.defaultCharset())
                        , type
                        , new SimpleDateFormat("dd_MM_yyyy_ssss").format(new Date())
                        , extension).getBytes(),
                StandardCharsets.UTF_8);
    }
}
