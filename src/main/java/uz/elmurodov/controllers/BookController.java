package uz.elmurodov.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import uz.elmurodov.dto.book.BookCreateDto;
import uz.elmurodov.services.book.BookService;

import java.io.IOException;
import java.util.UUID;

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
    private String create(@ModelAttribute BookCreateDto dto, @RequestParam("file") MultipartFile file) throws IOException {
        bookService.create(dto, file);
        return "redirect:/book/list/";
    }

    @RequestMapping(value = "delete/{id}/", method = RequestMethod.GET)
    private ModelAndView deletePage(ModelAndView modelAndView, @PathVariable String id) {
        modelAndView.setViewName("book/delete");
        modelAndView.addObject("book", bookService.get(id));
        return modelAndView;
    }

    @RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
    private String details(Model model, @PathVariable String id) {
        model.addAttribute("book", bookService.get(id));
        return "book/detail";
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

}