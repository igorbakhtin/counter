package com.bakhtin.counter.service;

import com.bakhtin.counter.entity.Counter;
import com.bakhtin.counter.repo.CounterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Primary
public class CounterTransactionalOptimisticService implements CounterTransactionalService {

    private final CounterRepository counterRepository;
    private long id = 1;

    @PostConstruct
    private void init() {
        getOrCreate();
    }

    @Override
    public int getCount() {
        return getOrCreate().getCount();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int incrementAndGet() {
        Counter counter = getOrCreate();
        int current = counter.getCount();
        System.out.println("current " + current);
        counter.setCount(++current);
        counterRepository.saveAndFlush(counter);
        return counter.getCount();
    }

    @Override
    public int setZero() {
        Counter counter = getOrCreate();
        counter.setCount(0);
        counterRepository.saveAndFlush(counter);
        return counter.getCount();
    }

    private Counter getOrCreate() {
        Counter counter = counterRepository.findById(id).orElseGet(() -> create());
        log.info("counter {}", counter);
        return counter;
    }

    private Counter create() {
        Counter counter = new Counter();
        counter.setId(id);
        log.info("create {}", counter);
        return counterRepository.saveAndFlush(counter);
    }

}
