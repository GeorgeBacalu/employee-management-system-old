package com.project.ems.mentor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface MentorApi {

    @Operation(summary = "Get all mentors", description = "Return a list of mentors", tags = "mentor", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<List<MentorDto>> findAll();

    @Operation(summary = "Get mentor by ID", description = "Return the mentor with the given ID", tags = "mentor", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<MentorDto> findById(@Parameter(name = "id", description = "ID of the mentor to fetch", example = "1") Integer id);

    @Operation(summary = "Save mentor", description = "Save a new mentor to the database", tags = "mentor", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<MentorDto> save(@RequestBody(description = "Mentor object to save") MentorDto mentorDto);

    @Operation(summary = "Update mentor by ID", description = "Update an existing mentor with the given ID", tags = "mentor", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<MentorDto> updateById(@RequestBody(description = "Updated mentor object") MentorDto mentorDto,
                                         @Parameter(name = "id", description = "ID of the mentor to update", example = "1") Integer id);

    @Operation(summary = "Delete mentor by ID", description = "Delete an existing mentor with the given ID", tags = "mentor", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<Void> deleteById(@Parameter(name = "id", description = "ID of the mentor to delete", example = "1") Integer id);

    @Operation(summary = "Get all mentors paginated, sorted and filtered", description = "Return a list of mentors paginated, sorted and filtered", tags = "mentor", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<Page<MentorDto>> findAllByKey(@Parameter(name = "pageable", description = "Pageable object for paging and sorting") Pageable pageable,
                                                 @Parameter(name = "key", description = "Key to filter by") String key);
}
