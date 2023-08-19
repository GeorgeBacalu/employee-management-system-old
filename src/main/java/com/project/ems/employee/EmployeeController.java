package com.project.ems.employee;

import com.project.ems.wrapper.SearchRequest;
import lombok.RequiredArgsConstructor;
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

import static com.project.ems.constants.ThymeleafViewConstants.EMPLOYEES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.EMPLOYEE_DETAILS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_EMPLOYEES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_EMPLOYEE_VIEW;
import static com.project.ems.util.PageUtil.getEndIndexCurrentPage;
import static com.project.ems.util.PageUtil.getEndIndexPageNavigation;
import static com.project.ems.util.PageUtil.getSortDirection;
import static com.project.ems.util.PageUtil.getSortField;
import static com.project.ems.util.PageUtil.getStartIndexCurrentPage;
import static com.project.ems.util.PageUtil.getStartIndexPageNavigation;

@Controller
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public String getAllEmployeesPage(Model model, @PageableDefault(sort = "id") Pageable pageable, @RequestParam(required = false, defaultValue = "") String key) {
        Page<EmployeeDto> employeeDtosPage = employeeService.findAllByKey(pageable, key);
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        String field = getSortField(pageable);
        String direction = getSortDirection(pageable);
        long nrEmployees = employeeDtosPage.getTotalElements();
        int nrPages = employeeDtosPage.getTotalPages();
        model.addAttribute("employees", employeeService.convertToEntities(employeeDtosPage.getContent()));
        model.addAttribute("nrEmployees", nrEmployees);
        model.addAttribute("nrPages", nrPages);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("key", key);
        model.addAttribute("field", field);
        model.addAttribute("direction", direction);
        model.addAttribute("startIndexCurrentPage", getStartIndexCurrentPage(page, size));
        model.addAttribute("endIndexCurrentPage", getEndIndexCurrentPage(page, size, nrEmployees));
        model.addAttribute("startIndexPageNavigation", getStartIndexPageNavigation(page, nrPages));
        model.addAttribute("endIndexPageNavigation", getEndIndexPageNavigation(page, nrPages));
        model.addAttribute("searchRequest", new SearchRequest(0, size, "", field + "," + direction));
        return EMPLOYEES_VIEW;
    }

    @PostMapping("/search")
    public String findAllByKey(@ModelAttribute SearchRequest searchRequest, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("page", searchRequest.getPage());
        redirectAttributes.addAttribute("size", searchRequest.getSize());
        redirectAttributes.addAttribute("key", searchRequest.getKey());
        redirectAttributes.addAttribute("sort", searchRequest.getSort());
        return REDIRECT_EMPLOYEES_VIEW;
    }

    @GetMapping("/{id}")
    public String getEmployeeByIdPage(Model model, @PathVariable Integer id) {
        model.addAttribute("employee", employeeService.convertToEntity(employeeService.findById(id)));
        return EMPLOYEE_DETAILS_VIEW;
    }

    @GetMapping("/save/{id}")
    public String getSaveEmployeePage(Model model, @PathVariable Integer id) {
        model.addAttribute("id", id);
        model.addAttribute("employeeDto", id == -1 ? new EmployeeDto() : employeeService.findById(id));
        return SAVE_EMPLOYEE_VIEW;
    }

    @PostMapping("/save/{id}")
    public String save(@ModelAttribute EmployeeDto employeeDto, @PathVariable Integer id) {
        if(id == -1) {
            employeeService.save(employeeDto);
        } else {
            employeeService.updateById(employeeDto, id);
        }
        return REDIRECT_EMPLOYEES_VIEW;
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id, RedirectAttributes redirectAttributes, @PageableDefault(sort = "id") Pageable pageable, @RequestParam(required = false, defaultValue = "") String key) {
        employeeService.deleteById(id);
        Page<EmployeeDto> employeeDtosPage = employeeService.findAllByKey(pageable, key);
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        String field = getSortField(pageable);
        String direction = getSortDirection(pageable);
        redirectAttributes.addAttribute("page", !employeeDtosPage.hasContent() && page > 0 ? page - 1 : page);
        redirectAttributes.addAttribute("size", size);
        redirectAttributes.addAttribute("key", key);
        redirectAttributes.addAttribute("sort", field + "," + direction);
        return REDIRECT_EMPLOYEES_VIEW;
    }
}
