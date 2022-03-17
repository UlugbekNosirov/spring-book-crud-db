package uz.pdp.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Component;
import uz.pdp.dto.book.BookCreateDto;
import uz.pdp.models.Book;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class BookDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAll() {
        return jdbcTemplate.query("select id,name,author,pageCount,path, img_path from book", (rs, rowNum) -> Book.builder()
                .id(UUID.fromString(rs.getString("id")))
                .name(rs.getString("name"))
                .author(rs.getString("author"))
                .pageCount(rs.getInt("pageCount"))
                .path(rs.getString("path"))
                .img_path(rs.getString("img_path"))
                .build());
    }

    public Book get(String id) {
        Optional<Book> first = getAll().stream().filter(book -> book.getId().toString().equals(id)).findFirst();
        return first.get();
    }

    public void delete(String id) {
        String str = "delete from book where id=?";
        jdbcTemplate.update(str, id);
    }

    public boolean update(BookCreateDto updateDto, String id) {
        String str = "update book set name = ?, author=?, pageCount=? where id= ?";
        jdbcTemplate.update(str, updateDto.getName(), updateDto.getAuthor(), updateDto.getPageCount(), id);
        return true;
    }

    public String create(Book book) {
        jdbcTemplate.execute("insert into book(id,name,author,path,pageCount, img_path) values(?,?,?,?,?,?)",
                new PreparedStatementCallback<Boolean>() {
                    @Override
                    public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                        ps.setObject(1, book.getId());
                        ps.setString(2, book.getName());
                        ps.setString(3, book.getAuthor());
                        ps.setString(4, book.getPath());
                        ps.setInt(5, book.getPageCount());
                        ps.setString(6, book.getImg_path());
                        return ps.execute();
                    }
                });
        return book.getIdAsString();
    }
}