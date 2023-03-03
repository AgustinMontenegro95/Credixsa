package ar.com.dinamicaonline.credixsa.services;

import org.springframework.http.ResponseEntity;

import ar.com.dinamicaonline.credixsa.dto.GetReportDTO;

public interface ReportService {
    
    ResponseEntity<?> getClientReport(GetReportDTO clientDTO);

}
