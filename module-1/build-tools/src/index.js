import './style.css';

// ES6+ features that Babel will transpile for older browsers

class QuoteGenerator {
    constructor(containerId) {
        this.container = document.getElementById(containerId);
        this.quotes = [
            { text: 'The best way to predict the future is to create it.', author: 'Peter Drucker' },
            { text: 'Code is like humor. When you have to explain it, it\'s bad.', author: 'Cory House' },
            { text: 'First, solve the problem. Then, write the code.', author: 'John Johnson' },
            { text: 'Simplicity is the soul of efficiency.', author: 'Austin Freeman' },
            { text: 'Make it work, make it right, make it fast.', author: 'Kent Beck' },
            { text: 'Any fool can write code that a computer can understand.', author: 'Martin Fowler' }
        ];
    }

    getRandomQuote() {
        const idx = Math.floor(Math.random() * this.quotes.length);
        return this.quotes[idx];
    }

    render() {
        const { text, author } = this.getRandomQuote();

        // template literals and destructuring
        this.container.innerHTML = `
            <div class="card">
                <h1>Daily Inspiration</h1>
                <blockquote>"${text}"</blockquote>
                <p class="author">-- ${author}</p>
                <button id="newQuoteBtn">New Quote</button>
                <p class="footer">Built with Webpack 5 + Babel</p>
            </div>
        `;

        // arrow function
        document.getElementById('newQuoteBtn').addEventListener('click', () => this.render());
    }
}

// optional chaining and nullish coalescing (modern JS that Babel handles)
const appElement = document.getElementById('app');
const title = appElement?.dataset?.title ?? 'Quote Generator';

const app = new QuoteGenerator('app');
app.render();

console.log('App "' + title + '" initialized successfully.');
