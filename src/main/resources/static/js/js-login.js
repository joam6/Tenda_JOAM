document.getElementById("loginForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const email = document.getElementById("email").value;
    const pass = document.getElementById("password").value;

    try {
        const res = await fetch("http://localhost:8081/api/auth/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email: email, pass: pass })
        });

        if (!res.ok) {
            document.getElementById("error").innerText = "❌ Credencials incorrectes";
            return;
        }

        const data = await res.json();

        // GUARDEM EL TOKEN I DADES DE L'USUARI
        localStorage.setItem("token", data.token);
        localStorage.setItem("nom", data.nom);
        localStorage.setItem("rol", data.rol);

        // REDIRIGIM A LA PÀGINA PRINCIPAL
        window.location.href = "index.html";

    } catch (error) {
        document.getElementById("error").innerText = "❌ Error de connexió amb el servidor";
    }
});
