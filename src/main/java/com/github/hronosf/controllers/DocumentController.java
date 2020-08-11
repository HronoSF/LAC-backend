package com.github.hronosf.controllers;

import com.github.hronosf.model.payload.request.PreTrialAppealRequestDTO;
import com.github.hronosf.services.facedes.DocumentGeneratorFacade;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.PrintWriter;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/documents")
public class DocumentController {

    private final DocumentGeneratorFacade documentFacade;

    @SneakyThrows
    @PostMapping("/generate_pretrial_appeal")
    public void generatePreTrialAppeal(@RequestBody PreTrialAppealRequestDTO request, HttpServletResponse response) {
        // get is from generated document:
        FileInputStream is = documentFacade.generatePreTrialAppeal(request);

        // set content type to allow preview of pdf:
        response.setContentType("application/pdf");

        // write pdf as data-stream:
        try (PrintWriter writer = response.getWriter()) {
            int nRead;
            while ((nRead = is.read()) != -1) {
                writer.write(nRead);
            }
        }
    }
}
