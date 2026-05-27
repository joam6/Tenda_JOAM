// Afegeix això a DALT DE TOT de js-navbar.js per fer la prova
function mostrarToast(missatge, tipus = 'success') {
    const container = document.getElementById("toastContainer");
    if (!container) return;
    const toast = document.createElement("div");
    toast.className = `custom-toast shadow-lg toast-${tipus}`;
    toast.innerHTML = `<span>${missatge}</span>`;
    container.appendChild(toast);
    setTimeout(() => toast.remove(), 3500);
}

// 1. Funció auxiliar per rescatar dades de forma segura
function obtenirDada(clau) {
    if (clau === "nom") return localStorage.getItem("nom") || localStorage.getItem("username") || "Usuari";
    if (clau === "email") return localStorage.getItem("email") || localStorage.getItem("userEmail") || "";
    return localStorage.getItem(clau);
}

// 2. Càrrega del component
fetch("components/navbar.html")
    .then(res => res.text())
    .then(html => {
        document.body.insertAdjacentHTML("afterbegin", html);
        // Esperem un mil·lisegon per assegurar que el navegador ha pintat la navbar
        setTimeout(setupNavbar, 0);
    });

function setupNavbar() {
    const loginBtn = document.getElementById("loginBtn");
    const logoutBtn = document.getElementById("logoutBtn");
    const userName = document.getElementById("userName");
    const cartIcon = document.getElementById("cartIcon");
    const themeToggle = document.getElementById("themeToggle");
    const themeIcon = document.getElementById("themeIcon");
    const backHomeBtn = document.getElementById("backHomeBtn");
    const addProductBtn = document.getElementById("addProductBtn");

    const nom = obtenirDada("nom");
    const token = localStorage.getItem("token");

    // Obtenim el rol amb prioritat clara
    const usuariLoguejat = JSON.parse(localStorage.getItem("usuari")) || {};
    const rol = localStorage.getItem("rol") || usuariLoguejat.rol || null;

    const isAuthPage = window.location.pathname.includes("login.html") ||
        window.location.pathname.includes("register.html");

    // --- LÒGICA DE VISIBILITAT ---
    if (isAuthPage) {
        if (loginBtn) loginBtn.classList.add("d-none");
        if (logoutBtn) logoutBtn.classList.add("d-none");
        if (userName) userName.classList.add("d-none");
        if (cartIcon) cartIcon.classList.add("d-none");
        if (addProductBtn) addProductBtn.classList.add("d-none");
        if (backHomeBtn) {
            backHomeBtn.classList.remove("d-none");
            backHomeBtn.onclick = () => window.location.href = "index.html";
        }
    } else if (!token) {
        // Usuari no loguejat en pàgina normal
        if (loginBtn) {
            loginBtn.classList.remove("d-none");
            loginBtn.onclick = () => window.location.href = "login.html";
        }
    } else {
        // Usuari loguejat
        if (loginBtn) loginBtn.classList.add("d-none");
        if (logoutBtn) {
            logoutBtn.classList.remove("d-none");
            logoutBtn.onclick = () => { localStorage.clear(); window.location.href = "login.html"; };
        }
        if (userName) {
            userName.classList.remove("d-none");
            userName.innerText = nom;
        }

        // CONTROL ROLS
        const esVenedor = (rol === "VENDEDOR" || rol === "VENEDOR");
        if (addProductBtn) addProductBtn.classList.toggle("d-none", !esVenedor);
        if (cartIcon) cartIcon.classList.toggle("d-none", esVenedor);
    }

    // --- GESTIÓ DE TEMES (MANTINGUT) ---
    const savedTheme = localStorage.getItem("theme");
    if (savedTheme === "dark") {
        document.documentElement.classList.add("dark");
        if (themeIcon) themeIcon.classList.replace("bi-moon-fill", "bi-brightness-high-fill");
    }

    if (themeToggle) {
        themeToggle.onclick = () => {
            const isDark = document.documentElement.classList.toggle("dark");
            localStorage.setItem("theme", isDark ? "dark" : "light");
            if (themeIcon) {
                themeIcon.classList.toggle("bi-moon-fill", !isDark);
                themeIcon.classList.toggle("bi-brightness-high-fill", isDark);
            }
        };
    }
}

async function guardarProducte() {
    const token = localStorage.getItem("token");
    let idVenedor = localStorage.getItem("idUsuari");
    
    // Neteja de l'ID
    if (idVenedor && idVenedor.includes(',')) {
        idVenedor = idVenedor.split(',')[0];
    }

    // Validació bàsica de formulari
    const nom = document.getElementById("pNom").value;
    if (!nom) {
        mostrarToast("Has d'omplir almenys el nom del producte", "danger");
        return;
    }

    const formData = new FormData();
    formData.append("nom", nom);
    formData.append("preu", document.getElementById("pPreu").value);
    formData.append("categoria", document.getElementById("pCategoria").value);
    formData.append("descripcio", document.getElementById("pDescripcio").value);
    formData.append("stock", document.getElementById("pStock").value);
    formData.append("idVenedor", idVenedor);
    
    const imatgeFile = document.getElementById("pImatge").files[0];
    if (imatgeFile) {
        formData.append("imatge", imatgeFile);
    }

    // Feedback visual abans de la petició
    const btnGuardar = document.querySelector("#modalAfegirProducte .btn-primary");
    const textOriginal = btnGuardar.innerText;
    btnGuardar.disabled = true;
    btnGuardar.innerText = "Guardant...";

    try {
        const res = await fetch("http://localhost:8081/api/productes/afegir", {
            method: "POST",
            headers: { "Authorization": `Bearer ${token}` },
            body: formData
        });

        if (res.ok) {
            mostrarToast("Producte afegit correctament al catàleg!", "success");
            // Tanquem el modal i refresquem després d'un segon
            const modal = bootstrap.Modal.getInstance(document.getElementById("modalAfegirProducte"));
            if (modal) modal.hide();
            setTimeout(() => location.reload(), 1200);
        } else {
            const error = await res.text();
            mostrarToast("Error: " + error, "danger");
        }
    } catch (e) {
        console.error("Error de xarxa:", e);
        mostrarToast("Error de connexió amb el servidor", "danger");
    } finally {
        btnGuardar.disabled = false;
        btnGuardar.innerText = textOriginal;
    }
}