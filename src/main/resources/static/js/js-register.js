document.getElementById("registerForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const nom = document.getElementById("nom").value;
    const email = document.getElementById("email").value;
    const pass = document.getElementById("password").value;

    const errorBox = document.getElementById("error");
    const successBox = document.getElementById("success");

    errorBox.innerText = "";
    successBox.innerText = "";

    try {
        const res = await fetch("http://localhost:8081/api/auth/register", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                // L'idUsuari s'elimina d'aquí: el backend el genera com a cli01, cli02, etc.
                nom: nom,
                email: email,
                pass: pass,
                rol: "CLIENT"
            })
        });

        if (!res.ok) {
            // Un error pot ser tant per email duplicat com per un altre motiu del servidor
            const textError = await res.text();
            errorBox.innerText = `❌ ${textError || "Error en registrar el compte"}`;
            return;
        }

        successBox.innerText = "✔ Compte creat correctament! Revisa el teu correu per confirmar-lo abans d'entrar.";

        setTimeout(() => {
            window.location.href = "/login";
        }, 3000);

    } catch (error) {
        errorBox.innerText = "❌ Error de connexió amb el servidor";
    }
});