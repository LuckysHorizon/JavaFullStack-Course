const API_BASE = 'https://jsonplaceholder.typicode.com';

function log(containerId, message, type) {
    const container = document.getElementById(containerId);
    const p = document.createElement('p');
    p.className = 'log-' + type;
    p.textContent = message;
    container.appendChild(p);
}

function clearLog(containerId) {
    document.getElementById(containerId).innerHTML = '';
}

// 1. Promise creation and resolution
function runPromiseDemo() {
    clearLog('promiseOutput');
    log('promiseOutput', 'Creating a new Promise...', 'loading');

    const myPromise = new Promise((resolve, reject) => {
        setTimeout(() => {
            resolve({ message: 'Promise resolved successfully!', timestamp: new Date().toLocaleTimeString() });
        }, 1500);
    });

    myPromise.then(data => {
        log('promiseOutput', 'Result: ' + data.message, 'success');
        log('promiseOutput', 'Resolved at: ' + data.timestamp, 'info');
    }).catch(err => {
        log('promiseOutput', 'Error: ' + err.message, 'error');
    });
}

// 2. Sequential API calls
async function runSequential() {
    clearLog('sequentialOutput');
    log('sequentialOutput', 'Step 1: Fetching user...', 'loading');

    try {
        const userRes = await fetch(API_BASE + '/users/1');
        const user = await userRes.json();
        log('sequentialOutput', 'User: ' + user.name + ' (' + user.email + ')', 'success');

        log('sequentialOutput', 'Step 2: Fetching posts by ' + user.name + '...', 'loading');
        const postsRes = await fetch(API_BASE + '/posts?userId=' + user.id);
        const posts = await postsRes.json();
        log('sequentialOutput', 'Found ' + posts.length + ' posts', 'success');

        for (let i = 0; i < Math.min(3, posts.length); i++) {
            log('sequentialOutput', '  ' + (i + 1) + '. ' + posts[i].title, 'data');
        }
        if (posts.length > 3) {
            log('sequentialOutput', '  ... and ' + (posts.length - 3) + ' more', 'info');
        }
    } catch (err) {
        log('sequentialOutput', 'Error: ' + err.message, 'error');
    }
}

// 3. Parallel API calls with Promise.all
async function runParallel() {
    clearLog('parallelOutput');
    log('parallelOutput', 'Fetching 3 endpoints in parallel...', 'loading');

    const start = performance.now();

    try {
        const [users, posts, todos] = await Promise.all([
            fetch(API_BASE + '/users').then(r => r.json()),
            fetch(API_BASE + '/posts').then(r => r.json()),
            fetch(API_BASE + '/todos?_limit=10').then(r => r.json())
        ]);

        const elapsed = Math.round(performance.now() - start);

        log('parallelOutput', 'All 3 requests completed in ' + elapsed + 'ms', 'success');
        log('parallelOutput', '  Users: ' + users.length, 'data');
        log('parallelOutput', '  Posts: ' + posts.length, 'data');
        log('parallelOutput', '  Todos: ' + todos.length, 'data');
    } catch (err) {
        log('parallelOutput', 'Error: ' + err.message, 'error');
    }
}

// 4. Error handling with try/catch
async function runErrorHandling() {
    clearLog('errorOutput');

    // intentional error
    log('errorOutput', 'Attempt 1: Fetching invalid endpoint...', 'loading');
    try {
        const response = await fetch(API_BASE + '/nonexistent-endpoint');
        if (!response.ok) {
            throw new Error('HTTP ' + response.status + ': ' + response.statusText);
        }
        const data = await response.json();
        log('errorOutput', 'Data: ' + JSON.stringify(data), 'data');
    } catch (err) {
        log('errorOutput', 'Caught error: ' + err.message, 'error');
    }

    // successful recovery
    log('errorOutput', '', 'info');
    log('errorOutput', 'Attempt 2: Fetching valid endpoint...', 'loading');
    try {
        const response = await fetch(API_BASE + '/users/3');
        if (!response.ok) {
            throw new Error('HTTP ' + response.status + ': ' + response.statusText);
        }
        const user = await response.json();
        log('errorOutput', 'Success! User: ' + user.name, 'success');
        log('errorOutput', '  Email: ' + user.email, 'data');
        log('errorOutput', '  Company: ' + user.company.name, 'data');
    } catch (err) {
        log('errorOutput', 'Error: ' + err.message, 'error');
    }
}

// 5. Promise.race and Promise.allSettled
async function runRaceAndAllSettled() {
    clearLog('raceOutput');

    // Promise.race
    log('raceOutput', '--- Promise.race ---', 'info');
    log('raceOutput', 'Racing 3 delays (500ms, 200ms, 800ms)...', 'loading');

    try {
        const winner = await Promise.race([
            new Promise(resolve => setTimeout(() => resolve('Slow (500ms)'), 500)),
            new Promise(resolve => setTimeout(() => resolve('Fast (200ms)'), 200)),
            new Promise(resolve => setTimeout(() => resolve('Slowest (800ms)'), 800))
        ]);
        log('raceOutput', 'Winner: ' + winner, 'success');
    } catch (err) {
        log('raceOutput', 'Error: ' + err.message, 'error');
    }

    // Promise.allSettled
    log('raceOutput', '', 'info');
    log('raceOutput', '--- Promise.allSettled ---', 'info');

    const results = await Promise.allSettled([
        fetch(API_BASE + '/users/1').then(r => r.json()).then(u => u.name),
        Promise.reject(new Error('Intentional failure')),
        fetch(API_BASE + '/users/2').then(r => r.json()).then(u => u.name)
    ]);

    results.forEach((r, i) => {
        if (r.status === 'fulfilled') {
            log('raceOutput', '  ' + (i + 1) + '. fulfilled: ' + r.value, 'data');
        } else {
            log('raceOutput', '  ' + (i + 1) + '. rejected: ' + r.reason.message, 'error');
        }
    });
}
