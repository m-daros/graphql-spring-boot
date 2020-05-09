package mdaros.training.graphql.spring.boot.service;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import io.leangen.graphql.annotations.*;
import io.leangen.graphql.execution.ResolutionEnvironment;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import io.leangen.graphql.spqr.spring.util.ConcurrentMultiMap;
import mdaros.training.graphql.spring.boot.model.Author;
import mdaros.training.graphql.spring.boot.repository.AuthorRepository;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;


@GraphQLApi
@Service
public class AuthorService extends AbstractService {

	@Autowired
	private AuthorRepository authorRepository;

	private ConcurrentMultiMap<String, FluxSink<Author>> subscribers;


	public AuthorService () {

		subscribers = new ConcurrentMultiMap<> ();
	}

	@GraphQLQuery ( name = "authors" )
	public Iterable<Author> getAuthors ( @GraphQLEnvironment ResolutionEnvironment env ) {

		EntityGraph entityGraph = buildDynamicEntityGraph ( env );

		return authorRepository.findAll ( entityGraph );
	}

	@GraphQLQuery ( name = "author" )
	public Author getAuthor ( @GraphQLArgument ( name = "id" ) Long id, @GraphQLEnvironment ResolutionEnvironment env ) {

		EntityGraph entityGraph = buildDynamicEntityGraph ( env );

		// TODO Handle NOT FOUND
		return authorRepository.findById ( id, entityGraph ).get ();
	}

	@GraphQLMutation ( name = "saveAuthor" )
	public Author saveAuthor ( @GraphQLArgument ( name = "author" ) Author author ) {

		Author savedAuthor = authorRepository.save ( author );

		// Notify all the subscribers following this book
		subscribers.get ( "" + author.getId () ).forEach ( subscriber -> subscriber.next ( savedAuthor ) );

		return savedAuthor;
	}

	@GraphQLMutation ( name = "deleteAuthor" )
	public Author deleteAuthor ( @GraphQLArgument ( name = "id" ) Long id ) {

		// TODO Handle NOT FOUND
		Author author = authorRepository.findById ( id ).get ();
		authorRepository.deleteById ( id );

		// Notify all the subscribers following this book
		subscribers.get ( "" + id ).forEach ( subscriber -> subscriber.next ( author ) );

		return author;
	}

	@GraphQLSubscription ( name = "authorChanged" )
	public Publisher<Author> authorChanged ( @GraphQLArgument ( name = "id" ) Long id ) {

		return Flux.create ( subscriber -> subscribers.add ( "" + id, subscriber.onDispose ( () -> subscribers.remove ( "" + id, subscriber ) ) ), FluxSink.OverflowStrategy.LATEST );
	}
}