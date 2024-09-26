package com.example.SWP391_KOIFARMSHOP_BE.service;



import com.example.SWP391_KOIFARMSHOP_BE.pojo.Feedback;

import java.util.List;
import java.util.Optional;

public interface IFeedbackService {
    public List<Feedback> getAllFeedback();
    public Feedback insertFeedback(Feedback feedback);
    public Feedback updateFeedback(long feedbackID, Feedback feedback);
    public void deleteFeedback (long feedbackID);
    public Optional<Feedback> getFeedbackByID(long feedbackID);
}
