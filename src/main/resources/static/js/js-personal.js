// ----------------------
// HEADER LOGIN / LOGOUT
// ----------------------
const token = localStorage.getItem("token");
const nom = localStorage.getItem("nom");
const email = localStorage.getItem("email");
const rol = localStorage.getItem("rol");

if (!token) {
    window.location.href = "login.html";
}

document.getElementById("header").innerHTML = `
    <i class="fa-solid fa-cart-shopping cart-icon" onclick="window.location.href='personal.html'"></i>

    <span class="navbar-text text-white me-3">
        👋 Benvingut, <strong>${nom}</strong>
    </span>

    <button class="btn btn-outline-light" onclick="logout()">Tancar sessió</button>
`;

function logout() {
    localStorage.clear();
    window.location.href = "index.html";
}

// ----------------------
// DADES USUARI
// ----------------------
document.getElementById("userNom").textContent = nom;
document.getElementById("userEmail").textContent = email;
document.getElementById("userRol").textContent = rol;

// ----------------------
// CARRO
// ----------------------
let carro = JSON.parse(localStorage.getItem("carro")) || [];

const llista = document.getElementById("llistaCarro");
const carroBuit = document.getElementById("carroBuit");
const totalCarro = document.getElementById("totalCarro");
const btnComprar = document.getElementById("btnComprar");

function renderCarro() {
    llista.innerHTML = "";
    let total = 0;

    if (carro.length === 0) {
        carroBuit.style.display = "block";
        totalCarro.textContent = "";
        btnComprar.style.display = "none";
        return;
    }

    carroBuit.style.display = "none";
    btnComprar.style.display = "block";

    carro.forEach((item, index) => {
        total += item.preu;

        llista.innerHTML += `
            <li class="list-group-item d-flex justify-content-between align-items-center">
                <div>
                    <strong>${item.nom}</strong>
                    <span class="text-muted ms-2">${item.preu} €</span>
                </div>

                <button class="btn btn-remove btn-sm" onclick="eliminarProducte(${index})">
                    <i class="fa-solid fa-trash"></i>
                </button>
            </li>
        `;
    });

    totalCarro.textContent = `Total: ${total.toFixed(2)} €`;
}

renderCarro();

// ----------------------
// ELIMINAR PRODUCTE
// ----------------------
function eliminarProducte(index) {
    carro.splice(index, 1);
    localStorage.setItem("carro", JSON.stringify(carro));
    renderCarro();
}

// ----------------------
// COMPRAR
// ----------------------
btnComprar.addEventListener("click", () => {
    if (carro.length === 0) return;

    alert("Compra realitzada correctament! 🎉");

    carro = [];
    localStorage.setItem("carro", JSON.stringify(carro));
    renderCarro();
});
