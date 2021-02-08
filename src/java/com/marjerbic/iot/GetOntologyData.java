/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marjerbic.iot;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
/**
 *
 * @author Marija
 */
@WebServlet(name = "GetOntologyData", urlPatterns = {"/OntologySearch"})
public class GetOntologyData extends HttpServlet {
    
    String selectedService;
    String selectedStream;
    String selectedSensor;
    String selectedSensor2;
    String selectedDP;
    String selectedObjectProperty;
    

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
                                
    }
    
    
    
    private List<String> getSensors() {
               
        String queryString = prefix
                + "SELECT ?sensor "
                + "WHERE "
                + "{ "
                + "?sensor rdf:type ssn:Sensor"
                + " }";
                    
        return OwlReaderUtil.executeQueryOneColumn(getServletContext(), queryString);
    }
    
    
    private List<String> getServices() {        
        
        String queryString = prefix
                + "SELECT ?services "
                + "WHERE "
                + "{ "
                + "?services rdf:type lite:Service"
                + " }";
                     
        return OwlReaderUtil.executeQueryOneColumn(getServletContext(), queryString);
    }
    
    
    private List<String> getDeviceFromService() {
                
        String queryString = prefix
                + "SELECT ?sensingDevice "
                + "WHERE "
                + "{ "
                + " ?sensingDevice lite:exposedBy <" + selectedService + ">"
                + " }";
        
      
        return OwlReaderUtil.executeQueryOneColumn(getServletContext(), queryString);
    }
    
    private List<String> getStreamFromService() {
        
        String queryString = prefix
                + "SELECT ?stream "
                + "WHERE "
                + "{ "
                + "?stream lite:providedBy <" + selectedService + ">"
                + " }";
               
                      
        return OwlReaderUtil.executeQueryOneColumn(getServletContext(), queryString);
    }
   
    
    private List<String> getDP() {
        
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
    
    
    private List<List<String>> getOntologyFacts() {
      
        String queryString = prefix
                + "SELECT DISTINCT ?x ?y "
                + "WHERE "
                + "{ "
                + "?x rdf:type ?y .}"
                + "ORDER BY ?y";

        System.out.println(queryString);  

        return OwlReaderUtil.executeQueryOTwoColumnPokusaj(getServletContext(), queryString); 
    }
    
    
    private List<List<String>> getTimesPropertyUsed() {
        System.out.println("getTimesPropertyUsed()");  
      
        String queryString = prefix
                + "SELECT ?x (COUNT(?x) AS ?y) "
                + "WHERE "
                + "{ "
                + "?s ?x ?o ."
                + "} "
                + "GROUP BY ?x";               

        System.out.println(queryString);  

        return OwlReaderUtil.executeQueryOTwoColumnLiteral(getServletContext(), queryString); 
    }
    
    private List<String> getOP() {
        
        String queryString = prefix
                + "SELECT ?x "
                + "WHERE "
                + "{ "
                + "?x rdf:type owl:ObjectProperty ."
                + " }";      
       
                      
        return OwlReaderUtil.executeQueryOneColumn(getServletContext(), queryString); 
    }  
    
    private List<List<String>> getStreamOP() {
        String queryString = prefix
            + "SELECT ?x ?y "
            + "WHERE "
            + "{ "
            + "?x <" + selectedObjectProperty + "> ?y ."
            + " }";      
      
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
         

        // Show data
        showDeviceByService(request);
        showStreamByService(request);
        showDP(request);
        showTimesPropertyUsed(request);
        showStreamDP(request);
        showOntologyFacts(request);
        showOP(request);
        showStreamOP(request);
      
        
        List<String> dp = getDP();
        request.setAttribute("dp", dp);
        
        List<String> op = getOP();
        request.setAttribute("op", op);
          
        List<List<String>> streamDP = getStreamDP();
        request.setAttribute("streamDP", streamDP);
        
        List<List<String>> streamOP = getStreamOP();
        request.setAttribute("streamOP", streamOP);
       
        
        request.getRequestDispatcher("data.jsp").forward(request, response);
    }
    
    
    private void showDeviceByService (HttpServletRequest request) {
        if((request.getParameter("quantityKinds")==null) && (request.getParameter("services")!=null) && (request.getParameter("units")==null) && (request.getParameter("streams")==null) && (request.getParameter("locations")==null) && (request.getParameter("sensors")==null)) { 
            selectedService = request.getParameter("services"); 
            List<String> deviceByService = getDeviceFromService();
            
            // Ispisivanje liste device-a prema Service-u
            for (int i = 0; i < deviceByService.size(); i++) {
                System.out.println(deviceByService.get(i));
            }
            request.setAttribute("deviceByService", deviceByService);
        }
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
    
    private void showOP (HttpServletRequest request) {                        
        System.out.println("dp: ");
        
        List<String> op = getOP();
              
         for (int i = 0; i < op.size(); i++) {
            System.out.println(op.get(i));
        }
        request.setAttribute("op", op);
    } 
    
    private void showStreamOP (HttpServletRequest request) {
        if(request.getParameter("op")!=null) {
            System.out.println("showStreamOP:");
             
            selectedObjectProperty = request.getParameter("op");
            System.out.println("Selected OP=" + selectedObjectProperty);


            List<List<String>> op = getStreamOP();;


            for (int i = 0; i < op.size(); i++) {
                for (int j = 0; j < op.get(i).size(); j++) {
                    System.out.println(op.get(i));
                } 
            }
            request.setAttribute("streamOP", op);
        }                    
    } 
    
    private void showTimesPropertyUsed (HttpServletRequest request) { 
        List<List<String>> timesPropertyUsed = getTimesPropertyUsed();

        for (int i = 0; i < timesPropertyUsed.size(); i++) {
            for (int j = 0; j < timesPropertyUsed.get(i).size(); j++) {
                System.out.println(timesPropertyUsed.get(i));
            } 
        }
        request.setAttribute("timesPropertyUsed", timesPropertyUsed);
   
    } 
    
    private void showOntologyFacts (HttpServletRequest request) {                        
        System.out.println("showOntologyFacts:");
              
        List<List<String>> ontologyFacts = getOntologyFacts();
        for (int i = 0; i < ontologyFacts.size(); i++) {
            for (int j = 0; j < ontologyFacts.get(i).size(); j++) {
                System.out.println(ontologyFacts.get(i));
            } 
        }
        request.setAttribute("ontologyFacts", ontologyFacts);
                      
    } 

    
}
    


