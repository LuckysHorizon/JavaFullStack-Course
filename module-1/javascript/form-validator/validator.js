const validationRules = {
  username: {
    validate: (value) => /^[a-zA-Z0-9]{3,20}$/.test(value),
    message: 'Username must be 3-20 alphanumeric characters'
  },
  email: {
    validate: (value) => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value),
    message: 'Please enter a valid email address'
  },
  phone: {
    validate: (value) => /^\d{10}$/.test(value),
    message: 'Phone number must be exactly 10 digits'
  },
  password: {
    validate: (value) => {
      return value.length >= 8 &&
        /[A-Z]/.test(value) &&
        /[a-z]/.test(value) &&
        /[0-9]/.test(value) &&
        /[!@#$%&*()_+\-=\[\]{};:'",.<>?/\\|~]/.test(value);
    },
    message: 'Password needs 8+ chars, uppercase, lowercase, number, and special character'
  },
  confirmPassword: {
    validate: (value) => {
      const password = document.getElementById('password').value;
      return value.length > 0 && value === password;
    },
    message: 'Passwords do not match'
  }
};

const getPasswordStrength = (password) => {
  let score = 0;
  if (password.length >= 8) score++;
  if (password.length >= 12) score++;
  if (/[A-Z]/.test(password) && /[a-z]/.test(password)) score++;
  if (/[0-9]/.test(password)) score++;
  if (/[^a-zA-Z0-9]/.test(password)) score++;
  return score;
};

const strengthConfig = [
  { label: '', color: '#eee', width: '0%' },
  { label: 'Very Weak', color: '#e74c3c', width: '20%' },
  { label: 'Weak', color: '#e67e22', width: '40%' },
  { label: 'Fair', color: '#f1c40f', width: '60%' },
  { label: 'Strong', color: '#27ae60', width: '80%' },
  { label: 'Very Strong', color: '#2ecc71', width: '100%' }
];

const updateStrengthMeter = (password) => {
  const strengthBar = document.getElementById('strengthBar');
  const strengthLabel = document.getElementById('strengthLabel');
  const score = getPasswordStrength(password);
  const config = strengthConfig[score];

  strengthBar.style.width = config.width;
  strengthBar.style.background = config.color;
  strengthLabel.textContent = password ? config.label : '';
  strengthLabel.style.color = config.color;
};

const validateField = (fieldName) => {
  const input = document.getElementById(fieldName);
  const formGroup = input.closest('.form-group');
  const errorMsg = formGroup.querySelector('.error-msg');
  const icon = formGroup.querySelector('.status-icon');
  const value = input.value.trim();
  const rule = validationRules[fieldName];

  if (!value) {
    input.classList.remove('valid', 'invalid');
    icon.textContent = '';
    errorMsg.textContent = '';
    return false;
  }

  const isValid = rule.validate(value);

  if (isValid) {
    input.classList.add('valid');
    input.classList.remove('invalid');
    icon.textContent = 'OK';
    icon.style.color = '#27ae60';
    errorMsg.textContent = '';
  } else {
    input.classList.add('invalid');
    input.classList.remove('valid');
    icon.textContent = '!!';
    icon.style.color = '#e74c3c';
    errorMsg.textContent = rule.message;
  }

  return isValid;
};

// Real-time validation on input
Object.keys(validationRules).forEach(fieldName => {
  const input = document.getElementById(fieldName);

  input.addEventListener('input', () => {
    validateField(fieldName);

    if (fieldName === 'password') {
      updateStrengthMeter(input.value);
      // Re-validate confirm if it has a value
      const confirm = document.getElementById('confirmPassword');
      if (confirm.value) validateField('confirmPassword');
    }
  });
});

// Form submission
const form = document.getElementById('registrationForm');
const formMessage = document.getElementById('formMessage');

form.addEventListener('submit', (e) => {
  e.preventDefault();
  formMessage.classList.add('hidden');

  let allValid = true;
  const fields = Object.keys(validationRules);

  fields.forEach(field => {
    const isValid = validateField(field);
    if (!isValid) allValid = false;
  });

  if (allValid) {
    formMessage.textContent = 'Registration successful!';
    formMessage.className = 'form-message success';
  } else {
    formMessage.textContent = 'Please fix the errors above before submitting.';
    formMessage.className = 'form-message error';
  }
});
