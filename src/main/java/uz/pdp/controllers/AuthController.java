package uz.pdp.controllers;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uz.pdp.dto.auth.LoginDto;
import uz.pdp.dto.auth.UserDto;

import java.util.List;

@Controller
@RequestMapping("/auth/*")
public class AuthController {

    private final List<UserDto> users = Lists.newArrayList(
            new UserDto("muslim", 22),
            new UserDto("akbar", 11),
            new UserDto("nurislom", 17)
    );


    @RequestMapping(value = "login", method = RequestMethod.GET)
    private String loginPage() {
        return "auth/login";
    }

    @RequestMapping(value = "do-login", method = RequestMethod.GET)
    private String doLoginPage(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String password) {
        System.out.println("username = " + username);
        System.out.println("password = " + password);
        return "auth/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    private String login( LoginDto loginDto) {
        System.out.println("loginDto = " + loginDto);
        System.out.println("Login Successfully");
        return "redirect:/";
    }

    @RequestMapping(value = "/auth/users")
    public String users(Model model) {
        model.addAttribute("users", users);
        return "auth/users_list";
    }
}
