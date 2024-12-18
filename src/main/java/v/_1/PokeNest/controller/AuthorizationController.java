package v._1.PokeNest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthorizationController {

    @PostMapping(value = "login")
    public String welcome(){
        return "Welcome from endpoint";
    }
}
