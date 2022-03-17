package uz.pdp.controllers;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.annotations.MvcController;

@MvcController
@RequestMapping
public class HomeController {

    @RequestMapping(value = {"/", "/home"})
    public String homePage() {
        return "home";
    }

    @RequestMapping("/play/{filepath}")
    private String path(Model model, @PathVariable(name = "filepath") String path) {
        model.addAttribute("path", "/uploads/" + path);
        return "helpers/play";
    }


}
