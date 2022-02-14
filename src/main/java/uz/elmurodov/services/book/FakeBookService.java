package uz.elmurodov.services.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.elmurodov.dto.book.BookCreateDto;
import uz.elmurodov.dto.file.ResourceDto;
import uz.elmurodov.exceptions.NotFoundException;
import uz.elmurodov.mappers.BookMapper;
import uz.elmurodov.models.Book;
import uz.elmurodov.services.file.FileStorageService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service("fakeBookService")
public class FakeBookService implements BookService {


    private final BookMapper mapper;
    private final FileStorageService fileStorageService;

    @Autowired
    public FakeBookService(BookMapper mapper, FileStorageService fileStorageService) {
        this.mapper = mapper;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public Book get(String id) {
        Optional<Book> optionalBook = BOOK_LIST.stream().filter(book -> book.getId().toString().equals(id)).findFirst();
        if (optionalBook.isEmpty())
            throw new NotFoundException(String.format("Book with id %s not found", id), HttpStatus.NOT_FOUND);
        return optionalBook.get();
    }

    @Override
    public List<Book> getAll() {
        return BOOK_LIST;
    }

    @Override
    public String create(BookCreateDto dto, MultipartFile file) throws IOException {
        Book book = mapper.toEntity(dto);
        ResourceDto resourceDto = fileStorageService.store(file);
        book.setPath(resourceDto.getPath());
        BOOK_LIST.add(book);
        return book.getIdAsString();
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public boolean update(Object o) {
        return false;
    }
}
