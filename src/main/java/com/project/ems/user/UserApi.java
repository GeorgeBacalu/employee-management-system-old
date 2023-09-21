package com.project.ems.user;

import com.project.ems.wrapper.PageWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface UserApi {

    @Operation(summary = "Get all users", description = "Return a list of users", tags = "user", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<List<UserDto>> findAll();

    @Operation(summary = "Get user by ID", description = "Return the user with the given ID", tags = "user", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<UserDto> findById(@Parameter(name = "id", description = "ID of the user to fetch", example = "1") Integer id);

    @Operation(summary = "Save user", description = "Save a new user to the database", tags = "user", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body"),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<UserDto> save(@RequestBody(description = "User object to save") UserDto userDto);

    @Operation(summary = "Update user by ID", description = "Update an existing user with the given ID", tags = "user", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body"),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<UserDto> updateById(@RequestBody(description = "Updated user object") UserDto userDto,
                                       @Parameter(name = "id", description = "ID of the user to update", example = "1") Integer id);

    @Operation(summary = "Delete user by ID", description = "Delete an existing user with the given ID", tags = "user", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<Void> deleteById(@Parameter(name = "id", description = "ID of the user to delete", example = "1") Integer id);

    @Operation(summary = "Get all users paginated, sorted and filtered", description = "Return a list of users paginated, sorted and filtered", tags = "user", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<PageWrapper<UserDto>> findAllByKey(@Parameter(name = "pageable", description = "Pageable object for paging and sorting") Pageable pageable,
                                                      @Parameter(name = "key", description = "Key to filter by") String key);
}
