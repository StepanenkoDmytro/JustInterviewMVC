package com.ourapp.SuperAppHome.controller.interview;

import com.ourapp.SuperAppHome.model.interview.Question;
import com.ourapp.SuperAppHome.model.interview.Section;
import com.ourapp.SuperAppHome.model.interview.SourceForQuestion;
import com.ourapp.SuperAppHome.repository.interview.QuestionRepository;
import com.ourapp.SuperAppHome.repository.interview.SectionRepository;
import com.ourapp.SuperAppHome.repository.interview.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/listQuestions/")
public class QuestionController {
    private final TopicRepository topicRepository;
    private final SectionRepository sectionRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionController(TopicRepository topicRepository, SectionRepository sectionRepository,
                              QuestionRepository questionRepository) {
        this.topicRepository = topicRepository;
        this.sectionRepository = sectionRepository;
        this.questionRepository = questionRepository;
    }

    @GetMapping("addQuestion")
    public String setQuestionTopic(Model model) {
        List<Section> listSection = sectionRepository.findAll();
        model.addAttribute("list_sections", listSection);
        return "interview-add-questions";
    }

    @PostMapping("addQuestion")
    public String saveQuestionTopic(@RequestParam(name = "secId") long id,
                                    @RequestParam String question,
                                    Model model){
        Question questionSec = new Question();
        questionSec.setQuestion(question);
        Section section = sectionRepository.findById(id).orElse(null);
        section.addQuestionToSection(questionSec);
        sectionRepository.save(section);
        return "redirect:/api/v1/listQuestions";
    }

    @GetMapping("editQuestion/{id}")
    public String getEditQuestionTopic(@PathVariable(name = "id") long id, Model model){
        if(!questionRepository.existsById(id)){
            return "redirect:/api/v1/listQuestions";//переписати на свою 404 сторінку
        }
        Question question = questionRepository.findById(id).get();
        model.addAttribute("question",question);
        return "interview-edit-questions";
    }

    @PostMapping("editQuestion/{id}")
    public String updateQuestionTopic(@PathVariable(name = "id") long id,
                                      @RequestParam String link){
        SourceForQuestion source = new SourceForQuestion();
        source.setLink(link);
        Question question = questionRepository.findById(id).get();
        question.addSourceToQuestion(source);
        questionRepository.save(question);
        System.out.println("I am here");
//        return "redirect:/api/v1/listQuestions/editQuestion/" + id;
        return "redirect:/api/v1/listQuestions";
    }
//    ************************Old and not check********************
//
//    @GetMapping("/v1/interview/editQuestion/{id}")
//    public String getEditQuestionTopic(@PathVariable(name = "id") long id, Model model){
//        if(!questionTopicService.existsQuestion(id)){
//            return "redirect:/v1/interview";//переписати на свою 404 сторінку
//        }
//        QuestionTopic question = questionTopicService.getQuestion(id);
//        model.addAttribute("question",question);
//
//        List<TopicOfSection> list = topicJavaDevService.getAllTopicsJavaDev();
//        model.addAttribute("list_topics", list);
//        return "interview-edit-question";
//    }
//
//    @PostMapping("/v1/interview/editQuestion/{id}")
//    public String updateQuestionTopic(@PathVariable(name = "id") long idq,
//                                      @RequestParam(name = "topicId") long id,
//                                    @RequestParam String question,
//                                    @RequestParam String answer,
//                                    Model model){
//        QuestionTopic questionTopic = new QuestionTopic();
//        questionTopic.setQuestion(question);
//        questionTopic.setAnswer(answer);
//        TopicOfSection topic = topicJavaDevService.getTopicJavaDevById(id);
//        questionTopic.setTopic(topic);
//        questionTopicService.saveQuestion(questionTopic);
//        return "redirect:/v1/interview/" + id;
//    }
//
//    @PostMapping("/v1/interview/editQuestion/{id}/remove")
//    public String removeTopicJV(@PathVariable(name = "id") long id, Model model){
//        questionTopicService.deleteQuestion(id);
//        return "redirect:/v1/interview";
//    }

}
