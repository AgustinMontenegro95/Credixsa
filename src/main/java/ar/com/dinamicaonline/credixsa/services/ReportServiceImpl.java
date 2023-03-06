package ar.com.dinamicaonline.credixsa.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import ar.com.dinamicaonline.credixsa.dto.GetReportDTO;

@Service
public class ReportServiceImpl implements ReportService{

    @Autowired
    ReceiveAndSendService receiveAndSendService;

    @Autowired
    private ParameterService parameterService;

    @Override
    public ResponseEntity<?> getClientReport(GetReportDTO clientDTO) {

        Map<String, Object> responseBody = new HashMap<>();

        //Get user and pass
        String userCredixsa = parameterService.fetchParamByParameterName("CREDIXSA_USER");
        String passCredixsa = parameterService.fetchParamByParameterName("CREDIXSA_PASS");
        
        //Verification entry
        System.out.println("///////////////////verificacion///////////////////");
        System.out.println("wscx_id: "+clientDTO.getWscx_id());
        System.out.println("wscx_usu: "+userCredixsa);
        System.out.println("wscx_pass: "+passCredixsa);
        System.out.println("wscx_nom: "+clientDTO.getWscx_nom());
        System.out.println("wscx_idcon: "+clientDTO.getWscx_idcon());
        System.out.println("wscx_clab: "+clientDTO.getWscx_clab());
        System.out.println("wscx_idpol: "+clientDTO.getWscx_idpol());
        System.out.println("//////////////////////////////////////////////////");

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

        responseBody.put("respuestaCredixsa", response.getBody());
        responseBody.put("respuestaFormateada", responseFormating(response.getBody()));
        
        // Crear un objeto ObjectMapper de Jackson
        ObjectMapper mapper = new ObjectMapper();

        String json = null;
        try {
            // Convertir el Map a JSON
            json = mapper.writeValueAsString(responseBody);
            //System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //guardo datos en tabla "Api_ReceiveAndSend"
        receiveAndSendService.saveReceiveAndSend(formData.toString(), json.toString(), clientDTO.getWscx_id());

        return new ResponseEntity<>(responseBody,  HttpStatus.OK);
    }

    public Map<String, Object> responseFormating(String response){
        Map<String, Object> responseBody = new HashMap<>();
        
        List<String> responseList = new ArrayList<>(Arrays.asList(response.split("\\|")));
        
        for (int index = responseList.size()+1; index < 184; index++) {
            responseList.add("");
        }
           
        //Datos recibidos
        Map<String, Object> datosRecibidos = new HashMap<>();
        datosRecibidos.put("idCliente", responseList.get(0));
        datosRecibidos.put("nombre", responseList.get(1));
        datosRecibidos.put("idConyugeConcubino", responseList.get(2));
        datosRecibidos.put("cuitLaboral", responseList.get(3));
        datosRecibidos.put("politicaAplicar", responseList.get(4));
        responseBody.put("datosRecibidos", datosRecibidos);

        //Resultado
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("idConsulta", responseList.get(5));
        resultado.put("existenciaValida", responseList.get(6));
        resultado.put("nombreValidado", responseList.get(7));
        resultado.put("resultado", responseList.get(8));
        resultado.put("observaciones", responseList.get(9));
        responseBody.put("resultado", resultado);

        //Datos personales
        Map<String, Object> datosPersonales = new HashMap<>();
        datosPersonales.put("dni", responseList.get(10));
        datosPersonales.put("cuit", responseList.get(11));
        datosPersonales.put("nombre", responseList.get(12));
        datosPersonales.put("domicilio", responseList.get(13));
        datosPersonales.put("cp", responseList.get(14));
        datosPersonales.put("localidad", responseList.get(15));
        datosPersonales.put("provincia", responseList.get(16));
        datosPersonales.put("fechaNacimiento", responseList.get(17));
        datosPersonales.put("sexo", responseList.get(18));
        responseBody.put("datosPersonales", datosPersonales);

        //Datos personales
        Map<String, Object> datosFiscales = new HashMap<>();
        datosFiscales.put("cuit", responseList.get(19));
        datosFiscales.put("razonSocial", responseList.get(20));
        datosFiscales.put("domicilio", responseList.get(21));
        datosFiscales.put("cp", responseList.get(22));
        datosFiscales.put("localidad", responseList.get(23));
        datosFiscales.put("provincia", responseList.get(24));
        datosFiscales.put("impGanancias", responseList.get(25));
        datosFiscales.put("impIva", responseList.get(26));
        datosFiscales.put("categoriaMonotributo", responseList.get(27));
        datosFiscales.put("integraSociedades", responseList.get(28));
        datosFiscales.put("empleador", responseList.get(29));
        datosFiscales.put("cantidadEmpleados", responseList.get(30));
        datosFiscales.put("actividadPrincipal", responseList.get(31));
        datosFiscales.put("fechaInicioActividades", responseList.get(32));
        responseBody.put("datosFiscales", datosFiscales);

        //Deudas en base a credixsa
        Map<String, Object> deudasBaseCredixsa = new HashMap<>();
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat2", responseList.get(33));
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat3", responseList.get(34));
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat4", responseList.get(35));
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat5", responseList.get(36));
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat2Ult3M", responseList.get(37));
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat3Ult3M", responseList.get(38));
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat4Ult3M", responseList.get(39));
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat5Ult3M", responseList.get(40));
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat2Ult6M", responseList.get(41));
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat3Ult6M", responseList.get(42));
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat4Ult6M", responseList.get(43));
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat5Ult6M", responseList.get(44));
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat2Ult12M", responseList.get(45));
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat3Ult12M", responseList.get(46));
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat4Ult12M", responseList.get(47));
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat5Ult12M", responseList.get(48));
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat2Ult24M", responseList.get(49));
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat3Ult24M", responseList.get(50));
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat4Ult24M", responseList.get(51));
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat5Ult24M", responseList.get(52));
        deudasBaseCredixsa.put("morosoEntidadConsultanteVigente", responseList.get(53));
        deudasBaseCredixsa.put("morosoEntidadConsultanteNoVigente", responseList.get(54));
        responseBody.put("deudasBaseCredixsa", deudasBaseCredixsa);

        //Deudas sistema financiero 
        Map<String, Object> deudasSistemaFinanciero = new HashMap<>();
        deudasSistemaFinanciero.put("cantidadSituaciones1Vigentes", responseList.get(55));
        deudasSistemaFinanciero.put("cantidadSituaciones2Vigentes", responseList.get(56));
        deudasSistemaFinanciero.put("cantidadSituaciones3Vigentes", responseList.get(57));
        deudasSistemaFinanciero.put("cantidadSituaciones4Vigentes", responseList.get(58));
        deudasSistemaFinanciero.put("cantidadSituaciones5Vigentes", responseList.get(59));
        deudasSistemaFinanciero.put("cantidadSituaciones6Vigentes", responseList.get(60));
        deudasSistemaFinanciero.put("cantidadRefinanciacionesVigentes", responseList.get(61));
        deudasSistemaFinanciero.put("cantidadSituacionesJuridicasVigentes", responseList.get(62));
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones1Vigentes", responseList.get(63));
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones2Vigentes", responseList.get(64));
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones3Vigentes", responseList.get(65));
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones4Vigentes", responseList.get(66));
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones5Vigentes", responseList.get(67));
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones6Vigentes", responseList.get(68));
        deudasSistemaFinanciero.put("montoAdeudadoRefinanciacionesVigentes", responseList.get(69));
        deudasSistemaFinanciero.put("montoAdeudadoSituacionesJuridicasVigentes", responseList.get(70));
        deudasSistemaFinanciero.put("cantidadSituaciones1Ult6M", responseList.get(71));
        deudasSistemaFinanciero.put("cantidadSituaciones2Ult6M", responseList.get(72));
        deudasSistemaFinanciero.put("cantidadSituaciones3Ult6M", responseList.get(73));
        deudasSistemaFinanciero.put("cantidadSituaciones4Ult6M", responseList.get(74));
        deudasSistemaFinanciero.put("cantidadSituaciones5Ult6M", responseList.get(75));
        deudasSistemaFinanciero.put("cantidadSituaciones6Ult6M", responseList.get(76));
        deudasSistemaFinanciero.put("cantidadRefinanciacionesUlt6M", responseList.get(77));
        deudasSistemaFinanciero.put("cantidadSituacionesJuridicasUlt6M", responseList.get(78));
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones1Ult6M", responseList.get(79));
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones2Ult6M", responseList.get(80));
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones3Ult6M", responseList.get(81));
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones4Ult6M", responseList.get(82));
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones5Ult6M", responseList.get(83));
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones6Ult6M", responseList.get(84));
        deudasSistemaFinanciero.put("montoAdeudadoRefinanciacionesUlt6M", responseList.get(85));
        deudasSistemaFinanciero.put("montoAdeudadoSituacionesJuridicasUlt6M", responseList.get(86));
        deudasSistemaFinanciero.put("cantidadSituaciones1Ult12M", responseList.get(87));
        deudasSistemaFinanciero.put("cantidadSituaciones2Ult12M", responseList.get(88));
        deudasSistemaFinanciero.put("cantidadSituaciones3Ult12M", responseList.get(89));
        deudasSistemaFinanciero.put("cantidadSituaciones4Ult12M", responseList.get(90));
        deudasSistemaFinanciero.put("cantidadSituaciones5Ult12M", responseList.get(91));
        deudasSistemaFinanciero.put("cantidadSituaciones6Ult12M", responseList.get(92));
        deudasSistemaFinanciero.put("cantidadRefinanciacionesUlt12M", responseList.get(93));
        deudasSistemaFinanciero.put("cantidadSituacionesJuridicasUlt12M", responseList.get(94));
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones1Ult12M", responseList.get(95));
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones2Ult12M", responseList.get(96));
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones3Ult12M", responseList.get(97));
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones4Ult12M", responseList.get(98));
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones5Ult12M", responseList.get(99));
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones6Ult12M", responseList.get(100));
        deudasSistemaFinanciero.put("montoAdeudadoRefinanciacionesUlt12M", responseList.get(101));
        deudasSistemaFinanciero.put("montoAdeudadoSituacionesJuridicasUlt12M", responseList.get(102));
        deudasSistemaFinanciero.put("cantidadSituaciones1Ult24M", responseList.get(103));
        deudasSistemaFinanciero.put("cantidadSituaciones2Ult24M", responseList.get(104));
        deudasSistemaFinanciero.put("cantidadSituaciones3Ult24M", responseList.get(105));
        deudasSistemaFinanciero.put("cantidadSituaciones4Ult24M", responseList.get(106));
        deudasSistemaFinanciero.put("cantidadSituaciones5Ult24M", responseList.get(107));
        deudasSistemaFinanciero.put("cantidadSituaciones6Ult24M", responseList.get(108));
        deudasSistemaFinanciero.put("cantidadRefinanciacionesUlt24M", responseList.get(109));
        deudasSistemaFinanciero.put("cantidadSituacionesJuridicasUlt24M", responseList.get(110));
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones1Ult24M", responseList.get(111));
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones2Ult24M", responseList.get(112));
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones3Ult24M", responseList.get(113));
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones4Ult24M", responseList.get(114));
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones5Ult24M", responseList.get(115));
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones6Ult24M", responseList.get(116));
        deudasSistemaFinanciero.put("montoAdeudadoRefinanciacionesUlt24M", responseList.get(117));
        deudasSistemaFinanciero.put("montoAdeudadoSituacionesJuridicasUlt24M", responseList.get(118));
        deudasSistemaFinanciero.put("cantidadDeudasExEntidadesFinancieras", responseList.get(119));
        responseBody.put("deudasSistemaFinanciero", deudasSistemaFinanciero);
        
        //Cheques rechazados
        Map<String, Object> chequesRechazados = new HashMap<>();
        chequesRechazados.put("cantidadSinFondosImpagosUlt3M", responseList.get(120));
        chequesRechazados.put("cantidadSinFondosImpagosUlt6M", responseList.get(121));
        chequesRechazados.put("cantidadSinFondosImpagosUlt12M", responseList.get(122));
        chequesRechazados.put("cantidadSinFondosImpagosUlt24M", responseList.get(123));
        chequesRechazados.put("cantidadSinFondosImpagosUlt60M", responseList.get(124));
        chequesRechazados.put("cantidadSinFondosPagadosUlt3M", responseList.get(125));
        chequesRechazados.put("cantidadSinFondosPagadosUlt6M", responseList.get(126));
        chequesRechazados.put("cantidadSinFondosPagadosUlt12M", responseList.get(127));
        chequesRechazados.put("cantidadSinFondosPagadosUlt24M", responseList.get(128));
        chequesRechazados.put("chequesSinFondosPagadosDiasAtraso", responseList.get(129));
        chequesRechazados.put("chequesSinFondoSinPagoMulta", responseList.get(130));
        chequesRechazados.put("montoSinFondoImpagosUlt3M", responseList.get(131));
        chequesRechazados.put("montoSinFondoImpagosUlt6M", responseList.get(132));
        chequesRechazados.put("montoSinFondoImpagosUlt12M", responseList.get(133));
        chequesRechazados.put("montoSinFondoImpagosUlt24M", responseList.get(134));
        chequesRechazados.put("montoSinFondoImpagosUlt60M", responseList.get(135));
        chequesRechazados.put("montoSinFondoPagadosUlt3M", responseList.get(136));
        chequesRechazados.put("montoSinFondoPagadosUlt6M", responseList.get(137));
        chequesRechazados.put("montoSinFondoPagadosUlt12M", responseList.get(138));
        chequesRechazados.put("montoSinFondoPagadosUlt24M", responseList.get(139));
        chequesRechazados.put("montosSinFondoSinPagoMulta", responseList.get(140));
        chequesRechazados.put("cantidadDefectosFormalesImpagosUlt3M", responseList.get(141));
        chequesRechazados.put("cantidadDefectosFormalesImpagosUlt6M", responseList.get(142));
        chequesRechazados.put("cantidadDefectosFormalesImpagosUlt12M", responseList.get(143));
        chequesRechazados.put("cantidadDefectosFormalesImpagosUlt24M", responseList.get(144));
        chequesRechazados.put("cantidadDefectosFormalesImpagosUlt60M", responseList.get(145));
        chequesRechazados.put("cantidadDefectosFormalesPagadosUlt3M", responseList.get(146));
        chequesRechazados.put("cantidadDefectosFormalesPagadosUlt6M", responseList.get(147));
        chequesRechazados.put("cantidadDefectosFormalesPagadosUlt12M", responseList.get(148));
        chequesRechazados.put("cantidadDefectosFormalesPagadosUlt24M", responseList.get(149));
        chequesRechazados.put("cantidadDefectosFormalesSinPagoMulta", responseList.get(150));
        chequesRechazados.put("montosDefectosFormalesImpagosUlt3M", responseList.get(151));
        chequesRechazados.put("montosDefectosFormalesImpagosUlt6M", responseList.get(152));
        chequesRechazados.put("montosDefectosFormalesImpagosUlt12M", responseList.get(153));
        chequesRechazados.put("montosDefectosFormalesImpagosUlt24M", responseList.get(154));
        chequesRechazados.put("montosDefectosFormalesImpagosUlt60M", responseList.get(155));
        chequesRechazados.put("montosDefectosFormalesPagadosUlt3M", responseList.get(156));
        chequesRechazados.put("montosDefectosFormalesPagadosUlt6M", responseList.get(157));
        chequesRechazados.put("montosDefectosFormalesPagadosUlt12M", responseList.get(158));
        chequesRechazados.put("montosDefectosFormalesPagadosUlt24M", responseList.get(159));
        chequesRechazados.put("chequesDefectosFormalesPagodosDiasAtraso", responseList.get(160));
        chequesRechazados.put("montoDefectosFormalesSinPagoMulta", responseList.get(161));
        responseBody.put("chequesRechazados", chequesRechazados);

        //Posible conyuge / concubino
        Map<String, Object> posibleConyugeConcubino = new HashMap<>();
        posibleConyugeConcubino.put("nombreSegunIdRecibido", responseList.get(162));
        posibleConyugeConcubino.put("alertaMorosidadIdRecibido", responseList.get(163));
        posibleConyugeConcubino.put("idCredixsa", responseList.get(164));
        posibleConyugeConcubino.put("nombreSegunIdCredixsa", responseList.get(165));
        posibleConyugeConcubino.put("alertaMorosidadIdCredixsa", responseList.get(166));
        responseBody.put("posibleConyugeConcubino", posibleConyugeConcubino);

        //alertaLaboral
        Map<String, Object> alertaLaboral = new HashMap<>();
        alertaLaboral.put("razonSocial", responseList.get(167));
        alertaLaboral.put("domicilioFiscal", responseList.get(168));
        alertaLaboral.put("telefono", responseList.get(169));
        alertaLaboral.put("actividadPrincipalAFIP", responseList.get(170));
        alertaLaboral.put("cantidadEmpeadosEstimado", responseList.get(171));
        alertaLaboral.put("alertaMorosidad", responseList.get(172));
        responseBody.put("alertaLaboral", alertaLaboral);

        //Consultado
        Map<String, Object> consultado = new HashMap<>();
        consultado.put("cantidadUltimos5Dias", responseList.get(173));
        consultado.put("cantidadUltimos30Dias", responseList.get(174));
        consultado.put("cantidadUltimos5DiasOtrasEntidades", responseList.get(175));
        consultado.put("cantidadUltimos30DiasOtrasEntidades", responseList.get(176));
        consultado.put("cantidadEntidadesUlt5Dias", responseList.get(177));
        consultado.put("cantidadEntidadesUlt30Dias", responseList.get(178));
        responseBody.put("consultado", consultado);

         //Alerta fallecido
         Map<String, Object> alertaFallecido = new HashMap<>();
         alertaFallecido.put("alertaFallecido", responseList.get(179));
         responseBody.put("alertaFallecido", alertaFallecido);

         //Datos telefonicos
         Map<String, Object> datosTelefonicos = new HashMap<>();
         datosTelefonicos.put("telefonosContacto1", responseList.get(180));
         datosTelefonicos.put("telefonosContacto2", responseList.get(181));
         datosTelefonicos.put("telefonosContacto3", responseList.get(182));
         responseBody.put("datosTelefonicos", datosTelefonicos);

        return responseBody;
    }
    
}

