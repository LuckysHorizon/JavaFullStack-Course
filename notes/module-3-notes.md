# Module 3  Core Java Notes

## OOP Principles
- **Encapsulation**: private fields + public getters/setters. Protects internal state
- **Inheritance**: extends for classes, implements for interfaces. Favor composition over inheritance when possible
- **Polymorphism**: method overriding (runtime) vs overloading (compile-time)
- **Abstraction**: abstract classes can have state + partial implementation, interfaces define contracts

## Collections
- ArrayList = fast random access, LinkedList = fast insert/delete at ends
- HashSet = no order guarantee, TreeSet = sorted, LinkedHashSet = insertion order
- HashMap internally uses array of linked lists (becomes tree at 8+ collisions)
- Streams don't modify source collection  they produce new results

## Exception Handling
- Checked exceptions = must handle (IOException), unchecked = programming errors (NullPointerException)
- try-with-resources automatically closes anything implementing AutoCloseable
- Custom exceptions: extend Exception for checked, RuntimeException for unchecked

## Multithreading
- synchronized keyword prevents race conditions but can cause deadlocks if misused
- ExecutorService is preferred over raw Thread creation
- volatile ensures visibility across threads, but doesn't ensure atomicity
- Virtual threads (Java 21)  lightweight, great for I/O-bound tasks

## JDBC
- Always use PreparedStatement over Statement  prevents SQL injection
- DAO pattern separates data access logic from business logic
- Connection pooling (HikariCP) is important for production but overkill for exercises

## Java 21 Highlights
- Records = immutable data carriers, auto-generate equals/hashCode/toString
- Sealed classes = controlled inheritance hierarchy
- Pattern matching in switch = cleaner type checking + destructuring
- Text blocks = multi-line strings without concatenation headaches
