package mdaros.training.graphql.spring.boot.model;

import lombok.*;
import mdaros.training.graphql.spring.boot.model.support.Identifiable;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table ( name = "AUTHOR" )
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString @EqualsAndHashCode
public class Author implements Identifiable<Long> {

	@Id
	@Column ( name = "ID", nullable = false)
	@GenericGenerator ( name = "assigned-identity", strategy = "mdaros.training.graphql.spring.boot.model.support.AssignableIdGenarator" )
	@GeneratedValue ( generator = "assigned-identity", strategy = GenerationType.AUTO )
	private Long id;

	@Column ( name = "NAME" )
	private String name;

	@Column ( name = "SURNAME" )
	private String surname;

	@OneToMany ( mappedBy = "author" )
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<Book> publishedBooks;
}