package uz.jl.springbootintro.springbootintro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
@Controller
public class SpringbootintroApplication {


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


    public static void main(String[] args) {
        SpringApplication.run(SpringbootintroApplication.class, args);
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("questions", questions);
        return "welcome_page";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String questionPage(Model model, @PathVariable(name = "id") String questionID) {
        Question question = questions.stream()
                .filter(q -> q.id().equals(questionID))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Question with id : '%s' not found".formatted(questionID)));

        List<QuestionAnswer> answers = questionAnswers.stream()
                .filter(questionAnswers -> questionAnswers.questionID().equals(questionID))
                .toList();
        model.addAttribute("question", question);
        model.addAttribute("answers", answers);
        return "question_details";
    }

    @RequestMapping(value = "/leave/answer/{id}", method = RequestMethod.GET)
    public String leaveQuestionAnswerPage(Model model, @PathVariable(name = "id") String questionID) {
        Question question = questions.stream()
                .filter(q -> q.id().equals(questionID))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Question with id : '%s' not found".formatted(questionID)));
        model.addAttribute("question", question);
        return "answer_create";
    }

    @RequestMapping(value = "/leave/answer/{id}", method = RequestMethod.POST)
    public String leaveQuestionAnswer(@ModelAttribute AnswerCreate answerCreate, @PathVariable(name = "id") String questionID) {
        QuestionAnswer questionAnswer = new QuestionAnswer(UUID.randomUUID().toString(), answerCreate.answer(), LocalDateTime.now(), questionID);
        questionAnswers.add(questionAnswer);
        return "redirect:/" + questionID;
    }


    @RequestMapping(value = "/question/create", method = RequestMethod.GET)
    public String createQuestionPage() {
        return "question_create";
    }

    @RequestMapping(value = "/question/create", method = RequestMethod.POST)
    public String questionCreate(@ModelAttribute QuestionCreate dto) {
        Question question = new Question(UUID.randomUUID().toString(), dto.title(), dto.body(), 0, 0);
        questions.add(question);
        return "redirect:/";
    }


    @ExceptionHandler(NotFoundException.class)
    public String notFoundPage(NotFoundException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "404";
    }


}


record Question(String id, String title, String body, int readCount, int answersCount) {
}

record QuestionAnswer(String id, String answer, LocalDateTime answeredAt, String questionID) {
}

record AnswerCreate(String answer) {
}

record QuestionCreate(String title, String body) {

}


//--------------------------
class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}