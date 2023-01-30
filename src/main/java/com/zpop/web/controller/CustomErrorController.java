package com.zpop.web.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController implements ErrorController {

    private final String ERROR_PATH = "/error";

    @GetMapping(ERROR_PATH)
    public String redirectRoot() {
        return "index.html";
    }

    public String getErrorPath() {
        return null;
    }
}