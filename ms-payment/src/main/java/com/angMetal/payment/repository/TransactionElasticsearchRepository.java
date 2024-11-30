package com.angMetal.payment.repository;

import com.angMetal.payment.entity.Transaction;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface ElasticsearchService extends ElasticsearchRepository<Transaction, Long> {
    void indexTransaction(Transaction transaction);
}
