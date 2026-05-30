# Digital Nurture 5.0  Java Full Stack Engineering

This repository contains my exercise solutions and project implementations from the **Cognizant Digital Nurture 5.0** Java Full Stack Engineering upskilling program. Each module focuses on a different part of the web development stack  from frontend fundamentals through databases and backend Java.

## Modules Covered

| Module | Topics | Folder |
|--------|--------|--------|
| **Module 1** | HTML5, CSS3, JavaScript, Bootstrap 5, Webpack/Babel | module-1/ |
| **Module 2** | ANSI SQL with MySQL | module-2/ |
| **Module 3** | Core Java (OOP, Collections, JDBC, Multithreading, Java 21) | module-3/ |

## Technologies Used

- **Frontend**: HTML5, CSS3, JavaScript (ES6+), Bootstrap 5
- **Build Tools**: Webpack 5, Babel
- **Database**: MySQL 8
- **Backend**: Java 21, Maven, JDBC
- **Testing**: JUnit 5

## Folder Structure


 module-1/
    html5/          # Semantic HTML exercises, forms, events, storage APIs
    css3/           # Layouts, responsive design, animations
    javascript/     # DOM, async JS, fetch API, CRUD apps
    bootstrap5/     # Grid system, components, responsive pages
    build-tools/    # Webpack + Babel configuration

 module-2/
    mysql/          # Schema design, SQL queries, joins, aggregates

 module-3/
    core-java/      # Maven project  OOP, collections, JDBC, threading

 assets/
    screenshots/    # Project screenshots

 notes/              # Quick reference notes per topic


## How to Run

### HTML/CSS/Bootstrap projects
Open any index.html directly in a browser. No build step needed for most exercises.

### JavaScript projects
Same as above  open index.html in browser. Console output visible in DevTools.

### Build Tools (Webpack)
bash
cd module-1/build-tools
npm install
npm run dev     # development server
npm run build   # production bundle


### MySQL scripts
Execute .sql files in MySQL Workbench or any MySQL client:
sql
SOURCE path/to/schema.sql;
SOURCE path/to/queries.sql;


### Core Java (Maven)
bash
cd module-3/core-java
mvn compile
mvn exec:java -Dexec.mainClass="com.upskill.javafse.basics.Calculator"


## Screenshots

Screenshots of key projects are stored in [assets/screenshots/](assets/screenshots/).

## Learning Outcomes

Working through these exercises helped me get comfortable with:

- Building responsive layouts from scratch using CSS Grid and Flexbox
- Writing modular JavaScript and working with browser APIs
- Designing relational database schemas and writing complex SQL queries
- Applying OOP principles in Java with real-world-ish examples
- Understanding build tooling (why bundlers exist, how transpilation works)
- Using modern Java features like records, sealed classes, and virtual threads

## Repository

 **GitHub**: *(will add link after pushing)*

---

> Built as part of the Cognizant Digital Nurture 5.0 program  Java Full Stack track.
