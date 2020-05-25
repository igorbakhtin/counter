package com.bakhtin.counter.controller;

import com.bakhtin.counter.service.CounterService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor(onConstructor=@__(@Autowired))
public class CounterController {

    private final CounterService counterService;

    @GetMapping("/")
    public Integer getCounter() {
        return counterService.getCount();
    }

    @PostMapping("/")
    public Integer postCounter() {

        return counterService.incrementAndGet();
    }

    @DeleteMapping("/")
    public Integer deleteCounter() {
        return counterService.setZero();
    }
}