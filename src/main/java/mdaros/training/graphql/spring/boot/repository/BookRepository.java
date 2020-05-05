package mdaros.training.graphql.spring.boot.repository;

import mdaros.training.graphql.spring.boot.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {

}