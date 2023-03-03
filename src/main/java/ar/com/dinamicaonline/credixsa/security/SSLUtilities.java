package ar.com.dinamicaonline.credixsa.security;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.*;

public class SSLUtilities {

    private static final TrustManager[] UNQUESTIONING_TRUST_MANAGER = new TrustManager[]{
        new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                // No necesitas implementar esto.
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                // No necesitas implementar esto.
            }

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        }
    };

    public static void trustAllHttpsCertificates() throws NoSuchAlgorithmException, KeyManagementException {
        // Crear un SSLContext que acepte todos los certificados:
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, UNQUESTIONING_TRUST_MANAGER, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }

}

