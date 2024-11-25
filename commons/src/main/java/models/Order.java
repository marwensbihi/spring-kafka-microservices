package models;

import enums.PaymentSource;
import enums.PaymentType;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    private Long id;               // Unique identifier for the order
    private Long customerId;       // ID of the customer
    private Long productId;        // ID of the product
    private int productCount;      // Quantity of the product
    private int price;             // Total price of the order
    private PaymentType type;      // Type of payment (e.g., CREDIT, DEBIT)
    private PaymentSource source;  // Source of payment (e.g., ONLINE, IN-STORE)
    private Long factureVenteId;   // ID of the associated FactureVente
    private Long factureAchatId;   // ID of the associated FactureAchat

    @Override
    public String toString() {
        return String.format(
                "Order[id=%d, customerId=%d, productId=%d, productCount=%d, price=%d, type=%s, source=%s, factureVenteId=%d, factureAchatId=%d]",
                id, customerId, productId, productCount, price, type, source, factureVenteId, factureAchatId);
    }
}
