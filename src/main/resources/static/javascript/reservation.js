async function getAvailableRooms() {
    const checkIn = document.getElementById('checkIn').value;
    const checkOut = document.getElementById('checkOut').value;
    const guests = document.getElementById('guests').value;

    // Basic validation before sending
    if (!checkIn || !checkOut || !guests) {
        alert('Please fill in all fields.');
        return;
    }

    const params = new URLSearchParams({checkIn, checkOut, guests});

    try {
        const response = await fetch(`/api/reservation?${params}`);

        if (!response.ok) {
            console.error('Failed to fetch rooms');
        }

        const rooms = await response.json();
        renderRooms(rooms);

    } catch (error) {
        console.error('Failed to fetch rooms:', error);
        alert('Something went wrong. Please try again.');
    }


}

function renderRooms(rooms) {
    const grid = document.getElementById("roomsGrid");
    if (rooms.length === 0) {
        grid.innerHTML = `
            <div class="alert alert-dark">
                No rooms available.
            </div>
        `;
        return;
    }
    grid.innerHTML = rooms.map(room => `
        <div class="card room-card">
            <div class="row g-0 align-items-center">
                <div class="col-md-2">
                    <img
                            src="images/Black_Cat_Hotel_Room.png"
                            class="img-fluid rounded-start room-image"
                            alt="Room">

                </div>
                <div class="col-md-8">
                    <div class="card-body py-2">
                        <p class="card-text mb-1">
                            Room nummer: ${room.id}
                        </p>
                        <p class="card-text mb-1">
                            Size: ${room.roomSize} guests
                        </p>
                        <p class="card-text mb-1">
                            Room numbers:
                            ${room.roomNumber}
                        </p>
                    </div>
                </div>
                <div class="col-md-2 text-center">
                    <h5 class="mb-3">
                        ${room.roomPrice} kr
                    </h5>
                    <button class="btn btn-dark btn-sm" id="bookBtn" onclick="createReservation(${room.id})">
                        Book
                    </button>
                </div>
            </div>
        </div>

    `).join('');
}

async function createReservation(roomId) {
    const guests = document.getElementById("guests").value
    const reservation = {
        roomId: roomId,
        checkIn: document.getElementById("checkIn").value,
        checkOut: document.getElementById("checkOut").value,
        guests: guests,
        extraBed: guests > 2 ? false : guests % 2 !== 0
    };

    const response = await fetch('api/reservation', {
        method: "POST",
        headers: {"Content-type": "application/json"},
        body: JSON.stringify(reservation)
    })

    const data = await response.json();
    showConfirmation(data);
}

function showConfirmation(reservation) {
    console.log(reservation);
    const container = document.getElementById("confirmationMessage")
    container.innerHTML = `
        <div class="alert alert-success mt-4">
            <h4>Booking Confirmed  </h4>
            <p> Reservation ID:${reservation.id} </p>
            <p> Room Number:${reservation.room.roomNumber} </p>
            <p> Check-in:${reservation.checkIn} </p>
            <p>Check-out:${reservation.checkOut}</p>    

        </div>
    `;

}
