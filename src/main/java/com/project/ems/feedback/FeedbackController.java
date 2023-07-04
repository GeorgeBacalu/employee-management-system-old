package com.project.ems.feedback;

import com.project.ems.user.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.project.ems.mapper.FeedbackMapper.convertToEntity;
import static com.project.ems.mapper.FeedbackMapper.convertToEntityList;

@Controller
@RequiredArgsConstructor
@RequestMapping("/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping
    public String getAllFeedbacksPage(Model model) {
        model.addAttribute("feedbacks", convertToEntityList(modelMapper, feedbackService.findAll(), userService));
        return "feedback/feedbacks";
    }

    @GetMapping("/{id}")
    public String getFeedbackByIdPage(Model model, @PathVariable Integer id) {
        model.addAttribute("feedback", convertToEntity(modelMapper, feedbackService.findById(id), userService));
        return "feedback/feedback-details";
    }

    @GetMapping("/save")
    public String getSaveFeedbackPage(Model model) {
        model.addAttribute("feedbackDto", new FeedbackDto());
        return "feedback/save-feedback";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute FeedbackDto feedbackDto) {
        feedbackService.save(feedbackDto);
        return "redirect:/feedbacks";
    }

    @GetMapping("/update/{id}")
    public String getUpdateFeedbackPage(Model model, @PathVariable Integer id) {
        model.addAttribute("feedbackDto", feedbackService.findById(id));
        return "feedback/update-feedback";
    }

    @PostMapping("/update/{id}")
    public String updateById(@ModelAttribute FeedbackDto feedbackDto, @PathVariable Integer id) {
        feedbackService.updateById(feedbackDto, id);
        return "redirect:/feedbacks";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id) {
        feedbackService.deleteById(id);
        return "redirect:/feedbacks";
    }
}
