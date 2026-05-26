async function login() {

    const email = document.getElementById("email_input").value;
    const password = document.getElementById("password_input").value;

    const response = await fetch("/api/customers/login", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({email, password})
    });

    const data = await response.json();

    if (response.ok) {
        window.location.href = "/mypage";
        return;
    }

    //Validation errors
    if(response.status === 400 && typeof data == "object") {
        const messages = Object.values(data);
        alert(messages.join("\n"));
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