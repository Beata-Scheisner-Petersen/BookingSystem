document.addEventListener("DOMContentLoaded", () => {
    fetch("http://localhost:8080/api/reservations/me/reservations", {
        credentials: "include" // skickar session-cookien
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Kunde inte hämta bokningar");
            }
            return response.json();
        })
        .then(data => renderReservations(data))
        .catch(error => console.error("Fel:", error));
});

function renderReservations(reservations) {
    const tbody = document.querySelector("#reservationTable tbody");
    tbody.innerHTML = "";

    reservations.forEach(res => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${res.checkIn}</td>
            <td>${res.checkOut}</td>
            <td>${res.room.roomNumber}</td>
            <td>${res.extraBed ? "Yes" : "No"}</td>
            <td>${res.totalCost} kr</td>
            <td class="status-${res.status.toLowerCase()}">${res.status}</td>
        `;

        tbody.appendChild(row);
    });
}
