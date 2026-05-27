// -----------------------------------------------------
// 📦 VARIABLE GLOBAL
// -----------------------------------------------------
let carroAgrupat = [];

// -----------------------------------------------------
// 🚀 INICIALITZACIÓ
// -----------------------------------------------------
document.addEventListener("DOMContentLoaded", () => {
    mostrarDadesUsuari();
    inicialitzarCarro();
});

// -----------------------------------------------------
// 🎨 FUNCIONS D'UTILITAT
// -----------------------------------------------------
function mostrarToast(missatge, tipus = 'success') {
    const container = document.getElementById("toastContainer");
    if (!container) return;
    const toast = document.createElement("div");
    toast.className = `custom-toast shadow-lg toast-${tipus}`;
    toast.innerHTML = `<i class="fa-solid ${tipus === 'error' ? 'fa-circle-exclamation' : 'fa-circle-check'} fs-4"></i><span>${missatge}</span>`;
    container.appendChild(toast);
    setTimeout(() => toast.remove(), 3500);
}

// -----------------------------------------------------
// 👤 GESTIÓ DEL PERFIL D'USUARI
// -----------------------------------------------------
async function mostrarDadesUsuari() {
    let nom = localStorage.getItem("nom") || "Usuari";
    let email = localStorage.getItem("email") || "";
    let direccio = localStorage.getItem("userDireccio") || "";
    const idUsuari = localStorage.getItem("idUsuari");
    const token = localStorage.getItem("token");

    if ((!email || !direccio) && idUsuari && token) {
        try {
            const res = await fetch(`http://localhost:8081/api/usuaris/perfil/${idUsuari}`, {
                method: "GET",
                headers: { "Authorization": `Bearer ${token}`, "Content-Type": "application/json" }
            });
            if (res.ok) {
                const dades = await res.json();
                email = dades.email || email;
                direccio = dades.direccio || direccio;
                localStorage.setItem("email", email);
                localStorage.setItem("userDireccio", direccio);
            }
        } catch (e) { console.error("Error carregant dades:", e); }
    }

    if (document.getElementById("userNom")) document.getElementById("userNom").textContent = nom;
    if (document.getElementById("userEmail")) document.getElementById("userEmail").textContent = email;
    const txtDireccio = document.getElementById("userDireccio");
    if (txtDireccio) txtDireccio.innerHTML = direccio ? direccio : `<i>No s'ha especificat cap domicili</i>`;
    
    if (document.getElementById("inputNom")) document.getElementById("inputNom").value = nom;
    if (document.getElementById("inputEmail")) document.getElementById("inputEmail").value = email;
    if (document.getElementById("inputDireccio")) document.getElementById("inputDireccio").value = direccio;
}

function activarModeEdicio() {
    document.getElementById("userNom").classList.add("d-none");
    document.getElementById("userEmail").classList.add("d-none");
    document.getElementById("userDireccio").classList.add("d-none");
    document.getElementById("btnEditarPerfil").classList.add("d-none");
    document.getElementById("inputNom").classList.remove("d-none");
    document.getElementById("inputEmail").classList.remove("d-none");
    document.getElementById("inputDireccio").classList.remove("d-none");
    document.getElementById("accionsEdicioPerfil").classList.remove("d-none");
}

function cancelarEdicio() {
    document.getElementById("userNom").classList.remove("d-none");
    document.getElementById("userEmail").classList.remove("d-none");
    document.getElementById("userDireccio").classList.remove("d-none");
    document.getElementById("btnEditarPerfil").classList.remove("d-none");
    document.getElementById("inputNom").classList.add("d-none");
    document.getElementById("inputEmail").classList.add("d-none");
    document.getElementById("inputDireccio").classList.add("d-none");
    document.getElementById("accionsEdicioPerfil").classList.add("d-none");
}

function guardarPerfil(event) {
    event.preventDefault();
    const idUsuari = localStorage.getItem("idUsuari");
    const token = localStorage.getItem("token");
    const nouNom = document.getElementById("inputNom").value.trim();
    const novaDireccio = document.getElementById("inputDireccio").value.trim();
    if (!nouNom) { mostrarToast("El nom no pot estar buit.", "error"); return; }

    fetch(`http://localhost:8081/api/usuaris/perfil/${idUsuari}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json", "Authorization": `Bearer ${token}` },
        body: JSON.stringify({ nom: nouNom, direccio: novaDireccio })
    })
    .then(res => { if (!res.ok) throw new Error(); return res.json(); })
    .then(u => {
        localStorage.setItem("nom", u.nom);
        localStorage.setItem("userDireccio", u.direccio || "");
        mostrarToast("Perfil desat correctament!");
        cancelarEdicio();
        mostrarDadesUsuari(); 
    })
    .catch(() => mostrarToast("Error al guardar el perfil.", "error"));
}

// -----------------------------------------------------
// 🛒 GESTIÓ DEL CARRET
// -----------------------------------------------------
function inicialitzarCarro() {
    const idCliente = localStorage.getItem("idUsuari");
    const token = localStorage.getItem("token");
    if (!idCliente || !token) return;

    fetch(`http://localhost:8081/api/carro/${idCliente}`, {
        headers: { "Authorization": `Bearer ${token}` }
    })
    .then(res => res.ok ? res.json() : [])
    .then(carroCompleto => {
        const linies = carroCompleto.carroProductes || [];
        carroAgrupat = linies.map(cp => ({
            idProducte: cp.producte.idProducte,
            nom: cp.producte.nom,
            preu: cp.producte.preu,
            quantitat: cp.quantitat,
            seleccionat: true
        }));
        renderCarro();
    })
    .catch(err => console.error("Error carret:", err));
}

function renderCarro() {
    const llista = document.getElementById("llistaCarro");
    const carroBuit = document.getElementById("carroBuit");
    const resumPreus = document.getElementById("resumPreus");
    const totalCarro = document.getElementById("totalCarro");
    const btnComprar = document.getElementById("btnComprar");
    const accionsCarro = document.getElementById("accionsCarro");

    if (!llista) return;
    llista.innerHTML = "";

    if (!carroAgrupat || carroAgrupat.length === 0) {
        carroBuit.style.display = "block";
        resumPreus.classList.add("d-none");
        accionsCarro.classList.add("d-none");
        if (btnComprar) btnComprar.disabled = true;
        return;
    }

    carroBuit.style.display = "none";
    resumPreus.classList.remove("d-none");
    accionsCarro.classList.remove("d-none");
    if (btnComprar) btnComprar.disabled = false;

    let total = 0;
    carroAgrupat.forEach((item, index) => {
        if (item.seleccionat) total += (item.preu * item.quantitat);
        
        llista.innerHTML += `
            <li class="list-group-item d-flex justify-content-between align-items-center">
                <div class="d-flex align-items-center">
                    <input type="checkbox" ${item.seleccionat ? 'checked' : ''} onchange="toggleSeleccionat(${index})" class="me-3">
                    <strong>${item.nom}</strong>
                </div>
                <div class="d-flex align-items-center">
                    <button class="btn btn-sm btn-outline-secondary" onclick="canviarQuantitatBD(${index}, -1)">-</button>
                    <span class="mx-3 fw-bold">${item.quantitat}</span>
                    <button class="btn btn-sm btn-outline-secondary" onclick="canviarQuantitatBD(${index}, 1)">+</button>
                    <button class="btn btn-sm btn-outline-danger ms-3" onclick="eliminarLiniaSenceraBD('${item.idProducte}')">
                        <i class="fa-solid fa-trash"></i>
                    </button>
                </div>
            </li>`;
    });
    totalCarro.textContent = `Total: ${total.toFixed(2)} €`;
}

function toggleSeleccionat(index) {
    carroAgrupat[index].seleccionat = !carroAgrupat[index].seleccionat;
    renderCarro();
}

function toggleSeleccionarTot(checkbox) {
    carroAgrupat.forEach(item => item.seleccionat = checkbox.checked);
    renderCarro();
}

async function canviarQuantitatBD(index, canvi) {
    const item = carroAgrupat[index];
    if (item.quantitat + canvi < 0) return;
    if (item.quantitat + canvi === 0) { eliminarLiniaSenceraBD(item.idProducte); return; }

    const idCliente = localStorage.getItem("idUsuari");
    await fetch(`http://localhost:8081/api/carro/${idCliente}/afegir?idProducte=${item.idProducte}&quantitat=${canvi}`, {
        method: "POST",
        headers: { "Authorization": `Bearer ${localStorage.getItem("token")}` }
    });
    inicialitzarCarro();
}



// Variable per guardar quina acció està pendent
let accioPendent = null;

function obrirModal(titol, missatge, callback) {
    document.getElementById("modalTitle").textContent = titol;
    document.getElementById("modalMessage").textContent = missatge;
    document.getElementById("confirmModal").classList.remove("d-none");
    accioPendent = callback;
}

// Botons del modal
document.getElementById("btnModalCancel").onclick = () => {
    document.getElementById("confirmModal").classList.add("d-none");
};

document.getElementById("btnModalConfirm").onclick = () => {
    document.getElementById("confirmModal").classList.add("d-none");
    if (accioPendent) accioPendent();
};

// Aquesta funció fa la crida REAL al servidor amb confirmació
async function executarAccioEliminar(url, missatgeToast) {
    const token = localStorage.getItem("token");
    const res = await fetch(url, {
        method: "DELETE",
        headers: { 
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json"
        }
    });

    if (res.ok) {
        mostrarToast(missatgeToast);
        inicialitzarCarro();
    } else {
        console.error("Error en petició:", res.status);
        if (res.status === 403) {
            mostrarToast("Accés denegat: comprova els teus permisos", "error");
        } else {
            mostrarToast("Error al processar l'acció", "error");
        }
    }
}

// Funcions disparades pels botons (ara obren el modal)
function eliminarLiniaSenceraBD(idProducte) {
    const idCliente = localStorage.getItem("idUsuari");
    obrirModal("Eliminar", "Segur que vols eliminar aquest producte?", () => {
        executarAccioEliminar(`http://localhost:8081/api/carro/${idCliente}/eliminar/${idProducte}`, "Producte eliminat");
    });
}

function buidarCarro() {
    const idCliente = localStorage.getItem("idUsuari");
    obrirModal("Buidar Carro", "Segur que vols buidar tot el carret?", () => {
        executarAccioEliminar(`http://localhost:8081/api/carro/${idCliente}/buidar`, "Carret buidat");
    });
}