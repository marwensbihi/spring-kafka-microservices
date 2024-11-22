package domain;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    private Long id;
    private Long customerId;
    private Long productId;
    private int productCount;
    private int price;
    private OrderStatus status;
    private OrderSource source;

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getProductId() {
        return productId;
    }

    public int getProductCount() {
        return productCount;
    }

    public int getPrice() {
        return price;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public OrderSource getSource() {
        return source;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSource(OrderSource source) {
        this.source = source;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", productId=" + productId +
                ", productCount=" + productCount +
                ", price=" + price +
                ", status=" + status +
                ", source=" + source +
                '}';
    }


}