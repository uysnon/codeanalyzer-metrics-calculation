package com.uysnon.codeanalyzer.webui.service;

import com.uysnon.codeanalyzer.onlinecalculation.model.export.report.ExportReport;
import com.uysnon.codeanalyzer.webui.repository.ExportReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class CalculateProjectService {

    private String calculatorUrl;
    private RestTemplate restTemplate;
    private ExportReportRepository exportReportRepository;

    @Autowired
    public void setExportReportRepository(ExportReportRepository exportReportRepository) {
        this.exportReportRepository = exportReportRepository;
    }

    @Autowired
    public void setCalculatorUrl(@Value("${calculator.url}") String calculatorUrl) {
        this.calculatorUrl = calculatorUrl;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Long calculate(MultipartFile multipartFile) throws IOException {
        HttpEntity<MultiValueMap<String, Object>> requestEntity = getRequestEntity(multipartFile);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ExportReport> response = restTemplate
                .postForEntity(calculatorUrl, requestEntity, ExportReport.class);
        ExportReport exportReport = response.getBody();
        return exportReportRepository.save(exportReport);
    }

    public ExportReport getExportReport(Long id) {
        return exportReportRepository.get(id);
    }

    private HttpEntity<MultiValueMap<String, Object>> getRequestEntity(MultipartFile multipartFile) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
        ContentDisposition contentDisposition = ContentDisposition.builder("form-data")
                .name("file")
                .filename(multipartFile.getOriginalFilename())
                .build();
        fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
        fileMap.add(HttpHeaders.CONTENT_TYPE, "application/zip");
        HttpEntity<byte[]> entity = new HttpEntity<>(multipartFile.getBytes(), fileMap);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", entity);
        return new HttpEntity<>(body, headers);
    }
}
