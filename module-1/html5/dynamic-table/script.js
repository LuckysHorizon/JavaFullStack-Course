const tableBody = document.getElementById('tableBody');
const searchInput = document.getElementById('searchInput');
const addBtn = document.getElementById('addBtn');
const overlay = document.getElementById('formOverlay');
const studentForm = document.getElementById('studentForm');
const cancelBtn = document.getElementById('cancelBtn');
const formTitle = document.getElementById('formTitle');
const editIndex = document.getElementById('editIndex');

// sample data
let students = [
    { name: 'Ananya Sharma', email: 'ananya.s@college.edu', grade: 'A', status: 'Active' },
    { name: 'Ravi Kumar', email: 'ravi.k@college.edu', grade: 'B', status: 'Active' },
    { name: 'Meena Patel', email: 'meena.p@college.edu', grade: 'A', status: 'Graduated' },
    { name: 'Arjun Reddy', email: 'arjun.r@college.edu', grade: 'C', status: 'Active' },
    { name: 'Priya Nair', email: 'priya.n@college.edu', grade: 'B', status: 'Inactive' },
    { name: 'Karthik Menon', email: 'karthik.m@college.edu', grade: 'A', status: 'Active' }
];

let sortCol = null;
let sortAsc = true;

function renderTable(data) {
    tableBody.innerHTML = '';

    if (data.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="5" style="text-align:center;color:#999;">No students found.</td></tr>';
        return;
    }

    data.forEach((s, i) => {
        const row = document.createElement('tr');
        row.innerHTML = 
            <td>${s.name}</td>
            <td>${s.email}</td>
            <td>${s.grade}</td>
            <td><span class="status-badge status-${s.status}">${s.status}</span></td>
            <td>
                <button class="action-btn edit-btn" data-idx="${i}">Edit</button>
                <button class="action-btn delete-btn" data-idx="${i}">Delete</button>
            </td>
        ;
        tableBody.appendChild(row);
    });
}

function getFilteredData() {
    const query = searchInput.value.toLowerCase().trim();
    let filtered = students.filter(s =>
        s.name.toLowerCase().includes(query) ||
        s.email.toLowerCase().includes(query)
    );

    if (sortCol) {
        filtered.sort((a, b) => {
            const valA = a[sortCol].toLowerCase();
            const valB = b[sortCol].toLowerCase();
            if (valA < valB) return sortAsc ? -1 : 1;
            if (valA > valB) return sortAsc ? 1 : -1;
            return 0;
        });
    }

    return filtered;
}

function refresh() {
    renderTable(getFilteredData());
}

// sorting
document.querySelectorAll('th.sortable').forEach(th => {
    th.addEventListener('click', () => {
        const col = th.dataset.col;
        if (sortCol === col) {
            sortAsc = !sortAsc;
        } else {
            sortCol = col;
            sortAsc = true;
        }
        refresh();
    });
});

searchInput.addEventListener('input', refresh);

// open add form
addBtn.addEventListener('click', () => {
    formTitle.textContent = 'Add Student';
    editIndex.value = -1;
    studentForm.reset();
    overlay.hidden = false;
});

cancelBtn.addEventListener('click', () => {
    overlay.hidden = true;
});

// close on overlay click outside modal
overlay.addEventListener('click', (e) => {
    if (e.target === overlay) overlay.hidden = true;
});

// form submit (add or edit)
studentForm.addEventListener('submit', (e) => {
    e.preventDefault();
    const student = {
        name: document.getElementById('nameInput').value.trim(),
        email: document.getElementById('emailInput').value.trim(),
        grade: document.getElementById('gradeInput').value,
        status: document.getElementById('statusInput').value
    };

    const idx = parseInt(editIndex.value);
    if (idx >= 0) {
        students[idx] = student;
    } else {
        students.push(student);
    }

    overlay.hidden = true;
    refresh();
});

// edit and delete via event delegation
tableBody.addEventListener('click', (e) => {
    const btn = e.target.closest('.action-btn');
    if (!btn) return;

    const filtered = getFilteredData();
    const idx = parseInt(btn.dataset.idx);
    const student = filtered[idx];

    // find real index in the master array
    const realIdx = students.findIndex(s =>
        s.name === student.name && s.email === student.email
    );

    if (btn.classList.contains('edit-btn')) {
        formTitle.textContent = 'Edit Student';
        editIndex.value = realIdx;
        document.getElementById('nameInput').value = student.name;
        document.getElementById('emailInput').value = student.email;
        document.getElementById('gradeInput').value = student.grade;
        document.getElementById('statusInput').value = student.status;
        overlay.hidden = false;
    }

    if (btn.classList.contains('delete-btn')) {
        if (confirm(Remove ${student.name}?)) {
            students.splice(realIdx, 1);
            refresh();
        }
    }
});

// initial render
refresh();
