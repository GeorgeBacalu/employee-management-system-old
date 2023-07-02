package com.project.ems.feedback;

import com.project.ems.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    void deleteAllByUser(User user);
}
