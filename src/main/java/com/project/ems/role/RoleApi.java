package com.project.ems.role;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface RoleApi {

    @Operation(summary = "Get all roles", description = "Return a list of roles", tags = "role", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<List<RoleDto>> findAll();

    @Operation(summary = "Get role by ID", description = "Return the role with the given ID", tags = "role", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<RoleDto> findById(@Parameter(name = "id", description = "ID of the role to fetch", example = "1") Integer id);

    @Operation(summary = "Save role", description = "Save a new role to the database", tags = "role", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<RoleDto> save(@RequestBody(description = "Role object to save") RoleDto roleDto);

    @Operation(summary = "Update role by ID", description = "Update an existing role with the given ID", tags = "role", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<RoleDto> updateById(@RequestBody(description = "Updated role object") RoleDto roleDto,
                                       @Parameter(name = "id", description = "ID of the role to update", example = "1") Integer id);

    @Operation(summary = "Delete role by ID", description = "Delete an existing role with the given ID", tags = "role", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<Void> deleteById(@Parameter(name = "id", description = "ID of the role to delete", example = "1") Integer id);
}
