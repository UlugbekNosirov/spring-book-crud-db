package uz.pdp.services.book;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.dao.BookDao;
import uz.pdp.dto.book.BookCreateDto;
import uz.pdp.dto.file.ResourceDto;
import uz.pdp.mappers.BookMapper;
import uz.pdp.models.Book;
import uz.pdp.services.file.FileStorageService;

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
        return bookDao.get(id);
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @SneakyThrows
    @Override
    public String create(BookCreateDto dto, MultipartFile file, MultipartFile img) {
        ResourceDto resourceDto = fileStorageService.store(file);
        String storeImg = fileStorageService.storeImg(img);
        Book book = mapper.toEntity(dto);
        book.setPath(resourceDto.getPath());
        book.setImg_path(storeImg);
        return bookDao.create(book);
    }

    @Override
    public void delete(String id) {
        bookDao.delete(id);
    }

    @Override
    public boolean update(BookCreateDto updateDto, String id) {
        return bookDao.update(updateDto, id);
    }
}
