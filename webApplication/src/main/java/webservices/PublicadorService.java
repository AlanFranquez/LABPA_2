package webservices;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;

/**
 * Cliente para consumir el servicio web con IP dinámica.
 */
@WebServiceClient(name = "PublicadorService", targetNamespace = "http://services/", wsdlLocation = "")
public class PublicadorService extends Service {

    private static final QName SERVICE_QNAME = new QName("http://services/", "PublicadorService");
    private static URL wsdlLocation;

    static {
        try {
            String ip = obtenerIPLocal(); // Obtener la IP de la máquina
            wsdlLocation = new URL("http://" + ip + ":1234/publicador?wsdl");
        } catch (MalformedURLException | UnknownHostException e) {
            throw new WebServiceException("Error al construir la URL del WSDL", e);
        }
    }

    public PublicadorService() {
        super(wsdlLocation, SERVICE_QNAME);
    }

    public PublicadorService(String wsdlUrl) throws MalformedURLException {
        super(new URL(wsdlUrl), SERVICE_QNAME);
    }

    public PublicadorService(String wsdlUrl, WebServiceFeature... features) throws MalformedURLException {
        super(new URL(wsdlUrl), SERVICE_QNAME, features);
    }

    @WebEndpoint(name = "PublicadorPort")
    public Publicador getPublicadorPort() {
        return super.getPort(new QName("http://services/", "PublicadorPort"), Publicador.class);
    }

    @WebEndpoint(name = "PublicadorPort")
    public Publicador getPublicadorPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://services/", "PublicadorPort"), Publicador.class, features);
    }

    private static String obtenerIPLocal() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }
}
