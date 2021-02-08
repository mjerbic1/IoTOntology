/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marjerbic.iot;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
/**
 *
 * @author Marija
 */
@WebServlet(name = "GetStreamData", urlPatterns = {"/GetStreamData"})
public class GetStreamData extends HttpServlet {
    
    String selectedUnit;
    String selectedService;
    String selectedQuantity;
    String selectedStream;
    String selectedLocation;
    String selectedSensor;
    String selectedSensor2;
    String selectedDP;
    String s="http://purl.oclc.org/NET/UNIS/fiware/iot-lite#streamStart";
    String selectedObjectProperty;
    String selectedObjectPropertyO;
    

    String prefix = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX lite: <http://purl.oclc.org/NET/UNIS/fiware/iot-lite#>"
                + "PREFIX unit: <http://purl.org/NET/ssnx/qu/qu#>"
                + "PREFIX sosa: <http://www.w3.org/ns/sosa#>"
                + "PREFIX ssn: <http://purl.oclc.org/NET/ssnx/ssn#>"
                + "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>"
                + " ";

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("doGet()");
        
        doPost(request, response);
                
        //request.getRequestDispatcher("data.jsp").forward(request, response);
                
    }
    
    
    
    private List<String> getSensors() {
        System.out.println("getSensors");
               
        String queryString = prefix
                + "SELECT ?sensor "
                + "WHERE "
                + "{ "
                + "?sensor rdf:type ssn:Sensor"
                + " }";
                    
        return OwlReaderUtil.executeQueryOneColumn(getServletContext(), queryString);
    }
    
           
    private List<String> getServices() {
        System.out.println("getServices");
        
        
        String queryString = prefix
                + "SELECT ?services "
                + "WHERE "
                + "{ "
                + "?services rdf:type lite:Service"
                + " }";
                     
        return OwlReaderUtil.executeQueryOneColumn(getServletContext(), queryString);
    }
    
    
    
    private List<String> getLocations() {
        System.out.println("getLocations");
        
        
        String queryString = prefix
                + "SELECT ?point "
                + "WHERE "
                + "{ "
                + "?point rdf:type geo:Point"
                + " }";
                     
        return OwlReaderUtil.executeQueryOneColumn(getServletContext(), queryString);
    }
    
    
    private List<String> getStreamFromService() {
        System.out.println("getStreamFromService()");      
        
        String queryString = prefix
                + "SELECT ?stream "
                + "WHERE "
                + "{ "
                + "?stream lite:providedBy <" + selectedService + ">"
                + " }";
               
                      
        return OwlReaderUtil.executeQueryOneColumn(getServletContext(), queryString);
    }
    
    
    
    private List<String> getStreamData() {
        
        String queryString =                
                prefix
                + "SELECT DISTINCT ?stream "
                + "WHERE "
                + "{ "
                + "?stream lite:generatedBy <" + selectedSensor + "> .  ?stream lite:providedBy <" + selectedService + "> .  ?stream lite:hasLocation <" + selectedLocation + "> ."
                + " }";      
       
                      
        return OwlReaderUtil.executeQueryOneColumn(getServletContext(), queryString); 
    }
    
    
    
    private List<String> getDP() {
        System.out.println("getDP()");      
        
        String queryString = prefix
                + "SELECT ?x "
                + "WHERE "
                + "{ "
                + "?x rdf:type owl:DatatypeProperty ."
                + " }";      
       
                      
        return OwlReaderUtil.executeQueryOneColumn(getServletContext(), queryString); 
    }  

    
    private List<List<String>> getStreamDP() {
      
        String queryString = prefix
                + "SELECT ?x ?y "
                + "WHERE "
                + "{ "
                + "?x <" + selectedDP + "> ?y ."
                + " }";      
                      
        return OwlReaderUtil.executeQueryOTwoColumnLiteral(getServletContext(), queryString); 
    }  
    
        
    private List<List<String>> getStreamFacts() {
        System.out.println("getStreamFacts()");
        
        String queryString = prefix
                + "SELECT ?x ?y "
                + "WHERE "
                + "{"
                + "?x <" + selectedObjectProperty + "> ?y ."               
                + "}";
        
        System.out.println(queryString);
        
        return OwlReaderUtil.executeQueryOTwoColumnPokusaj(getServletContext(), queryString);
    }
    
    
    private List<List<String>> getPM25() {
        System.out.println("getPM25()");  
      
        String queryString = prefix
                + "SELECT ?x ?y "
                + "WHERE "
                + "{ "
                + "?x <http://purl.oclc.org/NET/UNIS/fiware/iot-lite#PM25> ?y ."
                + "FILTER (?y > 100) ." 
                + " }";      

        System.out.println(queryString);  

        return OwlReaderUtil.executeQueryOTwoColumnLiteral(getServletContext(), queryString); 
    }  
    
    private List<List<String>> getStreamSensors() {
      
        String queryString = prefix
                + "SELECT ?x ?y "
                + "WHERE "
                + "{ "
                + "?x a lite:IoTStream ."
                + "?x lite:generatedBy ?y"
                + "}"
                + "ORDER BY asc(UCASE(str(?s)))";

        return OwlReaderUtil.executeQueryOTwoColumnPokusaj(getServletContext(), queryString); 
    }
    
    private List<List<String>> getStreamObservation() {
        System.out.println("getStreamSensors()");  
      
        String queryString = prefix
                + "SELECT ?x ?y "
                + "WHERE "
                + "{ "
                + "?x a lite:IoTStream ."
                + "?x sosa:madeObservation ?y"
                + "}"
                + "ORDER BY asc(UCASE(str(?s)))";

        return OwlReaderUtil.executeQueryOTwoColumnURI(getServletContext(), queryString); 
    }
    

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("doPost()");
        
 
        //Lista senzora
        List<String> sensors = getSensors();
        request.setAttribute("sensors", sensors);
        //Lista senzora2
        List<String> sensors2 = getSensors();
        request.setAttribute("sensors2", sensors2);
        //Lista Servisa
        List<String> services = getServices();
        request.setAttribute("services", services);
        //Lista Lokacija
        List<String> locations = getLocations();
        request.setAttribute("locations", locations);
             

        // Show data
        showStreamByService(request);
        showStreamData(request);
        showStreamFacts(request);
        showDP(request);
        showStreamDP(request);
        showPM25(request);
        showStreamSensors(request);
        showStreamObservations(request);
        
        
        List<String> dp = getDP();
        request.setAttribute("dp", dp);
        
        request.getRequestDispatcher("stream.jsp").forward(request, response);
    }
    
    
    private void showStreamByService (HttpServletRequest request) {
        if((request.getParameter("quantityKinds")==null) && (request.getParameter("services")!=null) && (request.getParameter("units")==null) && (request.getParameter("streams")==null) && (request.getParameter("locations")==null) && (request.getParameter("sensors")==null)) { 
            List<String> streamByService = getStreamFromService();
            
            // Ispisivanje liste IoT Streamova prema Service-u
            for (int i = 0; i < streamByService.size(); i++) {
                System.out.println(streamByService.get(i));
            }
            request.setAttribute("streamByService", streamByService);
        }
    }
    
    
    
    
    
    private void showStreamData (HttpServletRequest request) {
        if((request.getParameter("services")!=null) && (request.getParameter("locations")!=null) && (request.getParameter("sensors")!=null)) { 
            selectedLocation = request.getParameter("locations"); 
            selectedSensor = request.getParameter("sensors"); 
            selectedService = request.getParameter("services");
            
            List<String> streamData = getStreamData();
            
            
            for (int i = 0; i < streamData.size(); i++) {
                System.out.println(streamData.get(i));
            }
            request.setAttribute("streamData", streamData);
        }       
    }
    
   
    
    private void showDP (HttpServletRequest request) {                        
        System.out.println("dp: ");
        
        List<String> dp = getDP();
              
         for (int i = 0; i < dp.size(); i++) {
            System.out.println(dp.get(i));
        }
        request.setAttribute("dp", dp);
    } 
    
    private void showStreamDP (HttpServletRequest request) {                        
        System.out.println("showStreamDP:");   
        
        selectedDP = request.getParameter("dp");
        System.out.println("Selected DP=" + selectedDP);
        
        List<List<String>> dataProperties = getStreamDP();;
         
        for (int i = 0; i < dataProperties.size(); i++) {
            for (int j = 0; j < dataProperties.get(i).size(); j++) {
                System.out.println(dataProperties.get(i));
            } 
        }
        request.setAttribute("streamDP", dataProperties);
                      
    } 
    
    private void showStreamFacts (HttpServletRequest request) { 
        if(request.getParameter("objectProperty")!=null) {
            selectedObjectProperty = request.getParameter("objectProperty"); 
            List<List<String>> streamFacts = getStreamFacts();

            System.out.println("Stream facts: ");
            for (int i = 0; i < streamFacts.size(); i++) {
                for (int j = 0; j < streamFacts.get(i).size(); j++) {
                    System.out.println(streamFacts.get(i));
                } 
            }
            request.setAttribute("streamFacts", streamFacts);
        }
        
    } 
    
    
    private void showPM25 (HttpServletRequest request) {                        
        System.out.println("showPM25:");
              
        List<List<String>> dataProperties = getPM25();;
        System.out.println("PM25 unutar showPM25: ");       
        for (int i = 0; i < dataProperties.size(); i++) {
            for (int j = 0; j < dataProperties.get(i).size(); j++) {
                System.out.println(dataProperties.get(i));
            } 
        }
        request.setAttribute("streamPM25", dataProperties);
                      
    } 
    
    private void showStreamSensors (HttpServletRequest request) {                        
        System.out.println("showStreamSensors:");
              
        List<List<String>> streamSensors = getStreamSensors();
        for (int i = 0; i < streamSensors.size(); i++) {
            for (int j = 0; j < streamSensors.get(i).size(); j++) {
                System.out.println(streamSensors.get(i));
            } 
        }
        request.setAttribute("streamSensors", streamSensors);
                      
    } 
    
    
    private void showStreamObservations (HttpServletRequest request) {                        
              
        List<List<String>> streamObservations = getStreamObservation();
        for (int i = 0; i < streamObservations.size(); i++) {
            for (int j = 0; j < streamObservations.get(i).size(); j++) {
                System.out.println(streamObservations.get(i));
            } 
        }
        request.setAttribute("streamObservations", streamObservations);
                      
    } 
    
}
    


