document.addEventListener("DOMContentLoaded", () => {
    fetch("/api/reservation/getAllCustomerReservation", {
        credentials: "include"
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Could not fetch reservations");
            }
            return response.json();
        })
        .then(data => renderReservations(data))
        .catch(error => console.error("Error:", error));
});

function renderReservations(reservations) {
    const tbody = document.querySelector("#reservationTable tbody");
    tbody.innerHTML = "";

    reservations.forEach(res => {
        // MAIN ROW
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${res.checkIn}</td>
            <td>${res.checkOut}</td>
            <td>${res.roomNumber}</td>
            <td>${res.extraBed ? "Yes" : "No"}</td>
            <td>${res.totalCost} kr</td>
            <td class="status-${res.status.toLowerCase()}">${res.status}</td>
            <td>
                ${res.status !== "CANCELED" ? `
                    <button class="action-btn edit-btn" onclick="toggleEditForm(${res.id})">Edit</button>
                    <button class="action-btn delete-btn" onclick="deleteReservation(${res.id}, this)">Delete</button>
                ` : ""}
            </td>
        `;

        tbody.appendChild(row);

        // EDIT FORM ROW
        const formRow = document.createElement("tr");
        formRow.innerHTML = `
            <td colspan="7">
                <div id="edit-form-${res.id}" class="edit-form">
                
                    <h4 id="edit-title">Edit reservation</h4>
                    
                    <div class="edit-grid">
                    
                        <div class="date-block">
                            <label>Check-in date:</label>
                            <input type="date" id="checkIn-${res.id}" value="${res.checkIn}">
                        </div>
                        
                        <div class="date-block">
                            <label>Check-out date:</label>
                            <input type="date" id="checkOut-${res.id}" value="${res.checkOut}">
                        </div> 
                    </div>
                    
                    <button class="action-btn save-btn" onclick="saveChanges(${res.id})">Save changes</button>
                </div>
            </td>
        `;

        tbody.appendChild(formRow);
    });
}

// SHOW/HIDE EDIT FORM
function toggleEditForm(id) {
    const form = document.getElementById(`edit-form-${id}`);
    form.style.display = form.style.display === "block" ? "none" : "block";
}

// SAVE CHANGES (PUT)
function saveChanges(id) {
    const checkIn = document.getElementById(`checkIn-${id}`).value;
    const checkOut = document.getElementById(`checkOut-${id}`).value;

    const body = {
        checkIn,
        checkOut
    };

    fetch(`/api/reservation/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify(body)
    })
        .then(res => {
            if (!res.ok) {
                throw new Error("Could not update reservation");
            }
            alert("Reservation updated successfully");
            location.reload();
        })
        .catch(err => {
            document.getElementById("errorPopup").style.display = "flex";
            console.error(err);
        });
}

// DELETE RESERVATION
function deleteReservation(id, btn) {
    if (!confirm("Are you sure you want to delete this reservation?")) {
        return;
    }

    fetch(`/api/reservation/${id}`, {
        method: "DELETE",
        credentials: "include"
    })
        .then(res => {
            if (!res.ok) {
                throw new Error("Could not delete reservation");
            }

            btn.closest("tr").nextSibling.remove();
            btn.closest("tr").remove();
            alert("Reservation updated successfully");
            location.reload();
        })
        .catch(err => console.error(err));
}

// CLOSE ERROR POPUP
document.getElementById("closeErrorPopup").addEventListener("click", () => {
    document.getElementById("errorPopup").style.display = "none";
});

