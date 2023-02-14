package org.cart.payloads;

public class AddingCartWithQuantityAsStringPayload {
    private String variant_id;
    private String quantity;

    @Override
    public String toString() {
        return "AddingItemToCartPayload{" +
                "variant_id='" + variant_id + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }

    public String getVariant_id() {
        return variant_id;
    }

    public void setVariant_id(String variant_id) {
        this.variant_id = variant_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}

