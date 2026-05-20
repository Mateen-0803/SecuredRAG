package com.mongodb.securerag;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/{message}")
    public String sendMessage(@PathVariable String message,
                              @RequestParam(defaultValue = "public") String userRole,
                              @RequestParam(required = false) String department) {
        return chatService.sendSecureMessage(message, userRole, department);
    }

    @PostMapping("/secure")
    public String sendSecureMessage(@RequestBody ChatRequest request) {
        return chatService.sendSecureMessage(request.getMessage(), request.getUserRole(), request.getDepartment());
    }
}