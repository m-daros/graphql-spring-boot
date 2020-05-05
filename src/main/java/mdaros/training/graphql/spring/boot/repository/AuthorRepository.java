package mdaros.training.graphql.spring.boot.repository;

import mdaros.training.graphql.spring.boot.model.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {

}