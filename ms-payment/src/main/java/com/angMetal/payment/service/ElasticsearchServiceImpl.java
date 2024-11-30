package com.angMetal.payment.service;

import com.angMetal.payment.entity.TransactionElastic;
import com.angMetal.payment.entity.TransactionMySQL;
import com.angMetal.payment.repository.TransactionElasticsearchRepository;
import com.angMetal.payment.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElasticsearchServiceImpl {

    private final TransactionRepository transactionRepository;
    private final TransactionElasticsearchRepository elasticsearchRepository;

    @Autowired
    public ElasticsearchServiceImpl(TransactionRepository transactionRepository,
                                    TransactionElasticsearchRepository elasticsearchRepository) {
        this.transactionRepository = transactionRepository;
        this.elasticsearchRepository = elasticsearchRepository;
    }

    /**
     * Save the transaction to both MySQL and Elasticsearch.
     *
     * @param transactionMySQL the transaction entity to be saved in MySQL
     * @return the saved transaction entity in MySQL
     */
    public TransactionMySQL saveTransaction(TransactionMySQL transactionMySQL) {
        // Save to MySQL
        TransactionMySQL savedTransaction = transactionRepository.save(transactionMySQL);

        // Map MySQL entity to Elasticsearch entity
        TransactionElastic transactionElastic = mapToElastic(savedTransaction);

        // Save to Elasticsearch
        elasticsearchRepository.save(transactionElastic);

        return savedTransaction;
    }

    /**
     * Map a TransactionMySQL entity to a TransactionElastic entity.
     *
     * @param transactionMySQL the MySQL transaction entity
     * @return the corresponding Elasticsearch entity
     */
    private TransactionElastic mapToElastic(TransactionMySQL transactionMySQL) {
        TransactionElastic transactionElastic = new TransactionElastic();

        transactionElastic.setTransactionID(transactionMySQL.getTransactionID());
        transactionElastic.setMontant(transactionMySQL.getMontant());
        transactionElastic.setDateTransaction(transactionMySQL.getDateTransaction());
        transactionElastic.setTypeDeTransaction(transactionMySQL.getTypeDeTransaction());
        transactionElastic.setFactureVenteId(transactionMySQL.getFactureVenteId());
        transactionElastic.setFactureAchatId(transactionMySQL.getFactureAchatId());

        return transactionElastic;
    }

    /**
     * Index the transaction to Elasticsearch.
     *
     * @param transaction the transaction entity to be indexed
     */
    public void indexTransaction(TransactionElastic transaction) {
        elasticsearchRepository.save(transaction);
    }
}
