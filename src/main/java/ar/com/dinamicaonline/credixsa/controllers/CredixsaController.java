package ar.com.dinamicaonline.credixsa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.com.dinamicaonline.credixsa.dto.GetReportDTO;
import ar.com.dinamicaonline.credixsa.services.ReportService;

@RestController
@RequestMapping("/api/v1")
public class CredixsaController {

    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "/obtener_informe", method = RequestMethod.POST)
    public ResponseEntity<?> obtenerInforme(@RequestBody GetReportDTO clientDTO) {
        return reportService.getClientReport(clientDTO);
    }
}
