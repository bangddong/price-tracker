package com.bd.tracker.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/batchInfo/newBatch")
    public String createBatchInfo() {
        return "/batchInfo/new_batch";
    }
}
