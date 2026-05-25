async function login() {

    const email = document.getElementById("email_input").value;
    const password = document.getElementById("password_input").value;

    const response = await fetch("/api/customers/login", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({email, password})
    });

    if (response.ok) {
        window.location.href = "/mypage";
    } else {
        alert("Login failed");
    }
}

async function registerNewCustomer() {
    window.location.href = "/register";
}