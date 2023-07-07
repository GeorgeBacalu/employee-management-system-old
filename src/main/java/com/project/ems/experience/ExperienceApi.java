package com.project.ems.experience;

import com.project.ems.wrapper.PageWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ExperienceApi {

    @Operation(summary = "Get all experiences", description = "Return a list of experiences", tags = "experience", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<List<ExperienceDto>> findAll();

    @Operation(summary = "Get experience by ID", description = "Return the experience with the given ID", tags = "experience", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<ExperienceDto> findById(@Parameter(name = "id", description = "ID of the experience to fetch", example = "1") Integer id);

    @Operation(summary = "Save experience", description = "Save a new experience to the database", tags = "experience", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<ExperienceDto> save(@RequestBody(description = "Experience object to save") ExperienceDto experienceDto);

    @Operation(summary = "Update experience by ID", description = "Update an existing experience with the given ID", tags = "experience", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<ExperienceDto> updateById(@RequestBody(description = "Updated experience object") ExperienceDto experienceDto,
                                             @Parameter(name = "id", description = "ID of the experience to update", example = "1") Integer id);

    @Operation(summary = "Delete experience by ID", description = "Delete an existing experience with the given ID", tags = "experience", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<Void> deleteById(@Parameter(name = "id", description = "ID of the experience to delete", example = "1") Integer id);

    @Operation(summary = "Get all experiences paginated, sorted and filtered", description = "Return a list of experiences paginated, sorted and filtered", tags = "experience", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<PageWrapper<ExperienceDto>> findAllByKey(@Parameter(name = "pageable", description = "Pageable object for paging and sorting") Pageable pageable,
                                                            @Parameter(name = "key", description = "Key to filter by") String key);
}
