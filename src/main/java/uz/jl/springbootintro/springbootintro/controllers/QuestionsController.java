package uz.jl.springbootintro.springbootintro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.jl.springbootintro.springbootintro.domains.Answer;
import uz.jl.springbootintro.springbootintro.domains.Question;
import uz.jl.springbootintro.springbootintro.dto.AnswerCreateDTO;
import uz.jl.springbootintro.springbootintro.dto.QuestionCreateDTO;
import uz.jl.springbootintro.springbootintro.exeptions.NotFoundException;
import uz.jl.springbootintro.springbootintro.services.AnswerService;
import uz.jl.springbootintro.springbootintro.services.QuestionService;

@Controller
public class QuestionsController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("questions", questionService.getAll());
        return "welcome_page";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String questionPage(Model model, @PathVariable(name = "id") Long questionID) {
        model.addAttribute("question", questionService.get(questionID));
        model.addAttribute("answers", answerService.getAnswerListByQuestionsID(questionID));
        return "question_details";
    }

    @RequestMapping(value = "/leave/answer/{id}", method = RequestMethod.GET)
    public String leaveQuestionAnswerPage(Model model, @PathVariable(name = "id") Long questionID) {
        Question question = questionService.get(questionID);
        model.addAttribute("question", question);
        return "answer_create";
    }

    @RequestMapping(value = "/leave/answer/{id}", method = RequestMethod.POST)
    public String leaveQuestionAnswer(@ModelAttribute AnswerCreateDTO dto, @PathVariable(name = "id") Long questionID) {
        Answer answer = Answer
                .builder()
                .answer(dto.answer())
                .questionID(questionID)
                .build();
        Question question = questionService.get(questionID);
        question.setAnswersCount(question.getAnswersCount()+1);
        questionService.create(question);
        answerService.create(answer);
        return "redirect:/" + questionID;
    }


    @RequestMapping(value = "/question/create", method = RequestMethod.GET)
    public String createQuestionPage() {
        return "question_create";
    }

    @RequestMapping(value = "/question/create", method = RequestMethod.POST)
    public String questionCreate(@ModelAttribute QuestionCreateDTO dto) {
        Question question = Question
                .builder()
                .title(dto.title())
                .body(dto.body())
                .build();
        questionService.create(question);
        return "redirect:/";
    }

    @ExceptionHandler(NotFoundException.class)
    public String notFoundPage(NotFoundException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "404";
    }
}
