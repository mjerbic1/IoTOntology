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
@WebServlet(name = "GetSensorData", urlPatterns = {"/SensorSearch"})
public class GetSensorData extends HttpServlet {
    
    String selectedUnit;
    String selectedService;
    String selectedQuantity;
    String selectedStream;
    String selectedLocation;
    String selectedSensor;
    String selectedSensor2;
    

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
        System.out.println("getSensors");
               
        String queryString = prefix
                + "SELECT ?sensor "
                + "WHERE "
                + "{ "
                + "?sensor rdf:type ssn:Sensor"
                + " }";
                    
        return OwlReaderUtil.executeQueryOneColumn(getServletContext(), queryString);
    }
    
    private List<String> getUnits() {
        System.out.println("getUnits");
        
        
        String queryString = prefix
                + "SELECT ?units "
                + "WHERE "
                + "{ "
                + "?units rdf:type unit:Unit"
                + " }"; 
                      
        return OwlReaderUtil.executeQueryOneColumn(getServletContext(), queryString);
    }
    
    private List<String> getSensorFromUnit() {
        System.out.println("getSensorByUnit()");    
        
        String queryString = prefix
                + "SELECT ?sensor "
                + "WHERE "
                + "{ "
                + "?sensor lite:hasUnit <" +selectedUnit + ">"
                + " }";
        
       
                      
        return OwlReaderUtil.executeQueryOneColumn(getServletContext(), queryString);
    }
    
    private List<String> getQuantityKinds() {
        System.out.println("getQuantityKinds");
        
        
        String queryString = prefix
                + "SELECT ?qk "
                + "WHERE "
                + "{ "
                + "?qk rdf:type unit:QuantityKind"
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
    
    
    private List<String> getSensorFromQuantityKind() {
        System.out.println("getSensorFromQuantityKind()");      
        
        String queryString = prefix
                + "SELECT ?sensor "
                + "WHERE "
                + "{ "
                + "?sensor lite:hasQuantityKind <" + selectedQuantity + ">"
                + " }";
        
       
                      
        return OwlReaderUtil.executeQueryOneColumn(getServletContext(), queryString);
    }
    
    
    private List<List<String>> getSensorInfo() {
       
        String queryString = prefix
                + "SELECT DISTINCT ?x ?y "
                + "{"
                + "<" + selectedSensor2 + "> ?x ?y"               
                + "}";
        

        return OwlReaderUtil.executeQueryOTwoColumn(getServletContext(), queryString);
    }
         
    
    private List<List<String>> getSensorQuantityKinds() {
        System.out.println("ontologyFacts()");  
      
        String queryString = prefix
                + "SELECT ?x ?y "
                + "WHERE "
                + "{ "
                + "?x a ssn:Sensor ."
                + "?x lite:hasQuantityKind ?y"
                + "}"
                + "ORDER BY asc(UCASE(str(?s)))";

        System.out.println(queryString);  

        return OwlReaderUtil.executeQueryOTwoColumnLocalNames(getServletContext(), queryString); 
    }
    
    private List<List<String>> getSensorDevices() {
        System.out.println("getSensorDevices()");  
      
        String queryString = prefix
                + "SELECT ?x ?y "
                + "WHERE "
                + "{ "
                + "?x a ssn:Sensor ."
                + "?x lite:hasSensingDevice ?y"
                + "}"
                + "ORDER BY asc(UCASE(str(?s)))";

        System.out.println(queryString);  

        return OwlReaderUtil.executeQueryOTwoColumnLocalNames(getServletContext(), queryString); 
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
        //Lista senzora
        List<String> units = getUnits();
        request.setAttribute("units", units);
        //Lista Quantity Kinds
        List<String> quantityKinds = getQuantityKinds();
        request.setAttribute("quantityKinds", quantityKinds);
        //Lista Servisa
        List<String> services = getServices();
        request.setAttribute("services", services);
        //Lista Sttreamova
        List<String> streams = getStreams();
        request.setAttribute("streams", streams);      
        //Lista Lokacija
        List<String> locations = getLocations();
        request.setAttribute("locations", locations);
             

        // Show data
        showSensorByUnit(request);
        showSensorByQuantityKind(request);
        showBoobs(request);
        showSensorQuantityKinds(request);
        showSensorDevices(request);
        
        
        List<List<String>> sensorInfo = getSensorInfo();
        request.setAttribute("sensorInfo", sensorInfo);
        

        
        request.getRequestDispatcher("sensor.jsp").forward(request, response);
    }
    
    
    private void showSensorByUnit (HttpServletRequest request) {
        if((request.getParameter("quantityKinds")==null) && (request.getParameter("services")==null) && (request.getParameter("units")!=null) && (request.getParameter("streams")==null) && (request.getParameter("locations")==null) && (request.getParameter("sensors")==null)) { 
            selectedUnit = request.getParameter("units"); 
            List<String> sensorByUnit = getSensorFromUnit();
            
            // Ispisivanje liste senzota prema Unit-u
            for (int i = 0; i < sensorByUnit.size(); i++) {
                System.out.println(sensorByUnit.get(i));
            }
            request.setAttribute("sensorByUnit", sensorByUnit);
        }
    }    
    
    
    private void showSensorByQuantityKind (HttpServletRequest request) {
        if((request.getParameter("quantityKinds")!=null) && (request.getParameter("services")==null) && (request.getParameter("units")==null) && (request.getParameter("streams")==null) && (request.getParameter("locations")==null) && (request.getParameter("sensors")==null)) {  
            selectedQuantity = request.getParameter("quantityKinds"); 
            List<String> sensorByQuantityKind = getSensorFromQuantityKind();
            
            // Ispisivanje liste senzora prema Quantity Kind
            for (int i = 0; i < sensorByQuantityKind.size(); i++) {
                System.out.println(sensorByQuantityKind.get(i));
            }
            request.setAttribute("sensorByQuantityKind", sensorByQuantityKind);
        } 
    }
    
    
    private void showBoobs (HttpServletRequest request) { 
        if(request.getParameter("sensors2")!=null) {
            selectedSensor2 = request.getParameter("sensors2"); 
            List<List<String>> sensorInfo = getSensorInfo();

            for (int i = 0; i < sensorInfo.size(); i++) {
                for (int j = 0; j < sensorInfo.get(i).size(); j++) {
                    System.out.println(sensorInfo.get(i));
                } 
            }
            request.setAttribute("sensorInfo", sensorInfo);
        }
        
    } 
    
    
    private void showSensorQuantityKinds (HttpServletRequest request) {                        
        System.out.println("showSensorQuantityKinds:");
              
        List<List<String>> sensorQuantityKinds = getSensorQuantityKinds();
        for (int i = 0; i < sensorQuantityKinds.size(); i++) {
            for (int j = 0; j < sensorQuantityKinds.get(i).size(); j++) {
                System.out.println(sensorQuantityKinds.get(i));
            } 
        }
        request.setAttribute("sensorQuantityKinds", sensorQuantityKinds);
                      
    } 
    
    private void showSensorDevices (HttpServletRequest request) {                        
        System.out.println("showSensorDevices:");
              
        List<List<String>> sensorDevices = getSensorDevices();
        for (int i = 0; i < sensorDevices.size(); i++) {
            for (int j = 0; j < sensorDevices.get(i).size(); j++) {
                System.out.println(sensorDevices.get(i));
            } 
        }
        request.setAttribute("sensorDevices", sensorDevices);
                      
    } 
    
}
    


