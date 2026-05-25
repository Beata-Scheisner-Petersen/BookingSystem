async function apiFetch(url, options = {}) {
    const token = localStorage.getItem("jwt");

    const headers = {
        "Content-Type": "application/json",
        ...options.headers // ... simply means take ALL properties from the object and copy them into this one (This one being options {} )
    }

    if (token) {
        headers.Authorization = `Bearer ${token}`;
    }

    const response = await fetch(url, {
        ...options, //Once again ... to copy the whole object into this new one
        headers
    });

    // If token is expired it gets removed from LocalStorage, then user gets thrown back to login page
    if (response.status === 401) {
        localStorage.removeItem("jwt");
        window.location.href = "/login.html";
        return;
    }

    return response;
}