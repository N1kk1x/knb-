package com.example.demo.service;

import com.example.demo.model.AdminAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repository.AdminActionRepository;

@Service
public class AdminActionService {
    @Autowired private AdminActionRepository adminActionRepository;

    public AdminAction recordAction(AdminAction action) {
        return adminActionRepository.save(action);
    }
}
