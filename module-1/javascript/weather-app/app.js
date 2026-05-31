const API_KEY = 'YOUR_API_KEY_HERE'; // Get a free key from openweathermap.org
const BASE_URL = 'https://api.openweathermap.org/data/2.5/weather';

const searchBtn = document.getElementById('searchBtn');
const cityInput = document.getElementById('cityInput');
const weatherCard = document.getElementById('weatherCard');
const errorDiv = document.getElementById('error');
const loadingDiv = document.getElementById('loading');

searchBtn.addEventListener('click', handleSearch);
cityInput.addEventListener('keydown', (e) => {
    if (e.key === 'Enter') handleSearch();
});

async function handleSearch() {
    const city = cityInput.value.trim();
    if (!city) return;

    const url = `${BASE_URL}?q=${encodeURIComponent(city)}&appid=${API_KEY}&units=metric`;

    errorDiv.hidden = true;
    weatherCard.hidden = true;
    loadingDiv.hidden = false;

    try {
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error('City "' + city + '" not found. Please check the spelling.');
        }
        const data = await response.json();
        displayWeather(data);
    } catch (err) {
        showError(err.message);
    } finally {
        loadingDiv.hidden = true;
    }
}

function displayWeather(data) {
    weatherCard.hidden = false;

    document.getElementById('cityName').textContent = data.name + ', ' + data.sys.country;
    document.getElementById('temperature').textContent = Math.round(data.main.temp) + 'C';
    document.getElementById('description').textContent = data.weather[0].description;
    document.getElementById('humidity').textContent = data.main.humidity + '%';
    document.getElementById('wind').textContent = data.wind.speed + ' m/s';
    document.getElementById('feelsLike').textContent = Math.round(data.main.feels_like) + 'C';
    document.getElementById('visibility').textContent = (data.visibility / 1000).toFixed(1) + ' km';

    const iconCode = data.weather[0].icon;
    const iconEl = document.getElementById('weatherIcon');
    iconEl.src = 'https://openweathermap.org/img/wn/' + iconCode + '@2x.png';
    iconEl.alt = data.weather[0].description;
    iconEl.hidden = false;
}

function showError(msg) {
    errorDiv.textContent = msg;
    errorDiv.hidden = false;
}
