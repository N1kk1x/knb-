package com.example.demo.controller;

import com.example.demo.dto.AdminActionRequest;
import com.example.demo.dto.AdminActionResponse;
import com.example.demo.model.AdminAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.AdminActionService;

@RestController
@RequestMapping("/api/admin/actions")
public class AdminActionController {
    @Autowired private AdminActionService actionService;

    @PostMapping
    public ResponseEntity<AdminActionResponse> record(@RequestBody AdminActionRequest req) {
        AdminAction a = new AdminAction();
        a.setAdminId(req.getAdminId());
        a.setTargetUserId(req.getTargetUserId());
        a.setAction(req.getAction());
        a.setActionInfo(req.getActionInfo());
        AdminAction saved = actionService.recordAction(a);
        return ResponseEntity.ok(new AdminActionResponse(saved.getId(), saved.getAction(), saved.getActionTime()));
    }
}
