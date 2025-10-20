package com.example.redditdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/about")
    public String aboutPage(Model model) {
        // Add any model attributes if needed
        return "about"; // Thymeleaf will look for about.html
    }

    @GetMapping("/contact")
    public String contactPage(Model model) {
        // Add any model attributes if needed
        return "contact"; // Thymeleaf will look for contact.html
    }

    @GetMapping("/terms")
    public String termsPage() {
        return "terms"; // matches terms.html in templates folder
    }
}
