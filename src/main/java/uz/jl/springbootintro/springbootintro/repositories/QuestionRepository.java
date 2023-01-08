package uz.jl.springbootintro.springbootintro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.jl.springbootintro.springbootintro.domains.Question;


public interface QuestionRepository extends JpaRepository<Question, Long> {

}
