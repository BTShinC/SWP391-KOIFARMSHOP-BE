package com.example.SWP391_KOIFARMSHOP_BE.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "Orders_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ordersDetailID;


//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "order_id")
//    private Orders orders;
//
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name ="ordersdetail_id")
//    private Set<Product> product;
//
//
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name ="ordersdetail_id")
//    private Set<ProductCombo> productCombos;

}
