package hu.unideb.inf.beadando2.service;

import hu.unideb.inf.beadando2.BookNotFoundException;
import hu.unideb.inf.beadando2.repository.BookRepository;
import hu.unideb.inf.beadando2.BookRequest;
import hu.unideb.inf.beadando2.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Long createNewBook(BookRequest bookRequest){
        Book book=new Book();
        book.setAuthor(bookRequest.getAuthor());
        book.setAuthor(bookRequest.getIsbn());
        book.setAuthor(bookRequest.getTitle());

        book=bookRepository.save(book);
        return book.getId();
    }


    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        bookRepository.findById(id);
        Optional <Book> requestedBook=bookRepository.findById(id);
        if(requestedBook.isEmpty()){
            throw new BookNotFoundException(String.format("Book with id:'%s' not found",id));

        }
        return requestedBook.get();
    }
}
