async function registerCustomer() {

    const firstname = document.getElementById("firstname").value;
    const lastname = document.getElementById("lastname").value;
    const identificationNumber = document.getElementById("id_number").value;
    const email = document.getElementById("email").value;
    const phoneNumber = document.getElementById("phonenumber").value;
    const password = document.getElementById("password").value;

    //Clears old errors
    document.getElementById("firstname_error").innerText = "";
    document.getElementById("lastname_error").innerText = "";
    document.getElementById("identificationNumber_error").innerText = "";
    document.getElementById("email_error").innerText = "";
    document.getElementById("phoneNumber_error").innerText = "";
    document.getElementById("password_error").innerText = "";

    const response = await fetch("/api/customers", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            firstname,
            lastname,
            identificationNumber,
            email,
            password,
            phoneNumber
        })
    });

    const data = await response.json();

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

    alert("Failed to register");
}