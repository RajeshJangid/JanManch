package com.janmanch;

import javax.xml.soap.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;

public class SOAPService {

    /**
     * Starting point for the SAAJ - SOAP Client Testing
     */
    public static void main(String args[]) {
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            
            // Send SOAP Message to SOAP Server
            String url = "http://164.100.222.243/Service/Action";
            
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(), url);
            

            // Process the SOAP Response
            printSOAPResponse(soapResponse);

            soapConnection.close();
        } catch (Exception e) {
            System.err.println("Error occurred while sending SOAP Request to Server");
            e.printStackTrace();
        }
    }

    private static SOAPMessage createSOAPRequest() throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String serverURI = "http://164.100.222.243/Service/Action/";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        
        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapRoot = soapBody.addChildElement("root");
        SOAPElement soapInfo = soapRoot.addChildElement("Info");
        soapInfo.addChildElement("familyId");
        soapInfo.addChildElement("ackId").addTextNode("1207-CN0V-24567");
        soapInfo.addChildElement("aadharId");
        
        
        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", serverURI  + "bhamashahInfoRequest");

        soapMessage.saveChanges();

        /* Print the request message */
        System.out.print("Request SOAP Message = ");
        soapMessage.writeTo(System.out);
        System.out.println();

        return soapMessage;
    }

    /**
     * Method used to print the SOAP Response
     */
    private static void printSOAPResponse(SOAPMessage soapResponse) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        Source sourceContent = soapResponse.getSOAPPart().getContent();
        System.out.print("\nResponse SOAP Message = ");
        StreamResult result = new StreamResult(System.out);
        transformer.transform(sourceContent, result);
    }

}