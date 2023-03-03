package ar.com.dinamicaonline.credixsa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.dinamicaonline.credixsa.repositories.ParameterRepository;

@Service
public class ParameterServiceImpl implements ParameterService {

    @Autowired
    private ParameterRepository parameterRepository;

    @Override
    public String fetchParamById(int id) {
        return parameterRepository.findById(id);
    }

    @Override
    public String fetchParamByParameterName(String parameterName) {
        return parameterRepository.findByParameterName(parameterName);
    }

}
