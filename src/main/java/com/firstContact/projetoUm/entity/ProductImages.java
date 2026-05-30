package com.firstContact.projetoUm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "tb_productsImages")
public class ProductImages implements Serializable {
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String image_Key;
    private String image_Url;
    private Integer position;

    @ManyToOne
    @JoinColumn(name = "product_id")//campo na tabela imagem é definido aqui
    @JsonIgnore
    private Product product;

    public ProductImages() {}

    public ProductImages(Long id, String image_Key, String image_Url, Integer position) {
        this.id = id;
        this.image_Key = image_Key;
        this.image_Url = image_Url;
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage_Key() {
        return image_Key;
    }

    public void setImage_Key(String image_Key) {
        this.image_Key = image_Key;
    }

    public String getImage_Url() {
        return image_Url;
    }

    public void setImage_Url(String image_Url) {
        this.image_Url = image_Url;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product){
        this.product = product;
    }
}
