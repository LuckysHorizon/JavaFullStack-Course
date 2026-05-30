/*  1. Click Counter  */
const clickBtn = document.getElementById('clickBtn');
const resetBtn = document.getElementById('resetBtn');
const clickCount = document.getElementById('clickCount');
let count = 0;

clickBtn.addEventListener('click', () => {
    count++;
    clickCount.textContent = count;
});
resetBtn.addEventListener('click', () => {
    count = 0;
    clickCount.textContent = '0';
});

/*  2. Mouseover / Mouseout  */
const hoverBox = document.getElementById('hoverBox');

hoverBox.addEventListener('mouseover', () => {
    hoverBox.style.background = '#6c63ff';
    hoverBox.style.color = '#fff';
    hoverBox.textContent = "You're hovering! ";
});
hoverBox.addEventListener('mouseout', () => {
    hoverBox.style.background = '#f0f0f5';
    hoverBox.style.color = '#333';
    hoverBox.textContent = 'Hover over me!';
});

/*  3. Keypress Detector  */
const keypressInput = document.getElementById('keypressInput');
const keyInfo = document.getElementById('keyInfo');

keypressInput.addEventListener('keydown', (e) => {
    keyInfo.innerHTML = 
        <strong>Key:</strong> ${e.key} &nbsp;|&nbsp;
        <strong>Code:</strong> ${e.code} &nbsp;|&nbsp;
        <strong>Shift:</strong> ${e.shiftKey} &nbsp;|&nbsp;
        <strong>Ctrl:</strong> ${e.ctrlKey}
    ;
});

/*  4. Focus / Blur  */
const focusInput = document.getElementById('focusInput');
const focusStatus = document.getElementById('focusStatus');

focusInput.addEventListener('focus', () => {
    focusInput.classList.add('focused');
    focusStatus.textContent = ' Input is focused';
    focusStatus.style.color = '#00b894';
});
focusInput.addEventListener('blur', () => {
    focusInput.classList.remove('focused');
    focusStatus.textContent = ' Input lost focus (blurred)';
    focusStatus.style.color = '#636e72';
});

/*  5. Form Submit  */
const demoForm = document.getElementById('demoForm');
const formMsg = document.getElementById('formMsg');

demoForm.addEventListener('submit', (e) => {
    e.preventDefault();
    const username = demoForm.elements.username.value.trim();
    if (username) {
        formMsg.textContent = Form submitted! Username: "${username}" (default action was prevented);
        formMsg.style.color = '#00b894';
    } else {
        formMsg.textContent = 'Please enter a username.';
        formMsg.style.color = '#d63031';
    }
});

/*  6. Event Delegation  */
const itemList = document.getElementById('itemList');
const addItemBtn = document.getElementById('addItemBtn');
const delegationMsg = document.getElementById('delegationMsg');
let itemCount = 3;

// Single listener on parent handles all child clicks (including future items)
itemList.addEventListener('click', (e) => {
    if (e.target.tagName === 'LI') {
        delegationMsg.textContent = You clicked: "${e.target.textContent}";
        // Briefly highlight
        e.target.style.background = '#dfe6e9';
        setTimeout(() => { e.target.style.background = ''; }, 400);
    }
});

addItemBtn.addEventListener('click', () => {
    itemCount++;
    const li = document.createElement('li');
    li.textContent = Item ${itemCount};
    itemList.appendChild(li);
});

/*  7. Event Object Inspector  */
const inspectBtn = document.getElementById('inspectBtn');
const eventDetails = document.getElementById('eventDetails');

inspectBtn.addEventListener('click', (e) => {
    eventDetails.innerHTML = 
        <strong>type:</strong> ${e.type}<br>
        <strong>target:</strong> &lt;${e.target.tagName.toLowerCase()}&gt; "${e.target.textContent}"<br>
        <strong>currentTarget:</strong> &lt;${e.currentTarget.tagName.toLowerCase()}&gt;<br>
        <strong>timeStamp:</strong> ${Math.round(e.timeStamp)} ms<br>
        <strong>clientX / clientY:</strong> ${e.clientX}, ${e.clientY}<br>
        <strong>bubbles:</strong> ${e.bubbles}
    ;
});
