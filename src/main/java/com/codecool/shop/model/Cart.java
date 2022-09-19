package com.codecool.shop.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<Product> selectedProducts;
    private BigDecimal value;

    public Cart() {
        this.selectedProducts = new ArrayList<>();
        this.value = new BigDecimal("0");
    }

    public List<Product> getSelectedProducts() {
        return selectedProducts;
    }

    public void setSelectedProducts(List<Product> selectedProducts) {
        this.selectedProducts = selectedProducts;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public void addProduct(Product product) {
        selectedProducts.add(product);
        addValue(product);

    }

    public void addValue(Product product) {
        value.add(product.getDefaultPrice());
    }

    public void removeProduct(Product product) {
        for (Product selectedProduct : selectedProducts) {
            if (selectedProduct.getName().equals(product.getName())) {
                selectedProducts.remove(selectedProduct);
            }
        }
    }

    public void increaseProductQuantity(Product product) {
        for (Product selectedProduct : selectedProducts) {
            if (selectedProduct.getName().equals(product.getName())) {
                selectedProducts.add(new Product(
                        selectedProduct.getName()
                        , selectedProduct.getDefaultPrice()
                        , selectedProduct.getDefaultCurrency().toString()
                        , selectedProduct.getDescription()
                        , selectedProduct.getProductCategory()
                        , selectedProduct.getSupplier()));
            }
        }

    }

    public void decreaseProductQuantity(Product product) {
        for (Product selectedProduct : selectedProducts) {
            if (selectedProduct.getName().equals(product.getName())) {
                selectedProducts.remove(selectedProduct);
                return;
            }
        }
    }


}
