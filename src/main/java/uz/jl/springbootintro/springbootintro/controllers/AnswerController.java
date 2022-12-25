package uz.jl.springbootintro.springbootintro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uz.jl.springbootintro.springbootintro.domains.Answer;
import uz.jl.springbootintro.springbootintro.domains.Question;
import uz.jl.springbootintro.springbootintro.dto.AnswerCreateDTO;
import uz.jl.springbootintro.springbootintro.services.AnswerService;
import uz.jl.springbootintro.springbootintro.services.QuestionService;

@Controller
@RequestMapping("/answer")
public class AnswerController {


    private final QuestionService questionService;
    private final AnswerService answerService;

    public AnswerController(QuestionService questionService, AnswerService answerService) {
        this.questionService = questionService;
        this.answerService = answerService;
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String leaveAnswerPage(Model model, @PathVariable(name = "id") Long questionID) {
        Question question = questionService.get(questionID);
        model.addAttribute("question", question);
        return "answer_create";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String leaveAnswer(@ModelAttribute AnswerCreateDTO dto) {
        answerService.create(dto);
        return "redirect:/" + dto.questionID();
    }

}
