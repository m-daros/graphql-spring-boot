package mdaros.training.graphql.spring.boot.model;

import lombok.*;
import mdaros.training.graphql.spring.boot.model.support.Identifiable;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table ( name = "BOOK" )
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString @EqualsAndHashCode
public class Book implements Identifiable<Long> {

	@Id
	@Column ( name = "ID", nullable = false)
	@GenericGenerator ( name = "assigned-identity", strategy = "mdaros.training.graphql.spring.boot.model.support.AssignableIdGenarator" )
	@GeneratedValue ( generator = "assigned-identity", strategy = GenerationType.AUTO )
	private Long id;

	@Column ( name = "TITLE" )
	private String title;

	@ManyToOne
	private Author author;
}