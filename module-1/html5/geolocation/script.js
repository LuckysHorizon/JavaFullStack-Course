const getBtn = document.getElementById('getLocationBtn');
const loading = document.getElementById('loading');
const locationResult = document.getElementById('locationResult');
const errorBox = document.getElementById('errorBox');
const distForm = document.getElementById('distForm');
const distResult = document.getElementById('distResult');

getBtn.addEventListener('click', fetchLocation);

function fetchLocation() {
    if (!navigator.geolocation) {
        showError('Geolocation is not supported by this browser.');
        return;
    }

    loading.hidden = false;
    locationResult.hidden = true;
    errorBox.hidden = true;

    navigator.geolocation.getCurrentPosition(onSuccess, onError, {
        enableHighAccuracy: true,
        timeout: 10000,
        maximumAge: 0
    });
}

function onSuccess(position) {
    loading.hidden = true;
    locationResult.hidden = false;

    const { latitude, longitude, accuracy } = position.coords;
    document.getElementById('lat').textContent = latitude.toFixed(6);
    document.getElementById('lng').textContent = longitude.toFixed(6);
    document.getElementById('accuracy').textContent = ${accuracy.toFixed(0)}m;
    document.getElementById('timestamp').textContent =
        'Fetched at: ' + new Date(position.timestamp).toLocaleTimeString();

    // pre-fill Point A with current location for convenience
    document.getElementById('lat1').value = latitude.toFixed(6);
    document.getElementById('lng1').value = longitude.toFixed(6);
}

function onError(err) {
    loading.hidden = true;
    const messages = {
        1: 'Permission denied. Allow location access in your browser settings.',
        2: 'Position unavailable. Try again later.',
        3: 'Request timed out. Check your connection and try again.'
    };
    showError(messages[err.code] || 'An unknown error occurred.');
}

function showError(msg) {
    errorBox.textContent = msg;
    errorBox.hidden = false;
}

// Haversine formula for distance between two coordinates
function haversineDistance(lat1, lon1, lat2, lon2) {
    const R = 6371; // Earth radius in km
    const toRad = deg => deg * Math.PI / 180;

    const dLat = toRad(lat2 - lat1);
    const dLon = toRad(lon2 - lon1);

    const a = Math.sin(dLat / 2) ** 2 +
              Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
              Math.sin(dLon / 2) ** 2;

    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return R * c;
}

distForm.addEventListener('submit', (e) => {
    e.preventDefault();

    const lat1 = parseFloat(document.getElementById('lat1').value);
    const lng1 = parseFloat(document.getElementById('lng1').value);
    const lat2 = parseFloat(document.getElementById('lat2').value);
    const lng2 = parseFloat(document.getElementById('lng2').value);

    const dist = haversineDistance(lat1, lng1, lat2, lng2);
    distResult.hidden = false;
    distResult.textContent = Distance: ${dist.toFixed(2)} km (${(dist * 1000).toFixed(0)} meters);
});
