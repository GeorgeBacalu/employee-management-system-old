package com.project.ems.feedback;

import com.project.ems.user.UserService;
import com.project.ems.wrapper.SearchRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.project.ems.constants.ThymeleafViewConstants.FEEDBACKS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.FEEDBACK_DETAILS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_FEEDBACKS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_FEEDBACK_VIEW;
import static com.project.ems.mapper.FeedbackMapper.convertToEntity;
import static com.project.ems.mapper.FeedbackMapper.convertToEntityList;
import static com.project.ems.util.PageUtil.getEndIndexCurrentPage;
import static com.project.ems.util.PageUtil.getEndIndexPageNavigation;
import static com.project.ems.util.PageUtil.getSortDirection;
import static com.project.ems.util.PageUtil.getSortField;
import static com.project.ems.util.PageUtil.getStartIndexCurrentPage;
import static com.project.ems.util.PageUtil.getStartIndexPageNavigation;

@Controller
@RequiredArgsConstructor
@RequestMapping("/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping
    public String getAllFeedbacksPage(Model model, @PageableDefault(sort = "id") Pageable pageable, @RequestParam(required = false, defaultValue = "") String key) {
        Page<FeedbackDto> feedbackDtosPage = feedbackService.findAllByKey(pageable, key);
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        String field = getSortField(pageable);
        String direction = getSortDirection(pageable);
        long nrFeedbacks = feedbackDtosPage.getTotalElements();
        int nrPages = feedbackDtosPage.getTotalPages();
        model.addAttribute("feedbacks", convertToEntityList(modelMapper, feedbackDtosPage.getContent(), userService));
        model.addAttribute("nrFeedbacks", nrFeedbacks);
        model.addAttribute("nrPages", nrPages);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("key", key);
        model.addAttribute("field", field);
        model.addAttribute("direction", direction);
        model.addAttribute("startIndexCurrentPage", getStartIndexCurrentPage(page, size));
        model.addAttribute("endIndexCurrentPage", getEndIndexCurrentPage(page, size, nrFeedbacks));
        model.addAttribute("startIndexPageNavigation", getStartIndexPageNavigation(page, nrPages));
        model.addAttribute("endIndexPageNavigation", getEndIndexPageNavigation(page, nrPages));
        model.addAttribute("searchRequest", new SearchRequest(0, size, "", field + "," + direction));
        return FEEDBACKS_VIEW;
    }

    @PostMapping("/search")
    public String findAllByKey(@ModelAttribute SearchRequest searchRequest, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("page", searchRequest.getPage());
        redirectAttributes.addAttribute("size", searchRequest.getSize());
        redirectAttributes.addAttribute("key", searchRequest.getKey());
        redirectAttributes.addAttribute("sort", searchRequest.getSort());
        return REDIRECT_FEEDBACKS_VIEW;
    }

    @GetMapping("/{id}")
    public String getFeedbackByIdPage(Model model, @PathVariable Integer id) {
        model.addAttribute("feedback", convertToEntity(modelMapper, feedbackService.findById(id), userService));
        return FEEDBACK_DETAILS_VIEW;
    }

    @GetMapping("/save/{id}")
    public String getSaveFeedbackPage(Model model, @PathVariable Integer id) {
        model.addAttribute("id", id);
        model.addAttribute("feedbackDto", id == -1 ? new FeedbackDto() : feedbackService.findById(id));
        return SAVE_FEEDBACK_VIEW;
    }

    @PostMapping("/save/{id}")
    public String save(@ModelAttribute FeedbackDto feedbackDto, @PathVariable Integer id) {
        if(id == -1) {
            feedbackService.save(feedbackDto);
        } else {
            feedbackService.updateById(feedbackDto, id);
        }
        return REDIRECT_FEEDBACKS_VIEW;
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id, RedirectAttributes redirectAttributes, @PageableDefault(sort = "id") Pageable pageable, @RequestParam(required = false, defaultValue = "") String key) {
        feedbackService.deleteById(id);
        Page<FeedbackDto> feedbackDtosPage = feedbackService.findAllByKey(pageable, key);
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        String field = getSortField(pageable);
        String direction = getSortDirection(pageable);
        redirectAttributes.addAttribute("page", !feedbackDtosPage.hasContent() && page > 0 ? page - 1 : page);
        redirectAttributes.addAttribute("size", size);
        redirectAttributes.addAttribute("key", key);
        redirectAttributes.addAttribute("sort", field + "," + direction);
        return REDIRECT_FEEDBACKS_VIEW;
    }
}
