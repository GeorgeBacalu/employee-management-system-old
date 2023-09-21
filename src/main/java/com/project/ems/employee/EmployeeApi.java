package com.project.ems.employee;

import com.project.ems.wrapper.PageWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface EmployeeApi {

    @Operation(summary = "Get all employees", description = "Return a list of employees", tags = "employee", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<List<EmployeeDto>> findAll();

    @Operation(summary = "Get employee by ID", description = "Return the employee with the given ID", tags = "employee", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<EmployeeDto> findById(@Parameter(name = "id", description = "ID of the employee to fetch", example = "1") Integer id);

    @Operation(summary = "Save employee", description = "Save a new employee to the database", tags = "employee", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body"),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<EmployeeDto> save(@RequestBody(description = "Employee object to save") EmployeeDto employeeDto);

    @Operation(summary = "Update employee by ID", description = "Update an existing employee with the given ID", tags = "employee", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body"),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<EmployeeDto> updateById(@RequestBody(description = "Updated employee object") EmployeeDto employeeDto,
                                           @Parameter(name = "id", description = "ID of the employee to update", example = "1") Integer id);

    @Operation(summary = "Delete employee by ID", description = "Delete an existing employee with the given ID", tags = "employee", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<Void> deleteById(@Parameter(name = "id", description = "ID of the employee to delete", example = "1") Integer id);

    @Operation(summary = "Get all employees paginated, sorted and filtered", description = "Return a list of employees paginated, sorted and filtered", tags = "employee", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<PageWrapper<EmployeeDto>> findAllByKey(@Parameter(name = "pageable", description = "Pageable object for paging and sorting") Pageable pageable,
                                                          @Parameter(name = "key", description = "Key to filter by") String key);
}
