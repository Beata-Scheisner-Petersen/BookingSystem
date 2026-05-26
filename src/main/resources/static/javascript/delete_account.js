async function deleteYes() {

    const response = await fetch("/api/customers", {
        method: "DELETE"
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
        alert(data.message); //Account deleted
        window.location.href = "/";
        return;
    }

    if (response.status === 409) {
        document.getElementById(`delete_error`).innerText = data.error; //Could not delete account
        return;
    }

    alert(`Unexpected error ${rawValue}`);
}

async function deleteNo() {
    window.location.href = "/mypage"
}