document.addEventListener("DOMContentLoaded", () => {
    fetch("/components/footer.html")
        .then(response => response.text())
        .then(html => {
            document.getElementById("footer-container").innerHTML = html;

            // COMPROVACIÓ OPCIONAL: Si ja és venedor, amaguem el botó del footer
            const rol = localStorage.getItem("rol") || JSON.parse(localStorage.getItem("usuari"))?.rol;
            if (rol === "VENDEDOR" || rol === "VENEDOR") {
                const btnVenedor = document.querySelector("footer .btn-warning");
                if (btnVenedor) btnVenedor.style.display = "none";
            }
        })
        .catch(err => console.error("Error carregant el footer:", err));
});