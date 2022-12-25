package uz.jl.springbootintro.springbootintro.services;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.jl.springbootintro.springbootintro.domains.Answer;
import uz.jl.springbootintro.springbootintro.dto.AnswerCreateDTO;
import uz.jl.springbootintro.springbootintro.repositories.AnswerRepository;

import java.util.List;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    public List<Answer> getAnswerListByQuestionsID(@NonNull Long questionID) {
        return answerRepository.findAllAnswerByQuestionID(questionID);
    }

    public Long create(@NonNull AnswerCreateDTO dto) {
        var answer = Answer.builder()
                .answer(dto.answer())
                .questionID(dto.questionID())
                .build();
        // TODO: 25/12/22 update answer count here
        answerRepository.save(answer);
        return answer.getId();
    }
}
