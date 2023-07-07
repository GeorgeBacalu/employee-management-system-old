package com.project.ems.mock;

import com.project.ems.feedback.Feedback;
import com.project.ems.feedback.enums.FeedbackType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.project.ems.mock.UserMock.getMockedUser1;
import static com.project.ems.mock.UserMock.getMockedUser10;
import static com.project.ems.mock.UserMock.getMockedUser11;
import static com.project.ems.mock.UserMock.getMockedUser12;
import static com.project.ems.mock.UserMock.getMockedUser2;
import static com.project.ems.mock.UserMock.getMockedUser3;
import static com.project.ems.mock.UserMock.getMockedUser4;
import static com.project.ems.mock.UserMock.getMockedUser5;
import static com.project.ems.mock.UserMock.getMockedUser6;
import static com.project.ems.mock.UserMock.getMockedUser7;
import static com.project.ems.mock.UserMock.getMockedUser8;
import static com.project.ems.mock.UserMock.getMockedUser9;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FeedbackMock {

    public static List<Feedback> getMockedFeedbacks() {
        return List.of(
              getMockedFeedback1(), getMockedFeedback2(), getMockedFeedback3(), getMockedFeedback4(), getMockedFeedback5(), getMockedFeedback6(),
              getMockedFeedback7(), getMockedFeedback8(), getMockedFeedback9(), getMockedFeedback10(), getMockedFeedback11(), getMockedFeedback12());
    }

    public static List<Feedback> getMockedFilteredFeedbacks() {
        return List.of(getMockedFeedback3(), getMockedFeedback6(), getMockedFeedback9(), getMockedFeedback12());
    }

    public static Feedback getMockedFeedback1() {
        return new Feedback(1, FeedbackType.ISSUE, "App crashes when submitting a form.", LocalDateTime.of(2023, 4, 20, 14, 30), getMockedUser1());
    }

    public static Feedback getMockedFeedback2() {
        return new Feedback(2, FeedbackType.OPTIMIZATION, "Improve page load time for the dashboard.", LocalDateTime.of(2023, 4, 21, 9, 15), getMockedUser2());
    }

    public static Feedback getMockedFeedback3() {
        return new Feedback(3, FeedbackType.IMPROVEMENT, "Add a dark mode for better user experience.", LocalDateTime.of(2023, 4, 22, 16, 45), getMockedUser3());
    }

    public static Feedback getMockedFeedback4() {
        return new Feedback(4, FeedbackType.ISSUE, "Error message appears when uploading an image.", LocalDateTime.of(2023, 4, 23, 11, 10), getMockedUser4());
    }

    public static Feedback getMockedFeedback5() {
        return new Feedback(5, FeedbackType.OPTIMIZATION, "Optimize search functionality for better results.", LocalDateTime.of(2023, 4, 25, 10, 50), getMockedUser5());
    }

    public static Feedback getMockedFeedback6() {
        return new Feedback(6, FeedbackType.IMPROVEMENT, "Add more filtering options in the search bar.", LocalDateTime.of(2023, 4, 27, 15, 40), getMockedUser6());
    }

    public static Feedback getMockedFeedback7() {
        return new Feedback(7, FeedbackType.ISSUE, "Login issues after resetting the password.", LocalDateTime.of(2023, 4, 26, 17, 20), getMockedUser7());
    }

    public static Feedback getMockedFeedback8() {
        return new Feedback(8, FeedbackType.OPTIMIZATION, "Compress images to improve page load time.", LocalDateTime.of(2023, 5, 1, 9, 45), getMockedUser8());
    }

    public static Feedback getMockedFeedback9() {
        return new Feedback(9, FeedbackType.IMPROVEMENT, "Include an option to save items to a wishlist.", LocalDateTime.of(2023, 4, 30, 14, 15), getMockedUser9());
    }

    public static Feedback getMockedFeedback10() {
        return new Feedback(10, FeedbackType.ISSUE, "Notifications not appearing on the mobile app.", LocalDateTime.of(2023, 5, 2, 16, 10), getMockedUser10());
    }

    public static Feedback getMockedFeedback11() {
        return new Feedback(11, FeedbackType.OPTIMIZATION, "Implement lazy loading for faster initial load.", LocalDateTime.of(2023, 5, 4, 17, 20), getMockedUser11());
    }

    public static Feedback getMockedFeedback12() {
        return new Feedback(12, FeedbackType.IMPROVEMENT, "Allow users to customize their profile layout.", LocalDateTime.of(2023, 5, 3, 11, 35), getMockedUser12());
    }
}
