package com.project.ems.authority;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface AuthorityApi {

    @Operation(summary = "Get all authorities", description = "Return a list of authorities", tags = "authority", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<List<AuthorityDto>> findAll();

    @Operation(summary = "Get authority by ID", description = "Return the authority with the given ID", tags = "authority", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<AuthorityDto> findById(@Parameter(name = "id", description = "ID  of the authority to fetch", example = "1") Integer id);

    @Operation(summary = "Save authority", description = "Save a new authority to the database", tags = "authority", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body"),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<AuthorityDto> save(@RequestBody(description = "Authority object to save") AuthorityDto authorityDto);

    @Operation(summary = "Update authority by ID", description = "Update an existing authority with the given ID", tags = "authority", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body"),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<AuthorityDto> updateById(@RequestBody(description = "Updated authority object") AuthorityDto authorityDto,
                                            @Parameter(name = "id", description = "ID of the authority to update", example = "1") Integer id);
}
