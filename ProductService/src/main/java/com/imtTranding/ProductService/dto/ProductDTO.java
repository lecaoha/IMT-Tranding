package com.imtTranding.ProductService.dto;
public class ProductDTO {
    private Integer id;
    private String nameProduct;
    private String description;
    private String type;
    private Double price;

    public ProductDTO() {
    }

    public ProductDTO(Integer id, String nameProduct, String description, String type, Double price) {
        this.id = id;
        this.nameProduct = nameProduct;
        this.description = description;
        this.type = type;
        this.price = price;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}

