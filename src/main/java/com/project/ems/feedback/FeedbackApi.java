package com.project.ems.feedback;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface FeedbackApi {

    @Operation(summary = "Get all feedbacks", description = "Return a list of feedbacks", tags = "feedback", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<List<FeedbackDto>> findAll();

    @Operation(summary = "Get feedback by ID", description = "Return the feedback with the given ID", tags = "feedback", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<FeedbackDto> findById(@Parameter(name = "id", description = "ID of the feedback to fetch", example = "1") Integer id);

    @Operation(summary = "Save feedback", description = "Save a new feedback to the database", tags = "feedback", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<FeedbackDto> save(@RequestBody(description = "Feedback object to save") FeedbackDto feedbackDto);

    @Operation(summary = "Update feedback by ID", description = "Update an existing feedback with the given ID", tags = "feedback", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<FeedbackDto> updateById(@RequestBody(description = "Updated feedback object") FeedbackDto feedbackDto,
                                           @Parameter(name = "id", description = "ID of the feedback to update", example = "1") Integer id);

    @Operation(summary = "Delete feedback by ID", description = "Delete an existing feedback with the given ID", tags = "feedback", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<Void> deleteById(@Parameter(name = "id", description = "ID of the feedback to delete", example = "1") Integer id);

    @Operation(summary = "Get all feedbacks paginated, sorted and filtered", description = "Return a list of feedbacks paginated, sorted and filtered", tags = "feedback", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<Page<FeedbackDto>> findAllByKey(@Parameter(name = "pageable", description = "Pageable object for paging and sorting") Pageable pageable,
                                                   @Parameter(name = "key", description = "Key to filter by") String key);
}
