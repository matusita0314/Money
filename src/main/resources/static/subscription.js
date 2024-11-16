function updateForm() {
    const billingCycle = document.getElementById('billingCycle').value;
    const monthlyFields = document.getElementById('monthFields');
    const annualFields = document.getElementById('annualFields');

    if (billingCycle === 'MONTHLY') {
        monthlyFields.style.display = 'block';
        annualFields.style.display = 'none';
    } else if (billingCycle === 'ANNUAL') {
        monthlyFields.style.display = 'none';
        annualFields.style.display = 'block';
    } else {
        monthlyFields.style.display = 'none';
        annualFields.style.display = 'none';
    }
}

document.addEventListener('DOMContentLoaded', updateForm);
