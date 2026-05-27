let productesTotals = [];
let productesFiltrats = [];
let indexActual = 0;
const MAX_PER_PAGINA = 16;

function obtenirEstatUsuari() {
    const usuariLoguejat = JSON.parse(localStorage.getItem("usuari")) || null;
    const token = localStorage.getItem("token");
    let rol = localStorage.getItem("rol") || localStorage.getItem("role");

    if (!rol && usuariLoguejat) {
        rol = usuariLoguejat.rol || usuariLoguejat.role;
    }

    // Si no hi ha cap rastre d'usuari ni de rol ni de token, és ANÒNIM
    if (!token && !usuariLoguejat && !rol) {
        return "ANONIM";
    }

    return rol ? rol.toUpperCase() : "CLIENT";
}

function barrejar(array) {
    for (let i = array.length - 1;i > 0;i--) {
        const j = Math.floor(Math.random() * (i + 1));
        [array[i], array[j]] = [array[j], array[i]];
    }
    return array;
}

// ----------------------
// CARREGAR PRODUCTES
// ----------------------
fetch("http://localhost:8081/api/productes")
    .then(res => res.json())
    .then(productes => {
        const estat = obtenirEstatUsuari();
        const usuariLoguejat = JSON.parse(localStorage.getItem("usuari")) || null;

        let nomVenedorLocal = "";
        if (usuariLoguejat && (usuariLoguejat.nom || usuariLoguejat.username)) {
            nomVenedorLocal = usuariLoguejat.nom || usuariLoguejat.username;
        } else {
            nomVenedorLocal = localStorage.getItem("username") || localStorage.getItem("usuariNom") || "Test";
        }

        const elMeuNomFiltre = nomVenedorLocal.trim().toLowerCase();

        if (estat === "VENDEDOR" || estat === "VENEDOR") {
            productesTotals = productes.filter(p => {
                const nomOpcio1 = p.venedorNom ? p.venedorNom.toString() : "";
                const nomOpcio2 = (p.venedor && p.venedor.nom) ? p.venedor.nom.toString() : "";
                const nomOpcio3 = (p.venedor && p.venedor.username) ? p.venedor.username.toString() : "";

                return nomOpcio1.trim().toLowerCase() === elMeuNomFiltre ||
                    nomOpcio2.trim().toLowerCase() === elMeuNomFiltre ||
                    nomOpcio3.trim().toLowerCase() === elMeuNomFiltre;
            });

            const categoryFilter = document.getElementById("categoryFilter");
            if (categoryFilter) categoryFilter.style.display = "none";

            const titol = document.querySelector(".main-container h2");
            if (titol) titol.innerText = "El meu catàleg de productes";

        } else {
            productesTotals = barrejar(productes);

            const categoryFilter = document.getElementById("categoryFilter");
            if (categoryFilter) {
                categoryFilter.innerHTML = "";
                const categories = ["Totes", ...new Set(productesTotals.map(p => p.categoria).filter(Boolean))];
                categories.forEach((cat, index) => {
                    categoryFilter.innerHTML += `
                        <button class="btn ${index === 0 ? "btn-dark active" : "btn-outline-dark"}"
                                data-category="${cat}">
                            ${cat}
                        </button>
                    `;
                });
            }
        }

        productesFiltrats = [...productesTotals];
        document.getElementById("productes").innerHTML = "";
        indexActual = 0;

        carregarMesProductes();
        activarFiltreCategories();
    })
    .catch(err => console.error("Error carregant l'API de productes:", err));

// ----------------------
// MOSTRAR PRODUCTES MÉS
// ----------------------
function carregarMesProductes() {
    const container = document.getElementById("productes");

    if (productesFiltrats.length === 0) {
        container.innerHTML = `
            <div class="col-12 text-center py-5" style="color: var(--color-text); opacity: 0.6;">
                <i class="bi bi-box-seam display-1 mb-3 d-block"></i>
                <h4>Encara no tens cap producte publicat al teu catàleg</h4>
                <p class="text-muted">Fes clic al botó "Afegir Producte" de dalt per a estrenar la teva botiga.</p>
            </div>
        `;
        document.getElementById("loadMoreBtn").style.display = "none";
        return;
    }

    const limit = indexActual + MAX_PER_PAGINA;
    const productesPagina = productesFiltrats.slice(indexActual, limit);

    const estat = obtenirEstatUsuari();
    const visible = (estat === "CLIENT" || estat === "ANONIM") ? "display: block;" : "display: none;";

    productesPagina.forEach(p => {
		const img = (p.imatge && p.imatge.startsWith('http')) 
		    ? p.imatge 
		    : (p.imatge ? `/img/${p.imatge}` : "https://via.placeholder.com/300x200?text=Sense+imatge");

        const col = document.createElement("div");
        col.className = "col-md-3";
        col.innerHTML = `
		    <div class="card product-card shadow-sm" data-category="${p.categoria}" style="cursor: pointer; background-color: var(--color-card-bg); color: var(--color-text); border: none;">
		        <img src="${img}" class="card-img-top" alt="${p.nom}">
		        <div class="card-body">
		            <h5 class="card-title">${p.nom}</h5>
		            <p class="text-primary fw-semibold mb-1 vendor-name" style="color: var(--color-primary) !important;">
		                Venedor: ${p.venedorNom || p.venedor?.nom || "Desconegut"}
		            </p>
		            <p class="card-text text-muted">${p.descripcio || ""}</p>
		            <p class="fw-bold fs-5">${p.preu} €</p>
		            <button class="btn btn-primary w-100 add-cart-btn" style="${visible}">
		                <i class="fa-solid fa-cart-plus me-2"></i> Afegir al carro
		            </button>
		        </div>
		    </div>
		`;

        col.querySelector(".card").addEventListener("click", (e) => {
            if (!e.target.closest(".add-cart-btn")) {
                mostrarFinestraDetall(p);
            }
        });

        const btnCarro = col.querySelector(".add-cart-btn");
        if (btnCarro) {
            if (estat === "CLIENT") {
                btnCarro.addEventListener("click", () => afegirCarro(p));
            } else if (estat === "ANONIM") {
                btnCarro.addEventListener("click", () => mostrarNotificacioLogin());
            }
        }

        container.appendChild(col);
    });

    indexActual = limit;

    if (indexActual >= productesFiltrats.length) {
        document.getElementById("loadMoreBtn").style.display = "none";
    } else {
        document.getElementById("loadMoreBtn").style.display = "inline-block";
    }
}

// ----------------------
// FINESTRA MODAL DETALL PRODUCTE
// ----------------------
function mostrarFinestraDetall(p) {
	const img = (p.imatge && p.imatge.startsWith('http')) 
	    ? p.imatge 
	    : (p.imatge ? `/img/${p.imatge}` : "https://via.placeholder.com/300x200?text=Sense+imatge");

    document.getElementById("modalProductTitle").innerText = p.nom;
    document.getElementById("modalProductImg").src = img;
    document.getElementById("modalProductImg").alt = p.nom;
    document.getElementById("modalProductPrice").innerText = `${p.preu} €`;
    document.getElementById("modalProductDescription").innerText = p.descripcio || "Sense descripció disponible.";

    const nomVenedor = p.venedorNom || p.venedor?.nom || "Desconegut";
    const vendorLink = document.getElementById("modalProductVendor");
    vendorLink.innerText = nomVenedor;

    const estat = obtenirEstatUsuari();

    if (estat === "VENEDOR") {
        vendorLink.removeAttribute("href");
        vendorLink.onclick = null;
        vendorLink.style.cursor = "default";
    } else {
        vendorLink.href = "javascript:void(0);";
        vendorLink.style.cursor = "pointer";
        vendorLink.onclick = () => {
            const modalElement = document.getElementById('productModal');
            const modalInstance = bootstrap.Modal.getInstance(modalElement);
            if (modalInstance) modalInstance.hide();
            filtrarPerVenedor(nomVenedor);
        };
    }

    const modalBtn = document.getElementById("modalAddToCartBtn");

    if (estat === "CLIENT" || estat === "ANONIM") {
        modalBtn.style.display = "block";

        modalBtn.onclick = () => {
            const modalElement = document.getElementById('productModal');
            const modalInstance = bootstrap.Modal.getInstance(modalElement);
            if (modalInstance) modalInstance.hide();

            if (estat === "CLIENT") {
                // CORREGIT: Ara sí enviem l'objecte 'p' complet des de la modal!
                afegirCarro(p);
            } else {
                mostrarNotificacioLogin();
            }
        };
    } else {
        modalBtn.style.display = "none";
    }

    const laMevaModal = new bootstrap.Modal(document.getElementById('productModal'));
    laMevaModal.show();
}

document.getElementById("loadMoreBtn").addEventListener("click", carregarMesProductes);

// ----------------------
// FILTRE PER CATEGORIES
// ----------------------
function activarFiltreCategories() {
    const buttons = document.querySelectorAll("#categoryFilter button");

    buttons.forEach(btn => {
        btn.addEventListener("click", () => {
            if (document.getElementById("vendorFilterAlert")) {
                document.getElementById("vendorFilterAlert").style.display = "none";
            }

            buttons.forEach(b => b.classList.remove("active", "btn-dark"));
            buttons.forEach(b => b.classList.add("btn-outline-dark"));

            btn.classList.add("active", "btn-dark");
            btn.classList.remove("btn-outline-dark");

            const categoria = btn.dataset.category;

            if (categoria === "Totes") {
                productesFiltrats = [...productesTotals];
            } else {
                productesFiltrats = productesTotals.filter(p => p.categoria === categoria);
            }

            indexActual = 0;
            document.getElementById("productes").innerHTML = "";
            carregarMesProductes();
        });
    });
}

// ----------------------
// FILTRAR PER VENEDOR 
// ----------------------
function filtrarPerVenedor(nomVenedor) {
    productesFiltrats = productesTotals.filter(p => {
        const actual = p.venedorNom || p.venedor?.nom || "Desconegut";
        return actual.toLowerCase() === nomVenedor.toLowerCase();
    });

    indexActual = 0;
    document.getElementById("productes").innerHTML = "";

    if (document.getElementById("activeVendorName") && document.getElementById("vendorFilterAlert")) {
        document.getElementById("activeVendorName").innerText = nomVenedor;
        document.getElementById("vendorFilterAlert").style.display = "block";
    }

    const buttons = document.querySelectorAll("#categoryFilter button");
    buttons.forEach(b => {
        b.classList.remove("active", "btn-dark");
        b.classList.add("btn-outline-dark");
    });

    carregarMesProductes();
}

function netejarFiltreVenedor() {
    if (document.getElementById("vendorFilterAlert")) {
        document.getElementById("vendorFilterAlert").style.display = "none";
    }

    const buttons = document.querySelectorAll("#categoryFilter button");
    buttons.forEach(b => {
        if (b.dataset.category === "Totes") {
            b.classList.add("active", "btn-dark");
            b.classList.remove("btn-outline-dark");
        } else {
            b.classList.remove("active", "btn-dark");
            b.classList.add("btn-outline-dark");
        }
    });

    productesFiltrats = [...productesTotals];
    indexActual = 0;
    document.getElementById("productes").innerHTML = "";
    carregarMesProductes();
}

// ----------------------
// AFEGIR AL CARRO (BD)
// ----------------------
function afegirCarro(producte) {
    const usuariLoguejat = JSON.parse(localStorage.getItem("usuari")) || null;
    const token = localStorage.getItem("token"); // Recuperem el token!

    let idCliente = localStorage.getItem("idUsuari") ||
        localStorage.getItem("idCliente") ||
        localStorage.getItem("id");

    if (!idCliente && usuariLoguejat) {
        idCliente = usuariLoguejat.idUsuari || usuariLoguejat.idCliente || usuariLoguejat.id;
    }

    if (!idCliente || !token) { // Si no hi ha token, tampoc podem afegir
        mostrarNotificacioLogin();
        return;
    }

    const idProducte = producte.idProducte || producte.id;
    const quantitat = 1;

    // AFEGIM LA CAPÇALERA DE SEGURETAT AQUÍ
    fetch(`http://localhost:8081/api/carro/${idCliente}/afegir?idProducte=${idProducte}&quantitat=${quantitat}`, {
        method: "POST",
        headers: {
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json"
        }
    })
        .then(res => {
            if (!res.ok) {
                // Si el servidor retorna 403, el missatge serà clar
                throw new Error("No s'ha pogut afegir: " + res.status);
            }
            return res.text();
        })
        .then(() => {
            mostrarNotificacioToast(producte.nom);

            let carroLocal = JSON.parse(localStorage.getItem("carro")) || [];
            carroLocal.push({ idProducte: idProducte, nom: producte.nom, preu: producte.preu });
            localStorage.setItem("carro", JSON.stringify(carroLocal));

            if (typeof actualitzarComptadorCarroVisual === "function") {
                actualitzarComptadorCarroVisual();
            }
        })
        .catch(err => {
            console.error("Error de persistència:", err);
            // Canviem l'alert del navegador pel teu toast de error
            mostrarToast("Error en afegir al carro (seguretat)", "error");
        });
}

// ----------------------
// TOASTS DE NOTIFICACIÓ
// ----------------------
function mostrarNotificacioToast(nomProducte) {
    const container = document.getElementById("toastContainer");
    if (!container) return;

    const toast = document.createElement("div");
    toast.className = "custom-toast shadow-lg";

    toast.innerHTML = `
        <div class="d-flex align-items-center gap-3">
            <i class="fa-solid fa-cart-check fs-4" style="color: var(--color-success);"></i>
            <div>
                <strong style="color: var(--color-success); display: block; font-size: 0.95rem;">Afegit al carro</strong>
                <span style="font-size: 0.85rem; opacity: 0.8;">${nomProducte}</span>
            </div>
        </div>
        <button type="button" class="btn-close ms-3" aria-label="Close" onclick="this.parentElement.remove()"></button>
    `;

    container.appendChild(toast);

    setTimeout(() => { toast.classList.add("fade-out"); }, 2700);
    setTimeout(() => { toast.remove(); }, 3100);
}

function mostrarNotificacioLogin() {
    const container = document.getElementById("toastContainer");
    if (!container) return;

    const toast = document.createElement("div");
    toast.className = "custom-toast toast-danger shadow-lg";

    toast.innerHTML = `
        <div class="d-flex align-items-center gap-3">
            <i class="fa-solid fa-lock fs-4" style="color: var(--color-danger);"></i>
            <div>
                <strong style="color: var(--color-danger); display: block; font-size: 0.95rem;">Accés restringit</strong>
                <span style="font-size: 0.85rem; opacity: 0.8;">Inicia sessió per afegir productes</span>
            </div>
        </div>
        <button type="button" class="btn-close ms-3" aria-label="Close" onclick="this.parentElement.remove()"></button>
    `;

    container.appendChild(toast);

    setTimeout(() => { toast.classList.add("fade-out"); }, 3500);
    setTimeout(() => {
        toast.remove();
        window.location.href = "login.html";
    }, 4000);
}