// ----------------------
// CARREGAR PRODUCTES
// ----------------------
fetch("http://localhost:8081/api/productes")
    .then(res => res.json())
    .then(productes => {
        const container = document.getElementById("productes");

        productes.forEach(p => {
            const img = p.imatge ? p.imatge : "https://via.placeholder.com/300x200?text=Sense+imatge";

			container.innerHTML += `
			    <div class="col-md-3">
			        <div class="card product-card shadow-sm">
			            <img src="${img}" class="card-img-top" alt="${p.nom}">
			            <div class="card-body">
			                <h5 class="card-title">${p.nom}</h5>
			                <p class="card-text text-muted">${p.descripcio || ""}</p>
			                <p class="fw-bold fs-5">${p.preu} €</p>
			                <button class="btn btn-primary w-100 add-cart-btn" onclick="afegirCarro('${p.nom}', ${p.preu})">
			                    <i class="fa-solid fa-cart-plus me-2"></i> Afegir al carro
			                </button>
			            </div>
			        </div>
			    </div>
			`;

        });
    });

	function afegirCarro(nom, preu) {
	    let carro = JSON.parse(localStorage.getItem("carro")) || [];
	    carro.push({ nom, preu });
	    localStorage.setItem("carro", JSON.stringify(carro));
	    alert("Producte afegit al carro!");
	}
