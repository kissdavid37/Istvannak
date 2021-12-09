package hu.unideb.inf.beadando2;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.unideb.inf.beadando2.entity.Book;
import hu.unideb.inf.beadando2.service.BookService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @Captor
    private ArgumentCaptor<BookRequest> argumentCaptor;

    @Test
    public void postingAndNewBookShouldCreateANewBookInTheDatabase() throws Exception {
        BookRequest bookRequest= new BookRequest();
        bookRequest.setAuthor("Duke");
        bookRequest.setTitle("Arany Embe");
        bookRequest.setIsbn("1337");

        when(bookService.createNewBook(argumentCaptor.capture())).thenReturn(1L);

        this.mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location","http://localhost/api/books/1"));

        assertThat(argumentCaptor.getValue().getAuthor(),Matchers.is("Duke"));
        assertThat(argumentCaptor.getValue().getIsbn(),Matchers.is("1337"));
        assertThat(argumentCaptor.getValue().getTitle(),Matchers.is("Arany Embe"));
    }

    @Test
    public void allBooksEndPointShouldReturnTwoBooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(List.of(crateBooks(1L,"java 11","Duke","1337"),
                crateBooks(1L,"java ee 8","Duke","42")));

        this.mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].title",Matchers.is("java 11")))
                .andExpect(jsonPath("$[0].author",Matchers.is("Duke")))
                .andExpect(jsonPath("$[0].id",Matchers.is(1)));
    }



    private Book crateBooks(long id, String title, String author, String isbn) {
        Book book=new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setId(id);
        book.setIsbn(isbn);
        return book;
    }
}
