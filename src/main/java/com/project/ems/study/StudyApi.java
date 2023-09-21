package com.project.ems.study;

import com.project.ems.wrapper.PageWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface StudyApi {

    @Operation(summary = "Get all studies", description = "Return a list of studies", tags = "study", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<List<StudyDto>> findAll();

    @Operation(summary = "Get study by ID", description = "Return the study with the given ID", tags = "study", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<StudyDto> findById(@Parameter(name = "id", description = "ID of the study to fetch", example = "1") Integer id);

    @Operation(summary = "Save study", description = "Save a new study to the database", tags = "study", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body"),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<StudyDto> save(@RequestBody(description = "Study object to save") StudyDto studyDto);

    @Operation(summary = "Update study by ID", description = "Update an existing study with the given ID", tags = "study", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body"),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<StudyDto> updateById(@RequestBody(description = "Updated study object") StudyDto studyDto,
                                        @Parameter(name = "id", description = "ID of the study to update", example = "1") Integer id);

    @Operation(summary = "Delete study by ID", description = "Delete an existing study with the given ID", tags = "study", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<Void> deleteById(@Parameter(name = "id", description = "ID of the study to delete", example = "1") Integer id);

    @Operation(summary = "Get all studies paginated, sorted and filtered", description = "Return a list of studies paginated, sorted and filtered", tags = "study", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<PageWrapper<StudyDto>> findAllByKey(@Parameter(name = "pageable", description = "Pageable object for paging and sorting") Pageable pageable,
                                                       @Parameter(name = "key", description = "Key to filter by") String key);
}
