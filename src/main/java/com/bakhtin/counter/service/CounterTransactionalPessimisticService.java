package com.bakhtin.counter.service;

import com.bakhtin.counter.entity.Counter;
import com.bakhtin.counter.repo.CounterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
//@Primary
public class CounterTransactionalPessimisticService implements CounterTransactionalService {

    private final CounterRepository counterRepository;
    private final long id = 1;

    @PostConstruct
    private void init() {
        getOrCreate();
    }

    @Override
    @Transactional(readOnly = true)
    public int getCount() {
        return getOrCreate().getCount();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int incrementAndGet() {
        //Counter counter = getOrCreate();
        Counter counter = counterRepository.findByIdForUpdate(id);
        int current = counter.getCount();
        System.out.println("current " + current);
        counter.setCount(++current);
        counterRepository.saveAndFlush(counter);
        return counter.getCount();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int setZero() {
        Counter counter = counterRepository.findByIdForUpdate(id);
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
