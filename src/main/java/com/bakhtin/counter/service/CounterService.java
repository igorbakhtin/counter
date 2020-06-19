package com.bakhtin.counter.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CounterService {

    private static final int MAX_TRY_COMMIT = 50;
    private final CounterTransactionalService counterTransactionalService;

    public int getCount() {
        return counterTransactionalService.getCount();
    }

    @SneakyThrows
    @Retryable(value = {Exception.class},
            maxAttempts = MAX_TRY_COMMIT,
            backoff = @Backoff(delay = 1000))
    public int incrementAndGet() {
        return counterTransactionalService.incrementAndGet();
    }

    public int setZero() {
        return counterTransactionalService.setZero();
    }

}
