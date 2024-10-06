package com.example.SWP391_KOIFARMSHOP_BE.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "Orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    @Id
    @Column( name = "order_id")
    private String orderID;
    @NotBlank(message = "Status cannot be blank")
    @Size(max = 50, message = "Status must be less than 50 characters")
    private String status;
    @PositiveOrZero(message = "Total must be zero or a positive number")
    private double total;
    @NotNull(message = "Date cannot be null")
    private Date date;
    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account ;


    @OneToOne(cascade = CascadeType.ALL, mappedBy = "orders")
    private Payment payment;


    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private Set<OrdersDetail> ordersDetail;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "feedback_id")
    private Feedback feedback;


}
