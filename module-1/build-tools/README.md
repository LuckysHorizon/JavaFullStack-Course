# Build Tools Exercise

A simple project demonstrating **Webpack 5** bundling with **Babel** transpilation.

## What's Set Up

- **Webpack 5**  Module bundler with entry/output, loaders, and plugins
- **Babel**  Transpiles ES6+ syntax (classes, arrow functions, optional chaining) for older browsers
- **CSS Loader + Style Loader**  Imports CSS directly in JavaScript
- **HtmlWebpackPlugin**  Auto-generates index.html with the correct script tags
- **Dev Server**  Hot module replacement for fast development

## Project Structure


build-tools/
 src/
    index.html      # HTML template
    index.js         # ES6+ entry point
    style.css        # Imported styles
 webpack.config.js    # Webpack configuration
 babel.config.js      # Babel presets
 package.json         # Dependencies and scripts
 dist/                # Generated output (after build)


## Setup & Run

bash
# Install dependencies
npm install

# Start development server (with hot reload)
npm run dev
# or
npm start      # same thing, but also opens browser

# Build for production
npm run build


## How It Works

1. src/index.js imports style.css  Webpack handles this via css-loader
2. Babel transpiles modern JS (classes, template literals, optional chaining) into compatible code
3. HtmlWebpackPlugin injects the bundled script into src/index.html
4. Output goes to dist/ with content hashing for cache busting
