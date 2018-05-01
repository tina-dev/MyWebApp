package cs.readinglist.repository;

import cs.readinglist.entity.Book;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReadingListRepository extends CrudRepository<Book, Long>
{
    @Override
    List<Book> findAll();

    @Override
    List<Book> findAllById(Iterable<Long> longs);
    
    List<Book> findByIsbn(String isbn);
    
    @Transactional
    void deleteByIsbn(String isbn);
    
    /*@Modifying
    @Query("update books p set p.description = :description where p.isbn = :isbn")
    Integer updateBook(@Param("description") String description, @Param("isbn") String isbn);*/
}