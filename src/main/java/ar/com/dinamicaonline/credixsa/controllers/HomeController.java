package ar.com.dinamicaonline.credixsa.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public ResponseEntity<?> getRoot() {
        return new ResponseEntity<>("Root page", HttpStatus.OK);
    }
}
