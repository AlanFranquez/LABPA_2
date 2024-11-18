
package services;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "CategoriaException", targetNamespace = "http://services/")
public class CategoriaException_Exception
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private CategoriaException faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public CategoriaException_Exception(String message, CategoriaException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public CategoriaException_Exception(String message, CategoriaException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: services.CategoriaException
     */
    public CategoriaException getFaultInfo() {
        return faultInfo;
    }

}
