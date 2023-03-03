package ar.com.dinamicaonline.credixsa.services;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
        System.out.println(clientDTO.getWscx_id());
        System.out.println(userCredixsa);
        System.out.println(passCredixsa);
        System.out.println(clientDTO.getWscx_nom());
        System.out.println(clientDTO.getWscx_idcon());
        System.out.println(clientDTO.getWscx_clab());
        System.out.println(clientDTO.getWscx_idpol());

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
        
        String[] partes = response.getBody().split("\\|");
        System.out.println("Length response: " + partes.length);

        responseBody.put("respuestaCredixsa", response.getBody());
        responseBody.put("respuestaFormateada", responseFormating(response.getBody()));
        
        // Crear un objeto ObjectMapper de Jackson
        ObjectMapper mapper = new ObjectMapper();

        String json = null;
        try {
            // Convertir el Map a JSON
            json = mapper.writeValueAsString(responseBody);
            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //guardo datos en tabla "Api_ReceiveAndSend"
        receiveAndSendService.saveReceiveAndSend(formData.toString(), json.toString(), clientDTO.getWscx_id());

        return new ResponseEntity<>(responseBody,  HttpStatus.OK);
    }

    public Map<String, Object> responseFormating(String response){
        Map<String, Object> responseBody = new HashMap<>();
        String[] partes = response.split("\\|");
        
        
        //Datos recibidos
        Map<String, Object> datosRecibidos = new HashMap<>();
        datosRecibidos.put("idCliente", partes[0]);
        datosRecibidos.put("nombre", partes[1]);
        datosRecibidos.put("idConyugeConcubino", partes[2]);
        datosRecibidos.put("cuitLaboral", partes[3]);
        datosRecibidos.put("politicaAplicar", partes[4]);
        responseBody.put("datosRecibidos", datosRecibidos);

        //Resultado
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("idConsulta", partes[5]);
        resultado.put("existenciaValida", partes[6]);
        resultado.put("nombreValidado", partes[7]);
        resultado.put("resultado", partes[8]);
        resultado.put("observaciones", partes[9]);
        responseBody.put("resultado", resultado);

        //Datos personales
        Map<String, Object> datosPersonales = new HashMap<>();
        datosPersonales.put("dni", partes[10]);
        datosPersonales.put("cuit", partes[11]);
        datosPersonales.put("nombre", partes[12]);
        datosPersonales.put("domicilio", partes[13]);
        datosPersonales.put("cp", partes[14]);
        datosPersonales.put("localidad", partes[15]);
        datosPersonales.put("provincia", partes[16]);
        datosPersonales.put("fechaNacimiento", partes[17]);
        datosPersonales.put("sexo", partes[18]);
        responseBody.put("datosPersonales", datosPersonales);

        //Datos personales
        Map<String, Object> datosFiscales = new HashMap<>();
        datosFiscales.put("cuit", partes[19]);
        datosFiscales.put("razonSocial", partes[20]);
        datosFiscales.put("domicilio", partes[21]);
        datosFiscales.put("cp", partes[22]);
        datosFiscales.put("localidad", partes[23]);
        datosFiscales.put("provincia", partes[24]);
        datosFiscales.put("impGanancias", partes[25]);
        datosFiscales.put("impIva", partes[26]);
        datosFiscales.put("categoriaMonotributo", partes[27]);
        datosFiscales.put("integraSociedades", partes[28]);
        datosFiscales.put("empleador", partes[29]);
        datosFiscales.put("cantidadEmpleados", partes[30]);
        datosFiscales.put("actividadPrincipal", partes[31]);
        datosFiscales.put("fechaInicioActividades", partes[32]);
        responseBody.put("datosFiscales", datosFiscales);

        //Deudas en base a credixsa
        Map<String, Object> deudasBaseCredixsa = new HashMap<>();
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat2", partes[33]);
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat3", partes[34]);
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat4", partes[35]);
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat5", partes[36]);
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat2Ult3M", partes[37]);
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat3Ult3M", partes[38]);
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat4Ult3M", partes[39]);
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat5Ult3M", partes[40]);
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat2Ult6M", partes[41]);
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat3Ult6M", partes[42]);
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat4Ult6M", partes[43]);
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat5Ult6M", partes[44]);
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat2Ult12M", partes[45]);
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat3Ult12M", partes[46]);
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat4Ult12M", partes[47]);
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat5Ult12M", partes[48]);
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat2Ult24M", partes[49]);
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat3Ult24M", partes[50]);
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat4Ult24M", partes[51]);
        deudasBaseCredixsa.put("cantidadAtrasosVigentesCat5Ult24M", partes[52]);
        deudasBaseCredixsa.put("morosoEntidadConsultanteVigente", partes[53]);
        deudasBaseCredixsa.put("morosoEntidadConsultanteNoVigente", partes[54]);
        responseBody.put("deudasBaseCredixsa", deudasBaseCredixsa);

        //Deudas sistema financiero 
        Map<String, Object> deudasSistemaFinanciero = new HashMap<>();
        deudasSistemaFinanciero.put("cantidadSituaciones1Vigentes", partes[55]);
        deudasSistemaFinanciero.put("cantidadSituaciones2Vigentes", partes[56]);
        deudasSistemaFinanciero.put("cantidadSituaciones3Vigentes", partes[57]);
        deudasSistemaFinanciero.put("cantidadSituaciones4Vigentes", partes[58]);
        deudasSistemaFinanciero.put("cantidadSituaciones5Vigentes", partes[59]);
        deudasSistemaFinanciero.put("cantidadSituaciones6Vigentes", partes[60]);
        deudasSistemaFinanciero.put("cantidadRefinanciacionesVigentes", partes[61]);
        deudasSistemaFinanciero.put("cantidadSituacionesJuridicasVigentes", partes[62]);
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones1Vigentes", partes[63]);
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones2Vigentes", partes[64]);
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones3Vigentes", partes[65]);
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones4Vigentes", partes[66]);
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones5Vigentes", partes[67]);
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones6Vigentes", partes[68]);
        deudasSistemaFinanciero.put("montoAdeudadoRefinanciacionesVigentes", partes[69]);
        deudasSistemaFinanciero.put("montoAdeudadoSituacionesJuridicasVigentes", partes[70]);
        deudasSistemaFinanciero.put("cantidadSituaciones1Ult6M", partes[71]);
        deudasSistemaFinanciero.put("cantidadSituaciones2Ult6M", partes[72]);
        deudasSistemaFinanciero.put("cantidadSituaciones3Ult6M", partes[73]);
        deudasSistemaFinanciero.put("cantidadSituaciones4Ult6M", partes[74]);
        deudasSistemaFinanciero.put("cantidadSituaciones5Ult6M", partes[75]);
        deudasSistemaFinanciero.put("cantidadSituaciones6Ult6M", partes[76]);
        deudasSistemaFinanciero.put("cantidadRefinanciacionesUlt6M", partes[77]);
        deudasSistemaFinanciero.put("cantidadSituacionesJuridicasUlt6M", partes[78]);
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones1Ult6M", partes[79]);
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones2Ult6M", partes[80]);
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones3Ult6M", partes[81]);
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones4Ult6M", partes[82]);
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones5Ult6M", partes[83]);
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones6Ult6M", partes[84]);
        deudasSistemaFinanciero.put("montoAdeudadoRefinanciacionesUlt6M", partes[85]);
        deudasSistemaFinanciero.put("montoAdeudadoSituacionesJuridicasUlt6M", partes[86]);
        deudasSistemaFinanciero.put("cantidadSituaciones1Ult12M", partes[87]);
        deudasSistemaFinanciero.put("cantidadSituaciones2Ult12M", partes[88]);
        deudasSistemaFinanciero.put("cantidadSituaciones3Ult12M", partes[89]);
        deudasSistemaFinanciero.put("cantidadSituaciones4Ult12M", partes[90]);
        deudasSistemaFinanciero.put("cantidadSituaciones5Ult12M", partes[91]);
        deudasSistemaFinanciero.put("cantidadSituaciones6Ult12M", partes[92]);
        deudasSistemaFinanciero.put("cantidadRefinanciacionesUlt12M", partes[93]);
        deudasSistemaFinanciero.put("cantidadSituacionesJuridicasUlt12M", partes[94]);
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones1Ult12M", partes[95]);
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones2Ult12M", partes[96]);
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones3Ult12M", partes[97]);
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones4Ult12M", partes[98]);
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones5Ult12M", partes[99]);
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones6Ult12M", partes[100]);
        deudasSistemaFinanciero.put("montoAdeudadoRefinanciacionesUlt12M", partes[101]);
        deudasSistemaFinanciero.put("montoAdeudadoSituacionesJuridicasUlt12M", partes[102]);
        deudasSistemaFinanciero.put("cantidadSituaciones1Ult24M", partes[103]);
        deudasSistemaFinanciero.put("cantidadSituaciones2Ult24M", partes[104]);
        deudasSistemaFinanciero.put("cantidadSituaciones3Ult24M", partes[105]);
        deudasSistemaFinanciero.put("cantidadSituaciones4Ult24M", partes[106]);
        deudasSistemaFinanciero.put("cantidadSituaciones5Ult24M", partes[107]);
        deudasSistemaFinanciero.put("cantidadSituaciones6Ult24M", partes[108]);
        deudasSistemaFinanciero.put("cantidadRefinanciacionesUlt24M", partes[109]);
        deudasSistemaFinanciero.put("cantidadSituacionesJuridicasUlt24M", partes[110]);
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones1Ult24M", partes[111]);
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones2Ult24M", partes[112]);
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones3Ult24M", partes[113]);
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones4Ult24M", partes[114]);
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones5Ult24M", partes[115]);
        deudasSistemaFinanciero.put("montoAdeudadoSituaciones6Ult24M", partes[116]);
        deudasSistemaFinanciero.put("montoAdeudadoRefinanciacionesUlt24M", partes[117]);
        deudasSistemaFinanciero.put("montoAdeudadoSituacionesJuridicasUlt24M", partes[118]);
        deudasSistemaFinanciero.put("cantidadDeudasExEntidadesFinancieras", partes[119]);
        responseBody.put("deudasSistemaFinanciero", deudasSistemaFinanciero);
        
        //Cheques rechazados
        Map<String, Object> chequesRechazados = new HashMap<>();
        chequesRechazados.put("cantidadSinFondosImpagosUlt3M", partes[120]);
        chequesRechazados.put("cantidadSinFondosImpagosUlt6M", partes[121]);
        chequesRechazados.put("cantidadSinFondosImpagosUlt12M", partes[122]);
        chequesRechazados.put("cantidadSinFondosImpagosUlt24M", partes[123]);
        chequesRechazados.put("cantidadSinFondosImpagosUlt60M", partes[124]);
        chequesRechazados.put("cantidadSinFondosPagadosUlt3M", partes[125]);
        chequesRechazados.put("cantidadSinFondosPagadosUlt6M", partes[126]);
        chequesRechazados.put("cantidadSinFondosPagadosUlt12M", partes[127]);
        chequesRechazados.put("cantidadSinFondosPagadosUlt24M", partes[128]);
        chequesRechazados.put("chequesSinFondosPagadosDiasAtraso", partes[129]);
        chequesRechazados.put("chequesSinFondoSinPagoMulta", partes[130]);
        chequesRechazados.put("montoSinFondoImpagosUlt3M", partes[131]);
        chequesRechazados.put("montoSinFondoImpagosUlt6M", partes[132]);
        chequesRechazados.put("montoSinFondoImpagosUlt12M", partes[133]);
        chequesRechazados.put("montoSinFondoImpagosUlt24M", partes[134]);
        chequesRechazados.put("montoSinFondoImpagosUlt60M", partes[135]);
        chequesRechazados.put("montoSinFondoPagadosUlt3M", partes[136]);
        chequesRechazados.put("montoSinFondoPagadosUlt6M", partes[137]);
        chequesRechazados.put("montoSinFondoPagadosUlt12M", partes[138]);
        chequesRechazados.put("montoSinFondoPagadosUlt24M", partes[139]);
        chequesRechazados.put("montosSinFondoSinPagoMulta", partes[140]);
        chequesRechazados.put("cantidadDefectosFormalesImpagosUlt3M", partes[141]);
        chequesRechazados.put("cantidadDefectosFormalesImpagosUlt6M", partes[142]);
        chequesRechazados.put("cantidadDefectosFormalesImpagosUlt12M", partes[143]);
        chequesRechazados.put("cantidadDefectosFormalesImpagosUlt24M", partes[144]);
        chequesRechazados.put("cantidadDefectosFormalesImpagosUlt60M", partes[145]);
        chequesRechazados.put("cantidadDefectosFormalesPagadosUlt3M", partes[146]);
        chequesRechazados.put("cantidadDefectosFormalesPagadosUlt6M", partes[147]);
        chequesRechazados.put("cantidadDefectosFormalesPagadosUlt12M", partes[148]);
        chequesRechazados.put("cantidadDefectosFormalesPagadosUlt24M", partes[149]);
        chequesRechazados.put("cantidadDefectosFormalesSinPagoMulta", partes[150]);
        chequesRechazados.put("montosDefectosFormalesImpagosUlt3M", partes[151]);
        chequesRechazados.put("montosDefectosFormalesImpagosUlt6M", partes[152]);
        chequesRechazados.put("montosDefectosFormalesImpagosUlt12M", partes[153]);
        chequesRechazados.put("montosDefectosFormalesImpagosUlt24M", partes[154]);
        chequesRechazados.put("montosDefectosFormalesImpagosUlt60M", partes[155]);
        chequesRechazados.put("montosDefectosFormalesPagadosUlt3M", partes[156]);
        chequesRechazados.put("montosDefectosFormalesPagadosUlt6M", partes[157]);
        chequesRechazados.put("montosDefectosFormalesPagadosUlt12M", partes[158]);
        chequesRechazados.put("montosDefectosFormalesPagadosUlt24M", partes[159]);
        chequesRechazados.put("chequesDefectosFormalesPagodosDiasAtraso", partes[160]);
        chequesRechazados.put("montoDefectosFormalesSinPagoMulta", partes[161]);
        responseBody.put("chequesRechazados", chequesRechazados);

        //Posible conyuge / concubino
        Map<String, Object> posibleConyugeConcubino = new HashMap<>();
        posibleConyugeConcubino.put("nombreSegunIdRecibido", partes[162]);
        posibleConyugeConcubino.put("alertaMorosidadIdRecibido", partes[163]);
        posibleConyugeConcubino.put("idCredixsa", partes[164]);
        posibleConyugeConcubino.put("nombreSegunIdCredixsa", partes[165]);
        posibleConyugeConcubino.put("alertaMorosidadIdCredixsa", partes[166]);
        responseBody.put("posibleConyugeConcubino", posibleConyugeConcubino);

        //alertaLaboral
        Map<String, Object> alertaLaboral = new HashMap<>();
        alertaLaboral.put("razonSocial", partes[167]);
        alertaLaboral.put("domicilioFiscal", partes[168]);
        alertaLaboral.put("telefono", partes[169]);
        alertaLaboral.put("actividadPrincipalAFIP", partes[170]);
        alertaLaboral.put("cantidadEmpeadosEstimado", partes[171]);
        alertaLaboral.put("alertaMorosidad", partes[172]);
        responseBody.put("alertaLaboral", alertaLaboral);

        //Consultado
        Map<String, Object> consultado = new HashMap<>();
        consultado.put("cantidadUltimos5Dias", partes[173]);
        consultado.put("cantidadUltimos30Dias", partes[174]);
        consultado.put("cantidadUltimos5DiasOtrasEntidades", partes[175]);
        consultado.put("cantidadUltimos30DiasOtrasEntidades", partes[176]);
        consultado.put("cantidadEntidadesUlt5Dias", partes[177]);
        consultado.put("cantidadEntidadesUlt30Dias", partes[178]);
        responseBody.put("consultado", consultado);

         //Alerta fallecido
         Map<String, Object> alertaFallecido = new HashMap<>();
         alertaFallecido.put("alertaFallecido", partes[179]);
         responseBody.put("alertaFallecido", alertaFallecido);

         /* //Datos telefonicos
         Map<String, Object> datosTelefonicos = new HashMap<>();
         datosTelefonicos.put("telefonosContacto1", partes[180]);
         datosTelefonicos.put("telefonosContacto2", partes[181]);
         datosTelefonicos.put("telefonosContacto3", partes[182]);
         responseBody.put("datosTelefonicos", datosTelefonicos); */

        return responseBody;
    }
    
}

