package mdaros.training.graphql.spring.boot.service;

import com.cosium.spring.data.jpa.entity.graph.domain.DynamicEntityGraph;
import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphType;
import graphql.schema.DataFetchingFieldSelectionSet;
import graphql.schema.SelectedField;
import io.leangen.graphql.annotations.GraphQLEnvironment;
import io.leangen.graphql.execution.ResolutionEnvironment;

import java.util.List;
import java.util.stream.Collectors;

public class AbstractService {

	protected EntityGraph buildDynamicEntityGraph ( @GraphQLEnvironment ResolutionEnvironment env ) {

		DataFetchingFieldSelectionSet selection = env.dataFetchingEnvironment.getSelectionSet ();
		List<SelectedField> selectedFields = selection.getFields ();

		List<String> attributePaths = selectedFields.stream ()
				.map ( field -> field.getQualifiedName () )
				.map ( attribute -> attribute.replaceAll ( "/", "." ) )
				.collect ( Collectors.toList () );

		return new DynamicEntityGraph ( EntityGraphType.FETCH, attributePaths );
	}
}