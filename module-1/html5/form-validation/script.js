const form = document.getElementById('registrationForm');
const fields = {
    fullName: document.getElementById('fullName'),
    email: document.getElementById('email'),
    phone: document.getElementById('phone'),
    password: document.getElementById('password'),
    confirmPassword: document.getElementById('confirmPassword')
};

const errors = {
    fullName: document.getElementById('nameError'),
    email: document.getElementById('emailError'),
    phone: document.getElementById('phoneError'),
    password: document.getElementById('passError'),
    confirmPassword: document.getElementById('confirmError')
};

const strengthFill = document.getElementById('strengthFill');
const strengthText = document.getElementById('strengthText');

// validation rules for each field
const validators = {
    fullName(value) {
        if (!value.trim()) return 'Name is required';
        if (value.trim().length < 2) return 'Name must be at least 2 characters';
        if (!/[a-zA-Z\s]+$/.test(value)) return 'Only letters and spaces allowed';
        return '';
    },

    email(value) {
        if (!value.trim()) return 'Email is required';
        const pattern = /[\s@]+@[\s@]+\.[\s@]+$/;
        if (!pattern.test(value)) return 'Enter a valid email address';
        return '';
    },

    phone(value) {
        if (!value.trim()) return 'Phone number is required';
        const digits = value.replace(/\D/g, '');
        if (digits.length !== 10) return 'Phone must be exactly 10 digits';
        return '';
    },

    password(value) {
        if (!value) return 'Password is required';
        if (value.length < 8) return 'At least 8 characters needed';
        return '';
    },

    confirmPassword(value) {
        if (!value) return 'Please confirm your password';
        if (value !== fields.password.value) return 'Passwords do not match';
        return '';
    }
};

// password strength calculation
function getPasswordStrength(pwd) {
    let score = 0;
    if (pwd.length >= 8) score++;
    if (pwd.length >= 12) score++;
    if (/[A-Z]/.test(pwd)) score++;
    if (/[0-9]/.test(pwd)) score++;
    if (/[a-zA-Z0-9]/.test(pwd)) score++;
    return score;
}

function updateStrengthMeter(pwd) {
    if (!pwd) {
        strengthFill.style.width = '0';
        strengthText.textContent = '';
        return;
    }

    const score = getPasswordStrength(pwd);
    const levels = [
        { width: '20%', color: '#e74c3c', label: 'Very Weak' },
        { width: '40%', color: '#e67e22', label: 'Weak' },
        { width: '60%', color: '#f1c40f', label: 'Fair' },
        { width: '80%', color: '#27ae60', label: 'Strong' },
        { width: '100%', color: '#2ecc71', label: 'Very Strong' }
    ];

    const level = levels[Math.min(score, levels.length) - 1] || levels[0];
    strengthFill.style.width = level.width;
    strengthFill.style.backgroundColor = level.color;
    strengthText.textContent = level.label;
    strengthText.style.color = level.color;
}

function validateField(name) {
    const value = fields[name].value;
    const error = validators[name](value);

    errors[name].textContent = error;
    fields[name].classList.toggle('valid', !error && value.length > 0);
    fields[name].classList.toggle('invalid', !!error);

    if (name === 'password') {
        updateStrengthMeter(value);
        // re-validate confirm if it has content
        if (fields.confirmPassword.value) {
            validateField('confirmPassword');
        }
    }

    return !error;
}

// attach live validation to each field
Object.keys(fields).forEach(name => {
    fields[name].addEventListener('input', () => validateField(name));
    fields[name].addEventListener('blur', () => validateField(name));
});

form.addEventListener('submit', (e) => {
    e.preventDefault();

    let allValid = true;
    Object.keys(fields).forEach(name => {
        if (!validateField(name)) allValid = false;
    });

    if (allValid) {
        form.style.display = 'none';
        document.getElementById('successMsg').hidden = false;
    }
});
