package com.bakhtin.counter.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CounterService {

    private static final int MAX_TRY_COMMIT = 5;
    private final CounterTransactionalService counterTransactionalService;

    public int getCount() {
        return counterTransactionalService.getCount();
    }

    @SneakyThrows
    public int incrementAndGet() {
        int tryCommitCount = MAX_TRY_COMMIT;
        while (tryCommitCount-- > 0) {
            System.out.println("tryCommitCount " + tryCommitCount);
            try {
                return counterTransactionalService.incrementAndGet();
            } catch (Exception ex) {
                log.warn("!!!!! Exception {}", ex.getLocalizedMessage());
            }
        }
        throw new Exception("Can't update counter. Try later");
    }

    public int setZero() {
        return counterTransactionalService.setZero();
    }

}
