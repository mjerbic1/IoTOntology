/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marjerbic.iot;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.json.JSONObject;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.OWL2;
import org.apache.jena.vocabulary.RDFS;


/**
 *
 * @author Marija
 */
@WebServlet(name = "GetOwlData", urlPatterns = {"/StandardSearch"})
public class GetOwlData extends HttpServlet {
    FileOutputStream f;
    File filename = new File("C:\\Users\\Marija\\Documents\\NetBeansProjects\\IoTOntology\\web\\resources\\kk.owl");
    
    // weather data
    String temperature;
    String humidity;
    String pressure;
    String speed;
    String degrees;
    String latitude = "";
    String longitude = "";
    String country;
    
    //  air quality data
    String measuringStation = "";
    String co = "";
    String h = "";
    String no2 = "";
    String p = "";
    String pm10;
    String so2 = "";
    String t = "";
    String w = "";
    String wg = "";
    
    String o3 = "";
    String pm25 = "";
    String uvi = "";
    
    String insertedLocation = "";
    String replaced = "";
    String insertedLat = "";
    String insertedLong = "";

    OntModel ontologyModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
    
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd_HH:mm:ss.SSS");  
    LocalDateTime now;  
    String vrijeme;
    String kraj;
    String timestampToSaveP;
    String timestampToSave;
    String pocetak;
    
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * Runs on a thread whenever there is HTTP GET request
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
        
        ontologyModel.read(request.getServletContext().getRealPath("/")+ "/resources/iot_ontology_rdf.owl", null, "RDF/XML");
        now = LocalDateTime.now();
        vrijeme = dtf.format(now);
        timestampToSaveP = Instant.now().toString();
        
        OntClass ClassDevice = ontologyModel.getOntClass("http://purl.oclc.org/NET/ssnx/ssn#Device");
        OntClass ClassSensingDevice = ontologyModel.getOntClass("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#SensingDevice");
        OntClass ClassCoverage = ontologyModel.getOntClass("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#Rectangle");
        OntClass ClassResultGood = ontologyModel.getOntClass("http://www.w3.org/ns/sosa#Good");
        OntClass ClassResultBad = ontologyModel.getOntClass("http://www.w3.org/ns/sosa#Bad");
        OntClass ClassResultModerate = ontologyModel.getOntClass("http://www.w3.org/ns/sosa#Moderate");
        OntClass ClassPoint = ontologyModel.getOntClass("http://www.w3.org/2003/01/geo/wgs84_pos#Point");
        
        ObjectProperty hasSensingDevice = ontologyModel.getObjectProperty("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#hasSensingDevice");
        ObjectProperty hasCoverage = ontologyModel.getObjectProperty("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#hasCoverage");
        ObjectProperty generatedBy = ontologyModel.getObjectProperty("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#generatedBy");
        ObjectProperty generates = ontologyModel.getObjectProperty("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#generates");
        ObjectProperty madeObservation = ontologyModel.getObjectProperty("http://www.w3.org/ns/sosa#madeObservation");
        Property simpleResult = ontologyModel.getProperty("http://www.w3.org/ns/sosa#simpleResult");
        ObjectProperty madeByStream = ontologyModel.getObjectProperty("http://www.w3.org/ns/sosa#madeByStream");
        ObjectProperty hasPoint = ontologyModel.getObjectProperty("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#hasPoint");
        ObjectProperty hasResult = ontologyModel.getObjectProperty("http://www.w3.org/ns/sosa#hasResult");
        ObjectProperty exposedBy = ontologyModel.getObjectProperty("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#exposedBy");
        ObjectProperty exposes = ontologyModel.getObjectProperty("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#exposes");
        ObjectProperty providedBy = ontologyModel.getObjectProperty("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#providedBy");
        Property windowEnd = ontologyModel.getProperty("http://www.w3.org/ns/sosa#windowEnd");
        Property windowStart = ontologyModel.getProperty("http://www.w3.org/ns/sosa#windowStart");
        Property resultTime = ontologyModel.getProperty("http://www.w3.org/ns/sosa#resultTime");
        Property relativeLocation = ontologyModel.getProperty("http://www.w3.org/2003/01/geo/wgs84_pos#relativeLocation");
        
        // Create IoTStream
        OntClass ClassStream = ontologyModel.getOntClass("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#IoTStream");            
        Individual i = ontologyModel.createIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#IotStream_" + vrijeme, ClassStream);
        i.addRDFType(OWL2.NamedIndividual);
          
                
        // Create Observation
        OntClass ClassObservation = ontologyModel.getOntClass("http://www.w3.org/ns/sosa#Observation");      
        Individual iO = ontologyModel.createIndividual("http://www.w3.org/ns/sosa#StrObservation_" + vrijeme, ClassObservation);     
        iO.addRDFType(OWL2.NamedIndividual);
        
        // Add data property windowStart observation       
        Individual iOb = ontologyModel.getIndividual("http://www.w3.org/ns/sosa#StrObservation_" + vrijeme);        
        ontologyModel.add(iOb, windowStart, ResourceFactory.createTypedLiteral(vrijeme, XSDDatatype.XSDstring));
        
        Individual iStream = ontologyModel.getIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#IotStream_" + vrijeme);
        
        // get data from service            
        getAirQualityDataByCity();
                                                                                                                                                                                                            
        if ("".equals(insertedLocation) || (insertedLocation == null)) {
            request.setAttribute("valueLoc", "/");           
        }       
        else {
            getWeatherDataByCity();
            request.setAttribute("valueLoc", insertedLocation);
                        
            // formating inserted location
            if ((insertedLocation != null) && (insertedLocation.contains(" "))) {
                replaced = insertedLocation.replace(" ", "_");
                insertedLocation = replaced;
            }
                   
            // create individual Point
            Individual i2 = ontologyModel.createIndividual("http://www.w3.org/2003/01/geo/wgs84_pos#"+insertedLocation, ClassPoint);
            i2.addRDFType(OWL2.NamedIndividual);
            
            // add object property IotStream -> hasLocation -> geo:Location
            ObjectProperty hasLocation = ontologyModel.getObjectProperty("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#hasLocation");
            ontologyModel.add(i,hasLocation,i2);
                      
            // create individual Coverage
            Individual iCoverage = ontologyModel.createIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#Building_" + insertedLocation, ClassCoverage);
            iCoverage.addRDFType(OWL2.NamedIndividual);
            
            Individual iC1 = ontologyModel.createIndividual("http://www.w3.org/2003/01/geo/wgs84_pos#" + insertedLocation + "_SWCornerBuilding", ClassPoint);
            iC1.addRDFType(OWL2.NamedIndividual);
            Individual iC2 = ontologyModel.createIndividual("http://www.w3.org/2003/01/geo/wgs84_pos#" + insertedLocation + "_EWCornerBuilding", ClassPoint);
            iC2.addRDFType(OWL2.NamedIndividual);
            
            // add object property Coverage -> hasPoint -> geo:Location
            ontologyModel.add(iCoverage,hasPoint,iC1);       
            ontologyModel.add(iCoverage,hasPoint,iC2);
        }
        
        if ((("".equals(insertedLat) || (insertedLat == null)) || ("".equals(insertedLong) || (insertedLong == null))) && (insertedLocation == null)) {
            request.setAttribute("valueLat", "/"); 
            request.setAttribute("valueLon", "/"); 
        } 
        else if ((("".equals(insertedLat) || (insertedLat == null)) || ("".equals(insertedLong) || (insertedLong == null))) && (insertedLocation != null)) {
            request.setAttribute("valueLat", "/"); 
            request.setAttribute("valueLon", "/"); 
        }
        else {
            getWeatherDataByCoordinates();
            request.setAttribute("valueLat", insertedLat);
            request.setAttribute("valueLon", insertedLong);
            
                
            // create individual
            Individual i2 = ontologyModel.createIndividual("http://www.w3.org/2003/01/geo/wgs84_pos#"+insertedLat + ";" + insertedLong, ClassPoint);
            i2.addRDFType(OWL2.NamedIndividual);
            
            // add object property IotStream -> hasLocation -> geo:Location
            ObjectProperty hasLocation = ontologyModel.getObjectProperty("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#hasLocation");
            ontologyModel.add(i,hasLocation,i2);
            
            // create individual Coverage
            Individual iCoverage = ontologyModel.createIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#Building_" + insertedLat + ";" + insertedLong, ClassCoverage);
            iCoverage.addRDFType(OWL2.NamedIndividual);
            
            Individual iC1 = ontologyModel.createIndividual("http://www.w3.org/2003/01/geo/wgs84_pos#" + insertedLat + ";" + insertedLong + "_SWCornerBuilding", ClassPoint);
            iC1.addRDFType(OWL2.NamedIndividual);
            Individual iC2 = ontologyModel.createIndividual("http://www.w3.org/2003/01/geo/wgs84_pos#" + insertedLat + ";" + insertedLong + "_EWCornerBuilding", ClassPoint);
            iC2.addRDFType(OWL2.NamedIndividual);
            
            // add object property Coverage -> hasPoint -> geo:Location
            ontologyModel.add(iCoverage,hasPoint,iC1);       
            ontologyModel.add(iCoverage,hasPoint,iC2);
        }
        
        
        if (("".equals(latitude) || (latitude == null)) &&(insertedLocation == null)) {
            request.setAttribute("valueLat", "/");
        }
        else {
            request.setAttribute("valueLat", latitude);
                
            Individual i3; 
            Individual iC1;  
            Individual iC2;
            Property pLat = ontologyModel.getProperty("http://www.w3.org/2003/01/geo/wgs84_pos#lat");
            
           
            if (insertedLocation == null) {
                i3 = ontologyModel.getIndividual("http://www.w3.org/2003/01/geo/wgs84_pos#" + insertedLat + ";" + insertedLong);
                iC1 = ontologyModel.getIndividual("http://www.w3.org/2003/01/geo/wgs84_pos#" + insertedLat + ";" + insertedLong + "_SWCornerBuilding");
                ontologyModel.add(iC1, pLat, ResourceFactory.createTypedLiteral(latitude, XSDDatatype.XSDstring));  
                iC2 = ontologyModel.getIndividual("http://www.w3.org/2003/01/geo/wgs84_pos#" + insertedLat + ";" + insertedLong + "_EWCornerBuilding");
                ontologyModel.add(iC2, pLat, ResourceFactory.createTypedLiteral(latitude, XSDDatatype.XSDstring));
                /////////////////////////////////////////////////
                // setting coverage properties

                //double d = Double.parseDouble(latitude);        
            }
            else {
                i3 = ontologyModel.getIndividual("http://www.w3.org/2003/01/geo/wgs84_pos#"+insertedLocation);
                
                iC1 = ontologyModel.getIndividual("http://www.w3.org/2003/01/geo/wgs84_pos#" + insertedLocation + "_SWCornerBuilding");
                iC2 = ontologyModel.getIndividual("http://www.w3.org/2003/01/geo/wgs84_pos#" + insertedLocation + "_EWCornerBuilding");
                
                /////////////////////////////////////////////////
                // setting coverage properties

                //double d = Double.parseDouble(latitude);

            }
            
            // Add data property to individual
            ontologyModel.add(i3, pLat, ResourceFactory.createTypedLiteral(latitude, XSDDatatype.XSDstring)); 
            ontologyModel.add(i3, relativeLocation, ResourceFactory.createTypedLiteral("City", XSDDatatype.XSDstring)); 
            ontologyModel.add(iC1, pLat, ResourceFactory.createTypedLiteral(latitude, XSDDatatype.XSDstring));
            ontologyModel.add(iC2, pLat, ResourceFactory.createTypedLiteral(latitude, XSDDatatype.XSDstring));
                     
        }
        if (("".equals(longitude) || (longitude == null)) && (insertedLocation == null)) {
            request.setAttribute("valueLon", "/");       
        }
        else {
            request.setAttribute("valueLon", longitude);
            
            Individual i3;  
            Individual iC1;  
            Individual iC2;
            Property pLon = ontologyModel.getProperty("http://www.w3.org/2003/01/geo/wgs84_pos#lon");
           
            if (insertedLocation == null) {
                i3 = ontologyModel.getIndividual("http://www.w3.org/2003/01/geo/wgs84_pos#" + insertedLat + ";" + insertedLong);
                
                iC1 = ontologyModel.getIndividual("http://www.w3.org/2003/01/geo/wgs84_pos#" + insertedLat + ";" + insertedLong + "_SWCornerBuilding");
                iC2 = ontologyModel.getIndividual("http://www.w3.org/2003/01/geo/wgs84_pos#" + insertedLat + ";" + insertedLong + "_EWCornerBuilding");
            }
            else {
                i3 = ontologyModel.getIndividual("http://www.w3.org/2003/01/geo/wgs84_pos#"+insertedLocation);
                
                iC1 = ontologyModel.getIndividual("http://www.w3.org/2003/01/geo/wgs84_pos#" + insertedLocation + "_SWCornerBuilding");               
                iC2 = ontologyModel.getIndividual("http://www.w3.org/2003/01/geo/wgs84_pos#" + insertedLocation + "_EWCornerBuilding");
                
            }
            
            // Add data property to individual
            ontologyModel.add(i3, pLon, ResourceFactory.createTypedLiteral(longitude, XSDDatatype.XSDstring));  
            ontologyModel.add(iC1, pLon, ResourceFactory.createTypedLiteral(longitude, XSDDatatype.XSDstring));        
            ontologyModel.add(iC2, pLon, ResourceFactory.createTypedLiteral(longitude, XSDDatatype.XSDstring));
        }
        
        
        Individual iCoverage;
        if (insertedLocation == null) {
            iCoverage = ontologyModel.createIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#Building_" + insertedLat + ";" + insertedLong, ClassCoverage);
        }
        else {
            iCoverage = ontologyModel.createIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#Building_" + insertedLocation, ClassCoverage);
            
        }
        
        
        if ("".equals(temperature) || (temperature == null)) {
            request.setAttribute("valueTemperature", "/");
        }
        else {
            request.setAttribute("valueTemperature", temperature);
            
            // Create SensingDevice
            Individual iSensingDevice = ontologyModel.createIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#MAX31855", ClassSensingDevice);
            Individual iSensor = ontologyModel.getIndividual("http://purl.oclc.org/NET/ssnx/ssn#TemperatureSensor");                    
            Individual iService = ontologyModel.getIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#OpenWeatherMapService");

           
            // Add data property to individual
            Property pT = ontologyModel.getProperty("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#Temperature");               
            ontologyModel.add(iStream, pT, ResourceFactory.createTypedLiteral(temperature, XSDDatatype.XSDdecimal));
            
            addToOntology (iSensingDevice, iService, iSensor, iStream, iOb, exposedBy, exposes, hasSensingDevice, generatedBy, generates, madeByStream, madeObservation, hasCoverage, iCoverage);
        }
        
        
        if ("".equals(humidity) || (humidity == null)) {
            request.setAttribute("valueHumidity", "/");
        }
        else {
            request.setAttribute("valueHumidity", humidity);
            
            // Create SensingDevice
            Individual iSensingDevice = ontologyModel.createIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#HS11O1LF_OEM_Humidity", ClassSensingDevice);
            Individual iService = ontologyModel.getIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#OpenWeatherMapService");            
            Individual iSensor = ontologyModel.getIndividual("http://purl.oclc.org/NET/ssnx/ssn#HumiditySensor");
            
         
            // Add data property to individual
            Property pHumidity = ontologyModel.getProperty("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#Humidity");            
            ontologyModel.add(iStream, pHumidity, ResourceFactory.createTypedLiteral(humidity, XSDDatatype.XSDdecimal));
            
            addToOntology (iSensingDevice, iService, iSensor, iStream, iOb, exposedBy, exposes, hasSensingDevice, generatedBy, generates, madeByStream, madeObservation, hasCoverage, iCoverage);
        }
        
        /////////////////////////////////////////////
        
        if ("".equals(pressure) || (pressure == null)) {
            request.setAttribute("valuePressure", "/");
        }
        else {
            request.setAttribute("valuePressure", pressure);  
                        
            Individual iSensingDevice = ontologyModel.createIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#GaugePressureSensor", ClassSensingDevice);            
            Individual iSensor = ontologyModel.getIndividual("http://purl.oclc.org/NET/ssnx/ssn#PressureSensor");
            Individual iService = ontologyModel.getIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#OpenWeatherMapService");
            
            
            // Add data property to individual
            Property pPressure = ontologyModel.getProperty("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#P");            
            ontologyModel.add(iStream, pPressure, ResourceFactory.createTypedLiteral(pressure, XSDDatatype.XSDdecimal));
            
            addToOntology (iSensingDevice, iService, iSensor, iStream, iOb, exposedBy, exposes, hasSensingDevice, generatedBy, generates, madeByStream, madeObservation, hasCoverage, iCoverage);
        }
        /////////////////////////////////////////////
        if ("".equals(co) || (co == null)) {
            request.setAttribute("valueco", "/");
        }
        else {
            request.setAttribute("valueco", co);
            
            Individual iSensingDevice = ontologyModel.createIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#CarbonOxideSensor", ClassSensingDevice);            
            Individual iSensor = ontologyModel.getIndividual("http://purl.oclc.org/NET/ssnx/ssn#GasSensor");
            Individual iService = ontologyModel.getIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#AQIService");
            
            // Add data property to individual
            Property pCO = ontologyModel.getProperty("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#CO");            
            ontologyModel.add(iStream, pCO, ResourceFactory.createTypedLiteral(co, XSDDatatype.XSDdecimal)); 
            
            addToOntology (iSensingDevice, iService, iSensor, iStream, iOb, exposedBy, exposes, hasSensingDevice, generatedBy, generates, madeByStream, madeObservation, hasCoverage, iCoverage);

        }
        /////////////////////////////////////////////
        if ("".equals(h) || (h == null)) {
            request.setAttribute("valueh", "/");
        }
        else {
            request.setAttribute("valueh", h);        
            
            // Create Device
            Individual iSensingDevice = ontologyModel.createIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#ORBISPHERE_312xx", ClassSensingDevice);            
            Individual iSensor = ontologyModel.getIndividual("http://purl.oclc.org/NET/ssnx/ssn#GasSensor");
            Individual iService = ontologyModel.getIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#AQIService");
            
            
            // Add data property to individual
            Property pH = ontologyModel.getProperty("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#H");            
            ontologyModel.add(iStream, pH, ResourceFactory.createTypedLiteral(h, XSDDatatype.XSDdecimal));
            
            addToOntology (iSensingDevice, iService, iSensor, iStream, iOb, exposedBy, exposes, hasSensingDevice, generatedBy, generates, madeByStream, madeObservation, hasCoverage, iCoverage);
            
        }
        /////////////////////////////////////////////
        if ("".equals(no2) || (no2 == null)) {
            request.setAttribute("valueno2", "/");
        }
        else {
            request.setAttribute("valueno2", no2);
  
            Individual iSensingDevice = ontologyModel.createIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#AlphasenseB42F", ClassSensingDevice);                                     
            Individual iService = ontologyModel.getIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#AQIService");
            Individual iSensor = ontologyModel.getIndividual("http://purl.oclc.org/NET/ssnx/ssn#GasSensor");
            
            
            // Add data property to individual
            Property pNO2 = ontologyModel.getProperty("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#NO2");            
            ontologyModel.add(iStream, pNO2, ResourceFactory.createTypedLiteral(no2, XSDDatatype.XSDdecimal)); 
            
            addToOntology (iSensingDevice, iService, iSensor, iStream, iOb, exposedBy, exposes, hasSensingDevice, generatedBy, generates, madeByStream, madeObservation, hasCoverage, iCoverage);
        }
        
        /////////////////////////////////////////////
        if ("".equals(pm10) || (pm10 == null)) {
            request.setAttribute("valuepm10", "/");
        }
        else {
            request.setAttribute("valuepm10", pm10);
                       
            Individual iSensingDevice = ontologyModel.createIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#HoneyWell_HPMA_115", ClassSensingDevice);
            Individual iService = ontologyModel.getIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#AQIService");
            Individual iSensor = ontologyModel.getIndividual("http://purl.oclc.org/NET/ssnx/ssn#ParticalSensor");
        
            
            // Add data property to individual
            Property pPM10 = ontologyModel.getProperty("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#PM10");            
            ontologyModel.add(iStream, pPM10, ResourceFactory.createTypedLiteral(pm10, XSDDatatype.XSDdecimal)); 
            
            addToOntology (iSensingDevice, iService, iSensor, iStream, iOb, exposedBy, exposes, hasSensingDevice, generatedBy, generates, madeByStream, madeObservation, hasCoverage, iCoverage);
        }
        /////////////////////////////////////////////
        if ("".equals(pm25) || (pm25 == null)) {
            request.setAttribute("valuepm25", "/");
            //add result o3 if pm25 is null
            
            double d;
            int broj = 0;
            
            System.out.println("OVO JE O3 = " + o3);
            if (("".equals(o3)) || (o3 == null)) {
                d = Double.parseDouble(no2);
                broj = (int) d;
            }
            else {
                d = Double.parseDouble(o3);
                broj = (int) d;
            }
            
            
            Individual iResult;
            if (broj <= 50) {
                iResult = ontologyModel.createIndividual("http://www.w3.org/ns/sosa#Result_" + vrijeme + "_" + broj, ClassResultGood);
            }
            else if (broj > 100) {
                iResult = ontologyModel.createIndividual("http://www.w3.org/ns/sosa#Result_" + vrijeme + "_" + broj, ClassResultBad);
            }
            else {
                iResult = ontologyModel.createIndividual("http://www.w3.org/ns/sosa#Result_" + vrijeme + "_" + broj, ClassResultModerate);
            }    
            
            ontologyModel.add(iResult, simpleResult, ResourceFactory.createTypedLiteral(String.valueOf(broj), XSDDatatype.XSDinteger));
            ontologyModel.add(iOb, hasResult,iResult);
        }
        else {
            request.setAttribute("valuepm25", pm25);
            
            // Create Device
            Individual iSensingDevice = ontologyModel.createIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#Plantower_PMS_7003", ClassSensingDevice);
            Individual iService = ontologyModel.getIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#AQIService");
            Individual iSensor = ontologyModel.getIndividual("http://purl.oclc.org/NET/ssnx/ssn#ParticalSensor");
            
            
            // Add data property to individual
            Property pPM25 = ontologyModel.getProperty("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#PM25");            
            ontologyModel.add(iStream, pPM25, ResourceFactory.createTypedLiteral(pm25, XSDDatatype.XSDdecimal)); 
            
            addToOntology (iSensingDevice, iService, iSensor, iStream, iOb, exposedBy, exposes, hasSensingDevice, generatedBy, generates, madeByStream, madeObservation, hasCoverage, iCoverage);
            
            
            double d = Double.parseDouble(pm25);
            int broj = (int) d;
            
            Individual iResult;
            if (broj <= 50) {
                iResult = ontologyModel.createIndividual("http://www.w3.org/ns/sosa#Result_" + vrijeme + "_" + broj, ClassResultGood);
            }
            else if (broj > 100) {
                iResult = ontologyModel.createIndividual("http://www.w3.org/ns/sosa#Result_" + vrijeme + "_" + broj, ClassResultBad);
            }
            else {
                iResult = ontologyModel.createIndividual("http://www.w3.org/ns/sosa#Result_" + vrijeme + "_" + broj, ClassResultModerate);
            }    
            
            ontologyModel.add(iResult, simpleResult, ResourceFactory.createTypedLiteral(String.valueOf(broj), XSDDatatype.XSDinteger));
            ontologyModel.add(iOb, hasResult,iResult);
            
        }
        /////////////////////////////////////////////
        if ("".equals(so2) || (so2 == null)) {
            request.setAttribute("valueso2", "/");
        }    
        else {
            request.setAttribute("valueso2", so2);
            
            Individual iSensingDevice = ontologyModel.createIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#PT395", ClassSensingDevice);
            Individual iService = ontologyModel.getIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#AQIService");
            Individual iSensor = ontologyModel.getIndividual("http://purl.oclc.org/NET/ssnx/ssn#GasSensor");
            
            
            // Add data property to individual
            Property pSO2 = ontologyModel.getProperty("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#SO2");            
            ontologyModel.add(iStream, pSO2, ResourceFactory.createTypedLiteral(so2, XSDDatatype.XSDdecimal)); 
            
            addToOntology (iSensingDevice, iService, iSensor, iStream, iOb, exposedBy, exposes, hasSensingDevice, generatedBy, generates, madeByStream, madeObservation, hasCoverage, iCoverage);    
        }
        /////////////////////////////////////////////
        if ("".equals(t) || (t == null)) {
            request.setAttribute("valuet", "/");
        }     
        else {
            request.setAttribute("valuet", t);
            
            // Add data property to individual
            Property pT = ontologyModel.getProperty("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#T");            
            ontologyModel.add(iStream, pT, ResourceFactory.createTypedLiteral(t, XSDDatatype.XSDdecimal));  
            
            ////// ............................. /////// ovdje fali
        }
        /////////////////////////////////////////////
        if ("".equals(w) || (w == null)) {
            request.setAttribute("valuew", "/");
        }      
        else {
            request.setAttribute("valuew", w);
            
            // Add data property to individual
            Property pW = ontologyModel.getProperty("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#W");            
            ontologyModel.add(iStream, pW, ResourceFactory.createTypedLiteral(w, XSDDatatype.XSDdecimal));     
        }
        /////////////////////////////////////////////
        if ("".equals(wg) || (wg == null)) {
            request.setAttribute("valuewg", "/");
        }
        else {
            request.setAttribute("valuewg", wg);
            
            // Create SensingDevice
            Individual iSensingDevice = ontologyModel.createIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#PST3915", ClassSensingDevice);
            Individual iService = ontologyModel.getIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#AQIService");
            Individual iSensor = ontologyModel.getIndividual("http://purl.oclc.org/NET/ssnx/ssn#WaterGaugeSensor");
            
            // Add data property to individual
            Property pWG = ontologyModel.getProperty("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#WG");            
            ontologyModel.add(iStream, pWG, ResourceFactory.createTypedLiteral(wg, XSDDatatype.XSDdecimal)); 
            
            addToOntology (iSensingDevice, iService, iSensor, iStream, iOb, exposedBy, exposes, hasSensingDevice, generatedBy, generates, madeByStream, madeObservation, hasCoverage, iCoverage);
        }
        /////////////////////////////////////////////
        if ("".equals(o3) || (o3 == null)) {
            request.setAttribute("valueo3", "/");
        }
        else {
            request.setAttribute("valueo3", o3);
            
            // Create SensingDevice
            Individual iSensingDevice = ontologyModel.createIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#PT395", ClassSensingDevice);
            Individual iService = ontologyModel.getIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#AQIService");
            Individual iSensor = ontologyModel.getIndividual("http://purl.oclc.org/NET/ssnx/ssn#GasSensor");
            
            
            // Add data property to individual
            Property pO3 = ontologyModel.getProperty("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#O3");            
            ontologyModel.add(iStream, pO3, ResourceFactory.createTypedLiteral(o3, XSDDatatype.XSDdecimal)); 
            
            addToOntology (iSensingDevice, iService, iSensor, iStream, iOb, exposedBy, exposes, hasSensingDevice, generatedBy, generates, madeByStream, madeObservation, hasCoverage, iCoverage);  
        }
        /////////////////////////////////////////////
        if ("".equals(uvi) || (uvi == null)) {
            request.setAttribute("valueuvi", "/");
        }
        else {
            request.setAttribute("valueuvi", uvi);
            
            // Create SensingDevice
            Individual iSensingDevice = ontologyModel.createIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#SKU440_UVI", ClassSensingDevice);
            Individual iService = ontologyModel.getIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#AQIService");
            Individual iSensor = ontologyModel.getIndividual("http://purl.oclc.org/NET/ssnx/ssn#RadiationSensor");
            
                        
            // Add data property to individual
            Property pUVI = ontologyModel.getProperty("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#UVI");            
            ontologyModel.add(iStream, pUVI, ResourceFactory.createTypedLiteral(uvi, XSDDatatype.XSDdecimal)); 
            
            addToOntology (iSensingDevice, iService, iSensor, iStream, iOb, exposedBy, exposes, hasSensingDevice, generatedBy, generates, madeByStream, madeObservation, hasCoverage, iCoverage);
        }
        
        // Add data property Observation->windowEnd       
        now = LocalDateTime.now();
        kraj = dtf.format(now);
        
        ontologyModel.add(iOb, windowEnd, ResourceFactory.createTypedLiteral(kraj, XSDDatatype.XSDstring));
        
        // calculate resultTime
        timestampToSave = Instant.now().toString();        
        int diffInMills = (Duration.between(Instant.parse(timestampToSaveP), Instant.parse(timestampToSave)).getNano())/1000000;
        ontologyModel.add(iOb, resultTime, ResourceFactory.createTypedLiteral(String.valueOf(diffInMills), XSDDatatype.XSDinteger));
        
        // Ispis modela
        f = new FileOutputStream(filename, false);
        ontologyModel.write(f);
        f.close();
        
        
        // Pollutant info
        String no2desc = "Comes from vehicles, power plants, industrial emissions and off-road sources such as construction, lawn and gardening equipment. All of these sources burn fossil fuels.";
        request.setAttribute("valueno2desc", no2desc);
        String o3desc = "Forms when two types of pollutants (VOCs and NOx) react in sunlight. These pollutants come from sources such as vehicles, industries, power plants, and products such as solvents and paints.";
        request.setAttribute("valueo3desc", o3desc);
        String pm10desc = "Comes from many different types of sources. PM10 comes from crushing and grinding operations, road dust, and some agricultural operations.";
        request.setAttribute("valuepm10desc", pm10desc);
        String pm25desc = "Comes from many different types of sources. PM2.5 µm include power plants, industrial processes, vehicle tailpipes, wood stoves, and wildfires.";
        request.setAttribute("valuepm25desc", pm25desc);
                
        request.getRequestDispatcher("page.jsp").forward(request, response);
    }
    
    
    
    public static Map <String, Object> jsonToMap (String str) {
        Map<String, Object> map = new Gson().fromJson (str, new TypeToken<HashMap<String, Object>>() {}.getType());
        return map;
    }
    
    private void getWeatherDataByCity() {
        String API_KEY = "152829dfff72cb3dd9c61bf7ede643e3";
        String location = "";
        String urlString = "";
        
        if ((insertedLocation == null) || (insertedLocation == "")) {
            urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + location + "&appid=" + API_KEY + "&units=metric";
        }        
        else {
            urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + insertedLocation + "&appid=" + API_KEY + "&units=metric";
            
        }
                        
        // Add new service      
        OntClass ClassService = ontologyModel.getOntClass("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#Service");
        Individual i = ontologyModel.createIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#OpenWeatherMapService", ClassService);
        i.addRDFType(OWL2.NamedIndividual);
        
        // Add data property
        Individual iService = ontologyModel.getIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#OpenWeatherMapService");
        Property pEndpoint = ontologyModel.getProperty("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#endpoint");
        ontologyModel.add(iService, pEndpoint, ResourceFactory.createTypedLiteral(urlString, XSDDatatype.XSDstring));
        Property pInterface = ontologyModel.getProperty("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#interfaceType");
        ontologyModel.add(iService, pInterface, ResourceFactory.createTypedLiteral("REST", XSDDatatype.XSDstring));
        
        // Add object property providedBy    
        Individual iStream = ontologyModel.getIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#IotStream_" + vrijeme);        
        ObjectProperty providedBy = ontologyModel.getObjectProperty("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#providedBy");
        ontologyModel.add(iStream,providedBy,iService);
                   
        
        try {
            // created so I could keep receiving data
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlString);
            //opens connection to openweather
            URLConnection conn = url.openConnection();
            // starts reading data
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            //Ispisujemo API response
            System.out.println("Result Weather = " + result);
            
            // since we've declared it in string, we need to convert to JSON
            Map<String, Object> respMap = jsonToMap(result.toString());
            System.out.println("respMap: " + respMap);
            Map<String, Object> mainMap = jsonToMap(respMap.get("main").toString());
            Map<String, Object> windMap = jsonToMap(respMap.get("wind").toString());
            Map<String, Object> coordMap = jsonToMap(respMap.get("coord").toString());
            Map<String, Object> sysMap = jsonToMap(respMap.get("sys").toString());
            
            temperature = mainMap.get("temp").toString();
            pressure =  mainMap.get("pressure").toString();
            humidity =  mainMap.get("humidity").toString();
            speed = windMap.get("speed").toString();
            degrees = windMap.get("deg").toString();
            latitude = coordMap.get("lat").toString();
            longitude = coordMap.get("lon").toString();
            country = sysMap.get("country").toString();
            
            //System.out.println("Current Weather: " + weatherMap.get("main"));
            System.out.println("Current Temperature: " + temperature);
            System.out.println("Current Pressure: " + pressure);
            System.out.println("Current Humidity: " + humidity);
            System.out.println("Wind speeds: " + speed);
            System.out.println("Wind Angle: " + degrees);
            System.out.println("Latitude: " + latitude);
            System.out.println("Longitude: " + longitude);
            System.out.println("Country: " + country);
        } catch (IOException e) {
            System.out.println(e.getMessage() + " ovo je u catch");
        }
    }
    
    private void getWeatherDataByCoordinates() {
        String API_KEY = "152829dfff72cb3dd9c61bf7ede643e3";
        String location = "";
        String urlString = "";
        
        if ((insertedLat == null) && (insertedLong == null)) {
            urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + location + "&appid=" + API_KEY + "&units=metric";
        }   
        else {
            urlString = "https://api.openweathermap.org/data/2.5/weather?lat=" + insertedLat + "&lon=" + insertedLong + "&appid=" + API_KEY + "&units=metric";
        }      
        
                
        // Add new service        
        OntClass ClassService = ontologyModel.getOntClass("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#Service");
        Individual i = ontologyModel.createIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#OpenWeatherMapService", ClassService);
        i.addProperty(RDFS.comment, "Added through program");
        i.addRDFType(OWL2.NamedIndividual);
        
        // Add data property
        Individual iService = ontologyModel.getIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#OpenWeatherMapService");
        Property pEndpoint = ontologyModel.getProperty("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#endpoint");
        ontologyModel.add(iService, pEndpoint, ResourceFactory.createTypedLiteral(urlString, XSDDatatype.XSDstring));
        Property pInterface = ontologyModel.getProperty("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#interfaceType");
        ontologyModel.add(iService, pInterface, ResourceFactory.createTypedLiteral("REST", XSDDatatype.XSDstring));
        
        // Add object property providedBy    
        Individual iStream = ontologyModel.getIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#IotStream_" + vrijeme);        
        ObjectProperty providedBy = ontologyModel.getObjectProperty("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#providedBy");
        ontologyModel.add(iStream,providedBy,iService);
        
        
        try {
            // created so I could keep receiving data
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlString);
            //opens connection to openweather
            URLConnection conn = url.openConnection();
            // starts reading data
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            //Ispisujemo API response
            System.out.println("Result Weather = " + result);
            
            // since we've declared it in string, we need to convert to JSON
            Map<String, Object> respMap = jsonToMap(result.toString());
            System.out.println("respMap: " + respMap);
            Map<String, Object> mainMap = jsonToMap(respMap.get("main").toString());
            Map<String, Object> windMap = jsonToMap(respMap.get("wind").toString());
            Map<String, Object> coordMap = jsonToMap(respMap.get("coord").toString());
            Map<String, Object> sysMap = jsonToMap(respMap.get("sys").toString());
            
            temperature = mainMap.get("temp").toString();
            pressure =  mainMap.get("pressure").toString();
            humidity =  mainMap.get("humidity").toString();
            speed = windMap.get("speed").toString();
            degrees = windMap.get("deg").toString();
            latitude = coordMap.get("lat").toString();
            longitude = coordMap.get("lon").toString();
            country = sysMap.get("country").toString();
            
            //System.out.println("Current Weather: " + weatherMap.get("main"));
            System.out.println("Current Temperature: " + temperature);
            System.out.println("Current Pressure: " + pressure);
            System.out.println("Current Humidity: " + humidity);
            System.out.println("Wind speeds: " + speed);
            System.out.println("Wind Angle: " + degrees);
            System.out.println("Latitude: " + latitude);
            System.out.println("Longitude: " + longitude);
            System.out.println("Country: " + country);
        } catch (IOException e) {
            System.out.println(e.getMessage() + " ovo je u catch");
        }
    }
   
    
    
    private void getAirQualityDataByCity() {
        String API_KEY_AQ = "e469280810fd0c227a37859c86d1ca1483d48ef2";
        //String API_KEY_AQ2 = "b339202fb010b5cf88d378e12f6c3bf486338d71";
        String location_AQ = "Velika Gorica";
        String urlStringAQ = "";
        
        System.out.println("Inserted Location: " + insertedLocation);
        
        if ((insertedLocation == null) && ((insertedLat == null) && (insertedLong == null))) {
            urlStringAQ = "https://api.waqi.info/feed/" + location_AQ + "/?token=" + API_KEY_AQ;
        } 
        else if ((insertedLocation == null) && (insertedLat != null) && (insertedLong != null)) {
            urlStringAQ = "https://api.waqi.info/feed/geo:" + insertedLat + ";" + insertedLong + "/?token=" + API_KEY_AQ;
        }
        else {
            urlStringAQ = "https://api.waqi.info/feed/" + insertedLocation + "/?token=" + API_KEY_AQ;
        }
       
        
        OntClass ClassService = ontologyModel.getOntClass("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#Service");
        Individual i = ontologyModel.createIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#AQIService", ClassService);
        i.addRDFType(OWL2.NamedIndividual);
        
        // Add data property
        Individual iService = ontologyModel.getIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#AQIService");
        Property pEndpoint = ontologyModel.getProperty("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#endpoint");
        ontologyModel.add(iService, pEndpoint, ResourceFactory.createTypedLiteral(urlStringAQ, XSDDatatype.XSDstring));
        Property pInterface = ontologyModel.getProperty("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#interfaceType");
        ontologyModel.add(iService, pInterface, ResourceFactory.createTypedLiteral("REST", XSDDatatype.XSDstring));
        

        // Add object property providedBy    
        Individual iStream = ontologyModel.getIndividual("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#IotStream_" + vrijeme);
        ObjectProperty providedBy = ontologyModel.getObjectProperty("http://purl.oclc.org/NET/UNIS/fiware/iot-lite#providedBy");
        ontologyModel.add(iStream,providedBy,iService);
        
        try {
            
            // keep receiving data
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlStringAQ);
            // opens connection to AQI service
            URLConnection conn = url.openConnection();
            // starts reading data
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            String res = result.toString();
            //Ispisujemo API response
            System.out.println("Result AQI= " + result);
            
                      
            
            JSONObject element = new JSONObject(res);
            String token = element.getJSONObject("data").toString();
                        
            Map<String, Object> respMap = jsonToMap(token);
            System.out.println("respMap: " + respMap.toString());
            //Map<String, Object> weatherMap = jsonToMap(respMap.get("description").toString());
            Map<String, Object> mainMap = jsonToMap(respMap.get("iaqi").toString());
            System.out.println("mainMap: " + mainMap.toString());
            
        
            /////////////////////////////////////////////
            if (mainMap.get("co") == null) {
                co = "";
            }
            else {
                Map<String, Object> coMap = jsonToMap(mainMap.get("co").toString());
                co = coMap.toString().replaceAll("[^0-9.]","");
                System.out.println("co: " + co);
            }    
            /////////////////////////////////////////////
            if (mainMap.get("h") == null) {
                h = "";
            }
            else {
                Map<String, Object> hMap = jsonToMap(mainMap.get("h").toString());
                h = hMap.toString().replaceAll("[^0-9.]","");
                System.out.println("h: " + h);
            }           
            /////////////////////////////////////////////
            if (mainMap.get("no2") == null) {
                no2 = "";
            }
            else {
                Map<String, Object> no2Map = jsonToMap(mainMap.get("no2").toString());
                no2 = no2Map.toString().replaceAll("[^0-9.]","");
                System.out.println("no2: " + no2);
            }
            /////////////////////////////////////////////
            if (mainMap.get("p") == null) {
                p = "";
            }
            else {
                Map<String, Object> pMap = jsonToMap(mainMap.get("p").toString());
                p = pMap.toString().replaceAll("[^0-9.]","");
                System.out.println("p: " + p);
            }
            /////////////////////////////////////////////
            if (mainMap.get("pm10") == null) {
                pm10 = "";
            }
            else {
                Map<String, Object> pm10Map = jsonToMap(mainMap.get("pm10").toString());
                pm10 = pm10Map.toString().replaceAll("[^0-9.]","");
                System.out.println("pm10: " + pm10);
            }
            /////////////////////////////////////////////
            if (mainMap.get("pm25") == null) {
                pm25 = "";
            }
            else {
                Map<String, Object> pm25Map = jsonToMap(mainMap.get("pm25").toString());
                pm25 = pm25Map.toString().replaceAll("[^0-9.]","");
                System.out.println("pm25: " + pm25);
            }
         
            /////////////////////////////////////////////
            if (mainMap.get("so2") == null) {
                so2 = "";
            }
            else {
                Map<String, Object> so2Map = jsonToMap(mainMap.get("so2").toString());
                so2 = so2Map.toString().replaceAll("[^0-9.]","");
                System.out.println("so2: " + so2);
            }
            /////////////////////////////////////////////
            if (mainMap.get("t") == null) {
                t = "";
            }
            else {
                Map<String, Object> tMap = jsonToMap(mainMap.get("t").toString());
                t = tMap.toString().replaceAll("[^0-9.]","");
                System.out.println("t: " + t);
            }  
            /////////////////////////////////////////////
            if (mainMap.get("w") == null) {
                w = "";
            }
            else {
                Map<String, Object> wMap = jsonToMap(mainMap.get("w").toString());
                w = wMap.toString().replaceAll("[^0-9.]","");
                System.out.println("w: " + w);
            }  
            /////////////////////////////////////////////
            if (mainMap.get("wg") == null) {
                wg = "";
            }
            else {
                Map<String, Object> wgMap = jsonToMap(mainMap.get("wg").toString());
                wg = wgMap.toString().replaceAll("[^0-9.]","");
                System.out.println("wg: " + wg);
            }
            /////////////////////////////////////////////
            if (mainMap.get("o3") == null) {
                o3 = "";
            }
            else {
                Map<String, Object> o3Map = jsonToMap(mainMap.get("o3").toString());
                o3 = o3Map.toString().replaceAll("[^0-9.]","");
                System.out.println("o3: " + o3);
            }
            /////////////////////////////////////////////
            if (mainMap.get("uvi") == null) {
                uvi = "";
            }
            else {
                Map<String, Object> uviMap = jsonToMap(mainMap.get("uvi").toString());
                uvi = uviMap.toString().replaceAll("[^0-9.]","");
                System.out.println("uvi: " + uvi);
            }            
         
        } catch (IOException e) {
            System.out.println(e.getMessage() + " ovo je u catch");
        }
    }
    
    private void addToOntology (Individual iSensingDevice, Individual iService, Individual iSensor, Individual iStream, Individual iOb, ObjectProperty exposedBy, ObjectProperty exposes, ObjectProperty hasSensingDevice, ObjectProperty generatedBy, ObjectProperty generates, ObjectProperty madeByStream, ObjectProperty madeObservation, ObjectProperty hasCoverage, Individual iCoverage) {
        // Add object property exposedBy  
        ontologyModel.add(iSensingDevice,exposedBy,iService);
        // Add object property exposes 
        ontologyModel.add(iService,exposes,iSensingDevice);
        // add object property Sensor -> hasSensingDevice -> Device
        ontologyModel.add(iSensor,hasSensingDevice,iSensingDevice);
        // add object property lite:IoTStream -> generatedBy -> ssn:Sensor
        ontologyModel.add(iStream,generatedBy,iSensor);
        // add object property ssn:Sensor -> generates -> lite:IoTStream
        ontologyModel.add(iSensor,generates,iStream);
        // add object property sosa:Observation -> madeByStream -> lite:IoTStream
        ontologyModel.add(iOb,madeByStream,iStream);
        // add object property lite:Stream -> madeObservation -> sosa:Observation
        ontologyModel.add(iStream,madeObservation,iOb);
        // Add object property hasCoverage
        ontologyModel.add(iSensingDevice,hasCoverage,iCoverage);
    
    }
 

    /**
     * Handles the HTTP <code>POST</code> method.
     * Runs as a thread whenever there is HTTP POST request

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
                
        insertedLocation = request.getParameter("insert");       
        System.out.println("Ovo je lokacija: " + insertedLocation);       
        
        insertedLat = request.getParameter("insertLat");
        System.out.println("Ovo je Lat: " + insertedLat);
        insertedLong = request.getParameter("insertLong");
        System.out.println("Ovo je Long: " + insertedLong);
      
        doGet(request, response);
         
        f.close();
    }

    
}
