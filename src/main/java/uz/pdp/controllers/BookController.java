package uz.pdp.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import uz.pdp.dto.book.BookCreateDto;
import uz.pdp.services.book.BookService;

import java.io.IOException;

@Controller
@RequestMapping("/book/*")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(@Qualifier("postgresqlBookService") BookService bookService) {
        this.bookService = bookService;
    }


    @RequestMapping(value = "create/", method = RequestMethod.GET)
    private String createPage() {
        return "/book/create";
    }

    @RequestMapping(value = "create/", method = RequestMethod.POST)
    private String create(@ModelAttribute BookCreateDto dto, @RequestParam("file") MultipartFile file, @RequestParam("image") MultipartFile img) throws IOException {
        bookService.create(dto, file, img);
        return "redirect:/book/list/";
    }

    @RequestMapping(value = "delete/{id}/", method = RequestMethod.GET)
    private ModelAndView deletePage(ModelAndView modelAndView, @PathVariable String id) {
        modelAndView.setViewName("book/delete");
        modelAndView.addObject("book", bookService.get(id));
        return modelAndView;
    }

    @RequestMapping(value = "detail/{id}/", method = RequestMethod.GET)
    private ModelAndView detail(ModelAndView modelAndView, @PathVariable String id) {
        modelAndView.setViewName("book/detail");
        modelAndView.addObject("book", bookService.get(id));
        return modelAndView;
    }


    @RequestMapping(value = "list/", method = RequestMethod.GET)
    private String bookListPage(Model model) {
        model.addAttribute("books", bookService.getAll());
        return "book/list";
    }

    @RequestMapping(value = "delete/{id}/", method = RequestMethod.POST)
    private String delete(@PathVariable String id) {
        bookService.delete(id);
        return "redirect:/book/list/";
    }

    @RequestMapping(value = "update/{id}/", method = RequestMethod.GET)
    private ModelAndView updatePage(ModelAndView modelAndView, @PathVariable String id) {
//        Optional<Book> optionalBook = Optional.ofNullable(bookService.get(id));
//        if (optionalBook.isEmpty())
//            throw new NotFoundException(String.format("Book with id %s not found", id), HttpStatus.NOT_FOUND);

        modelAndView.setViewName("book/update");
        modelAndView.addObject("book", bookService.get(id));
        return modelAndView;
    }

    @RequestMapping(value = "update/{id}/", method = RequestMethod.POST)
    private String update(BookCreateDto bookCreateDto, @PathVariable String id) {
        bookService.update(bookCreateDto, id);
        return "redirect:/book/list/";
    }


}