package bank.project.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web")
public class MvnController {
    @GetMapping("/loan")
    public String list_of_loan() {
        return "list_of_loan";
    }

    @GetMapping("/log")
    public String login() {
        return "login";
    }

    @GetMapping("/dash")
    public String dash() {
        return "dash";
    }

    @GetMapping("/profile")
    public String list_of_approval() {
        return "list_of_approval";
    }

    @GetMapping("/home")
    public String home() {return "home";}
}



