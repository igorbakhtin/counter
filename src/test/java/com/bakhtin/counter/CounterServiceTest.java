package com.bakhtin.counter;

import com.bakhtin.counter.service.CounterService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class CounterServiceTest {

    private static int threadCount = 10;

    private final ExecutorService executor = Executors.newFixedThreadPool(threadCount);
    private final CyclicBarrier BARRIER = new CyclicBarrier(threadCount);

    @Autowired
    private CounterService counterService;

    @SneakyThrows
    @Test
    void incrementAndGet() {
        int startCount = counterService.getCount();

        List<CounterTask> taskList = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            taskList.add(new CounterTask());
        }
        executor.invokeAll(taskList);

        int count = counterService.getCount();
        assertEquals(threadCount, count - startCount);
        System.out.println("RESULT " + count);
        executor.shutdown();
    }

    private class CounterTask implements Callable<Integer> {
        @SneakyThrows
        @Override
        public Integer call() {
            BARRIER.await();
            System.out.println("start");
            int i = counterService.incrementAndGet();
            System.out.println("end " + i);
            return i;
        }
    }

    @Test
    void setZero() {
        int count = counterService.incrementAndGet();
        assertNotEquals(0, count);

        count = counterService.setZero();
        assertEquals(0, count);
    }
}