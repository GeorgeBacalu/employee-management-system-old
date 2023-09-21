package com.project.ems.authority;

import com.project.ems.exception.ResourceNotFoundException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static com.project.ems.constants.ExceptionMessageConstants.AUTHORITY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<AuthorityDto> findAll() {
        List<Authority> authorities = authorityRepository.findAll();
        return !authorities.isEmpty() ? convertToDtos(authorities) : new ArrayList<>();
    }

    @Override
    public AuthorityDto findById(Integer id) {
        Authority authority = findEntityById(id);
        return convertToDto(authority);
    }

    @Override
    public AuthorityDto save(AuthorityDto authorityDto) {
        Authority authority = convertToEntity(authorityDto);
        Authority savedAuthority = authorityRepository.save(authority);
        return convertToDto(savedAuthority);
    }

    @Override
    public AuthorityDto updateById(AuthorityDto authorityDto, Integer id) {
        Authority authorityToUpdate = findEntityById(id);
        updateEntityFromDto(authorityDto, authorityToUpdate);
        Authority updatedAuthority = authorityRepository.save(authorityToUpdate);
        return convertToDto(updatedAuthority);
    }

    @Override
    public List<AuthorityDto> convertToDtos(List<Authority> authorities) {
        return authorities.stream().map(this::convertToDto).toList();
    }

    @Override
    public List<Authority> convertToEntities(List<AuthorityDto> authorityDtos) {
        return authorityDtos.stream().map(this::convertToEntity).toList();
    }

    @Override
    public AuthorityDto convertToDto(Authority authority) {
        return modelMapper.map(authority, AuthorityDto.class);
    }

    @Override
    public Authority convertToEntity(AuthorityDto authorityDto) {
        return modelMapper.map(authorityDto, Authority.class);
    }

    @Override
    public Authority findEntityById(Integer id) {
        return authorityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(AUTHORITY_NOT_FOUND, id)));
    }

    private void updateEntityFromDto(AuthorityDto authorityDto, Authority authority) {
        authority.setType(authorityDto.getType());
    }
}
