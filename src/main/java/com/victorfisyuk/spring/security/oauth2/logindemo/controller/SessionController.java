package com.victorfisyuk.spring.security.oauth2.logindemo.controller;

import com.victorfisyuk.spring.security.oauth2.logindemo.dto.SessionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sessions")
public class SessionController {
    private final FindByIndexNameSessionRepository<? extends Session> sessionRepository;

    @Autowired
    public SessionController(FindByIndexNameSessionRepository<? extends Session> sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @GetMapping
    public List<SessionDTO> getSessions(Principal principal) {
        Map<String, ? extends Session> sessions = sessionRepository.findByPrincipalName(principal.getName());
        return sessions.values().stream()
                .map(session -> new SessionDTO(
                        session.getId(),
                        session.getCreationTime(),
                        session.getMaxInactiveInterval().toMinutes(),
                        session.isExpired()))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void deleteSession(@PathVariable("id") String id) {
        sessionRepository.deleteById(id);
    }
}
