package org.cart.payloads;

public class AddingItemToCartPayload {
    private String variant_id;
    private Integer quantity;

    @Override
    public String toString() {
        return "AddingItemToCartPayload{" +
                "variant_id='" + variant_id + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    public String getVariant_id() {
        return variant_id;
    }

    public void setVariant_id(String variant_id) {
        this.variant_id = variant_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}

