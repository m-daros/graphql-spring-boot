package mdaros.training.graphql.spring.boot.service;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import io.leangen.graphql.annotations.*;
import io.leangen.graphql.execution.ResolutionEnvironment;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import io.leangen.graphql.spqr.spring.util.ConcurrentMultiMap;
import mdaros.training.graphql.spring.boot.model.Book;
import mdaros.training.graphql.spring.boot.repository.BookRepository;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;


@GraphQLApi
@Service
public class BookService extends AbstractService {

	@Autowired
	private BookRepository bookRepository;

	private ConcurrentMultiMap<String, FluxSink<Book>> subscribers;


	public BookService () {

		subscribers = new ConcurrentMultiMap<> ();
	}

	@GraphQLQuery ( name = "books" )
	public Iterable<Book> getBooks ( @GraphQLEnvironment ResolutionEnvironment env ) {

		EntityGraph entityGraph = buildDynamicEntityGraph ( env );

		return bookRepository.findAll ( entityGraph );
	}

	@GraphQLQuery ( name = "book" )
	public Book getBook ( @GraphQLArgument ( name = "id" ) Long id, @GraphQLEnvironment ResolutionEnvironment env ) {

		EntityGraph entityGraph = buildDynamicEntityGraph ( env );

		// TODO Handle NOT FOUND
		return bookRepository.findById ( id, entityGraph ).get ();
	}

	@GraphQLMutation ( name = "saveBook" )
	public Book saveBook ( @GraphQLArgument ( name = "book" ) Book book ) {

		Book savedBook = bookRepository.save ( book );

		// Notify all the subscribers following this book
		subscribers.get ( "" + book.getId () ).forEach ( subscriber -> subscriber.next ( savedBook ) );

		return savedBook;
	}

	@GraphQLMutation ( name = "deleteBook" )
	public Book deleteBook ( @GraphQLArgument ( name = "id" ) Long id ) {

		// TODO Handle NOT FOUND
		Book book = bookRepository.findById ( id ).get ();
		bookRepository.delete ( book );

		// Notify all the subscribers following this book
		subscribers.get ( "" + id ).forEach ( subscriber -> subscriber.next ( book ) );

		return book;
	}

	@GraphQLSubscription ( name = "bookChanged" )
	public Publisher<Book> bookChanged ( @GraphQLArgument ( name = "id" ) Long id ) {

		return Flux.create ( subscriber -> subscribers.add ( "" + id, subscriber.onDispose ( () -> subscribers.remove ( "" + id, subscriber ) ) ), FluxSink.OverflowStrategy.LATEST );
	}
}