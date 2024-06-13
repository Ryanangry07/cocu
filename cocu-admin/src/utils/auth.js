// utils/auth.js
export function getQueryParams() {
    const params = {};
    console.log('cur location' + window.location.search.toString())
    window.location.search.substring(1).split("&").forEach(function (pair) {
        const [key, value] = pair.split("=");
        params[key] = decodeURIComponent(value || "");
    });
    return params;
}

export function saveTokenFromUrl() {
    const params = getQueryParams();
    const token = params.token;
    console.log('token' +  token)

    if (token) {
        // Store the token in localStorage
        localStorage.setItem('jwtToken', token);

        // Optionally, remove the token from the URL
        const url = new URL(window.location);
        url.searchParams.delete('token');
        window.history.replaceState({}, document.title, url.toString());
    }
}

export function getToken() {
    return localStorage.getItem('jwtToken');
}