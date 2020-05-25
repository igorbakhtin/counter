package com.bakhtin.counter.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
@Data
public class Counter {
    @Id
    private long id;

    @Version
    private long version;

    private int count;

}
