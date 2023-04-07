package com.ourapp.SuperAppHome.controller.interview;

import com.ourapp.SuperAppHome.model.interview.ExamOrder;
import com.ourapp.SuperAppHome.model.interview.Question;
import com.ourapp.SuperAppHome.model.interview.Section;
import com.ourapp.SuperAppHome.model.interview.Topic;
import com.ourapp.SuperAppHome.repository.PostRepository;
import com.ourapp.SuperAppHome.repository.interview.QuestionRepository;
import com.ourapp.SuperAppHome.repository.interview.SectionRepository;
import com.ourapp.SuperAppHome.repository.interview.TopicRepository;
import com.ourapp.SuperAppHome.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/api/v1/exam")
public class DesignExamController {
    private final SectionRepository sectionRepository;
    private final TopicRepository topicRepository;
    private ExamOrder examOrder;
    List<Question> questionsExam;
    private Question questionOnExam;
    private final QuestionRepository questionRepository;
    private final PostRepository postRepository;
    private final PostService postService;

    @Autowired
    public DesignExamController(SectionRepository sectionRepository, TopicRepository topicRepository,
                                QuestionRepository questionRepository,
                                PostRepository postRepository, PostService postService) {
        this.sectionRepository = sectionRepository;
        this.topicRepository = topicRepository;
        this.questionRepository = questionRepository;
        this.postRepository = postRepository;
        this.postService = postService;
    }

    @ModelAttribute
    public void addSectionsToTopicExam(Model model){
        List<Topic> topics = topicRepository.findAll();
        model.addAttribute("topics", topics);
    }

    @ModelAttribute(name = "examOrder")
    public ExamOrder order(){
        return new ExamOrder();
    }

    @GetMapping
    public String getExamOrderPage(){
        return "exam-main";
    }

    @PostMapping
    public String examOrder(ExamOrder order){
        examOrder = order;
        questionsExam = new ArrayList<>();
        List<Section> init = new ArrayList<>();
        for(Section section : examOrder.getSectionsForExam()){
            if(sectionRepository.existsById(section.getId())) {
                init.add(sectionRepository.findBySectionName(section.getSectionName()));
            }
        }

        for(Section section : init){
            questionsExam.addAll(randomQuestionFromSection(section,1));
        }
        questionOnExam = questionRepository.findById(questionsExam.get(0).getId()).get();
        return "redirect:/api/v1/exam/start";
    }

    @GetMapping("/start")
    public String examStart(Model model){
        if(examOrder == null){
            return "redirect:/api/v1/exam";
        }

        List<Long> allPostsByTagLongL = postRepository.findAllPostsByTagNameL("#Що");
//        List<String> allPostsByTagLongS = postRepository.findAllPostsByTagNameS(questionOnExam.getQuestion());
        System.out.println(allPostsByTagLongL);

        model.addAttribute("post_id", allPostsByTagLongL);
//        model.addAttribute("post_title", allPostsByTagLongS);

        model.addAttribute("questionsExam", questionOnExam);
        model.addAttribute("exam", examOrder);
        return "exam-start";
    }

    @GetMapping("/start/next")
    public String nextQuestion(){
        questionsExam.remove(questionOnExam);
        System.out.println(questionsExam);
        if(questionsExam.isEmpty()){
            return "exam-end";
        }
        questionOnExam = questionRepository.findById(questionsExam.get(0).getId()).get();
        return "redirect:/api/v1/exam/start";
    }



    public List<Question> randomQuestionFromSection(Section section, int sizeList){
        List<Question> allQuestionFromSection = section.getQuestions();
        Set<Question> result = new HashSet<>();
        int size = section.getQuestions().size();
        while (result.size() != sizeList){
            int random = (int) (Math.random() * size);
            result.add(allQuestionFromSection.get(random));
        }
        return new ArrayList<>(result);
    }
}

