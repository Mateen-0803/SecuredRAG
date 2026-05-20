package com.mongodb.securerag;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class DocumentLoader implements CommandLineRunner {

    private final VectorStore vectorStore;

    public DocumentLoader(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public void run(String... args) throws Exception {
        loadSampleDocuments();
    }

    public void loadSampleDocuments() {
        List<Document> documents = new ArrayList<>();

        documents.add(new Document(
                "Employee Handbook - Vacation Policy: All full-time employees are entitled to 15 days of paid vacation per year.",
                Map.of("department", "HR", "access_level", "confidential",
                        "roles", Arrays.asList("hr_manager", "hr_coordinator"), "title", "Vacation Policy")
        ));

        documents.add(new Document(
                "Salary Review Process: Annual salary reviews are conducted in Q4 of each year.",
                Map.of("department", "HR", "access_level", "restricted",
                        "roles", Arrays.asList("hr_manager", "executive"), "title", "Salary Review Process")
        ));

        documents.add(new Document(
                "Q3 Budget Report: Total revenue for Q3 was $2.4M.",
                Map.of("department", "Finance", "access_level", "confidential",
                        "roles", Arrays.asList("finance_manager", "cfo", "executive"), "title", "Q3 Budget Report")
        ));

        documents.add(new Document(
                "Expense Policy: All business expenses over $100 require manager approval.",
                Map.of("department", "Finance", "access_level", "public",
                        "roles", Arrays.asList("all_employees"), "title", "Expense Policy")
        ));

        documents.add(new Document(
                "Company Mission Statement: Our mission is to democratize access to artificial intelligence.",
                Map.of("department", "General", "access_level", "public",
                        "roles", Arrays.asList("all_employees", "public"), "title", "Company Mission Statement")
        ));

        documents.add(new Document(
                "Office Hours and Location: Our main office is located at 123 Tech Street, San Francisco, CA.",
                Map.of("department", "General", "access_level", "public",
                        "roles", Arrays.asList("all_employees", "public"), "title", "Office Information")
        ));

        documents.add(new Document(
                "Q4 Sales Targets: Individual sales targets for Q4 are set at $500K per sales representative.",
                Map.of("department", "Sales", "access_level", "confidential",
                        "roles", Arrays.asList("sales_rep", "sales_manager"), "title", "Q4 Sales Targets")
        ));

        documents.add(new Document(
                "Strategic Planning 2025: Key initiatives include international expansion and AI model optimization.",
                Map.of("department", "Executive", "access_level", "restricted",
                        "roles", Arrays.asList("ceo", "cto", "cfo", "executive"), "title", "Strategic Planning 2025")
        ));

        System.out.println("Loading " + documents.size() + " sample documents...");
        vectorStore.add(documents);
        System.out.println("Sample documents loaded successfully!");
    }
}