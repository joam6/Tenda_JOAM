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
                idUsuari: email.split("@")[0], // ID automàtic simple
                nom: nom,
                email: email,
                pass: pass,
                rol: "CLIENT"
            })
        });

        if (!res.ok) {
            errorBox.innerText = "❌ Aquest email ja està registrat";
            return;
        }

        successBox.innerText = "✔ Compte creat correctament! Redirigint...";

        setTimeout(() => {
            window.location.href = "login.html";
        }, 1500);

    } catch (error) {
        errorBox.innerText = "❌ Error de connexió amb el servidor";
    }
});
