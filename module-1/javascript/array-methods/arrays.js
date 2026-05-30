const students = [
    { name: 'Ananya Sharma', age: 21, grade: 'A', city: 'Mumbai', gpa: 3.9 },
    { name: 'Ravi Kumar', age: 22, grade: 'B+', city: 'Delhi', gpa: 3.4 },
    { name: 'Meena Patel', age: 20, grade: 'A+', city: 'Ahmedabad', gpa: 4.0 },
    { name: 'Arjun Reddy', age: 23, grade: 'B', city: 'Hyderabad', gpa: 3.1 },
    { name: 'Priya Nair', age: 21, grade: 'A', city: 'Kochi', gpa: 3.7 },
    { name: 'Karthik Menon', age: 22, grade: 'B+', city: 'Bangalore', gpa: 3.5 },
    { name: 'Sneha Joshi', age: 20, grade: 'A', city: 'Pune', gpa: 3.8 },
    { name: 'Deepak Singh', age: 24, grade: 'C+', city: 'Jaipur', gpa: 2.8 },
    { name: 'Lakshmi Rao', age: 21, grade: 'A+', city: 'Chennai', gpa: 3.95 },
    { name: 'Vikas Sharma', age: 22, grade: 'B', city: 'Lucknow', gpa: 3.2 },
    { name: 'Pooja Gupta', age: 20, grade: 'C', city: 'Kolkata', gpa: 2.5 }
];

function showResult(elementId, text) {
    document.getElementById(elementId).textContent = text;
}

// Display the raw dataset
function showDataset() {
    const lines = students.map(s =>
        s.name.padEnd(18) + ' | Age: ' + s.age + ' | Grade: ' + s.grade.padEnd(2) + ' | City: ' + s.city.padEnd(12) + ' | GPA: ' + s.gpa
    );
    showResult('datasetOutput', lines.join('\n'));
}

// map: transform to display names with GPA
function runMap() {
    const result = students.map(s => s.name + ' (GPA: ' + s.gpa + ')');
    showResult('mapResult', result.join('\n'));
}

// filter: students with GPA > 3.5
function runFilter() {
    const honorRoll = students.filter(s => s.gpa > 3.5);
    const display = honorRoll.map(s => s.name + '  GPA: ' + s.gpa + ', City: ' + s.city).join('\n');
    showResult('filterResult', 'Found ' + honorRoll.length + ' students with GPA > 3.5:\n\n' + display);
}

// reduce: calculate average GPA
function runReduce() {
    const totalGpa = students.reduce((sum, s) => sum + s.gpa, 0);
    const avgGpa = (totalGpa / students.length).toFixed(2);
    showResult('reduceResult', 'Total GPA sum: ' + totalGpa.toFixed(1) + '\nNumber of students: ' + students.length + '\nAverage GPA: ' + avgGpa);
}

// find: find specific student
function runFind() {
    const student = students.find(s => s.name.includes('Priya'));
    if (student) {
        showResult('findResult', 'Found: ' + student.name + '\nAge: ' + student.age + '\nGrade: ' + student.grade + '\nCity: ' + student.city + '\nGPA: ' + student.gpa);
    } else {
        showResult('findResult', 'Student not found.');
    }
}

// some/every: check conditions
function runSomeEvery() {
    const hasPerfectGpa = students.some(s => s.gpa === 4.0);
    const allAdults = students.every(s => s.age >= 18);
    const allAbove3 = students.every(s => s.gpa >= 3.0);

    const text = 'some(s => s.gpa === 4.0): ' + hasPerfectGpa + ' \n' +
                 'every(s => s.age >= 18): ' + allAdults + ' \n' +
                 'every(s => s.gpa >= 3.0): ' + allAbove3;
    showResult('someEveryResult', text);
}

// sort: by GPA descending
function runSort() {
    const sorted = [...students].sort((a, b) => b.gpa - a.gpa);
    const display = sorted.map((s, i) => (i + 1) + '. ' + s.name + '  GPA: ' + s.gpa).join('\n');
    showResult('sortResult', 'Sorted by GPA (highest first):\n\n' + display);
}

// method chaining: filter + sort + map
function runChaining() {
    const result = students
        .filter(s => s.gpa >= 3.5)
        .sort((a, b) => b.gpa - a.gpa)
        .map(s => s.name + ' (' + s.gpa + ')');

    showResult('chainingResult',
        'Honor Roll (GPA >= 3.5, sorted):\n\n' + result.join('\n') + '\n\nTotal: ' + result.length + ' students'
    );
}

showDataset();
