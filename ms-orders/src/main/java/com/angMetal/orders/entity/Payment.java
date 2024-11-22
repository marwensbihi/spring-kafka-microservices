package com.angMetal.orders.entity;

import com.angMetal.orders.enums.StatusFacture;
import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class Payment {
    private Long orderId;
    private Double amount;
    private StatusFacture status; // e.g., INITIATED, COMPLETED, FAILED


}
