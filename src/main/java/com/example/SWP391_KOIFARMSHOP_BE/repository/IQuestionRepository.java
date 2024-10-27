package com.example.SWP391_KOIFARMSHOP_BE.repository;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.Promotion;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IQuestionRepository extends JpaRepository<Question, String> {
    Question findTopByOrderByQuestionIDDesc();
}
