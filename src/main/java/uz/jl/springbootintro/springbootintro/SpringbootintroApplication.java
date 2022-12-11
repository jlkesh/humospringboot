package uz.jl.springbootintro.springbootintro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    List<QuestionAnswer> questionAnswers = new ArrayList<>() {{
        add(new QuestionAnswer("1", "int data type in primitive data type which is not object", LocalDateTime.now(), "1"));
        add(new QuestionAnswer("2", "Are you stupid", LocalDateTime.now(), "1"));
        add(new QuestionAnswer("4", "No idea why", LocalDateTime.now(), "1"));
        add(new QuestionAnswer("3", "Because of security features", LocalDateTime.now(), "2"));
        add(new QuestionAnswer("5", "It was build like this for some private reason", LocalDateTime.now(), "2"));
    }};

    @RequestMapping(value = {"/", "/home", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        model.addAttribute("questions", questions);
        return "welcome";
    }

    @RequestMapping(value = "/welcome/{id}", method = RequestMethod.GET)
    public String questionPage(Model model, @PathVariable(name = "id") String questionID) {
        Optional<Question> optionalQuestion = questions.stream()
                .filter(question -> question.id().equals(questionID))
                .findFirst();

        Question question = optionalQuestion.get();

        List<QuestionAnswer> answers = questionAnswers.stream()
                .filter(questionAnswers -> questionAnswers.questionID().equals(questionID))
                .toList();

        model.addAttribute("questionID", questionID);
        model.addAttribute("content", new QuestionDetails(question, answers));
        return "question";
    }

    @RequestMapping(value = "/leave/answer/{id}", method = RequestMethod.GET)
    public String leaveQuestionAnswerPage(Model model, @PathVariable(name = "id") String questionID) {
        Optional<Question> optionalQuestion = questions.stream()
                .filter(question -> question.id().equals(questionID))
                .findFirst();
        Question question = optionalQuestion.get();
        model.addAttribute("question", question);
        return "leave_answer";
    }

    @RequestMapping(value = "/leave/answer/{id}", method = RequestMethod.POST)
    public String leaveQuestionAnswer(@ModelAttribute LeaveAnswer leaveAnswer, @PathVariable(name = "id") String questionID) {
        QuestionAnswer questionAnswer = new QuestionAnswer(UUID.randomUUID().toString(), leaveAnswer.answer(), LocalDateTime.now(), questionID);
        questionAnswers.add(questionAnswer);
        return "redirect:/welcome/" + questionID;
    }
}


record Question(String id, String title, String body, int readCount, int answersCount) {
}

record QuestionAnswer(String id, String answer, LocalDateTime answeredAt, String questionID) {
}

record QuestionDetails(Question question, List<QuestionAnswer> answers) {
}

record LeaveAnswer(String answer) {
}