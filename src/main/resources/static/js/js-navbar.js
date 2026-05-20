fetch("components/navbar.html")
    .then(res => res.text())
    .then(html => {
        document.body.insertAdjacentHTML("afterbegin", html);
        setupNavbar();
    });

function setupNavbar() {
    const loginBtn = document.getElementById("loginBtn");
    const logoutBtn = document.getElementById("logoutBtn");
    const userName = document.getElementById("userName");
    const cartIcon = document.getElementById("cartIcon");
    const themeToggle = document.getElementById("themeToggle");
    const themeIcon = document.getElementById("themeIcon");
    const backHomeBtn = document.getElementById("backHomeBtn");

    const nom = localStorage.getItem("nom");
    const token = localStorage.getItem("token");

    const isAuthPage =
        window.location.pathname.includes("login.html") ||
        window.location.pathname.includes("register.html");

    // -------------------------
    // MOSTRAR BOTÓ DE TORNAR A MAIN A LOGIN/REGISTER
    // -------------------------
    if (isAuthPage) {
        loginBtn.classList.add("d-none");
        logoutBtn.classList.add("d-none");
        userName.classList.add("d-none");
        cartIcon.classList.add("d-none");

        backHomeBtn.classList.remove("d-none");
        backHomeBtn.onclick = () => window.location.href = "index.html";
    }

    // -------------------------
    // MODE FOSC GLOBAL
    // -------------------------
    const savedTheme = localStorage.getItem("theme");

    if (savedTheme === "dark") {
        document.documentElement.classList.add("dark");
        themeIcon.classList.replace("bi-moon-fill", "bi-brightness-high-fill");
    }

    themeToggle.onclick = () => {
        document.documentElement.classList.toggle("dark");

        if (document.documentElement.classList.contains("dark")) {
            localStorage.setItem("theme", "dark");
            themeIcon.classList.replace("bi-moon-fill", "bi-brightness-high-fill");
        } else {
            localStorage.setItem("theme", "light");
            themeIcon.classList.replace("bi-brightness-high-fill", "bi-moon-fill");
        }
    };

    // -------------------------
    // LOGIN / LOGOUT NORMAL
    // -------------------------
    if (!token && !isAuthPage) {
        loginBtn.classList.remove("d-none");
        loginBtn.onclick = () => window.location.href = "login.html";
        return;
    }

    if (token) {
        loginBtn.classList.add("d-none");
        logoutBtn.classList.remove("d-none");
        userName.classList.remove("d-none");
        cartIcon.classList.remove("d-none");

        userName.innerText = "Benvingut, " + nom;

        logoutBtn.onclick = () => {
            localStorage.clear();
            window.location.href = "login.html";
        };
    }
}
