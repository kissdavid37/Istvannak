package hu.unideb.inf.beadando2.repository;

import hu.unideb.inf.beadando2.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
}
