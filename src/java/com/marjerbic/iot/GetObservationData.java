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
@WebServlet(name = "GetObservationData", urlPatterns = {"/ObservationSearch"})
public class GetObservationData extends HttpServlet {
    
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
    String selectedResult;
    

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
    
    private List<String> getResults() {
        System.out.println("getResults");
               
        String queryString = prefix
                + "SELECT ?result "
                + "WHERE "
                + "{ "
                + "?result rdf:type sosa:Bad"
                + " }";
                    
        return OwlReaderUtil.executeQueryOneColumn(getServletContext(), queryString);
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
        
    
    private List<String> getStreams() {
        System.out.println("getStreams");
        
        
        String queryString = prefix
                + "SELECT ?stream "
                + "WHERE "
                + "{ "
                + "?stream rdf:type lite:IoTStream"
                + " }";
                     
        return OwlReaderUtil.executeQueryOneColumn(getServletContext(), queryString);
    }  
    
    
    
    private List<String> getObservationFromStream() {
        System.out.println("getObservationFromStream()");      
        
        String queryString = 
                
                prefix
                + "SELECT ?o "
                + "WHERE "
                + "{ "
                + "?o sosa:madeByStream <" + selectedStream + ">"
                + " }";      
       
        return OwlReaderUtil.executeQueryOneColumn(getServletContext(), queryString); 
    }
    
    private List<String> getObservationD() {
        
        String queryString = 
                
                prefix
                + "SELECT ?o "
                + "WHERE "
                + "{ "
                + "?o sosa:hasResult <" + selectedResult + "> . "
                + " }";      
       
        // dodati tu eventualno has result              
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

    
    private List<List<String>> getObservationDP() {
        
        String queryString = prefix
                + "SELECT ?x ?y "
                + "WHERE "
                + "{ "
                + "?x <" + selectedDP + "> ?y ."
                + " }";      
        queryString = String.format(queryString, "?p", "?o");
                      
        return OwlReaderUtil.executeQueryOTwoColumnLiteral(getServletContext(), queryString); 
    }  
    
    
    private List<List<String>> getObservationFacts() {
        
        String queryString = prefix
                + "SELECT ?x ?y "
                + "WHERE "
                + "{"
                + "?x <" + selectedObjectPropertyO + "> ?y ."               
                + "}";
        
       
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
        
        //Lista senzora2
        List<String> sensors2 = getSensors();
        request.setAttribute("sensors2", sensors2);
        //Lista streamova
        List<String> streams = getStreams();
        request.setAttribute("streams", streams);
        //Lista results
        List<String> results = getResults();
        request.setAttribute("results", results);
        
        List<String> dp = getDP();
        request.setAttribute("dp", dp);
        
        // Show data
        showObservationByStream(request);
        showDP(request);
        showStreamDP(request);
        showSObservationFacts(request);
        showResults(request);
        showObservationD(request);
        
    
         
        request.getRequestDispatcher("observation.jsp").forward(request, response);
    }
    
    
    
    
    private void showObservationByStream (HttpServletRequest request) {
    if((request.getParameter("streams")!=null)) { 
            selectedStream = request.getParameter("streams");
            List<String> observationByStream = getObservationFromStream();
            
            // Ispisivanje liste Observationa prema Stream-u
            for (int i = 0; i < observationByStream.size(); i++) {
                System.out.println(observationByStream.get(i));
            }
            request.setAttribute("observationByStream", observationByStream);
        }    
    }
    
    private void showResults (HttpServletRequest request) {                        
        
        List<String> results = getResults();
              
         for (int i = 0; i < results.size(); i++) {
            System.out.println(results.get(i));
        }
        request.setAttribute("results", results);
    } 
    
   
    private void showDP (HttpServletRequest request) {                        
        
        List<String> dp = getDP();
              
         for (int i = 0; i < dp.size(); i++) {
            System.out.println(dp.get(i));
        }
        request.setAttribute("dp", dp);
    } 
    
    private void showStreamDP (HttpServletRequest request) {                              
        if((request.getParameter("dp")!=null)) { 
            selectedDP = request.getParameter("dp");

            List<List<String>> dataProperties = getObservationDP();;


            for (int i = 0; i < dataProperties.size(); i++) {
                for (int j = 0; j < dataProperties.get(i).size(); j++) {
                    System.out.println(dataProperties.get(i));
                } 
            }
            request.setAttribute("observationDP", dataProperties);
        }              
    } 
     
    
    private void showSObservationFacts (HttpServletRequest request) { 
        if(request.getParameter("objectPropertyO")!=null) {
            selectedObjectPropertyO = request.getParameter("objectPropertyO"); 
            List<List<String>> observationFacts = getObservationFacts();

            for (int i = 0; i < observationFacts.size(); i++) {
                for (int j = 0; j < observationFacts.get(i).size(); j++) {
                    System.out.println(observationFacts.get(i));
                } 
            }
            request.setAttribute("observationFacts", observationFacts);
        }
        
    } 
    
    private void showObservationD (HttpServletRequest request) {
        if(request.getParameter("results")!=null) {
            selectedResult = request.getParameter("results");
            List<String> observationData = getObservationD();
            
            // Ispisivanje liste Observationa prema Stream-u
            for (int i = 0; i < observationData.size(); i++) {
                System.out.println(observationData.get(i));
            }
            request.setAttribute("observationData", observationData);
        }  
    }
    
}
    


