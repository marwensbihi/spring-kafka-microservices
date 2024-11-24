package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FactureEvent {

    private Long id;              // Unique facture ID
    private Long customerId;      // ID of the customer
    private Long productId;       // ID of the product
    private int productCount;     // Quantity of the product
    private int price;            // Total price
    private String type;          // Facture type (e.g., vente or achat)
    private String source;        // Source of facture (e.g., system)
}
