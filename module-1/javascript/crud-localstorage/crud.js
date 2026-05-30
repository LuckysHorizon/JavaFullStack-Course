// Contact Manager - CRUD operations with localStorage

class ContactManager {
    constructor() {
        this.contacts = this.load();
        this.editingId = null;

        this.form = document.getElementById('contactForm');
        this.nameInput = document.getElementById('nameInput');
        this.emailInput = document.getElementById('emailInput');
        this.phoneInput = document.getElementById('phoneInput');
        this.categorySelect = document.getElementById('categorySelect');
        this.submitBtn = document.getElementById('submitBtn');
        this.cancelEditBtn = document.getElementById('cancelEditBtn');
        this.contactList = document.getElementById('contactList');
        this.searchInput = document.getElementById('searchInput');
        this.filterCategory = document.getElementById('filterCategory');
        this.contactCount = document.getElementById('contactCount');

        this.bindEvents();
        this.render();
    }

    bindEvents() {
        this.form.addEventListener('submit', (e) => {
            e.preventDefault();
            this.editingId ? this.updateContact() : this.addContact();
        });

        this.cancelEditBtn.addEventListener('click', () => this.cancelEdit());
        this.searchInput.addEventListener('input', () => this.render());
        this.filterCategory.addEventListener('change', () => this.render());
    }

    addContact() {
        const contact = {
            id: Date.now(),
            name: this.nameInput.value.trim(),
            email: this.emailInput.value.trim(),
            phone: this.phoneInput.value.trim(),
            category: this.categorySelect.value,
            createdAt: new Date().toISOString()
        };

        this.contacts.push(contact);
        this.save();
        this.form.reset();
        this.render();
    }

    getFilteredContacts() {
        let filtered = this.contacts;

        const query = this.searchInput.value.toLowerCase().trim();
        if (query) {
            filtered = filtered.filter(c =>
                c.name.toLowerCase().includes(query) ||
                c.email.toLowerCase().includes(query) ||
                c.phone.includes(query)
            );
        }

        const cat = this.filterCategory.value;
        if (cat) {
            filtered = filtered.filter(c => c.category === cat);
        }

        return filtered;
    }

    render() {
        const contacts = this.getFilteredContacts();
        this.contactList.innerHTML = '';

        if (contacts.length === 0) {
            this.contactList.innerHTML = '<p class="empty-msg">No contacts found.</p>';
            this.contactCount.textContent = '0 contacts';
            return;
        }

        contacts.forEach(contact => {
            const card = document.createElement('div');
            card.className = 'contact-card';
            card.innerHTML =
                '<h3>' + contact.name + '</h3>' +
                '<p>Email: ' + contact.email + '</p>' +
                '<p>Phone: ' + contact.phone + '</p>' +
                '<span class="contact-badge badge-' + contact.category + '">' + contact.category + '</span>' +
                '<div class="contact-actions">' +
                '  <button class="edit-btn" data-id="' + contact.id + '">Edit</button>' +
                '  <button class="delete-btn" data-id="' + contact.id + '">Delete</button>' +
                '</div>';
            this.contactList.appendChild(card);
        });

        this.contactCount.textContent = contacts.length + ' contact' + (contacts.length !== 1 ? 's' : '');
    }

    // Form submit handler handles both add and update
    updateContact() {
        const contact = this.contacts.find(c => c.id === this.editingId);
        if (!contact) return;

        contact.name = this.nameInput.value.trim();
        contact.email = this.emailInput.value.trim();
        contact.phone = this.phoneInput.value.trim();
        contact.category = this.categorySelect.value;

        this.save();
        this.cancelEdit();
        this.render();
    }

    startEdit(id) {
        const contact = this.contacts.find(c => c.id === id);
        if (!contact) return;

        this.editingId = id;
        this.nameInput.value = contact.name;
        this.emailInput.value = contact.email;
        this.phoneInput.value = contact.phone;
        this.categorySelect.value = contact.category;
        this.submitBtn.textContent = 'Update Contact';
        this.cancelEditBtn.hidden = false;
        this.nameInput.focus();
    }

    cancelEdit() {
        this.editingId = null;
        this.form.reset();
        this.submitBtn.textContent = 'Add Contact';
        this.cancelEditBtn.hidden = true;
    }

    deleteContact(id) {
        const contact = this.contacts.find(c => c.id === id);
        if (contact && confirm('Delete "' + contact.name + '"?')) {
            this.contacts = this.contacts.filter(c => c.id !== id);
            this.save();
            this.render();
        }
    }

    save() {
        localStorage.setItem('contacts', JSON.stringify(this.contacts));
    }

    load() {
        try {
            const data = localStorage.getItem('contacts');
            return data ? JSON.parse(data) : this.getSampleData();
        } catch {
            return this.getSampleData();
        }
    }

    getSampleData() {
        return [
            { id: 1, name: 'Ananya Sharma', email: 'ananya@mail.com', phone: '9876543210', category: 'friend', createdAt: new Date().toISOString() },
            { id: 2, name: 'Ravi Kumar', email: 'ravi.k@company.com', phone: '9123456789', category: 'work', createdAt: new Date().toISOString() },
            { id: 3, name: 'Meena Patel', email: 'meena@family.com', phone: '9988776655', category: 'family', createdAt: new Date().toISOString() }
        ];
    }
}

// event delegation for edit/delete buttons
document.getElementById('contactList').addEventListener('click', (e) => {
    const btn = e.target.closest('button');
    if (!btn) return;

    const id = parseInt(btn.dataset.id);
    if (btn.classList.contains('edit-btn')) {
        app.startEdit(id);
    } else if (btn.classList.contains('delete-btn')) {
        app.deleteContact(id);
    }
});

const app = new ContactManager();
