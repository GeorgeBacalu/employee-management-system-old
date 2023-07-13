package com.project.ems.integration.user;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.role.Role;
import com.project.ems.role.RoleService;
import com.project.ems.user.User;
import com.project.ems.user.UserController;
import com.project.ems.user.UserDto;
import com.project.ems.user.UserService;
import com.project.ems.wrapper.SearchRequest;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.project.ems.constants.EndpointConstants.USERS;
import static com.project.ems.constants.ExceptionMessageConstants.USER_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.USER_FILTER_KEY;
import static com.project.ems.constants.PaginationConstants.pageable;
import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_USERS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_USER_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.TEXT_HTML_UTF8;
import static com.project.ems.constants.ThymeleafViewConstants.USERS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.USER_DETAILS_VIEW;
import static com.project.ems.mock.RoleMock.getMockedRole1;
import static com.project.ems.mock.RoleMock.getMockedRole2;
import static com.project.ems.mock.UserMock.*;
import static com.project.ems.util.PageUtil.getEndIndexCurrentPage;
import static com.project.ems.util.PageUtil.getEndIndexPageNavigation;
import static com.project.ems.util.PageUtil.getSortDirection;
import static com.project.ems.util.PageUtil.getSortField;
import static com.project.ems.util.PageUtil.getStartIndexCurrentPage;
import static com.project.ems.util.PageUtil.getStartIndexPageNavigation;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(UserController.class)
class UserControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private RoleService roleService;

    @MockBean
    private ModelMapper modelMapper;

    private User user1;
    private User user2;
    private User user3;
    private User user4;
    private User user5;
    private User user6;
    private User user7;
    private User user8;
    private User user9;
    private User user10;
    private User user11;
    private User user12;
    private User user13;
    private User user14;
    private User user15;
    private User user16;
    private User user17;
    private User user18;
    private User user19;
    private User user20;
    private User user21;
    private User user22;
    private User user23;
    private User user24;
    private User user25;
    private User user26;
    private User user27;
    private User user28;
    private User user29;
    private User user30;
    private User user31;
    private User user32;
    private User user33;
    private User user34;
    private User user35;
    private User user36;
    private User user37;
    private User user38;
    private User user39;
    private User user40;
    private User user41;
    private User user42;
    private User user43;
    private User user44;
    private User user45;
    private User user46;
    private User user47;
    private User user48;
    private User user49;
    private User user50;
    private User user51;
    private User user52;
    private User user53;
    private User user54;
    private User user55;
    private User user56;
    private User user57;
    private User user58;
    private User user59;
    private User user60;
    private User user61;
    private User user62;
    private User user63;
    private User user64;
    private User user65;
    private User user66;
    private User user67;
    private User user68;
    private User user69;
    private User user70;
    private User user71;
    private User user72;
    private List<User> users;
    private List<User> usersFirstPage;
    private Role role1;
    private Role role2;
    private UserDto userDto1;
    private UserDto userDto2;
    private UserDto userDto3;
    private UserDto userDto4;
    private UserDto userDto5;
    private UserDto userDto6;
    private UserDto userDto7;
    private UserDto userDto8;
    private UserDto userDto9;
    private UserDto userDto10;
    private UserDto userDto11;
    private UserDto userDto12;
    private UserDto userDto13;
    private UserDto userDto14;
    private UserDto userDto15;
    private UserDto userDto16;
    private UserDto userDto17;
    private UserDto userDto18;
    private UserDto userDto19;
    private UserDto userDto20;
    private UserDto userDto21;
    private UserDto userDto22;
    private UserDto userDto23;
    private UserDto userDto24;
    private UserDto userDto25;
    private UserDto userDto26;
    private UserDto userDto27;
    private UserDto userDto28;
    private UserDto userDto29;
    private UserDto userDto30;
    private UserDto userDto31;
    private UserDto userDto32;
    private UserDto userDto33;
    private UserDto userDto34;
    private UserDto userDto35;
    private UserDto userDto36;
    private UserDto userDto37;
    private UserDto userDto38;
    private UserDto userDto39;
    private UserDto userDto40;
    private UserDto userDto41;
    private UserDto userDto42;
    private UserDto userDto43;
    private UserDto userDto44;
    private UserDto userDto45;
    private UserDto userDto46;
    private UserDto userDto47;
    private UserDto userDto48;
    private UserDto userDto49;
    private UserDto userDto50;
    private UserDto userDto51;
    private UserDto userDto52;
    private UserDto userDto53;
    private UserDto userDto54;
    private UserDto userDto55;
    private UserDto userDto56;
    private UserDto userDto57;
    private UserDto userDto58;
    private UserDto userDto59;
    private UserDto userDto60;
    private UserDto userDto61;
    private UserDto userDto62;
    private UserDto userDto63;
    private UserDto userDto64;
    private UserDto userDto65;
    private UserDto userDto66;
    private UserDto userDto67;
    private UserDto userDto68;
    private UserDto userDto69;
    private UserDto userDto70;
    private UserDto userDto71;
    private UserDto userDto72;
    private List<UserDto> userDtos;
    private List<UserDto> userDtosFirstPage;

    @BeforeEach
    void setUp() {
        user1 = getMockedUser1();
        user2 = getMockedUser2();
        user3 = getMockedUser3();
        user4 = getMockedUser4();
        user5 = getMockedUser5();
        user6 = getMockedUser6();
        user7 = getMockedUser7();
        user8 = getMockedUser8();
        user9 = getMockedUser9();
        user10 = getMockedUser10();
        user11 = getMockedUser11();
        user12 = getMockedUser12();
        user13 = getMockedUser13();
        user14 = getMockedUser14();
        user15 = getMockedUser15();
        user16 = getMockedUser16();
        user17 = getMockedUser17();
        user18 = getMockedUser18();
        user19 = getMockedUser19();
        user20 = getMockedUser20();
        user21 = getMockedUser21();
        user22 = getMockedUser22();
        user23 = getMockedUser23();
        user24 = getMockedUser24();
        user25 = getMockedUser25();
        user26 = getMockedUser26();
        user27 = getMockedUser27();
        user28 = getMockedUser28();
        user29 = getMockedUser29();
        user30 = getMockedUser30();
        user31 = getMockedUser31();
        user32 = getMockedUser32();
        user33 = getMockedUser33();
        user34 = getMockedUser34();
        user35 = getMockedUser35();
        user36 = getMockedUser36();
        user37 = getMockedUser37();
        user38 = getMockedUser38();
        user39 = getMockedUser39();
        user40 = getMockedUser40();
        user41 = getMockedUser41();
        user42 = getMockedUser42();
        user43 = getMockedUser43();
        user44 = getMockedUser44();
        user45 = getMockedUser45();
        user46 = getMockedUser46();
        user47 = getMockedUser47();
        user48 = getMockedUser48();
        user49 = getMockedUser49();
        user50 = getMockedUser50();
        user51 = getMockedUser51();
        user52 = getMockedUser52();
        user53 = getMockedUser53();
        user54 = getMockedUser54();
        user55 = getMockedUser55();
        user56 = getMockedUser56();
        user57 = getMockedUser57();
        user58 = getMockedUser58();
        user59 = getMockedUser59();
        user60 = getMockedUser60();
        user61 = getMockedUser61();
        user62 = getMockedUser62();
        user63 = getMockedUser63();
        user64 = getMockedUser64();
        user65 = getMockedUser65();
        user66 = getMockedUser66();
        user67 = getMockedUser67();
        user68 = getMockedUser68();
        user69 = getMockedUser69();
        user70 = getMockedUser70();
        user71 = getMockedUser71();
        user72 = getMockedUser72();
        users = getMockedUsers();
        usersFirstPage = List.of(user1, user2, user3, user4, user5, user6, user7, user8, user9);
        role1 = getMockedRole1();
        role2 = getMockedRole2();
        userDto1 = convertToDto(user1);
        userDto2 = convertToDto(user2);
        userDto3 = convertToDto(user3);
        userDto4 = convertToDto(user4);
        userDto5 = convertToDto(user5);
        userDto6 = convertToDto(user6);
        userDto7 = convertToDto(user7);
        userDto8 = convertToDto(user8);
        userDto9 = convertToDto(user9);
        userDto10 = convertToDto(user10);
        userDto11 = convertToDto(user11);
        userDto12 = convertToDto(user12);
        userDto13 = convertToDto(user13);
        userDto14 = convertToDto(user14);
        userDto15 = convertToDto(user15);
        userDto16 = convertToDto(user16);
        userDto17 = convertToDto(user17);
        userDto18 = convertToDto(user18);
        userDto19 = convertToDto(user19);
        userDto20 = convertToDto(user20);
        userDto21 = convertToDto(user21);
        userDto22 = convertToDto(user22);
        userDto23 = convertToDto(user23);
        userDto24 = convertToDto(user24);
        userDto25 = convertToDto(user25);
        userDto26 = convertToDto(user26);
        userDto27 = convertToDto(user27);
        userDto28 = convertToDto(user28);
        userDto29 = convertToDto(user29);
        userDto30 = convertToDto(user30);
        userDto31 = convertToDto(user31);
        userDto32 = convertToDto(user32);
        userDto33 = convertToDto(user33);
        userDto34 = convertToDto(user34);
        userDto35 = convertToDto(user35);
        userDto36 = convertToDto(user36);
        userDto37 = convertToDto(user37);
        userDto38 = convertToDto(user38);
        userDto39 = convertToDto(user39);
        userDto40 = convertToDto(user40);
        userDto41 = convertToDto(user41);
        userDto42 = convertToDto(user42);
        userDto43 = convertToDto(user43);
        userDto44 = convertToDto(user44);
        userDto45 = convertToDto(user45);
        userDto46 = convertToDto(user46);
        userDto47 = convertToDto(user47);
        userDto48 = convertToDto(user48);
        userDto49 = convertToDto(user49);
        userDto50 = convertToDto(user50);
        userDto51 = convertToDto(user51);
        userDto52 = convertToDto(user52);
        userDto53 = convertToDto(user53);
        userDto54 = convertToDto(user54);
        userDto55 = convertToDto(user55);
        userDto56 = convertToDto(user56);
        userDto57 = convertToDto(user57);
        userDto58 = convertToDto(user58);
        userDto59 = convertToDto(user59);
        userDto60 = convertToDto(user60);
        userDto61 = convertToDto(user61);
        userDto62 = convertToDto(user62);
        userDto63 = convertToDto(user63);
        userDto64 = convertToDto(user64);
        userDto65 = convertToDto(user65);
        userDto66 = convertToDto(user66);
        userDto67 = convertToDto(user67);
        userDto68 = convertToDto(user68);
        userDto69 = convertToDto(user69);
        userDto70 = convertToDto(user70);
        userDto71 = convertToDto(user71);
        userDto72 = convertToDto(user72);
        userDtos = List.of(userDto1, userDto2, userDto3, userDto4, userDto5, userDto6, userDto7, userDto8, userDto9, userDto10, userDto11, userDto12,
                           userDto13, userDto14, userDto15, userDto16, userDto17, userDto18, userDto19, userDto20, userDto21, userDto22, userDto23, userDto24,
                           userDto25, userDto26, userDto27, userDto28, userDto29, userDto30, userDto31, userDto32, userDto33, userDto34, userDto35, userDto36,
                           userDto37, userDto38, userDto39, userDto40, userDto41, userDto42, userDto43, userDto44, userDto45, userDto46, userDto47, userDto48,
                           userDto49, userDto50, userDto51, userDto52, userDto53, userDto54, userDto55, userDto56, userDto57, userDto58, userDto59, userDto60,
                           userDto61, userDto62, userDto63, userDto64, userDto65, userDto66, userDto67, userDto68, userDto69, userDto70, userDto71, userDto72);
        userDtosFirstPage = List.of(userDto1, userDto2, userDto3, userDto4, userDto5, userDto6, userDto7, userDto8, userDto9);

        given(modelMapper.map(userDto1, User.class)).willReturn(user1);
        given(modelMapper.map(userDto2, User.class)).willReturn(user2);
        given(modelMapper.map(userDto3, User.class)).willReturn(user3);
        given(modelMapper.map(userDto4, User.class)).willReturn(user4);
        given(modelMapper.map(userDto5, User.class)).willReturn(user5);
        given(modelMapper.map(userDto6, User.class)).willReturn(user6);
        given(modelMapper.map(userDto7, User.class)).willReturn(user7);
        given(modelMapper.map(userDto8, User.class)).willReturn(user8);
        given(modelMapper.map(userDto9, User.class)).willReturn(user9);
        given(modelMapper.map(userDto10, User.class)).willReturn(user10);
        given(modelMapper.map(userDto11, User.class)).willReturn(user11);
        given(modelMapper.map(userDto12, User.class)).willReturn(user12);
        given(modelMapper.map(userDto13, User.class)).willReturn(user13);
        given(modelMapper.map(userDto14, User.class)).willReturn(user14);
        given(modelMapper.map(userDto15, User.class)).willReturn(user15);
        given(modelMapper.map(userDto16, User.class)).willReturn(user16);
        given(modelMapper.map(userDto17, User.class)).willReturn(user17);
        given(modelMapper.map(userDto18, User.class)).willReturn(user18);
        given(modelMapper.map(userDto19, User.class)).willReturn(user19);
        given(modelMapper.map(userDto20, User.class)).willReturn(user20);
        given(modelMapper.map(userDto21, User.class)).willReturn(user21);
        given(modelMapper.map(userDto22, User.class)).willReturn(user22);
        given(modelMapper.map(userDto23, User.class)).willReturn(user23);
        given(modelMapper.map(userDto24, User.class)).willReturn(user24);
        given(modelMapper.map(userDto25, User.class)).willReturn(user25);
        given(modelMapper.map(userDto26, User.class)).willReturn(user26);
        given(modelMapper.map(userDto27, User.class)).willReturn(user27);
        given(modelMapper.map(userDto28, User.class)).willReturn(user28);
        given(modelMapper.map(userDto29, User.class)).willReturn(user29);
        given(modelMapper.map(userDto30, User.class)).willReturn(user30);
        given(modelMapper.map(userDto31, User.class)).willReturn(user31);
        given(modelMapper.map(userDto32, User.class)).willReturn(user32);
        given(modelMapper.map(userDto33, User.class)).willReturn(user33);
        given(modelMapper.map(userDto34, User.class)).willReturn(user34);
        given(modelMapper.map(userDto35, User.class)).willReturn(user35);
        given(modelMapper.map(userDto36, User.class)).willReturn(user36);
        given(modelMapper.map(userDto37, User.class)).willReturn(user37);
        given(modelMapper.map(userDto38, User.class)).willReturn(user38);
        given(modelMapper.map(userDto39, User.class)).willReturn(user39);
        given(modelMapper.map(userDto40, User.class)).willReturn(user40);
        given(modelMapper.map(userDto41, User.class)).willReturn(user41);
        given(modelMapper.map(userDto42, User.class)).willReturn(user42);
        given(modelMapper.map(userDto43, User.class)).willReturn(user43);
        given(modelMapper.map(userDto44, User.class)).willReturn(user44);
        given(modelMapper.map(userDto45, User.class)).willReturn(user45);
        given(modelMapper.map(userDto46, User.class)).willReturn(user46);
        given(modelMapper.map(userDto47, User.class)).willReturn(user47);
        given(modelMapper.map(userDto48, User.class)).willReturn(user48);
        given(modelMapper.map(userDto49, User.class)).willReturn(user49);
        given(modelMapper.map(userDto50, User.class)).willReturn(user50);
        given(modelMapper.map(userDto51, User.class)).willReturn(user51);
        given(modelMapper.map(userDto52, User.class)).willReturn(user52);
        given(modelMapper.map(userDto53, User.class)).willReturn(user53);
        given(modelMapper.map(userDto54, User.class)).willReturn(user54);
        given(modelMapper.map(userDto55, User.class)).willReturn(user55);
        given(modelMapper.map(userDto56, User.class)).willReturn(user56);
        given(modelMapper.map(userDto57, User.class)).willReturn(user57);
        given(modelMapper.map(userDto58, User.class)).willReturn(user58);
        given(modelMapper.map(userDto59, User.class)).willReturn(user59);
        given(modelMapper.map(userDto60, User.class)).willReturn(user60);
        given(modelMapper.map(userDto61, User.class)).willReturn(user61);
        given(modelMapper.map(userDto62, User.class)).willReturn(user62);
        given(modelMapper.map(userDto63, User.class)).willReturn(user63);
        given(modelMapper.map(userDto64, User.class)).willReturn(user64);
        given(modelMapper.map(userDto65, User.class)).willReturn(user65);
        given(modelMapper.map(userDto66, User.class)).willReturn(user66);
        given(modelMapper.map(userDto67, User.class)).willReturn(user67);
        given(modelMapper.map(userDto68, User.class)).willReturn(user68);
        given(modelMapper.map(userDto69, User.class)).willReturn(user69);
        given(modelMapper.map(userDto70, User.class)).willReturn(user70);
        given(modelMapper.map(userDto71, User.class)).willReturn(user71);
        given(modelMapper.map(userDto72, User.class)).willReturn(user72);

        given(roleService.findEntityById(userDto1.getRoleId())).willReturn(role2);
        given(roleService.findEntityById(userDto2.getRoleId())).willReturn(role2);
        given(roleService.findEntityById(userDto3.getRoleId())).willReturn(role2);
        given(roleService.findEntityById(userDto4.getRoleId())).willReturn(role2);
        given(roleService.findEntityById(userDto5.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto6.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto7.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto8.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto9.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto10.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto11.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto12.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto13.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto14.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto15.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto16.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto17.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto18.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto19.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto20.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto21.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto22.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto23.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto24.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto25.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto26.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto27.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto28.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto29.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto30.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto31.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto32.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto33.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto34.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto35.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto36.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto37.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto38.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto39.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto40.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto41.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto42.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto43.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto44.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto45.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto46.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto47.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto48.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto49.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto50.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto51.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto52.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto53.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto54.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto55.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto56.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto57.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto58.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto59.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto60.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto61.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto62.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto63.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto64.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto65.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto66.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto67.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto68.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto69.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto70.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto71.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto72.getRoleId())).willReturn(role1);
    }

    @Test
    void getAllUsersPage_shouldReturnUsersPage() throws Exception {
        PageImpl<UserDto> userDtosPage = new PageImpl<>(userDtosFirstPage);
        given(userService.findAllByKey(any(Pageable.class), anyString())).willReturn(userDtosPage);
        int page = pageable.getPageNumber();
        int size = userDtosFirstPage.size();
        String field = getSortField(pageable);
        String direction = getSortDirection(pageable);
        long nrUsers = userDtosPage.getTotalElements();
        int nrPages = userDtosPage.getTotalPages();
        int startIndexCurrentPage = getStartIndexCurrentPage(page, size);
        long endIndexCurrentPage = getEndIndexCurrentPage(page, size, nrUsers);
        int startIndexPageNavigation = getStartIndexPageNavigation(page, nrPages);
        int endIndexPageNavigation = getEndIndexPageNavigation(page, nrPages);
        SearchRequest searchRequest = new SearchRequest(0, size, "", field + "," + direction);
        mockMvc.perform(get(USERS).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(USERS_VIEW))
              .andExpect(model().attribute("users", usersFirstPage))
              .andExpect(model().attribute("nrUsers", nrUsers))
              .andExpect(model().attribute("nrPages", nrPages))
              .andExpect(model().attribute("page", page))
              .andExpect(model().attribute("size", size))
              .andExpect(model().attribute("key", ""))
              .andExpect(model().attribute("field", field))
              .andExpect(model().attribute("direction", direction))
              .andExpect(model().attribute("startIndexCurrentPage", startIndexCurrentPage))
              .andExpect(model().attribute("endIndexCurrentPage", endIndexCurrentPage))
              .andExpect(model().attribute("startIndexPageNavigation", startIndexPageNavigation))
              .andExpect(model().attribute("endIndexPageNavigation", endIndexPageNavigation))
              .andExpect(model().attribute("searchRequest", searchRequest));
    }

    @Test
    void findAllByKey_shouldProcessSearchRequestAndReturnListOfUsersFilteredByKey() throws Exception {
        int page = pageable.getPageNumber();
        int size = userDtosFirstPage.size();
        String field = getSortField(pageable);
        String direction = getSortDirection(pageable);
        mockMvc.perform(post(USERS + "/search").accept(TEXT_HTML)
                    .param("page", String.valueOf(page))
                    .param("size", String.valueOf(size))
                    .param("key", USER_FILTER_KEY)
                    .param("sort", field + "," + direction))
              .andExpect(status().is3xxRedirection())
              .andExpect(redirectedUrlPattern(USERS + "?page=*&size=*&key=*&sort=*"));
    }

    @Test
    void getUserByIdPage_withValidId_shouldReturnUserDetailsPage() throws Exception {
        given(userService.findById(anyInt())).willReturn(userDto1);
        mockMvc.perform(get(USERS + "/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(USER_DETAILS_VIEW))
              .andExpect(model().attribute("user", user1));
        verify(userService).findById(VALID_ID);
    }

    @Test
    void getUserByIdPage_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(USER_NOT_FOUND, INVALID_ID);
        given(userService.findById(anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(USERS + "/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(userService).findById(INVALID_ID);
    }

    @Test
    void getSaveUserPage_withNegativeId_shouldReturnSaveUserPage() throws Exception {
        mockMvc.perform(get(USERS + "/save/{id}", -1).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_USER_VIEW))
              .andExpect(model().attribute("id", -1))
              .andExpect(model().attribute("userDto", new UserDto()));
    }

    @Test
    void getSaveUserPage_withValidId_shouldReturnUpdateUserPage() throws Exception {
        given(userService.findById(anyInt())).willReturn(userDto1);
        mockMvc.perform(get(USERS + "/save/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_USER_VIEW))
              .andExpect(model().attribute("id", VALID_ID))
              .andExpect(model().attribute("userDto", userDto1));
    }

    @Test
    void getSaveUserPage_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(USER_NOT_FOUND, INVALID_ID);
        given(userService.findById(anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(USERS + "/save/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void save_withNegativeId_shouldSaveUser() throws Exception {
        mockMvc.perform(post(USERS + "/save/{id}", -1).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(userDto1)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_USERS_VIEW))
              .andExpect(redirectedUrl(USERS));
        verify(userService).save(any(UserDto.class));
    }

    @Test
    void save_withValidId_shouldUpdateUserWithGivenId() throws Exception {
        mockMvc.perform(post(USERS + "/save/{id}", VALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(userDto1)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_USERS_VIEW))
              .andExpect(redirectedUrl(USERS));
        verify(userService).updateById(userDto1, VALID_ID);
    }

    @Test
    void save_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(USER_NOT_FOUND, INVALID_ID);
        given(userService.updateById(any(UserDto.class), anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(post(USERS + "/save/{id}", INVALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(userDto1)))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(userService).updateById(any(UserDto.class), anyInt());
    }

    @Test
    void deleteById_withValidId_shouldRemoveUserWithGivenIdFromList() throws Exception {
        PageImpl<UserDto> userDtosPage = new PageImpl<>(userDtosFirstPage);
        given(userService.findAllByKey(any(Pageable.class), anyString())).willReturn(userDtosPage);
        int page = pageable.getPageNumber();
        int size = userDtosFirstPage.size();
        String sort = getSortField(pageable) + "," + getSortDirection(pageable);
        mockMvc.perform(get(USERS + "/delete/{id}", VALID_ID).accept(TEXT_HTML)
                    .param("page", String.valueOf(page))
                    .param("size", String.valueOf(size))
                    .param("key", USER_FILTER_KEY)
                    .param("sort", sort))
              .andExpect(status().is3xxRedirection())
              .andExpect(redirectedUrlPattern(USERS + "?page=*&size=*&key=*&sort=*"));
        verify(userService).deleteById(VALID_ID);
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(USER_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(userService).deleteById(anyInt());
        mockMvc.perform(get(USERS + "/delete/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(userService).deleteById(INVALID_ID);
    }

    private MultiValueMap<String, String> convertToMultiValueMap(UserDto userDto) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", userDto.getName());
        params.add("email", userDto.getEmail());
        params.add("password", userDto.getPassword());
        params.add("mobile", userDto.getMobile());
        params.add("address", userDto.getAddress());
        params.add("birthday", userDto.getBirthday().toString());
        params.add("roleId", userDto.getRoleId().toString());
        return params;
    }

    private UserDto convertToDto(User user) {
        return UserDto.builder()
              .id(user.getId())
              .name(user.getName())
              .email(user.getEmail())
              .password(user.getPassword())
              .mobile(user.getMobile())
              .address(user.getAddress())
              .birthday(user.getBirthday())
              .roleId(user.getRole().getId())
              .build();
    }
}
