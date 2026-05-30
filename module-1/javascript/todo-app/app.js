class TodoManager {
  constructor() {
    this.tasks = this.loadTasks();
    this.currentFilter = 'all';

    this.taskInput = document.getElementById('taskInput');
    this.addBtn = document.getElementById('addBtn');
    this.taskList = document.getElementById('taskList');
    this.taskCount = document.getElementById('taskCount');
    this.clearCompletedBtn = document.getElementById('clearCompleted');
    this.filterBtns = document.querySelectorAll('.filter-btn');

    this.bindEvents();
    this.render();
  }

  bindEvents() {
    this.addBtn.addEventListener('click', () => this.addTask());
    this.taskInput.addEventListener('keydown', (e) => {
      if (e.key === 'Enter') this.addTask();
    });

    this.filterBtns.forEach(btn => {
      btn.addEventListener('click', () => {
        this.currentFilter = btn.dataset.filter;
        this.filterBtns.forEach(b => b.classList.remove('active'));
        btn.classList.add('active');
        this.render();
      });
    });

    this.clearCompletedBtn.addEventListener('click', () => {
      this.tasks = this.tasks.filter(t => !t.completed);
      this.save();
      this.render();
    });
  }

  addTask() {
    const text = this.taskInput.value.trim();
    if (!text) return;

    this.tasks.push({
      id: Date.now(),
      text,
      completed: false
    });

    this.taskInput.value = '';
    this.taskInput.focus();
    this.save();
    this.render();
  }

  toggleTask(id) {
    const task = this.tasks.find(t => t.id === id);
    if (task) {
      task.completed = !task.completed;
      this.save();
      this.render();
    }
  }

  deleteTask(id) {
    this.tasks = this.tasks.filter(t => t.id !== id);
    this.save();
    this.render();
  }

  getFilteredTasks() {
    switch (this.currentFilter) {
      case 'active':
        return this.tasks.filter(t => !t.completed);
      case 'completed':
        return this.tasks.filter(t => t.completed);
      default:
        return this.tasks;
    }
  }

  render() {
    const filtered = this.getFilteredTasks();
    this.taskList.innerHTML = '';

    filtered.forEach(task => {
      const li = document.createElement('li');
      if (task.completed) li.classList.add('completed');

      const checkbox = document.createElement('input');
      checkbox.type = 'checkbox';
      checkbox.checked = task.completed;
      checkbox.addEventListener('change', () => this.toggleTask(task.id));

      const span = document.createElement('span');
      span.className = 'task-text';
      span.textContent = task.text;

      const deleteBtn = document.createElement('button');
      deleteBtn.className = 'delete-btn';
      deleteBtn.textContent = 'X';
      deleteBtn.addEventListener('click', () => this.deleteTask(task.id));

      li.append(checkbox, span, deleteBtn);
      this.taskList.appendChild(li);
    });

    const remaining = this.tasks.filter(t => !t.completed).length;
    this.taskCount.textContent = `${remaining} task${remaining !== 1 ? 's' : ''} remaining`;
  }

  save() {
    localStorage.setItem('todo-tasks', JSON.stringify(this.tasks));
  }

  loadTasks() {
    try {
      const data = localStorage.getItem('todo-tasks');
      return data ? JSON.parse(data) : [];
    } catch {
      return [];
    }
  }
}

document.addEventListener('DOMContentLoaded', () => {
  new TodoManager();
});
