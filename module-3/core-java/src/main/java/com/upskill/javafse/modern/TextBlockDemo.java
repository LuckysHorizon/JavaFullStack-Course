package com.upskill.javafse.modern;

public class TextBlockDemo {

    public static void main(String[] args) {
        System.out.println("=== Text Blocks (Java 15+) ===\n");

        // --- JSON text block ---
        var jsonBlock = """
                {
                    "students": [
                        {
                            "name": "Alice",
                            "age": 22,
                            "courses": ["Java", "Python", "SQL"]
                        },
                        {
                            "name": "Bob",
                            "age": 24,
                            "courses": ["JavaScript", "React", "Node.js"]
                        }
                    ]
                }
                """;
        System.out.println("JSON text block:");
        System.out.println(jsonBlock);

        // --- SQL text block ---
        var sqlQuery = """
                SELECT e.employee_id,
                       e.first_name,
                       e.last_name,
                       d.department_name,
                       e.salary
                  FROM employees e
                  JOIN departments d
                    ON e.department_id = d.department_id
                 WHERE e.salary > 50000
                   AND d.location = 'New York'
                 ORDER BY e.salary DESC
                 LIMIT 10;
                """;
        System.out.println("SQL text block:");
        System.out.println(sqlQuery);

        // --- HTML text block ---
        var htmlPage = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>Student Report</title>
                    <style>
                        body { font-family: Arial, sans-serif; margin: 20px; }
                        table { border-collapse: collapse; width: 100%; }
                        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
                        th { background-color: #4CAF50; color: white; }
                    </style>
                </head>
                <body>
                    <h1>Student Report</h1>
                    <p>Generated on 2026-05-25</p>
                </body>
                </html>
                """;
        System.out.println("HTML text block:");
        System.out.println(htmlPage);

        // --- formatted() for interpolation ---
        showFormattedTextBlocks();

        // --- Comparison: text block vs traditional concatenation ---
        showReadabilityComparison();
    }

    static void showFormattedTextBlocks() {
        System.out.println("--- Using formatted() for interpolation ---\n");

        var name = "Charlie";
        var age = 28;
        var city = "Bangalore";
        var gpa = 3.85;

        // formatted() works just like String.format() but called on the text block itself
        var profile = """
                =================================
                  Student Profile
                =================================
                  Name : %s
                  Age  : %d
                  City : %s
                  GPA  : %.2f
                =================================
                """.formatted(name, age, city, gpa);
        System.out.println(profile);

        // JSON template with formatted()
        var endpoint = "/api/v1/users";
        var method = "POST";
        var statusCode = 201;
        var apiLog = """
                {
                    "endpoint": "%s",
                    "method": "%s",
                    "statusCode": %d,
                    "timestamp": "%tF %<tT"
                }
                """.formatted(endpoint, method, statusCode, System.currentTimeMillis());
        System.out.println("API log entry (formatted):");
        System.out.println(apiLog);
    }

    static void showReadabilityComparison() {
        System.out.println("--- Readability Comparison ---\n");

        // Traditional string concatenation  hard to read
        String traditionalJson = "{\n" +
                "    \"name\": \"Diana\",\n" +
                "    \"email\": \"diana@example.com\",\n" +
                "    \"roles\": [\n" +
                "        \"admin\",\n" +
                "        \"developer\"\n" +
                "    ]\n" +
                "}";

        // Text block  much cleaner
        var textBlockJson = """
                {
                    "name": "Diana",
                    "email": "diana@example.com",
                    "roles": [
                        "admin",
                        "developer"
                    ]
                }""";

        System.out.println("Traditional concatenation result:");
        System.out.println(traditionalJson);
        System.out.println();
        System.out.println("Text block result:");
        System.out.println(textBlockJson);
        System.out.println();

        // They produce the same output
        System.out.printf("Both produce identical output: %b%n%n", traditionalJson.equals(textBlockJson));

        // Bonus: useful text block methods
        var indented = """
                Line one
                Line two
                Line three
                """;
        System.out.println("stripIndent() and translateEscapes():");
        System.out.printf("  Original lines: %d%n", indented.lines().count());
        System.out.printf("  After strip:    \"%s\"%n",
                indented.stripIndent().replace("\n", "\\n"));
    }
}
