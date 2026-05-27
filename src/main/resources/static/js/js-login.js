document.getElementById("loginForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const email = document.getElementById("email").value;
    const pass = document.getElementById("password").value;
    const errorBox = document.getElementById("error");

    errorBox.innerText = "";

    try {
        const res = await fetch("http://localhost:8081/api/auth/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email: email, pass: pass })
        });

        if (!res.ok) {
            const textError = await res.text();
            errorBox.innerText = `❌ ${textError || "Credencials incorrectes"}`;
            return;
        }

        const data = await res.json();

        // 1. GUARDEM LES DADES INDIVIDUALS AL LOCALSTORAGE
		localStorage.setItem("token", data.token);
		localStorage.setItem("rol", data.rol);
		localStorage.setItem("nom", data.nom); 
		localStorage.setItem("email", data.email); 
		localStorage.setItem("idUsuari", data.idUsuari);
		localStorage.setItem("userDireccio", data.direccio); 

        // 2. CREEM L'OBJECTE 'usuari' COMPACTE EN FORMAT STRING (MOLT IMPORTANT)
        // El teu catàleg principal fa un JSON.parse(localStorage.getItem("usuari")) 
        // i busca aquestes propietats exactes per afegir elements al carro.
        const objecteUsuari = {
            idUsuari: data.idUsuari,
            nom: data.nom,
            rol: data.rol
        };
        localStorage.setItem("usuari", JSON.stringify(objecteUsuari));

        // 3. REDIRIGIM AL CATÀLEG PRINCIPAL
        // Modificat a 'main.html' perquè el teu JavaScript de catàleg està programat per a aquesta vista
        window.location.href = "/main";

    } catch (error) {
        errorBox.innerText = "❌ Error de connexió amb el servidor";
    }
});