package com.bakhtin.counter.service;

import org.hibernate.StaleObjectStateException;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;

public interface CounterTransactionalService {

    int getCount();

    int incrementAndGet();

    int setZero();
}
