package ar.com.dinamicaonline.credixsa.services;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.dinamicaonline.credixsa.models.ReceiveAndSend;
import ar.com.dinamicaonline.credixsa.repositories.ReceiveAndSendRepository;

@Service
public class ReceiveAndSendServiceImpl implements ReceiveAndSendService {

    @Autowired
    private ReceiveAndSendRepository receiveAndSendRepository;

    @Autowired
    private EntidadService entidadServiceImpl;

    @Override
    public void saveReceiveAndSend(String request, String response, String dni) {
        int idOrigin = 9; // 9 por tabla api_ReceiveAndSendOrigin
        
        // obtengo el idAccount necesario para api_ReceiveAndSend
        Long idAccount = entidadServiceImpl.fetchEntidadByDocumentId(dni);
        // guardo consulta
        ReceiveAndSend rasConsulta = new ReceiveAndSend();
        rasConsulta.setCreatedOn(Calendar.getInstance().getTime());
        rasConsulta.setIdAccount(idAccount);
        rasConsulta.setIdOrigin(idOrigin);
        rasConsulta.setReceiveSend(1);
        rasConsulta.setMessagge(request);
        receiveAndSendRepository.save(rasConsulta);
        
        // guardo respuesta
        ReceiveAndSend rasRespuesta = new ReceiveAndSend();
        rasRespuesta.setCreatedOn(Calendar.getInstance().getTime());
        rasRespuesta.setIdAccount(idAccount);
        rasRespuesta.setIdOrigin(idOrigin);
        rasRespuesta.setReceiveSend(2);
        rasRespuesta.setMessagge(response);
        receiveAndSendRepository.save(rasRespuesta);
        
    }

}
