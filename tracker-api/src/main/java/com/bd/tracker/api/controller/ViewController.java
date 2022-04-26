package com.bd.tracker.api.controller;

import com.bd.tracker.api.service.ScrapInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class ViewController {

    private final ScrapInfoService scrapInfoService;

    @GetMapping("/")
    public ModelAndView index() {
        Map<String, Object> map = new HashMap<>();

        return new ModelAndView("index", map);
    }

    @GetMapping("/batchInfo/newBatch")
    public String createBatchInfo() {
        return "/batchInfo/new_batch";
    }
}
