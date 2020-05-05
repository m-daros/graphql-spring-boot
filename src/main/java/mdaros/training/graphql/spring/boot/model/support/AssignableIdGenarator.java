package mdaros.training.graphql.spring.boot.model.support;


import mdaros.training.graphql.spring.boot.model.support.Identifiable;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

import java.io.Serializable;

public class AssignableIdGenarator extends IdentityGenerator {

	@Override
	public Serializable generate ( SharedSessionContractImplementor session, Object obj ) {

		if ( obj instanceof Identifiable ) {

			Identifiable<Serializable> identifiable = ( Identifiable ) obj;
			Serializable id = identifiable.getId ();

			if ( id != null ) {

				return id;
			}
		}

		return super.generate ( session, obj );
	}
}