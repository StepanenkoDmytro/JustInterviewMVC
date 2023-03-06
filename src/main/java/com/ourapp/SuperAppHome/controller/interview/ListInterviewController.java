package com.ourapp.SuperAppHome.controller.interview;

import com.ourapp.SuperAppHome.model.interview.Section;
import com.ourapp.SuperAppHome.model.interview.Topic;
import com.ourapp.SuperAppHome.repository.interview.SectionRepository;
import com.ourapp.SuperAppHome.repository.interview.TopicRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/listQuestions")
public class ListInterviewController {
    private final TopicRepository topicRepository;
    private final SectionRepository sectionRepository;

    public ListInterviewController(TopicRepository topicRepository,
                               SectionRepository sectionRepository) {
        this.topicRepository = topicRepository;
        this.sectionRepository = sectionRepository;
    }

    @GetMapping("")
    public String getAllTopics(Model model){
        List<Topic> allTopics = topicRepository.findAll();
        model.addAttribute("allTopics", allTopics);
        return "interview-main";
    }

    @GetMapping("/addTopic")
    public String addNewTopic(){
        return "interview-add-topics";
    }

    @PostMapping("/addTopic")
    public String addTopic(@RequestParam String name,
                           @RequestParam String description,
                           Model model){
        Topic topic = new Topic();
        topic.setName(name);
        topic.setDescription(description);
        topicRepository.save(topic);
        return "redirect:/api/v1/interview";
    }

    @GetMapping("/addSection")
    public String addNewPost(Model model){
        List<Topic> list = topicRepository.findAll();
        model.addAttribute("list_topics", list);
        return "interview-add-sections";
    }

    @PostMapping("/addSection")
    public String savePost(@RequestParam(name = "topicId") long id,
                           @RequestParam String name,
                           @RequestParam String description,
                           Model model){
        Topic topic = topicRepository.findById(id).orElse(null);
        Section section = new Section();
        section.setSectionName(name);
        section.setSectionDesc(description);
        topic.addSectionToTopic(section);
        topicRepository.save(topic);
        return "redirect:/api/v1/interview";
    }

    @GetMapping("/{id}")
    public String getTopicById(@PathVariable(value = "id") long id, Model model){
        if(!topicRepository.existsById(id)){
            return "redirect:/api/v1/interview";//переписати на свою 404 сторінку
        }
        Optional<Topic> topic = topicRepository.findById(id);

        model.addAttribute("topic",topic.orElse(null));


        return "interview-details";
    }
}
