document.getElementById("login_form")
    .addEventListener("submit", async function (event) {
        event.preventDefault();

        const email = document.getElementById("email_input").value;
        const password = document.getElementById("password_input").value;

        const response = await fetch("/api/customers/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, password })
        });

        if (response.ok) {
            const jwtData = await response.json();
            window.localStorage.setItem("jwt", jwtData.token);

            window.location.href = "static/MyPage.html";
        } else {
            alert("Login failed");
        }
    });