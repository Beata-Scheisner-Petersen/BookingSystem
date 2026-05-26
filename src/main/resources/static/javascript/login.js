async function login() {

    //Values from Input
    const email = document.getElementById("email_input").value;
    const password = document.getElementById("password_input").value;

    //Clear old errors
    document.getElementById("email_error").innerText = "";
    document.getElementById("password_error").innerText = "";


    const response = await fetch("/api/customers/login", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({email, password})
    });

    const rawValue = await response.text();

    //Parsing between text and json
    let data;
    try {
        data = JSON.parse(rawValue);
    } catch {
        data = rawValue;
    }

    if (response.ok) {
        window.location.href = "/mypage";
        return;
    }

    //Validation errors
    if (response.status === 400 && typeof data == "object") {
        for (const field in data) {
            const errorDiv = document.getElementById(`${field}_error`);
            if (errorDiv) {
                errorDiv.innerHTML = data[field];
            }
        }
        return;
    }

    //Wrong Email/Password
    if (response.status === 409) {
        alert(data);
        return;
    }

    alert("Login Failed");
}

async function registerNewCustomer() {
    window.location.href = "/register";
}