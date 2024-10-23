package com.example.SWP391_KOIFARMSHOP_BE.repository;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.Account;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Feedback;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IFeedbackRepository extends JpaRepository<Feedback, String> {
    Feedback findTopByOrderByFeedbackIDDesc();

    Feedback findByFeedbackID(String feedbackID);
    Optional<Feedback> findByOrder(Orders order);
}
