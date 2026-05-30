// Hamburger toggle
const hamburger = document.getElementById('hamburger');
const navMenu = document.getElementById('navMenu');

hamburger.addEventListener('click', () => {
    navMenu.classList.toggle('open');
});

// Close mobile menu when a link is clicked
navMenu.addEventListener('click', (e) => {
    if (e.target.classList.contains('nav-link')) {
        navMenu.classList.remove('open');
    }
});

// Active link highlighting based on scroll position
const sections = document.querySelectorAll('.section');
const navLinks = document.querySelectorAll('.nav-link');

function highlightNav() {
    let current = '';
    const scrollY = window.scrollY;

    sections.forEach((section) => {
        const top = section.offsetTop - 100;
        const height = section.offsetHeight;
        if (scrollY >= top && scrollY < top + height) {
            current = section.getAttribute('id');
        }
    });

    navLinks.forEach((link) => {
        link.classList.remove('active');
        if (link.getAttribute('href') === '#' + current) {
            link.classList.add('active');
        }
    });
}

window.addEventListener('scroll', highlightNav);
window.addEventListener('load', highlightNav);
