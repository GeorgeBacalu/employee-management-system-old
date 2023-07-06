package com.project.ems.integration.mentor;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.Experience;
import com.project.ems.experience.ExperienceService;
import com.project.ems.mentor.Mentor;
import com.project.ems.mentor.MentorController;
import com.project.ems.mentor.MentorDto;
import com.project.ems.mentor.MentorService;
import com.project.ems.role.Role;
import com.project.ems.role.RoleService;
import com.project.ems.study.Study;
import com.project.ems.study.StudyService;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.project.ems.constants.EndpointConstants.MENTORS;
import static com.project.ems.constants.ExceptionMessageConstants.MENTOR_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.ThymeleafViewConstants.MENTORS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.MENTOR_DETAILS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_MENTORS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_MENTOR_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.TEXT_HTML_UTF8;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences1;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences2;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences3;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences4;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences5;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences6;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences7;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences8;
import static com.project.ems.mock.MentorMock.*;
import static com.project.ems.mock.RoleMock.getMockedRole1;
import static com.project.ems.mock.RoleMock.getMockedRole2;
import static com.project.ems.mock.StudyMock.getMockedStudies1;
import static com.project.ems.mock.StudyMock.getMockedStudies2;
import static com.project.ems.mock.StudyMock.getMockedStudies3;
import static com.project.ems.mock.StudyMock.getMockedStudies4;
import static com.project.ems.mock.StudyMock.getMockedStudies5;
import static com.project.ems.mock.StudyMock.getMockedStudies6;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(MentorController.class)
class MentorControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MentorService mentorService;

    @MockBean
    private RoleService roleService;

    @MockBean
    private StudyService studyService;

    @MockBean
    private ExperienceService experienceService;

    @MockBean
    private ModelMapper modelMapper;

    private Mentor mentor1;
    private Mentor mentor2;
    private Mentor mentor3;
    private Mentor mentor4;
    private Mentor mentor5;
    private Mentor mentor6;
    private Mentor mentor7;
    private Mentor mentor8;
    private Mentor mentor9;
    private Mentor mentor10;
    private Mentor mentor11;
    private Mentor mentor12;
    private Mentor mentor13;
    private Mentor mentor14;
    private Mentor mentor15;
    private Mentor mentor16;
    private Mentor mentor17;
    private Mentor mentor18;
    private Mentor mentor19;
    private Mentor mentor20;
    private Mentor mentor21;
    private Mentor mentor22;
    private Mentor mentor23;
    private Mentor mentor24;
    private Mentor mentor25;
    private Mentor mentor26;
    private Mentor mentor27;
    private Mentor mentor28;
    private Mentor mentor29;
    private Mentor mentor30;
    private Mentor mentor31;
    private Mentor mentor32;
    private Mentor mentor33;
    private Mentor mentor34;
    private Mentor mentor35;
    private Mentor mentor36;
    private List<Mentor> mentors;

    private Role role1;
    private Role role2;
    private List<Study> studies1;
    private List<Study> studies2;
    private List<Study> studies3;
    private List<Study> studies4;
    private List<Study> studies5;
    private List<Study> studies6;
    private List<Experience> experiences1;
    private List<Experience> experiences2;
    private List<Experience> experiences3;
    private List<Experience> experiences4;
    private List<Experience> experiences5;
    private List<Experience> experiences6;
    private List<Experience> experiences7;
    private List<Experience> experiences8;

    private MentorDto mentorDto1;
    private MentorDto mentorDto2;
    private MentorDto mentorDto3;
    private MentorDto mentorDto4;
    private MentorDto mentorDto5;
    private MentorDto mentorDto6;
    private MentorDto mentorDto7;
    private MentorDto mentorDto8;
    private MentorDto mentorDto9;
    private MentorDto mentorDto10;
    private MentorDto mentorDto11;
    private MentorDto mentorDto12;
    private MentorDto mentorDto13;
    private MentorDto mentorDto14;
    private MentorDto mentorDto15;
    private MentorDto mentorDto16;
    private MentorDto mentorDto17;
    private MentorDto mentorDto18;
    private MentorDto mentorDto19;
    private MentorDto mentorDto20;
    private MentorDto mentorDto21;
    private MentorDto mentorDto22;
    private MentorDto mentorDto23;
    private MentorDto mentorDto24;
    private MentorDto mentorDto25;
    private MentorDto mentorDto26;
    private MentorDto mentorDto27;
    private MentorDto mentorDto28;
    private MentorDto mentorDto29;
    private MentorDto mentorDto30;
    private MentorDto mentorDto31;
    private MentorDto mentorDto32;
    private MentorDto mentorDto33;
    private MentorDto mentorDto34;
    private MentorDto mentorDto35;
    private MentorDto mentorDto36;
    private List<MentorDto> mentorDtos;

    @BeforeEach
    void setUp() {
        mentor1 = getMockedMentor1();
        mentor2 = getMockedMentor2();
        mentor3 = getMockedMentor3();
        mentor4 = getMockedMentor4();
        mentor5 = getMockedMentor5();
        mentor6 = getMockedMentor6();
        mentor7 = getMockedMentor7();
        mentor8 = getMockedMentor8();
        mentor9 = getMockedMentor9();
        mentor10 = getMockedMentor10();
        mentor11 = getMockedMentor11();
        mentor12 = getMockedMentor12();
        mentor13 = getMockedMentor13();
        mentor14 = getMockedMentor14();
        mentor15 = getMockedMentor15();
        mentor16 = getMockedMentor16();
        mentor17 = getMockedMentor17();
        mentor18 = getMockedMentor18();
        mentor19 = getMockedMentor19();
        mentor20 = getMockedMentor20();
        mentor21 = getMockedMentor21();
        mentor22 = getMockedMentor22();
        mentor23 = getMockedMentor23();
        mentor24 = getMockedMentor24();
        mentor25 = getMockedMentor25();
        mentor26 = getMockedMentor26();
        mentor27 = getMockedMentor27();
        mentor28 = getMockedMentor28();
        mentor29 = getMockedMentor29();
        mentor30 = getMockedMentor30();
        mentor31 = getMockedMentor31();
        mentor32 = getMockedMentor32();
        mentor33 = getMockedMentor33();
        mentor34 = getMockedMentor34();
        mentor35 = getMockedMentor35();
        mentor36 = getMockedMentor36();
        mentors = getMockedMentors();

        role1 = getMockedRole1();
        role2 = getMockedRole2();
        studies1 = getMockedStudies1();
        studies2 = getMockedStudies2();
        studies3 = getMockedStudies3();
        studies4 = getMockedStudies4();
        studies5 = getMockedStudies5();
        studies6 = getMockedStudies6();
        experiences1 = getMockedExperiences1();
        experiences2 = getMockedExperiences2();
        experiences3 = getMockedExperiences3();
        experiences4 = getMockedExperiences4();
        experiences5 = getMockedExperiences5();
        experiences6 = getMockedExperiences6();
        experiences7 = getMockedExperiences7();
        experiences8 = getMockedExperiences8();

        mentorDto1 = convertToDto(mentor1);
        mentorDto2 = convertToDto(mentor2);
        mentorDto3 = convertToDto(mentor3);
        mentorDto4 = convertToDto(mentor4);
        mentorDto5 = convertToDto(mentor5);
        mentorDto6 = convertToDto(mentor6);
        mentorDto7 = convertToDto(mentor7);
        mentorDto8 = convertToDto(mentor8);
        mentorDto9 = convertToDto(mentor9);
        mentorDto10 = convertToDto(mentor10);
        mentorDto11 = convertToDto(mentor11);
        mentorDto12 = convertToDto(mentor12);
        mentorDto13 = convertToDto(mentor13);
        mentorDto14 = convertToDto(mentor14);
        mentorDto15 = convertToDto(mentor15);
        mentorDto16 = convertToDto(mentor16);
        mentorDto17 = convertToDto(mentor17);
        mentorDto18 = convertToDto(mentor18);
        mentorDto19 = convertToDto(mentor19);
        mentorDto20 = convertToDto(mentor20);
        mentorDto21 = convertToDto(mentor21);
        mentorDto22 = convertToDto(mentor22);
        mentorDto23 = convertToDto(mentor23);
        mentorDto24 = convertToDto(mentor24);
        mentorDto25 = convertToDto(mentor25);
        mentorDto26 = convertToDto(mentor26);
        mentorDto27 = convertToDto(mentor27);
        mentorDto28 = convertToDto(mentor28);
        mentorDto29 = convertToDto(mentor29);
        mentorDto30 = convertToDto(mentor30);
        mentorDto31 = convertToDto(mentor31);
        mentorDto32 = convertToDto(mentor32);
        mentorDto33 = convertToDto(mentor33);
        mentorDto34 = convertToDto(mentor34);
        mentorDto35 = convertToDto(mentor35);
        mentorDto36 = convertToDto(mentor36);
        mentorDtos = List.of(mentorDto1, mentorDto2, mentorDto3, mentorDto4, mentorDto5, mentorDto6, mentorDto7, mentorDto8, mentorDto9, mentorDto10, mentorDto11, mentorDto12,
                             mentorDto13, mentorDto14, mentorDto15, mentorDto16, mentorDto17, mentorDto18, mentorDto19, mentorDto20, mentorDto21, mentorDto22, mentorDto23, mentorDto24,
                             mentorDto25, mentorDto26, mentorDto27, mentorDto28, mentorDto29, mentorDto30, mentorDto31, mentorDto32, mentorDto33, mentorDto34, mentorDto35, mentorDto36);

        given(modelMapper.map(mentorDto1, Mentor.class)).willReturn(mentor1);
        given(modelMapper.map(mentorDto2, Mentor.class)).willReturn(mentor2);
        given(modelMapper.map(mentorDto3, Mentor.class)).willReturn(mentor3);
        given(modelMapper.map(mentorDto4, Mentor.class)).willReturn(mentor4);
        given(modelMapper.map(mentorDto5, Mentor.class)).willReturn(mentor5);
        given(modelMapper.map(mentorDto6, Mentor.class)).willReturn(mentor6);
        given(modelMapper.map(mentorDto7, Mentor.class)).willReturn(mentor7);
        given(modelMapper.map(mentorDto8, Mentor.class)).willReturn(mentor8);
        given(modelMapper.map(mentorDto9, Mentor.class)).willReturn(mentor9);
        given(modelMapper.map(mentorDto10, Mentor.class)).willReturn(mentor10);
        given(modelMapper.map(mentorDto11, Mentor.class)).willReturn(mentor11);
        given(modelMapper.map(mentorDto12, Mentor.class)).willReturn(mentor12);
        given(modelMapper.map(mentorDto13, Mentor.class)).willReturn(mentor13);
        given(modelMapper.map(mentorDto14, Mentor.class)).willReturn(mentor14);
        given(modelMapper.map(mentorDto15, Mentor.class)).willReturn(mentor15);
        given(modelMapper.map(mentorDto16, Mentor.class)).willReturn(mentor16);
        given(modelMapper.map(mentorDto17, Mentor.class)).willReturn(mentor17);
        given(modelMapper.map(mentorDto18, Mentor.class)).willReturn(mentor18);
        given(modelMapper.map(mentorDto19, Mentor.class)).willReturn(mentor19);
        given(modelMapper.map(mentorDto20, Mentor.class)).willReturn(mentor20);
        given(modelMapper.map(mentorDto21, Mentor.class)).willReturn(mentor21);
        given(modelMapper.map(mentorDto22, Mentor.class)).willReturn(mentor22);
        given(modelMapper.map(mentorDto23, Mentor.class)).willReturn(mentor23);
        given(modelMapper.map(mentorDto24, Mentor.class)).willReturn(mentor24);
        given(modelMapper.map(mentorDto25, Mentor.class)).willReturn(mentor25);
        given(modelMapper.map(mentorDto26, Mentor.class)).willReturn(mentor26);
        given(modelMapper.map(mentorDto27, Mentor.class)).willReturn(mentor27);
        given(modelMapper.map(mentorDto28, Mentor.class)).willReturn(mentor28);
        given(modelMapper.map(mentorDto29, Mentor.class)).willReturn(mentor29);
        given(modelMapper.map(mentorDto30, Mentor.class)).willReturn(mentor30);
        given(modelMapper.map(mentorDto31, Mentor.class)).willReturn(mentor31);
        given(modelMapper.map(mentorDto32, Mentor.class)).willReturn(mentor32);
        given(modelMapper.map(mentorDto33, Mentor.class)).willReturn(mentor33);
        given(modelMapper.map(mentorDto34, Mentor.class)).willReturn(mentor34);
        given(modelMapper.map(mentorDto35, Mentor.class)).willReturn(mentor35);
        given(modelMapper.map(mentorDto36, Mentor.class)).willReturn(mentor36);

        given(roleService.findEntityById(mentorDto1.getRoleId())).willReturn(role2);
        given(roleService.findEntityById(mentorDto2.getRoleId())).willReturn(role2);
        given(roleService.findEntityById(mentorDto3.getRoleId())).willReturn(role2);
        given(roleService.findEntityById(mentorDto4.getRoleId())).willReturn(role2);
        given(roleService.findEntityById(mentorDto5.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(mentorDto6.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(mentorDto7.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(mentorDto8.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(mentorDto9.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(mentorDto10.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(mentorDto11.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(mentorDto12.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(mentorDto13.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(mentorDto14.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(mentorDto15.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(mentorDto16.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(mentorDto17.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(mentorDto18.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(mentorDto19.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(mentorDto20.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(mentorDto21.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(mentorDto22.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(mentorDto23.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(mentorDto24.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(mentorDto25.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(mentorDto26.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(mentorDto27.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(mentorDto28.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(mentorDto29.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(mentorDto30.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(mentorDto31.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(mentorDto32.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(mentorDto33.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(mentorDto34.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(mentorDto35.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(mentorDto36.getRoleId())).willReturn(role1);

        given(mentorService.findEntityById(mentorDto1.getSupervisingMentorId())).willReturn(null);
        given(mentorService.findEntityById(mentorDto2.getSupervisingMentorId())).willReturn(null);
        given(mentorService.findEntityById(mentorDto3.getSupervisingMentorId())).willReturn(null);
        given(mentorService.findEntityById(mentorDto4.getSupervisingMentorId())).willReturn(null);
        given(mentorService.findEntityById(mentorDto5.getSupervisingMentorId())).willReturn(null);
        given(mentorService.findEntityById(mentorDto6.getSupervisingMentorId())).willReturn(null);
        given(mentorService.findEntityById(mentorDto7.getSupervisingMentorId())).willReturn(mentor1);
        given(mentorService.findEntityById(mentorDto8.getSupervisingMentorId())).willReturn(mentor1);
        given(mentorService.findEntityById(mentorDto9.getSupervisingMentorId())).willReturn(mentor1);
        given(mentorService.findEntityById(mentorDto10.getSupervisingMentorId())).willReturn(mentor1);
        given(mentorService.findEntityById(mentorDto11.getSupervisingMentorId())).willReturn(mentor1);
        given(mentorService.findEntityById(mentorDto12.getSupervisingMentorId())).willReturn(mentor1);
        given(mentorService.findEntityById(mentorDto13.getSupervisingMentorId())).willReturn(mentor2);
        given(mentorService.findEntityById(mentorDto14.getSupervisingMentorId())).willReturn(mentor2);
        given(mentorService.findEntityById(mentorDto15.getSupervisingMentorId())).willReturn(mentor2);
        given(mentorService.findEntityById(mentorDto16.getSupervisingMentorId())).willReturn(mentor2);
        given(mentorService.findEntityById(mentorDto17.getSupervisingMentorId())).willReturn(mentor2);
        given(mentorService.findEntityById(mentorDto18.getSupervisingMentorId())).willReturn(mentor2);
        given(mentorService.findEntityById(mentorDto19.getSupervisingMentorId())).willReturn(mentor3);
        given(mentorService.findEntityById(mentorDto20.getSupervisingMentorId())).willReturn(mentor3);
        given(mentorService.findEntityById(mentorDto21.getSupervisingMentorId())).willReturn(mentor3);
        given(mentorService.findEntityById(mentorDto22.getSupervisingMentorId())).willReturn(mentor3);
        given(mentorService.findEntityById(mentorDto23.getSupervisingMentorId())).willReturn(mentor3);
        given(mentorService.findEntityById(mentorDto24.getSupervisingMentorId())).willReturn(mentor3);
        given(mentorService.findEntityById(mentorDto25.getSupervisingMentorId())).willReturn(mentor4);
        given(mentorService.findEntityById(mentorDto26.getSupervisingMentorId())).willReturn(mentor4);
        given(mentorService.findEntityById(mentorDto27.getSupervisingMentorId())).willReturn(mentor4);
        given(mentorService.findEntityById(mentorDto28.getSupervisingMentorId())).willReturn(mentor4);
        given(mentorService.findEntityById(mentorDto29.getSupervisingMentorId())).willReturn(mentor4);
        given(mentorService.findEntityById(mentorDto30.getSupervisingMentorId())).willReturn(mentor4);
        given(mentorService.findEntityById(mentorDto31.getSupervisingMentorId())).willReturn(mentor5);
        given(mentorService.findEntityById(mentorDto32.getSupervisingMentorId())).willReturn(mentor5);
        given(mentorService.findEntityById(mentorDto33.getSupervisingMentorId())).willReturn(mentor5);
        given(mentorService.findEntityById(mentorDto34.getSupervisingMentorId())).willReturn(mentor5);
        given(mentorService.findEntityById(mentorDto35.getSupervisingMentorId())).willReturn(mentor5);
        given(mentorService.findEntityById(mentorDto36.getSupervisingMentorId())).willReturn(mentor5);

        given(studyService.findEntityById(mentorDto1.getStudiesIds().get(0))).willReturn(studies1.get(0));
        given(studyService.findEntityById(mentorDto1.getStudiesIds().get(1))).willReturn(studies1.get(1));
        given(studyService.findEntityById(mentorDto2.getStudiesIds().get(0))).willReturn(studies2.get(0));
        given(studyService.findEntityById(mentorDto2.getStudiesIds().get(1))).willReturn(studies2.get(1));
        given(studyService.findEntityById(mentorDto3.getStudiesIds().get(0))).willReturn(studies3.get(0));
        given(studyService.findEntityById(mentorDto3.getStudiesIds().get(1))).willReturn(studies3.get(1));
        given(studyService.findEntityById(mentorDto4.getStudiesIds().get(0))).willReturn(studies4.get(0));
        given(studyService.findEntityById(mentorDto4.getStudiesIds().get(1))).willReturn(studies4.get(1));
        given(studyService.findEntityById(mentorDto5.getStudiesIds().get(0))).willReturn(studies5.get(0));
        given(studyService.findEntityById(mentorDto5.getStudiesIds().get(1))).willReturn(studies5.get(1));
        given(studyService.findEntityById(mentorDto6.getStudiesIds().get(0))).willReturn(studies6.get(0));
        given(studyService.findEntityById(mentorDto6.getStudiesIds().get(1))).willReturn(studies6.get(1));
        given(studyService.findEntityById(mentorDto7.getStudiesIds().get(0))).willReturn(studies1.get(0));
        given(studyService.findEntityById(mentorDto7.getStudiesIds().get(1))).willReturn(studies1.get(1));
        given(studyService.findEntityById(mentorDto8.getStudiesIds().get(0))).willReturn(studies2.get(0));
        given(studyService.findEntityById(mentorDto8.getStudiesIds().get(1))).willReturn(studies2.get(1));
        given(studyService.findEntityById(mentorDto9.getStudiesIds().get(0))).willReturn(studies3.get(0));
        given(studyService.findEntityById(mentorDto9.getStudiesIds().get(1))).willReturn(studies3.get(1));
        given(studyService.findEntityById(mentorDto10.getStudiesIds().get(0))).willReturn(studies4.get(0));
        given(studyService.findEntityById(mentorDto10.getStudiesIds().get(1))).willReturn(studies4.get(1));
        given(studyService.findEntityById(mentorDto11.getStudiesIds().get(0))).willReturn(studies5.get(0));
        given(studyService.findEntityById(mentorDto11.getStudiesIds().get(1))).willReturn(studies5.get(1));
        given(studyService.findEntityById(mentorDto12.getStudiesIds().get(0))).willReturn(studies6.get(0));
        given(studyService.findEntityById(mentorDto12.getStudiesIds().get(1))).willReturn(studies6.get(1));
        given(studyService.findEntityById(mentorDto13.getStudiesIds().get(0))).willReturn(studies1.get(0));
        given(studyService.findEntityById(mentorDto13.getStudiesIds().get(1))).willReturn(studies1.get(1));
        given(studyService.findEntityById(mentorDto14.getStudiesIds().get(0))).willReturn(studies2.get(0));
        given(studyService.findEntityById(mentorDto14.getStudiesIds().get(1))).willReturn(studies2.get(1));
        given(studyService.findEntityById(mentorDto15.getStudiesIds().get(0))).willReturn(studies3.get(0));
        given(studyService.findEntityById(mentorDto15.getStudiesIds().get(1))).willReturn(studies3.get(1));
        given(studyService.findEntityById(mentorDto16.getStudiesIds().get(0))).willReturn(studies4.get(0));
        given(studyService.findEntityById(mentorDto16.getStudiesIds().get(1))).willReturn(studies4.get(1));
        given(studyService.findEntityById(mentorDto17.getStudiesIds().get(0))).willReturn(studies5.get(0));
        given(studyService.findEntityById(mentorDto17.getStudiesIds().get(1))).willReturn(studies5.get(1));
        given(studyService.findEntityById(mentorDto18.getStudiesIds().get(0))).willReturn(studies6.get(0));
        given(studyService.findEntityById(mentorDto18.getStudiesIds().get(1))).willReturn(studies6.get(1));
        given(studyService.findEntityById(mentorDto19.getStudiesIds().get(0))).willReturn(studies1.get(0));
        given(studyService.findEntityById(mentorDto19.getStudiesIds().get(1))).willReturn(studies1.get(1));
        given(studyService.findEntityById(mentorDto20.getStudiesIds().get(0))).willReturn(studies2.get(0));
        given(studyService.findEntityById(mentorDto20.getStudiesIds().get(1))).willReturn(studies2.get(1));
        given(studyService.findEntityById(mentorDto21.getStudiesIds().get(0))).willReturn(studies3.get(0));
        given(studyService.findEntityById(mentorDto21.getStudiesIds().get(1))).willReturn(studies3.get(1));
        given(studyService.findEntityById(mentorDto22.getStudiesIds().get(0))).willReturn(studies4.get(0));
        given(studyService.findEntityById(mentorDto22.getStudiesIds().get(1))).willReturn(studies4.get(1));
        given(studyService.findEntityById(mentorDto23.getStudiesIds().get(0))).willReturn(studies5.get(0));
        given(studyService.findEntityById(mentorDto23.getStudiesIds().get(1))).willReturn(studies5.get(1));
        given(studyService.findEntityById(mentorDto24.getStudiesIds().get(0))).willReturn(studies6.get(0));
        given(studyService.findEntityById(mentorDto24.getStudiesIds().get(1))).willReturn(studies6.get(1));
        given(studyService.findEntityById(mentorDto25.getStudiesIds().get(0))).willReturn(studies1.get(0));
        given(studyService.findEntityById(mentorDto25.getStudiesIds().get(1))).willReturn(studies1.get(1));
        given(studyService.findEntityById(mentorDto26.getStudiesIds().get(0))).willReturn(studies2.get(0));
        given(studyService.findEntityById(mentorDto26.getStudiesIds().get(1))).willReturn(studies2.get(1));
        given(studyService.findEntityById(mentorDto27.getStudiesIds().get(0))).willReturn(studies3.get(0));
        given(studyService.findEntityById(mentorDto27.getStudiesIds().get(1))).willReturn(studies3.get(1));
        given(studyService.findEntityById(mentorDto28.getStudiesIds().get(0))).willReturn(studies4.get(0));
        given(studyService.findEntityById(mentorDto28.getStudiesIds().get(1))).willReturn(studies4.get(1));
        given(studyService.findEntityById(mentorDto29.getStudiesIds().get(0))).willReturn(studies5.get(0));
        given(studyService.findEntityById(mentorDto29.getStudiesIds().get(1))).willReturn(studies5.get(1));
        given(studyService.findEntityById(mentorDto30.getStudiesIds().get(0))).willReturn(studies6.get(0));
        given(studyService.findEntityById(mentorDto30.getStudiesIds().get(1))).willReturn(studies6.get(1));
        given(studyService.findEntityById(mentorDto31.getStudiesIds().get(0))).willReturn(studies1.get(0));
        given(studyService.findEntityById(mentorDto31.getStudiesIds().get(1))).willReturn(studies1.get(1));
        given(studyService.findEntityById(mentorDto32.getStudiesIds().get(0))).willReturn(studies2.get(0));
        given(studyService.findEntityById(mentorDto32.getStudiesIds().get(1))).willReturn(studies2.get(1));
        given(studyService.findEntityById(mentorDto33.getStudiesIds().get(0))).willReturn(studies3.get(0));
        given(studyService.findEntityById(mentorDto33.getStudiesIds().get(1))).willReturn(studies3.get(1));
        given(studyService.findEntityById(mentorDto34.getStudiesIds().get(0))).willReturn(studies4.get(0));
        given(studyService.findEntityById(mentorDto34.getStudiesIds().get(1))).willReturn(studies4.get(1));
        given(studyService.findEntityById(mentorDto35.getStudiesIds().get(0))).willReturn(studies5.get(0));
        given(studyService.findEntityById(mentorDto35.getStudiesIds().get(1))).willReturn(studies5.get(1));
        given(studyService.findEntityById(mentorDto36.getStudiesIds().get(0))).willReturn(studies6.get(0));
        given(studyService.findEntityById(mentorDto36.getStudiesIds().get(1))).willReturn(studies6.get(1));

        given(experienceService.findEntityById(mentorDto1.getExperiencesIds().get(0))).willReturn(experiences1.get(0));
        given(experienceService.findEntityById(mentorDto1.getExperiencesIds().get(1))).willReturn(experiences1.get(1));
        given(experienceService.findEntityById(mentorDto2.getExperiencesIds().get(0))).willReturn(experiences2.get(0));
        given(experienceService.findEntityById(mentorDto2.getExperiencesIds().get(1))).willReturn(experiences2.get(1));
        given(experienceService.findEntityById(mentorDto3.getExperiencesIds().get(0))).willReturn(experiences3.get(0));
        given(experienceService.findEntityById(mentorDto3.getExperiencesIds().get(1))).willReturn(experiences3.get(1));
        given(experienceService.findEntityById(mentorDto4.getExperiencesIds().get(0))).willReturn(experiences4.get(0));
        given(experienceService.findEntityById(mentorDto4.getExperiencesIds().get(1))).willReturn(experiences4.get(1));
        given(experienceService.findEntityById(mentorDto5.getExperiencesIds().get(0))).willReturn(experiences5.get(0));
        given(experienceService.findEntityById(mentorDto5.getExperiencesIds().get(1))).willReturn(experiences5.get(1));
        given(experienceService.findEntityById(mentorDto6.getExperiencesIds().get(0))).willReturn(experiences6.get(0));
        given(experienceService.findEntityById(mentorDto6.getExperiencesIds().get(1))).willReturn(experiences6.get(1));
        given(experienceService.findEntityById(mentorDto7.getExperiencesIds().get(0))).willReturn(experiences7.get(0));
        given(experienceService.findEntityById(mentorDto7.getExperiencesIds().get(1))).willReturn(experiences7.get(1));
        given(experienceService.findEntityById(mentorDto8.getExperiencesIds().get(0))).willReturn(experiences8.get(0));
        given(experienceService.findEntityById(mentorDto8.getExperiencesIds().get(1))).willReturn(experiences8.get(1));
        given(experienceService.findEntityById(mentorDto9.getExperiencesIds().get(0))).willReturn(experiences1.get(0));
        given(experienceService.findEntityById(mentorDto9.getExperiencesIds().get(1))).willReturn(experiences1.get(1));
        given(experienceService.findEntityById(mentorDto10.getExperiencesIds().get(0))).willReturn(experiences2.get(0));
        given(experienceService.findEntityById(mentorDto10.getExperiencesIds().get(1))).willReturn(experiences2.get(1));
        given(experienceService.findEntityById(mentorDto11.getExperiencesIds().get(0))).willReturn(experiences3.get(0));
        given(experienceService.findEntityById(mentorDto11.getExperiencesIds().get(1))).willReturn(experiences3.get(1));
        given(experienceService.findEntityById(mentorDto12.getExperiencesIds().get(0))).willReturn(experiences4.get(0));
        given(experienceService.findEntityById(mentorDto12.getExperiencesIds().get(1))).willReturn(experiences4.get(1));
        given(experienceService.findEntityById(mentorDto13.getExperiencesIds().get(0))).willReturn(experiences5.get(0));
        given(experienceService.findEntityById(mentorDto13.getExperiencesIds().get(1))).willReturn(experiences5.get(1));
        given(experienceService.findEntityById(mentorDto14.getExperiencesIds().get(0))).willReturn(experiences6.get(0));
        given(experienceService.findEntityById(mentorDto14.getExperiencesIds().get(1))).willReturn(experiences6.get(1));
        given(experienceService.findEntityById(mentorDto15.getExperiencesIds().get(0))).willReturn(experiences7.get(0));
        given(experienceService.findEntityById(mentorDto15.getExperiencesIds().get(1))).willReturn(experiences7.get(1));
        given(experienceService.findEntityById(mentorDto16.getExperiencesIds().get(0))).willReturn(experiences8.get(0));
        given(experienceService.findEntityById(mentorDto16.getExperiencesIds().get(1))).willReturn(experiences8.get(1));
        given(experienceService.findEntityById(mentorDto17.getExperiencesIds().get(0))).willReturn(experiences1.get(0));
        given(experienceService.findEntityById(mentorDto17.getExperiencesIds().get(1))).willReturn(experiences1.get(1));
        given(experienceService.findEntityById(mentorDto18.getExperiencesIds().get(0))).willReturn(experiences2.get(0));
        given(experienceService.findEntityById(mentorDto18.getExperiencesIds().get(1))).willReturn(experiences2.get(1));
        given(experienceService.findEntityById(mentorDto19.getExperiencesIds().get(0))).willReturn(experiences3.get(0));
        given(experienceService.findEntityById(mentorDto19.getExperiencesIds().get(1))).willReturn(experiences3.get(1));
        given(experienceService.findEntityById(mentorDto20.getExperiencesIds().get(0))).willReturn(experiences4.get(0));
        given(experienceService.findEntityById(mentorDto20.getExperiencesIds().get(1))).willReturn(experiences4.get(1));
        given(experienceService.findEntityById(mentorDto21.getExperiencesIds().get(0))).willReturn(experiences5.get(0));
        given(experienceService.findEntityById(mentorDto21.getExperiencesIds().get(1))).willReturn(experiences5.get(1));
        given(experienceService.findEntityById(mentorDto22.getExperiencesIds().get(0))).willReturn(experiences6.get(0));
        given(experienceService.findEntityById(mentorDto22.getExperiencesIds().get(1))).willReturn(experiences6.get(1));
        given(experienceService.findEntityById(mentorDto23.getExperiencesIds().get(0))).willReturn(experiences7.get(0));
        given(experienceService.findEntityById(mentorDto23.getExperiencesIds().get(1))).willReturn(experiences7.get(1));
        given(experienceService.findEntityById(mentorDto24.getExperiencesIds().get(0))).willReturn(experiences8.get(0));
        given(experienceService.findEntityById(mentorDto24.getExperiencesIds().get(1))).willReturn(experiences8.get(1));
        given(experienceService.findEntityById(mentorDto25.getExperiencesIds().get(0))).willReturn(experiences1.get(0));
        given(experienceService.findEntityById(mentorDto25.getExperiencesIds().get(1))).willReturn(experiences1.get(1));
        given(experienceService.findEntityById(mentorDto26.getExperiencesIds().get(0))).willReturn(experiences2.get(0));
        given(experienceService.findEntityById(mentorDto26.getExperiencesIds().get(1))).willReturn(experiences2.get(1));
        given(experienceService.findEntityById(mentorDto27.getExperiencesIds().get(0))).willReturn(experiences3.get(0));
        given(experienceService.findEntityById(mentorDto27.getExperiencesIds().get(1))).willReturn(experiences3.get(1));
        given(experienceService.findEntityById(mentorDto28.getExperiencesIds().get(0))).willReturn(experiences4.get(0));
        given(experienceService.findEntityById(mentorDto28.getExperiencesIds().get(1))).willReturn(experiences4.get(1));
        given(experienceService.findEntityById(mentorDto29.getExperiencesIds().get(0))).willReturn(experiences5.get(0));
        given(experienceService.findEntityById(mentorDto29.getExperiencesIds().get(1))).willReturn(experiences5.get(1));
        given(experienceService.findEntityById(mentorDto30.getExperiencesIds().get(0))).willReturn(experiences6.get(0));
        given(experienceService.findEntityById(mentorDto30.getExperiencesIds().get(1))).willReturn(experiences6.get(1));
        given(experienceService.findEntityById(mentorDto31.getExperiencesIds().get(0))).willReturn(experiences7.get(0));
        given(experienceService.findEntityById(mentorDto31.getExperiencesIds().get(1))).willReturn(experiences7.get(1));
        given(experienceService.findEntityById(mentorDto32.getExperiencesIds().get(0))).willReturn(experiences8.get(0));
        given(experienceService.findEntityById(mentorDto32.getExperiencesIds().get(1))).willReturn(experiences8.get(1));
        given(experienceService.findEntityById(mentorDto33.getExperiencesIds().get(0))).willReturn(experiences1.get(0));
        given(experienceService.findEntityById(mentorDto33.getExperiencesIds().get(1))).willReturn(experiences1.get(1));
        given(experienceService.findEntityById(mentorDto34.getExperiencesIds().get(0))).willReturn(experiences2.get(0));
        given(experienceService.findEntityById(mentorDto34.getExperiencesIds().get(1))).willReturn(experiences2.get(1));
        given(experienceService.findEntityById(mentorDto35.getExperiencesIds().get(0))).willReturn(experiences3.get(0));
        given(experienceService.findEntityById(mentorDto35.getExperiencesIds().get(1))).willReturn(experiences3.get(1));
        given(experienceService.findEntityById(mentorDto36.getExperiencesIds().get(0))).willReturn(experiences4.get(0));
        given(experienceService.findEntityById(mentorDto36.getExperiencesIds().get(1))).willReturn(experiences4.get(1));
    }

    @Test
    void getAllMentorsPage_shouldReturnMentorsPage() throws Exception {
        given(mentorService.findAll()).willReturn(mentorDtos);
        mockMvc.perform(get(MENTORS).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(MENTORS_VIEW))
              .andExpect(model().attribute("mentors", mentors));
        verify(mentorService).findAll();
    }

    @Test
    void getMentorByIdPage_withValidId_shouldReturnMentorDetailsPage() throws Exception {
        given(mentorService.findById(anyInt())).willReturn(mentorDto1);
        mockMvc.perform(get(MENTORS + "/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(MENTOR_DETAILS_VIEW))
              .andExpect(model().attribute("mentor", mentor1));
        verify(mentorService).findById(VALID_ID);
    }

    @Test
    void getMentorByIdPage_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(MENTOR_NOT_FOUND, INVALID_ID);
        given(mentorService.findById(anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(MENTORS + "/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(mentorService).findById(INVALID_ID);
    }

    @Test
    void getSaveMentorPage_withNegativeId_shouldReturnSaveMentorPage() throws Exception {
        mockMvc.perform(get(MENTORS + "/save/{id}", -1).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_MENTOR_VIEW))
              .andExpect(model().attribute("id", -1))
              .andExpect(model().attribute("mentorDto", new MentorDto()));
    }

    @Test
    void getSaveMentorPage_withValidId_shouldReturnUpdateMentorPage() throws Exception {
        given(mentorService.findById(anyInt())).willReturn(mentorDto1);
        mockMvc.perform(get(MENTORS + "/save/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_MENTOR_VIEW))
              .andExpect(model().attribute("id", VALID_ID))
              .andExpect(model().attribute("mentorDto", mentorDto1));
    }

    @Test
    void getSaveMentorPage_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(MENTOR_NOT_FOUND, INVALID_ID);
        given(mentorService.findById(anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(MENTORS + "/save/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void save_withNegativeId_shouldSaveMentor() throws Exception {
        mockMvc.perform(post(MENTORS + "/save/{id}", -1).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(mentorDto1)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_MENTORS_VIEW))
              .andExpect(redirectedUrl(MENTORS));
        verify(mentorService).save(any(MentorDto.class));
    }

    @Test
    void save_withValidId_shouldUpdateMentorWithGivenId() throws Exception {
        mockMvc.perform(post(MENTORS + "/save/{id}", VALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(mentorDto1)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_MENTORS_VIEW))
              .andExpect(redirectedUrl(MENTORS));
        verify(mentorService).updateById(mentorDto1, VALID_ID);
    }

    @Test
    void save_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(MENTOR_NOT_FOUND, INVALID_ID);
        given(mentorService.updateById(any(MentorDto.class), anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(post(MENTORS + "/save/{id}", INVALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(mentorDto1)))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(mentorService).updateById(any(MentorDto.class), anyInt());
    }

    @Test
    void deleteById_withValidId_shouldRemoveMentorWithGivenIdFromList() throws Exception {
        mockMvc.perform(get(MENTORS + "/delete/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_MENTORS_VIEW))
              .andExpect(redirectedUrl(MENTORS));
        verify(mentorService).deleteById(VALID_ID);
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(MENTOR_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(mentorService).deleteById(anyInt());
        mockMvc.perform(get(MENTORS + "/delete/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(mentorService).deleteById(INVALID_ID);
    }

    private MultiValueMap<String, String> convertToMultiValueMap(MentorDto mentorDto) {
        Integer supervisingMentorId = mentorDto.getSupervisingMentorId();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", mentorDto.getName());
        params.add("email", mentorDto.getEmail());
        params.add("password", mentorDto.getPassword());
        params.add("mobile", mentorDto.getMobile());
        params.add("address", mentorDto.getAddress());
        params.add("birthday", mentorDto.getBirthday().toString());
        params.add("roleId", mentorDto.getRoleId().toString());
        params.add("employmentType", mentorDto.getEmploymentType().toString());
        params.add("position", mentorDto.getPosition().toString());
        params.add("grade", mentorDto.getGrade().toString());
        params.add("supervisingMentorId", supervisingMentorId != null ? mentorDto.getSupervisingMentorId().toString() : null);
        params.add("studiesIds", mentorDto.getStudiesIds().stream().map(String::valueOf).collect(Collectors.joining(",")));
        params.add("experiencesIds", mentorDto.getExperiencesIds().stream().map(String::valueOf).collect(Collectors.joining(",")));
        params.add("nrTrainees", mentorDto.getNrTrainees().toString());
        params.add("maxTrainees", mentorDto.getMaxTrainees().toString());
        params.add("isTrainingOpen", mentorDto.getIsTrainingOpen().toString());
        return params;
    }

    private MentorDto convertToDto(Mentor mentor) {
        Mentor supervisingMentor = mentor.getSupervisingMentor();
        return MentorDto.builder()
              .id(mentor.getId())
              .name(mentor.getName())
              .email(mentor.getEmail())
              .password(mentor.getPassword())
              .mobile(mentor.getMobile())
              .address(mentor.getAddress())
              .birthday(mentor.getBirthday())
              .roleId(mentor.getRole().getId())
              .employmentType(mentor.getEmploymentType())
              .position(mentor.getPosition())
              .grade(mentor.getGrade())
              .supervisingMentorId(supervisingMentor != null ? supervisingMentor.getId() : null)
              .studiesIds(mentor.getStudies().stream().map(Study::getId).toList())
              .experiencesIds(mentor.getExperiences().stream().map(Experience::getId).toList())
              .nrTrainees(mentor.getNrTrainees())
              .maxTrainees(mentor.getMaxTrainees())
              .isTrainingOpen(mentor.getIsTrainingOpen())
              .build();
    }
}
