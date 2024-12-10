package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FactureEvent {


    @JsonProperty("id")
    private String id; // Elasticsearch requires an id field

    @JsonProperty("factureId")
    private Long factureId;

    @JsonProperty("customerId")
    private Long customerId;

    @JsonProperty("fournisseurId")
    private Long fournisseurId;

    @JsonProperty("banqueId")
    private Long banqueId;

    @JsonProperty("productId")
    private Long productId;

    @JsonProperty("productCount")
    private Integer productCount;

    @JsonProperty("amount")
    private Double amount;

    @JsonProperty("type")
    private String type;

    @JsonProperty("source")
    private String source;

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Other getters and setters for the fields...
}