package com.mongodb.securerag;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {

    private final ChatModel chatModel;
    private final VectorStore vectorStore;

    ChatService(ChatModel chatModel, VectorStore vectorStore) {
        this.chatModel = chatModel;
        this.vectorStore = vectorStore;
    }

    public String sendSecureMessage(String message, String userRole, String department) {
        QuestionAnswerAdvisor advisor = QuestionAnswerAdvisor.builder(vectorStore)
                .searchRequest(SearchRequest.builder()
                        .topK(5)
                        .build())
                .build();

        ChatClient filteredChatClient = ChatClient.builder(chatModel)
                .defaultAdvisors(advisor)
                .build();

        String filterExpression = createAccessFilterExpression(userRole, department);

        return filteredChatClient.prompt()
                .user(message)
                .advisors(a -> a.param(QuestionAnswerAdvisor.FILTER_EXPRESSION, filterExpression))
                .call()
                .content();
    }

    private String createAccessFilterExpression(String userRole, String department) {
        List<String> conditions = new ArrayList<>();
        conditions.add("access_level == 'public'");

        if (userRole != null && !userRole.equalsIgnoreCase("public")) {
            StringBuilder roleCondition = new StringBuilder();
            roleCondition.append("(roles in ['").append(userRole).append("', 'all_employees'])");

            if (department != null && !department.trim().isEmpty()) {
                roleCondition.append(" && (department == '").append(department).append("')");
            }

            conditions.add(roleCondition.toString());
        }

        return String.join(" || ", conditions);
    }
}