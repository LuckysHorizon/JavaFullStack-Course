// Modal form validation and submission handler
document.addEventListener('DOMContentLoaded', function () {
    const contactForm = document.getElementById('contactForm');
    const submitBtn = document.getElementById('submitBtn');
    const contactModal = bootstrap.Modal.getOrCreateInstance(document.getElementById('contactModal'));
    const successModal = new bootstrap.Modal(document.getElementById('successModal'));
    const successToast = new bootstrap.Toast(document.getElementById('successToast'));

    submitBtn.addEventListener('click', function () {
        // Trigger Bootstrap validation styles
        contactForm.classList.add('was-validated');

        // Check if the form is valid
        if (!contactForm.checkValidity()) {
            return; // Stop if validation fails
        }

        // Form is valid  close contact modal and show confirmation
        contactModal.hide();

        // Small delay so the close animation finishes before success modal opens
        setTimeout(function () {
            successModal.show();
            successToast.show();
        }, 300);

        // Reset the form for next use
        contactForm.reset();
        contactForm.classList.remove('was-validated');
    });
});
