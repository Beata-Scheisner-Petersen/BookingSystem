document.addEventListener("DOMContentLoaded", () => {

    const form = document.getElementById("update");

    form.addEventListener("submit", async function (event) {
        event.preventDefault(); // stoppar vanlig submit

        // Rensa gamla fel
        document.getElementById("emailError").textContent = "";
        document.getElementById("phoneError").textContent = "";

        const formData = new FormData(form);

        const response = await fetch("/api/customers/update", {
            method: "POST",
            body: formData
        });

        // 🔥 Om inte inloggad → redirect till login
        if (response.status === 401) {
            window.location.href = "/login";
            return;
        }

        const data = await response.json();

        // 🔥 Visa felmeddelanden
        if (data.emailError) {
            document.getElementById("emailError").textContent = data.emailError;
        }

        if (data.phoneError) {
            document.getElementById("phoneError").textContent = data.phoneError;
        }

        // 🔥 Om allt gick bra → redirect
        if (data.success) {
            window.location.href = "/mypage";
        }
    });
});
