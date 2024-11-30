package com.angMetal.payment.repository;

import com.angMetal.payment.entity.TransactionElastic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface TransactionElasticsearchRepository extends ElasticsearchRepository<TransactionElastic, Long> {
}
