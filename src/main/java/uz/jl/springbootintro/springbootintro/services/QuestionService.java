package uz.jl.springbootintro.springbootintro.services;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.jl.springbootintro.springbootintro.domains.Question;
import uz.jl.springbootintro.springbootintro.exeptions.NotFoundException;
import uz.jl.springbootintro.springbootintro.repositories.AnswerRepository;
import uz.jl.springbootintro.springbootintro.repositories.QuestionRepository;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;


    public List<Question> getAll() {
        return questionRepository.findAll();
    }

    public Question get(@NonNull Long questionID) {
        Question question = questionRepository.findById(questionID)
                .orElseThrow(() -> {
                    throw new NotFoundException("Question with id '%d' not found".formatted(questionID));
                });
        question.setReadCount(question.getReadCount() + 1);
        questionRepository.save(question);
        return question;
    }

    public Long create(@NonNull Question question) {
        questionRepository.save(question);
        return question.getId();
    }
}
