package com.project.ems.mock;

import com.project.ems.feedback.Feedback;
import com.project.ems.feedback.enums.FeedbackType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.project.ems.mock.UserMock.getMockedUser1;
import static com.project.ems.mock.UserMock.getMockedUser2;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FeedbackMock {

    public static List<Feedback> getMockedFeedbacks() {
        return List.of(getMockedFeedback1(), getMockedFeedback2());
    }

    public static Feedback getMockedFeedback1() {
        return Feedback.builder()
              .id(1)
              .type(FeedbackType.ISSUE)
              .description("Feedback_description1")
              .sentAt(LocalDateTime.of(2010, 1, 1, 0, 0, 0))
              .user(getMockedUser1())
              .build();
    }

    public static Feedback getMockedFeedback2() {
        return Feedback.builder()
              .id(2)
              .type(FeedbackType.OPTIMIZATION)
              .description("Feedback_description2")
              .sentAt(LocalDateTime.of(2010, 1, 2, 0, 0, 0))
              .user(getMockedUser2())
              .build();
    }
}
