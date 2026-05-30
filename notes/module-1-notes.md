# Module 1  Quick Notes

## HTML5
- Always use semantic tags (<header>, <nav>, <main>, <section>, <footer>) instead of generic divs
- Forms: use appropriate type attributes for inputs  gives you free validation on mobile
- localStorage persists across sessions, sessionStorage clears when tab closes
- Geolocation API requires HTTPS in production (localhost works for dev)

## CSS3
- Flexbox = one-dimensional (row OR column), Grid = two-dimensional (rows AND columns)
- min(), max(), clamp() are super useful for responsive typography
- Media queries: mobile-first approach means default styles are for small screens, use min-width to scale up
- transition for simple state changes, @keyframes for complex multi-step animations

## JavaScript
- const by default, let when you need reassignment, avoid var
- Arrow functions don't have their own this  important for callbacks
- async/await is syntactic sugar over Promises, but makes code way more readable
- fetch() returns a Promise. Don't forget to check response.ok before parsing

## Bootstrap 5
- Dropped jQuery dependency  uses vanilla JS now
- Grid: 12 columns, use col-md-4 etc. for responsive breakpoints
- Utility classes reduce custom CSS needs (d-flex, justify-content-center, mt-3)
- Always include viewport meta tag for mobile responsiveness
