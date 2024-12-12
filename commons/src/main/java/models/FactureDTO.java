package models;

import Payload.BanqueDTO;
import Payload.ClientDTO;
import Payload.FournisseurDTO;
import Payload.ProductDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import enums.FactureType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FactureEvent {

    @JsonProperty("id")
    private String id;

    @JsonProperty("factureId")
    private Long factureId;

    @JsonProperty("customerId")
    private ClientDTO client;

    @JsonProperty("fournisseurId")
    private FournisseurDTO fournisseur;

    @JsonProperty("banqueId")
    private BanqueDTO banque;

    @JsonProperty("productId")
    private List<ProductDTO> products;

    @JsonProperty("type")
    private FactureType type;

    @JsonProperty("source")
    private String source;


}