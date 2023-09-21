package com.project.ems.authority;

import java.util.List;

public interface AuthorityService {

    List<AuthorityDto> findAll();

    AuthorityDto findById(Integer id);

    AuthorityDto save(AuthorityDto authorityDto);

    AuthorityDto updateById(AuthorityDto authorityDto, Integer id);

    List<AuthorityDto> convertToDtos(List<Authority> authorities);

    List<Authority> convertToEntities(List<AuthorityDto> authorityDtos);

    AuthorityDto convertToDto(Authority authority);

    Authority convertToEntity(AuthorityDto authorityDto);

    Authority findEntityById(Integer id);
}
