package com.project.ems.study;

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

import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_STUDIES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_STUDY_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.STUDIES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.STUDY_DETAILS_VIEW;
import static com.project.ems.mapper.StudyMapper.convertToEntity;
import static com.project.ems.mapper.StudyMapper.convertToEntityList;
import static com.project.ems.util.PageUtil.getEndIndexCurrentPage;
import static com.project.ems.util.PageUtil.getEndIndexPageNavigation;
import static com.project.ems.util.PageUtil.getSortDirection;
import static com.project.ems.util.PageUtil.getSortField;
import static com.project.ems.util.PageUtil.getStartIndexCurrentPage;
import static com.project.ems.util.PageUtil.getStartIndexPageNavigation;

@Controller
@RequiredArgsConstructor
@RequestMapping("/studies")
public class StudyController {

    private final StudyService studyService;
    private final ModelMapper modelMapper;

    @GetMapping
    public String getAllStudiesPage(Model model, @PageableDefault(sort = "id") Pageable pageable, @RequestParam(required = false, defaultValue = "") String key) {
        Page<StudyDto> studyDtosPage = studyService.findAllByKey(pageable, key);
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        String field = getSortField(pageable);
        String direction = getSortDirection(pageable);
        long nrStudies = studyDtosPage.getTotalElements();
        int nrPages = studyDtosPage.getTotalPages();
        model.addAttribute("studies", convertToEntityList(modelMapper, studyDtosPage.getContent()));
        model.addAttribute("nrStudies", nrStudies);
        model.addAttribute("nrPages", nrPages);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("key", key);
        model.addAttribute("field", field);
        model.addAttribute("direction", direction);
        model.addAttribute("startIndexCurrentPage", getStartIndexCurrentPage(page, size));
        model.addAttribute("endIndexCurrentPage", getEndIndexCurrentPage(page, size, nrStudies));
        model.addAttribute("startIndexPageNavigation", getStartIndexPageNavigation(page, nrPages));
        model.addAttribute("endIndexPageNavigation", getEndIndexPageNavigation(page, nrPages));
        model.addAttribute("searchRequest", new SearchRequest(0, size, "", field + "," + direction));
        return STUDIES_VIEW;
    }

    @PostMapping("/search")
    public String findAllByKey(@ModelAttribute SearchRequest searchRequest, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("page", searchRequest.getPage());
        redirectAttributes.addAttribute("size", searchRequest.getSize());
        redirectAttributes.addAttribute("key", searchRequest.getKey());
        redirectAttributes.addAttribute("sort", searchRequest.getSort());
        return REDIRECT_STUDIES_VIEW;
    }

    @GetMapping("/{id}")
    public String getStudyByIdPage(Model model, @PathVariable Integer id) {
        model.addAttribute("study", convertToEntity(modelMapper, studyService.findById(id)));
        return STUDY_DETAILS_VIEW;
    }

    @GetMapping("/save/{id}")
    public String getSaveStudyPage(Model model, @PathVariable Integer id) {
        model.addAttribute("id", id);
        model.addAttribute("studyDto", id == -1 ? new StudyDto() : studyService.findById(id));
        return SAVE_STUDY_VIEW;
    }

    @PostMapping("/save/{id}")
    public String save(@ModelAttribute StudyDto studyDto, @PathVariable Integer id) {
        if(id == -1) {
            studyService.save(studyDto);
        } else {
            studyService.updateById(studyDto, id);
        }
        return REDIRECT_STUDIES_VIEW;
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id, RedirectAttributes redirectAttributes, @PageableDefault(sort = "id") Pageable pageable, @RequestParam(required = false, defaultValue = "") String key) {
        studyService.deleteById(id);
        Page<StudyDto> studyDtosPage = studyService.findAllByKey(pageable, key);
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        String field = getSortField(pageable);
        String direction = getSortDirection(pageable);
        redirectAttributes.addAttribute("page", !studyDtosPage.hasContent() && page > 0 ? page - 1 : page);
        redirectAttributes.addAttribute("size", size);
        redirectAttributes.addAttribute("key", key);
        redirectAttributes.addAttribute("sort", field + "," + direction);
        return REDIRECT_STUDIES_VIEW;
    }
}
