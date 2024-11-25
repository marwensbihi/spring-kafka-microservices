package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FactureEvent {

    private Long factureId;              // Unique facture ID
    private Long customerId;      // ID of the customer
    private Long fournisseurId;      // ID of the Fourniseur
    private Long productId;       // ID of the product
    private int productCount;     // Quantity of the product
    private Double amount;            // Total price
    private String type;          // Facture type (e.g., vente or achat)
    private String source;        // Source of facture (e.g., system)


}
