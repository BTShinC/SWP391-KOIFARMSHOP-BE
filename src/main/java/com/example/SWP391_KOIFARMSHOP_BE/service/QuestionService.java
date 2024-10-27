package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.exception.EntityNotFoundException;
import com.example.SWP391_KOIFARMSHOP_BE.model.QuestionReponse;
import com.example.SWP391_KOIFARMSHOP_BE.model.QuestionRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.TransactionReponse;
import com.example.SWP391_KOIFARMSHOP_BE.model.TransactionRequest;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.ProductCombo;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Question;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Transaction;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IQuestionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionService {
      @Autowired
      IQuestionRepository iQuestionRepository;
      @Autowired
      ModelMapper modelMapper;

    private  String generateTransactionID() {
        Question question = iQuestionRepository.findTopByOrderByQuestionIDDesc();
        if (question != null) {
            String lastId = question.getQuestionID();
            int idNumber = Integer.parseInt(lastId.substring(1));
            String nextId = String.format("Q%03d", idNumber + 1);
            return nextId;
        } else {
            return "Q001";
        }
    }
    public Question create(QuestionRequest questionRequest) {

        Question q = new Question();
        q.setQuestionID(generateTransactionID());
        q.setUserName(questionRequest.getUserName());
        q.setContact(questionRequest.getContact());
        q.setQuestion(questionRequest.getQuestion());
        q.setTopic(questionRequest.getTopic());
        q.setStatus(questionRequest.getStatus());
        Question savedQuestion = iQuestionRepository.save(q);

        return savedQuestion;
    }

    public List<QuestionReponse> getAllQuestion() {
        List<Question> questions = iQuestionRepository.findAll();
        return questions.stream()
                .map(question -> modelMapper.map(question, QuestionReponse.class))
                .collect(Collectors.toList());
    }

    public QuestionReponse updateStatus(String questionID, String newStatus) {
        Question question = iQuestionRepository.findById(questionID)
                .orElseThrow(() -> new EntityNotFoundException("Product Combo with ID " + questionID + " not found"));
        question.setStatus(newStatus);

        Question updatedQuestion = iQuestionRepository.save(question);


        return modelMapper.map(updatedQuestion, QuestionReponse.class);
    }
}
