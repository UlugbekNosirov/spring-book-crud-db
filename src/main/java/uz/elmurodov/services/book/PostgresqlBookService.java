package uz.elmurodov.services.book;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.elmurodov.dao.BookDao;
import uz.elmurodov.dto.book.BookCreateDto;
import uz.elmurodov.dto.file.ResourceDto;
import uz.elmurodov.mappers.BookMapper;
import uz.elmurodov.models.Book;
import uz.elmurodov.services.file.FileStorageService;

import java.util.List;


@Service
public class PostgresqlBookService implements BookService {

    private final BookMapper mapper;

    private final BookDao bookDao;

    private final FileStorageService fileStorageService;


    public PostgresqlBookService(BookMapper mapper, BookDao bookDao, FileStorageService fileStorageService) {
        this.mapper = mapper;
        this.bookDao = bookDao;
        this.fileStorageService = fileStorageService;
    }


    @Override
    public Book get(String id) {
        return null;
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @SneakyThrows
    @Override
    public String create(BookCreateDto dto, MultipartFile file) {
        ResourceDto resourceDto = fileStorageService.store(file);
        Book book = mapper.toEntity(dto);
        book.setPath(resourceDto.getPath());
        return bookDao.create(book);
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public boolean update(Object o) {
        return false;
    }
}
