package hu.unideb.inf.beadando2;

import com.github.javafaker.Faker;
import hu.unideb.inf.beadando2.entity.Book;
import hu.unideb.inf.beadando2.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Slf4j
@Component
public class BookInitializer implements CommandLineRunner {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Elkezdődik az inicializás");
        Faker faker= new Faker();
        for (int i=0;i<10;i++){
            Book book=new Book();
            book.setAuthor(faker.book().author());
            book.setTitle(faker.book().title());
            book.setIsbn(UUID.randomUUID().toString());

            bookRepository.save(book);
        }

        log.info("Befejeződött az inicializálás");

    }
}
