package mdaros.training.graphql.spring.boot.repository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphCrudRepository;
import mdaros.training.graphql.spring.boot.model.Author;

public interface AuthorRepository extends EntityGraphCrudRepository<Author, Long> {

}