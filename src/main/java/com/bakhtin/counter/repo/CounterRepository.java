package com.bakhtin.counter.repo;

import com.bakhtin.counter.entity.Counter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;

public interface CounterRepository extends JpaRepository<Counter, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from Counter c where c.id = :id")
    Counter findByIdForUpdate(long id);

}