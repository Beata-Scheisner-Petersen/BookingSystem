async function registerCustomer() {

    const firstname = document.getElementById("firstname").value;
    const lastname = document.getElementById("lastname").value;
    const identificationNumber = document.getElementById("id_number").value;
    const email = document.getElementById("email").value;
    const phoneNumber = document.getElementById("phonenumber").value;
    const password = document.getElementById("password").value;

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

    if (response.ok) {
        window.location.href = "/mypage";
    } else {
        alert("Failed to register");
    }
}