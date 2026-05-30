const form = document.getElementById('prefForm');
const nameInput = document.getElementById('userName');
const colorInput = document.getElementById('themeColor');
const sizeInput = document.getElementById('fontSize');
const sizeVal = document.getElementById('fontSizeVal');
const dataTable = document.getElementById('dataTable').querySelector('tbody');
const emptyMsg = document.getElementById('emptyMsg');
const storageInfo = document.getElementById('storageInfo');
const preview = document.getElementById('preview');
const previewText = document.getElementById('previewText');
const clearBtn = document.getElementById('clearBtn');

function getStorage() {
    const type = document.querySelector('input[name="storageType"]:checked').value;
    return type === 'local' ? localStorage : sessionStorage;
}

function getStorageLabel() {
    return document.querySelector('input[name="storageType"]:checked').value === 'local'
        ? 'localStorage' : 'sessionStorage';
}

function refreshTable() {
    const storage = getStorage();
    dataTable.innerHTML = '';

    let count = 0;
    for (let i = 0; i < storage.length; i++) {
        const key = storage.key(i);
        const val = storage.getItem(key);
        const row = document.createElement('tr');
        row.innerHTML = <td>${key}</td><td>${val}</td>;
        dataTable.appendChild(row);
        count++;
    }

    emptyMsg.hidden = count > 0;
    storageInfo.textContent = ${getStorageLabel()}  ${count} item${count !== 1 ? 's' : ''};
}

function applyPreview() {
    const storage = getStorage();
    const name = storage.getItem('userName');
    const color = storage.getItem('themeColor');
    const size = storage.getItem('fontSize');

    if (name || color || size) {
        preview.hidden = false;
        previewText.textContent = Hello${name ? ', ' + name : ''}! This text reflects your saved preferences.;
        if (color) previewText.style.color = color;
        if (size) previewText.style.fontSize = size + 'px';
    } else {
        preview.hidden = true;
    }
}

function loadSavedValues() {
    const storage = getStorage();
    nameInput.value = storage.getItem('userName') || '';
    colorInput.value = storage.getItem('themeColor') || '#4a6cf7';
    sizeInput.value = storage.getItem('fontSize') || 16;
    sizeVal.textContent = sizeInput.value + 'px';
    refreshTable();
    applyPreview();
}

sizeInput.addEventListener('input', () => {
    sizeVal.textContent = sizeInput.value + 'px';
});

// switch between storage types
document.querySelectorAll('input[name="storageType"]').forEach(radio => {
    radio.addEventListener('change', loadSavedValues);
});

form.addEventListener('submit', (e) => {
    e.preventDefault();
    const storage = getStorage();
    storage.setItem('userName', nameInput.value);
    storage.setItem('themeColor', colorInput.value);
    storage.setItem('fontSize', sizeInput.value);
    refreshTable();
    applyPreview();
});

clearBtn.addEventListener('click', () => {
    getStorage().clear();
    nameInput.value = '';
    colorInput.value = '#4a6cf7';
    sizeInput.value = 16;
    sizeVal.textContent = '16px';
    refreshTable();
    applyPreview();
});

// load on page open
loadSavedValues();
