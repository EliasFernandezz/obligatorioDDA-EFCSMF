// partida.js — no contiene Thymeleaf, usa window.gameConfig

(function () {
    document.addEventListener('DOMContentLoaded', () => {

        // ---------- Leer config ----------
        const cfg = window.gameConfig || {};
        const partidaMinutos = Number(cfg.partidaMinutos) || 0;
        const tiempoGraciaSegundos = Number(cfg.tiempoGraciaSegundos) || 0;
        const letraSorteada = (cfg.letra || 'A').toString().charAt(0).toLowerCase();

        // ---------- Variables de tiempo ----------
        let partidaTime = partidaMinutos * 60;
        let graceTime = tiempoGraciaSegundos;

        // ---------- DOM ----------
        const mainTimer = document.getElementById("main-timer");
        const graceTimer = document.getElementById("grace-timer");
        const prorrogaMsg = document.getElementById("prorroga-msg");
        const graceAmount = document.getElementById("grace-amount");

        // ---------- Util ----------
        function formatTimeMMSS(sec) {
            const s = Math.max(0, Math.floor(sec));
            const m = Math.floor(s / 60);
            const ss = s % 60;
            return `${m}:${ss < 10 ? '0' : ''}${ss}`;
        }

        // ---------- Actualizadores ----------
        function updateMainTimerDisplay() {
            if (mainTimer) mainTimer.textContent = formatTimeMMSS(partidaTime);
        }
        function updateGraceTimerDisplay() {
            if (graceTimer) graceTimer.textContent = formatTimeMMSS(graceTime);
        }

        // ---------- Contadores ----------
        let mainInterval = null;
        let graceInterval = null;

        function startMainCountdown() {
            updateMainTimerDisplay();
            if (partidaTime <= 0) {
                startGracePeriod();
                return;
            }
            mainInterval = setInterval(() => {
                partidaTime--;
                updateMainTimerDisplay();
                if (partidaTime <= 0) {
                    clearInterval(mainInterval);
                    startGracePeriod();
                }
            }, 1000);
        }

        function startGracePeriod() {
            if (prorrogaMsg && graceAmount) {
                graceAmount.textContent = tiempoGraciaSegundos;
                prorrogaMsg.classList.remove('hidden');
                prorrogaMsg.classList.add('visible');
                setTimeout(() => {
                    prorrogaMsg.classList.remove('visible');
                    prorrogaMsg.classList.add('hidden');
                }, 2200);
            }

            updateGraceTimerDisplay();
            if (graceInterval) clearInterval(graceInterval);

            graceInterval = setInterval(() => {
                graceTime--;
                updateGraceTimerDisplay();
                if (graceTime <= 0) {
                    clearInterval(graceInterval);
                    if (prorrogaMsg) prorrogaMsg.textContent = "Fin del tiempo";
                }
            }, 1000);
        }

        // ===========================================================
        //          BLOQUEOS PARA PRIMERA LETRA + PEGAR TEXTO
        // ===========================================================
        const letraCorrecta = letraSorteada.toUpperCase();
        const inputs = document.querySelectorAll(".formulario input[type='text']");

        inputs.forEach(input => {

            // Desactivar autocompletado
            input.setAttribute("autocomplete", "off");

            // ----- BLOQUEAR PRIMERA LETRA -----
            input.addEventListener("beforeinput", (e) => {
                const valorActual = input.value;
                const textoIngresado = e.data;

                // Solo evaluar si está escribiendo en primera posición
                if (valorActual.length === 0 && textoIngresado) {
                    const letra = textoIngresado.charAt(0).toUpperCase();

                    // Si NO es una letra válida o NO coincide → bloquear
                    if (!letra.match(/[A-ZÁÉÍÓÚÜÑ]/) || letra !== letraCorrecta) {
                        e.preventDefault();
                    }
                }

                // También impedir espacios, números o símbolos como 1er caracter
                if (valorActual.length === 0 && textoIngresado) {
                    if (!textoIngresado.match(/[A-Za-zÁÉÍÓÚÜÑ]/)) {
                        e.preventDefault();
                    }
                }
            });

            // ----- BLOQUEAR PEGAR TEXTO INCORRECTO -----
            input.addEventListener("paste", (e) => {
                const textoPegado = (e.clipboardData || window.clipboardData).getData('text');

                if (!textoPegado) return;

                const primera = textoPegado.charAt(0).toUpperCase();

                // Si NO empieza por la letra correcta → bloquear
                if (!primera.match(/[A-ZÁÉÍÓÚÜÑ]/) || primera !== letraCorrecta) {
                    e.preventDefault();
                }
            });

        });

        // ---------- Iniciar ----------
        startMainCountdown();

    });
})();
