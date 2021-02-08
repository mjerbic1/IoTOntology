/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marjerbic.iot;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Servlet;
import org.apache.jena.ontology.*;
import java.util.Arrays;
import javax.servlet.ServletContext;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;

/**
 *
 * @author Marija
 */
public class OwlReaderUtil {
    
            
    public static List<String> executeQueryOneColumn(ServletContext context, String queryString) {
    List<String> values = new ArrayList<>();
       
    OntModel ontologyModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
    ontologyModel.read(context.getRealPath("/")+ "/resources/kk.owl", null, "RDF/XML");
    //ontologyModel.read(context.getRealPath("/")+ "/resources/iot_ontology_rdf.owl", null, "RDF/XML");

    
    Query query = QueryFactory.create(queryString);
    QueryExecution qexec = QueryExecutionFactory.create(query, ontologyModel);
    
    try{
        
        ResultSet results = QueryExecutionFactory.create( query, ontologyModel ).execSelect();
        
        while(results.hasNext()) {
            QuerySolution sol = (QuerySolution) results.next();
            for (String var: results.getResultVars()) {
                   values.add(sol.getResource(var).toString());
            }
        }
             
    } finally {
        qexec.close();
    }
    
    return values;
    }
    
    public static List<List<String>> executeQueryOTwoColumn(ServletContext context, String queryString) {
        List<List<String>> rows = new ArrayList<>();


        OntModel ontologyModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        ontologyModel.read(context.getRealPath("/")+ "/resources/kk.owl", null, "RDF/XML");

        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, ontologyModel);

        try{
            ResultSet results = QueryExecutionFactory.create( query, ontologyModel ).execSelect();
            
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();

                Resource x = solution.getResource("x");
                Resource y = solution.getResource("y");
                
                List<String> column1 = Arrays.asList(x.getLocalName(), y.getURI() + "");
                rows.add(column1);
            }
            
        } finally {
            qexec.close();
        }

        return rows;
    }
    
    public static List<List<String>> executeQueryOTwoColumnLocalNames(ServletContext context, String queryString) {
        List<List<String>> rows = new ArrayList<>();


        OntModel ontologyModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        //ontologyModel.read(context.getRealPath("/")+ "/resources/iot_ontology_rdf.owl", null, "RDF/XML");
        ontologyModel.read(context.getRealPath("/")+ "/resources/kk.owl", null, "RDF/XML");

        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, ontologyModel);

        try{
            ResultSet results = QueryExecutionFactory.create( query, ontologyModel ).execSelect();
           
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();

                Resource x = solution.getResource("x");
                Resource y = solution.getResource("y");
                
                List<String> column1 = Arrays.asList(x.getLocalName(), y.getLocalName());
                rows.add(column1);
            }
            
        } finally {
            qexec.close();
        }

        return rows;
    }
    
    public static List<List<String>> executeQueryOTwoColumnLiteral(ServletContext context, String queryString) {
        List<List<String>> rows = new ArrayList<>();


        OntModel ontologyModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        //ontologyModel.read(context.getRealPath("/")+ "/resources/iot_ontology_rdf.owl", null, "RDF/XML");
        ontologyModel.read(context.getRealPath("/")+ "/resources/kk.owl", null, "RDF/XML");

        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, ontologyModel);

        try{
            ResultSet results = QueryExecutionFactory.create( query, ontologyModel ).execSelect();
            
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();

                Resource x = solution.getResource("x");
                Literal y = solution.getLiteral("y");
                
                List<String> column1 = Arrays.asList(x.getURI(), y.getValue()+ "");
                rows.add(column1);
            }
            
        } finally {
            qexec.close();
        }

        return rows;
    }
    
    public static List<List<String>> executeQueryOTwoColumnLiteralDate(ServletContext context, String queryString) {
        List<List<String>> rows = new ArrayList<>();


        OntModel ontologyModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        //ontologyModel.read(context.getRealPath("/")+ "/resources/iot_ontology_rdf.owl", null, "RDF/XML");
        ontologyModel.read(context.getRealPath("/")+ "/resources/kk.owl", null, "RDF/XML");

        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, ontologyModel);

        try{
            ResultSet results = QueryExecutionFactory.create( query, ontologyModel ).execSelect();
            
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();

                Resource x = solution.getResource("x");
                Literal y = solution.getLiteral("y");
                
                List<String> column1 = Arrays.asList(x.getURI(), y.getString()+ "");
                rows.add(column1);
            }
            
        } finally {
            qexec.close();
        }

        return rows;
    }
    
    public static List<List<String>> executeQueryOTwoColumnPokusaj(ServletContext context, String queryString) {
        List<List<String>> rows = new ArrayList<>();


        OntModel ontologyModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        ontologyModel.read(context.getRealPath("/")+ "/resources/kk.owl", null, "RDF/XML");
        //ontologyModel.read(context.getRealPath("/")+ "/resources/iot_ontology_rdf.owl", null, "RDF/XML");
        

        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, ontologyModel);

        try{
            ResultSet results = QueryExecutionFactory.create( query, ontologyModel ).execSelect();
            
          
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();

                Resource x = solution.getResource("x");
                //System.out.println("pokusaj x - " + x);
                Resource y = solution.getResource("y");
                //System.out.println("pokusaj y - " + y);
                
                List<String> column1 = Arrays.asList(x.getURI() + "", y.getLocalName());
                rows.add(column1);
            }
            
        } finally {
            qexec.close();
        }

        return rows;
    }
    
    public static List<List<String>> executeQueryOTwoColumnURI(ServletContext context, String queryString) {
        List<List<String>> rows = new ArrayList<>();


        OntModel ontologyModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        //ontologyModel.read(context.getRealPath("/")+ "/resources/iot_ontology_rdf.owl", null, "RDF/XML");
        ontologyModel.read(context.getRealPath("/")+ "/resources/kk.owl", null, "RDF/XML");

        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, ontologyModel);

        try{
            ResultSet results = QueryExecutionFactory.create( query, ontologyModel ).execSelect();
            
          
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();

                Resource x = solution.getResource("x");
                System.out.println("pokusaj x - " + x);
                Resource y = solution.getResource("y");
                System.out.println("pokusaj y - " + y);
                
                List<String> column1 = Arrays.asList(x.getURI() + "", y.getURI());
                rows.add(column1);
            }
            
        } finally {
            qexec.close();
        }

        return rows;
    }
    
}
