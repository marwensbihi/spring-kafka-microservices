#!/bin/bash

# Create the product_index
curl -u elastic:changeme -X PUT "localhost:9200/product_index"

# Create the transaction_index
curl -u elastic:changeme -X PUT "localhost:9200/transaction_index"

curl -u elastic:changeme -X GET "localhost:9200/_cat/indices?v"

