package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.Feedback;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class FeedbackService implements IFeedbackService{
    @Autowired
    private IFeedbackRepository iFeedbackRepository;
    @Override
    public List<Feedback> getAllFeedback() {
        return iFeedbackRepository.findAll();
    }

    @Override
    public Feedback insertFeedback(Feedback feedback) {
        return iFeedbackRepository.save(feedback);
    }

    @Override
    public Feedback updateFeedback(long feedbackID, Feedback feedback) {
        Feedback f = iFeedbackRepository.getById(feedbackID);
        if(f != null){
            f.setDesciption(feedback.getDesciption());
            return iFeedbackRepository.save(f);
        }
        return null;
    }

    @Override
    public void deleteFeedback(long feedbackID) {
        iFeedbackRepository.deleteById(feedbackID);
    }

    @Override
    public Optional<Feedback> getFeedbackByID(long feedbackID) {
        return iFeedbackRepository.findById(feedbackID);
    }
}
