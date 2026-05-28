document.getElementById("registerForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    // Agafem els camps del formulari
    const nomEmpresa = document.getElementById("company").value; 
    const email = document.getElementById("email").value;
    const pass = document.getElementById("password").value;

    const errorBox = document.getElementById("error");
    const successBox = document.getElementById("success");

    // Netegem missatges anteriors
    errorBox.innerText = "";
    successBox.innerText = "";

    try {
        // Cridem al teu Spring Boot local
        const res = await fetch("http://localhost:8081/api/auth/register", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                idUsuari: email.split("@")[0],
                nom: nomEmpresa, 
                email: email,
                pass: pass,
                rol: "VENDEDOR" 
            })
        });

        // Si el servidor respon amb un error (com un 400 Bad Request)
        if (!res.ok) {
            const errorMsg = await res.text();
            errorBox.innerText = `❌ ${errorMsg}`;
            return;
        }

        // Si tot va bé, ignorem el text genèric del Java i fiquem el teu missatge personalitzat
        successBox.innerText = "✔ Sol·licitud enviada, l'administrador revisarà la petició";

        // Redirigim al login al cap de 4 segons perquè puguin llegir el missatge
        setTimeout(() => {
            window.location.href = "/login";
        }, 4000);

    } catch (error) {
        errorBox.innerText = "❌ Error de connexió amb el servidor";
    }
});