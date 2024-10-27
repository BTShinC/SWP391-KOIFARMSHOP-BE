package com.example.SWP391_KOIFARMSHOP_BE.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class QuestionReponse {

    private String questionID;
    private String userName;
    private String contact;
    private String topic;
    private String question;
    private String status;
}
