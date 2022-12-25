package uz.jl.springbootintro.springbootintro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.jl.springbootintro.springbootintro.domains.Answer;
import uz.jl.springbootintro.springbootintro.domains.Question;
import uz.jl.springbootintro.springbootintro.dto.AnswerCreateDTO;
import uz.jl.springbootintro.springbootintro.dto.QuestionCreateDTO;
import uz.jl.springbootintro.springbootintro.dto.QuestionUpdateDTO;
import uz.jl.springbootintro.springbootintro.exeptions.NotFoundException;
import uz.jl.springbootintro.springbootintro.services.AnswerService;
import uz.jl.springbootintro.springbootintro.services.QuestionService;

@Controller
@RequestMapping("/question")
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

    @RequestMapping(value = "/see/{id}", method = RequestMethod.GET)
    public String questionDetailsPage(Model model, @PathVariable(name = "id") Long questionID) {
        model.addAttribute("question", questionService.get(questionID));
        model.addAttribute("answers", answerService.getAnswerListByQuestionsID(questionID));
        return "question_details";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String questionUpdatePage(Model model, @PathVariable(name = "id") Long questionID) {
        Question question = questionService.get(questionID);
        model.addAttribute("question", question);
        return "update_question";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String questionUpdate(@ModelAttribute QuestionUpdateDTO dto) {
        questionService.update(dto);
        return "redirect:/question/";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String questionDeletePage(Model model, @PathVariable(name = "id") Long questionID) {
        Question question = questionService.get(questionID);
        model.addAttribute("question_title", question.getTitle());
        return "delete_question";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String questionDelete(@PathVariable(name = "id") Long questionID) {
        questionService.delete(questionID);
        return "redirect:/question/";
    }


    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createQuestionPage() {
        return "question_create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String questionCreate(@ModelAttribute QuestionCreateDTO dto) {
        questionService.create(dto);
        return "redirect:/";
    }


    @ExceptionHandler(NotFoundException.class)
    public String notFoundPage(NotFoundException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "404";
    }
}
