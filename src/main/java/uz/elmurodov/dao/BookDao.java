package uz.elmurodov.dao;


import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import uz.elmurodov.models.Book;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        return jdbcTemplate.query("select id,name,author,pageCount,path from book", new RowMapper<Book>() {
            @Override
            public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
                return Book.builder()
                        .name(rs.getString("name"))
                        .author(rs.getString("author"))
                        .pageCount(rs.getInt("pageCount"))
                        .path(rs.getString("path"))
                        .build();
            }
        });
    }

    public Optional<Book> get(String id) {
        return Optional.empty();
    }

    public String create(Book book) {
        jdbcTemplate.execute("insert into book(id,name,author,path,pageCount) values(?,?,?,?,?)",
                new PreparedStatementCallback<Boolean>() {
                    @Override
                    public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                        ps.setObject(1, book.getId());
                        ps.setString(2, book.getName());
                        ps.setString(3, book.getAuthor());
                        ps.setString(4, book.getPath());
                        ps.setInt(5, book.getPageCount());
                        return ps.execute();
                    }
                });
        return book.getIdAsString();
    }


}
