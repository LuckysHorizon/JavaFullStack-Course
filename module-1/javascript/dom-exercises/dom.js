// --- Exercise 1: Element Creation ---
let paragraphCount = 0;

function randomColor() {
    const hue = Math.floor(Math.random() * 360);
    return 'hsl(' + hue + ', 70%, 85%)';
}

document.getElementById('addParagraph').addEventListener('click', () => {
    const container = document.getElementById('paragraphContainer');
    const p = document.createElement('p');
    paragraphCount++;
    p.textContent = 'Dynamic paragraph #' + paragraphCount + ' -- created at ' + new Date().toLocaleTimeString();
    p.style.background = randomColor();
    p.style.padding = '0.6rem 1rem';
    p.style.borderRadius = '6px';
    p.style.marginBottom = '0.5rem';
    container.appendChild(p);
});

document.getElementById('clearParagraphs').addEventListener('click', () => {
    document.getElementById('paragraphContainer').innerHTML = '';
    paragraphCount = 0;
});


// --- Exercise 2: Style Manipulation ---
const styleTarget = document.getElementById('styleTarget');
const colorBtns = document.querySelectorAll('.color-btn');
const fontSizeSlider = document.getElementById('fontSizeSlider');
const fontSizeVal = document.getElementById('fontSizeVal');

colorBtns.forEach(btn => {
    btn.addEventListener('click', () => {
        styleTarget.style.backgroundColor = btn.dataset.color;
    });
});

fontSizeSlider.addEventListener('input', () => {
    const size = fontSizeSlider.value;
    styleTarget.style.fontSize = size + 'px';
    fontSizeVal.textContent = size + 'px';
});

document.getElementById('toggleBold').addEventListener('click', () => {
    const current = styleTarget.style.fontWeight;
    styleTarget.style.fontWeight = current === 'bold' ? 'normal' : 'bold';
});

document.getElementById('toggleBorder').addEventListener('click', () => {
    const current = styleTarget.style.border;
    styleTarget.style.border = current ? '' : '3px solid #6c5ce7';
});


// --- Exercise 3: Event Delegation ---
const delegationList = document.getElementById('delegationList');
const delegationInput = document.getElementById('delegationInput');

document.getElementById('addDelegationItem').addEventListener('click', () => {
    const text = delegationInput.value.trim();
    if (!text) return;

    const li = document.createElement('li');
    li.innerHTML = '<span>' + text + '</span><button class="remove-item">X</button>';
    delegationList.appendChild(li);
    delegationInput.value = '';
});

delegationInput.addEventListener('keydown', (e) => {
    if (e.key === 'Enter') document.getElementById('addDelegationItem').click();
});

// single event listener on the parent handles all items
delegationList.addEventListener('click', (e) => {
    if (e.target.classList.contains('remove-item')) {
        e.target.parentElement.remove();
    } else if (e.target.tagName === 'SPAN') {
        e.target.parentElement.classList.toggle('highlighted');
    }
});


// --- Exercise 4: Class Manipulation ---
const classBox = document.getElementById('classBox');

document.getElementById('addClass').addEventListener('click', () => {
    classBox.classList.add('accent');
});

document.getElementById('removeClass').addEventListener('click', () => {
    classBox.classList.remove('accent');
});

document.getElementById('toggleClass').addEventListener('click', () => {
    classBox.classList.toggle('rounded');
});


// --- Exercise 5: DOM Traversal ---
const traversalTree = document.getElementById('traversalTree');

traversalTree.addEventListener('click', (e) => {
    const li = e.target.closest('li');
    if (!li) return;

    const label = li.querySelector(':scope > span') || li;
    const labelText = (label.textContent || '').trim();

    const parent = li.parentElement.closest('li');
    const parentLabel = parent
        ? (parent.querySelector(':scope > span') || parent).textContent.trim()
        : 'Root';

    const childUl = li.querySelector(':scope > ul');
    const children = childUl ? Array.from(childUl.querySelectorAll(':scope > li')) : [];
    const childLabels = children.length > 0
        ? children.map(c => (c.querySelector(':scope > span') || c).textContent.trim()).join(', ')
        : 'None';

    const siblings = Array.from(li.parentElement.children).filter(s => s !== li);
    const siblingLabels = siblings.length > 0
        ? siblings.map(s => (s.querySelector(':scope > span') || s).textContent.trim()).join(', ')
        : 'None';

    const info = 'Selected: ' + labelText + '\n' +
                 'Parent: ' + parentLabel + '\n' +
                 'Children: ' + childLabels + '\n' +
                 'Siblings: ' + siblingLabels;

    document.getElementById('traversalInfo').textContent = info;
});


// --- Exercise 6: Attribute Manipulation ---
let imageIndex = 1;
const attrImage = document.getElementById('attrImage');

document.getElementById('changeImage').addEventListener('click', () => {
    imageIndex++;
    attrImage.setAttribute('src', 'https://picsum.photos/seed/' + imageIndex + '/200/120');
    attrImage.setAttribute('alt', 'Random image #' + imageIndex);
});

document.getElementById('toggleHidden').addEventListener('click', () => {
    if (attrImage.hasAttribute('hidden')) {
        attrImage.removeAttribute('hidden');
    } else {
        attrImage.setAttribute('hidden', '');
    }
});

document.getElementById('addDataAttr').addEventListener('click', () => {
    attrImage.dataset.loaded = new Date().toLocaleTimeString();
    document.getElementById('dataAttrDisplay').textContent =
        'data-loaded = ' + attrImage.dataset.loaded;
});
