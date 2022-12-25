package uz.jl.springbootintro.springbootintro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.jl.springbootintro.springbootintro.domains.Answer;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findAllAnswerByQuestionID(Long questionID);

}
