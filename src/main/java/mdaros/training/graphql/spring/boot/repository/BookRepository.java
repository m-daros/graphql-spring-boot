package mdaros.training.graphql.spring.boot.repository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphCrudRepository;
import mdaros.training.graphql.spring.boot.model.Book;

public interface BookRepository extends EntityGraphCrudRepository<Book, Long> {

}