// Portions Copyright (c) 2006 - 2008, Clark & Parsia, LLC. <http://www.clarkparsia.com>
// Clark & Parsia, LLC parts of this source code are available under the terms of the Affero General Public License v3.
//
// Please see LICENSE.txt for full license terms, including the availability of proprietary exceptions.
// Questions, comments, or requests for clarification: licensing@clarkparsia.com

package br.ufba.matac93.msr;

import java.util.Iterator;

import javax.annotation.Resource;

/**
 * An example to show how to use PelletReasoner as a Jena reasoner. The reasoner can
 * be directly attached to a plain RDF <code>Model</code> or attached to an <code>OntModel</code>.
 * This program shows how to do both of these operations and achieve the exact same results. 
 * 
 * @author Evren Sirin
 */
public class JenaReasoner {
    public static void main(String[] args) {
        usageWithDefaultModel();
        
        //usageWithOntModel();
    }
    
    public static void usageWithDefaultModel() {
        System.out.println("Results with plain RDF Model");
        System.out.println("----------------------------");
        System.out.println();
        
        // ontology that will be used
//        String ont = "http://protege.cim3.net/file/pub/ontologies/koala/koala.owl#";
//        String ns = "http://protege.stanford.edu/plugins/owl/owl-library/koala.owl#";
        
        String ont = "ontofilmeX.owl";
		String ns = "http://www.semanticweb.org/jandson/ontologies/2014/1/untitled-ontology-4#";

        
  	    // create Pellet reasoner
        Reasoner reasoner = PelletReasonerFactory.theInstance().create();
        
        // create an empty model
//        Model emptyModel = ModelFactory.createDefaultModel( );
        
        Model emptyModel = ModelFactory.createOntologyModel(PelletReasonerFactory.THE_SPEC );
        
        // create an inferencing model using Pellet reasoner
        InfModel model = ModelFactory.createInfModel( reasoner, emptyModel );
            
        // read the file
        model.read( ont );
        
        // print validation report
        ValidityReport report = model.validate();
        printIterator( report.getReports(), "Validation Results" );
        
        // print superclasses
        Resource c = model.getResource( ns + "Joao" );
        StmtIterator sti =  c.listProperties(model.getProperty(ns + "gosta"));
        printIterator(sti, "All super classes of " + c.getLocalName());
        
        System.out.println();
    }

    public static void usageWithOntModel() {    
        System.out.println("Results with OntModel");
        System.out.println("---------------------");
        System.out.println();

        // ontology that will be used
        String ont = "http://protege.cim3.net/file/pub/ontologies/koala/koala.owl#";
        String ns = "http://protege.stanford.edu/plugins/owl/owl-library/koala.owl#";
        
        // create an empty ontology model using Pellet spec
        OntModel model = ModelFactory.createOntologyModel( PelletReasonerFactory.THE_SPEC );
            
        // read the file
        model.read( ont );
        
        // print validation report
        ValidityReport report = model.validate();
        printIterator( report.getReports(), "Validation Results" );
        
        // print superclasses using the utility function
        OntClass c = model.getOntClass( ns + "MaleStudentWith3Daughters" );         
        printIterator(c.listSuperClasses(), "All super classes of " + c.getLocalName());
        // OntClass provides function to print *only* the direct subclasses 
        printIterator(c.listSuperClasses(true), "Direct superclasses of " + c.getLocalName());
        
        System.out.println();
    }
    
    public static void printIterator(Iterator<?> i, String header) {
        System.out.println(header);
        for(int c = 0; c < header.length(); c++)
            System.out.print("=");
        System.out.println();
        
        if(i.hasNext()) {
	        while (i.hasNext()) 
	            System.out.println( i.next() );
        }       
        else
            System.out.println("<EMPTY>");
        
        System.out.println();
    }
}