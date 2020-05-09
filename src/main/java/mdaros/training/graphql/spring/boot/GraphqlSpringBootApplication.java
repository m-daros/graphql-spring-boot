package mdaros.training.graphql.spring.boot;

import com.cosium.spring.data.jpa.entity.graph.repository.support.EntityGraphJpaRepositoryFactoryBean;
import mdaros.training.graphql.spring.boot.model.Author;
import mdaros.training.graphql.spring.boot.model.Book;
import mdaros.training.graphql.spring.boot.repository.AuthorRepository;
import mdaros.training.graphql.spring.boot.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
@EnableWebSocket
@EnableJpaRepositories ( repositoryFactoryBeanClass = EntityGraphJpaRepositoryFactoryBean.class )
public class GraphqlSpringBootApplication implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger ( GraphqlSpringBootApplication.class );

	@Autowired
	private BookRepository bookRespository;

	@Autowired
	private AuthorRepository authorRepository;

	public static void main ( String [] args ) {

		SpringApplication.run ( GraphqlSpringBootApplication.class, args );
	}

	@Override
	public void run ( String ... args ) throws Exception {

		Author author1 = new Author ( null, "Aldo", "Bianchi" );
		Author author2 = new Author ( null, "Roberto", "Rossi" );

		authorRepository.save ( author1 );
		LOGGER.info ( "Saved author {}", author1 );

		authorRepository.save ( author2 );
		LOGGER.info ( "Saved author {}", author2 );

		Book book1 = new Book ( null, "La vita è bella", author1 );
		Book book2 = new Book ( null, "Paura del buio", author2 );

		bookRespository.save ( book1 );
		LOGGER.info ( "Saved book {}", book1 );

		bookRespository.save ( book2 );
		LOGGER.info ( "Saved book {}", book2 );
	}
}					