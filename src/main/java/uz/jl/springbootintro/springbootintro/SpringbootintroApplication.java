package uz.jl.springbootintro.springbootintro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class SpringbootintroApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootintroApplication.class, args);
    }
}


@Controller
class HomeController {
    List<Question> questions = new ArrayList<>() {{
        add(new Question("1", "int java", "Why int is int in java", 0, 0));
        add(new Question("2", "jvm", "Why java has three step class loading hierarchy", 0, 0));
    }};

    List<QuestionAnswers> questionAnswers = new ArrayList<>() {{
        add(new QuestionAnswers("1", "int data type in primitive data type which is not object", LocalDateTime.now(), "1"));
        add(new QuestionAnswers("2", "Are you stupid", LocalDateTime.now(), "1"));
        add(new QuestionAnswers("4", "No idea why", LocalDateTime.now(), "1"));
        add(new QuestionAnswers("3", "Because of security features", LocalDateTime.now(), "2"));
        add(new QuestionAnswers("5", "It was build like this for some private reason", LocalDateTime.now(), "2"));
    }};

    @RequestMapping("/welcome")
    public String welcome(Model model) {
        model.addAttribute("questions", questions);
        return "welcomePage";
    }

    @RequestMapping("/welcome/{id}")
    public String questionPage(Model model, @PathVariable(name = "id") String questionID) {
        Optional<Question> optionalQuestion = questions.stream()
                .filter(question -> question.id().equals(questionID))
                .findFirst();

        Question question = optionalQuestion.get();

        List<QuestionAnswers> answers = questionAnswers.stream()
                .filter(questionAnswers -> questionAnswers.questionID().equals(questionID))
                .toList();

        model.addAttribute("questionID", questionID);
        model.addAttribute("content", new QuestionDetails(question, answers));
        return "question";
    }
}


record Question(String id, String title, String body, int readCount, int answersCount) {
}

record QuestionAnswers(String id, String answer, LocalDateTime answeredAt, String questionID) {
}

record QuestionDetails(Question question, List<QuestionAnswers> answers) {
}