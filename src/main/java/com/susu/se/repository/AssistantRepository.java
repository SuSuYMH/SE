package com.susu.se.repository;

import com.susu.se.model.users.Assistant;
import com.susu.se.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssistantRepository extends JpaRepository<Assistant, Integer> {
    Assistant findAssistantByUser(User user);
}
