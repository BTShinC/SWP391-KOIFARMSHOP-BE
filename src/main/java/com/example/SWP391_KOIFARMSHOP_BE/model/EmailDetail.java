package com.example.SWP391_KOIFARMSHOP_BE.model;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.Account;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailDetail {
    Account receiver;
    String subject;
    String link;
}
