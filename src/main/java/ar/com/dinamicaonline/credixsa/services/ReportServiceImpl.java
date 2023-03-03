package ar.com.dinamicaonline.credixsa.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import ar.com.dinamicaonline.credixsa.dto.GetReportDTO;
import ar.com.dinamicaonline.credixsa.security.SSLUtilities;


@Service
public class ReportServiceImpl implements ReportService{

    @Autowired
    ReceiveAndSendService receiveAndSendService;

    @Autowired
    private ParameterService parameterService;

    @Override
    public ResponseEntity<?> getClientReport(GetReportDTO clientDTO) {

        //Solo version de prueba - deshabilitado ssl
        try {
            SSLUtilities.trustAllHttpsCertificates();
        } catch (Exception e) {
            //Manejar la excepción
        }


        Map<String, Object> responseBody = new HashMap<>();

        //Get user and pass
        String userCredixsa = parameterService.fetchParamByParameterName("CREDIXSA_USER");
        String passCredixsa = parameterService.fetchParamByParameterName("CREDIXSA_PASS");
        
        //Verification entry
        System.out.println(clientDTO.getWscx_id());
        System.out.println(userCredixsa);
        System.out.println(passCredixsa);
        System.out.println(clientDTO.getWscx_nom());
        System.out.println(clientDTO.getWscx_idcon());
        System.out.println(clientDTO.getWscx_clab());
        System.out.println(clientDTO.getWscx_idpol());

        //JSON send
        String body = "{\"wscx_id\": \""+clientDTO.getWscx_id()+"\", "+
            "\"wscx_usu\": \""+userCredixsa+"\", "+
            "\"wscx_pas\": \""+passCredixsa+"\", "+
            "\"wscx_nom\": \""+clientDTO.getWscx_nom()+"\", "+
            "\"wscx_idcon\": \""+clientDTO.getWscx_idcon()+"\", "+
            "\"wscx_clab\": \""+clientDTO.getWscx_clab()+"\", "+
            "\"wscx_idpol\":\""+clientDTO.getWscx_idpol()+"\" "+
            "}";

        //verification sent
        System.out.println("Body sent: " + body);

        //send request 
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("wscx_id", clientDTO.getWscx_id());
        formData.add("wscx_usu", userCredixsa);
        formData.add("wscx_pas", passCredixsa);
        formData.add("wscx_nom", clientDTO.getWscx_nom());
        formData.add("wscx_idcon", clientDTO.getWscx_idcon());
        formData.add("wscx_clab", clientDTO.getWscx_clab());
        formData.add("wscx_idpol", clientDTO.getWscx_idpol());

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(formData, headers);

        String url = "https://webservice.credixsa.com/ws004.php";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

        System.out.println(response.getBody());
        responseBody.put("respuesta", response.getBody());

        return new ResponseEntity<>(responseBody,  HttpStatus.OK);
    }
    
}

