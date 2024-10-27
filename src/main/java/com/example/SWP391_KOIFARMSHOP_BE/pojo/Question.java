package com.example.SWP391_KOIFARMSHOP_BE.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Question")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @Id
    private String questionID;

    @NotBlank(message = "Username cannot be blank")
    @Size(max = 50, message = "Username cannot exceed 50 characters")
    private String userName;

    @Column(name = "contact", unique = true, nullable = false)
    @Size(max = 100, message = "Thông tin liên hệ không được quá 100 ký tự")
    @Pattern(
            regexp = "^(\\+84|0\\d{9,10}|[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6})$",
            message = "Thông tin liên hệ phải là email hoặc số điện thoại hợp lệ"
    )
    private String contact;


    @NotBlank(message = "Topic cannot be blank")
    private String topic;

    @NotBlank(message = " Question cannot be blank")
    private String question;

    @NotBlank(message = "Status cannot be blank")
    private String status;

}
