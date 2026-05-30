const display = document.getElementById('display');
const historyList = document.getElementById('historyList');

let currentInput = '0';
let previousInput = '';
let operator = '';
let shouldResetDisplay = false;

function updateDisplay() {
    display.textContent = currentInput;
}

function appendNumber(num) {
    if (num === '.' && currentInput.includes('.')) return;
    if (shouldResetDisplay) {
        currentInput = num === '.' ? '0.' : num;
        shouldResetDisplay = false;
    } else {
        currentInput = currentInput === '0' && num !== '.' ? num : currentInput + num;
    }
    updateDisplay();
}

function setOperator(op) {
    if (operator && !shouldResetDisplay) {
        calculate();
    }
    previousInput = currentInput;
    operator = op;
    shouldResetDisplay = true;
}

function calculate() {
    if (!operator || !previousInput) return;

    const prev = parseFloat(previousInput);
    const curr = parseFloat(currentInput);
    let result;

    switch (operator) {
        case '+': result = prev + curr; break;
        case '-': result = prev - curr; break;
        case 'x': result = prev * curr; break;
        case '/':
            if (curr === 0) {
                display.textContent = 'Error';
                currentInput = '0';
                previousInput = '';
                operator = '';
                return;
            }
            result = prev / curr;
            break;
        case '%': result = prev % curr; break;
        default: return;
    }

    result = parseFloat(result.toFixed(10));

    const expression = previousInput + ' ' + operator + ' ' + currentInput;
    addToHistory(expression, result);

    currentInput = result.toString();
    previousInput = '';
    operator = '';
    shouldResetDisplay = true;
    updateDisplay();
}

function clearAll() {
    currentInput = '0';
    previousInput = '';
    operator = '';
    shouldResetDisplay = false;
    updateDisplay();
}

function backspace() {
    if (shouldResetDisplay) return;
    currentInput = currentInput.length > 1 ? currentInput.slice(0, -1) : '0';
    updateDisplay();
}

function addToHistory(expression, result) {
    const li = document.createElement('li');
    li.innerHTML = expression + ' = <span>' + result + '</span>';
    historyList.prepend(li);

    // keep last 10
    while (historyList.children.length > 10) {
        historyList.removeChild(historyList.lastChild);
    }
}

// button click handler
document.querySelector('.buttons').addEventListener('click', (e) => {
    const btn = e.target.closest('.btn');
    if (!btn) return;

    const action = btn.dataset.action;
    if (!action) return;

    if (!isNaN(action) || action === '.') {
        appendNumber(action);
    } else if (action === 'clear') {
        clearAll();
    } else if (action === 'backspace') {
        backspace();
    } else if (action === '=') {
        calculate();
    } else if (['+', '-', 'x', '/'].includes(action)) {
        setOperator(action);
    } else if (action === '%') {
        setOperator('%');
    }
});

// keyboard support
document.addEventListener('keydown', (e) => {
    const key = e.key;

    if ((key >= '0' && key <= '9') || key === '.') {
        appendNumber(key);
    } else if (key === '+') {
        setOperator('+');
    } else if (key === '-') {
        setOperator('-');
    } else if (key === '*') {
        setOperator('x');
    } else if (key === '/') {
        e.preventDefault();
        setOperator('/');
    } else if (key === '%') {
        setOperator('%');
    } else if (key === 'Enter' || key === '=') {
        calculate();
    } else if (key === 'Backspace') {
        backspace();
    } else if (key === 'Escape') {
        clearAll();
    }
});

updateDisplay();
