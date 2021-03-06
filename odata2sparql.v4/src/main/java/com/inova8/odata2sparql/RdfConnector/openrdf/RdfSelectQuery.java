package com.inova8.odata2sparql.RdfConnector.openrdf;

import org.eclipse.rdf4j.query.MalformedQueryException;
import org.eclipse.rdf4j.query.QueryEvaluationException;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.repository.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inova8.odata2sparql.Exception.OData2SparqlException;
import com.inova8.odata2sparql.RdfRepository.RdfRoleRepository;

public class RdfSelectQuery extends RdfQuery{
	private final Logger log = LoggerFactory.getLogger(RdfConstructQuery.class);
	private TupleQuery tupleQuery;
	public RdfSelectQuery(RdfRoleRepository rdfRoleRepository, String query) {
		super.rdfRoleRepository = rdfRoleRepository;
		super.query = query;
	}
	public RdfResultSet execSelect() throws OData2SparqlException {
		return execSelect(true);
	}
	public RdfResultSet execSelect(Boolean logQuery) throws OData2SparqlException {
		RdfResultSet rdfResultSet = null;
		try {
			super.connection = rdfRoleRepository.getRepository().getConnection();
			tupleQuery = connection.prepareTupleQuery(QueryLanguage.SPARQL, super.query);
			if( logQuery)log.info( super.query);
			rdfResultSet = new RdfResultSet(connection, tupleQuery.evaluate());
		} catch (RepositoryException | MalformedQueryException | QueryEvaluationException e) {
			log.error( super.query);
			throw new OData2SparqlException("RdfSelectQuery execSelect failure with message:\n"+ e.getMessage(),e);
		}
		return rdfResultSet;
	}	
}
