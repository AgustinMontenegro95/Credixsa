package ar.com.dinamicaonline.credixsa.services;

public interface ParameterService {

    String fetchParamById(int id);

    String fetchParamByParameterName(String parameterName);
}
