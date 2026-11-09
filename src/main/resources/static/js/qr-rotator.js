/*
 * Propósito: en la pantalla de proyección, recarga la imagen del QR cada `windowSeconds`,
 * muestra la cuenta regresiva y actualiza el contador de asistentes.
 *
 * Responsabilidad: solo presentación. No calcula tokens (eso lo hace el servidor con el Clock).
 *
 * Entradas: atributos data-* del elemento #projection: data-qr-url, data-count-url, data-window-seconds.
 * Salidas: cambios en el DOM.
 *
 * Pasos a implementar:
 *   1. Leer los data-* al cargar (DOMContentLoaded). Si falta alguno, no hacer nada.
 *   2. refreshQr(): cambiar img.src a `${qrUrl}?v=${Date.now()}` para evitar la caché.
 *   3. Cuenta regresiva: setInterval cada 1 s que reste uno y, al llegar a 0, llame refreshQr()
 *      y reinicie el contador a windowSeconds.
 *      (Mejor: alinear con el reloj del servidor usando `expiresAt` de /token-info.)
 *   4. setInterval (p. ej. cada 5 s) que haga fetch(countUrl) y pinte el número.
 *   5. Si fetch falla (sesión cerrada o 401/404), detener los intervalos con clearInterval.
 *
 * Prohibido: while (true), bucles de espera activa o recursión sin fin. Solo setInterval/clearInterval.
 * Depende de: nada (JavaScript del navegador, sin librerías).
 */

document.addEventListener('DOMContentLoaded', () => {
    const COUNTDOWN_MILLIS = 1000;
    const COUNT_REFRESH_MILLIS = 5000;

    const projection = document.getElementById('projection');
    const qr = document.getElementById('qr');
    const countdown = document.getElementById('countdown');
    const count = document.getElementById('count');
    if (!projection || !qr || !countdown || !count) {
        return;
    }

    const { qrUrl, tokenInfoUrl, countUrl } = projection.dataset;
    const windowSeconds = Number(projection.dataset.windowSeconds);
    if (!qrUrl || !tokenInfoUrl || !countUrl || !(windowSeconds > 0)) {
        return;
    }

    let remaining = windowSeconds;
    let countdownTimer;
    let countTimer;

    const fetchJson = async (url) => {
        const response = await fetch(url, { cache: 'no-store', redirect: 'error' });
        if (!response.ok) {
            throw new Error(`HTTP ${response.status}`);
        }
        return response.json();
    };

    const stopAll = () => {
        clearInterval(countdownTimer);
        clearInterval(countTimer);
    };

    const render = () => {
        countdown.textContent = String(remaining);
    };

    const alignWithServer = async () => {
        try {
            const { expiresAt } = await fetchJson(tokenInfoUrl);
            const seconds = Math.ceil((Date.parse(expiresAt) - Date.now()) / COUNTDOWN_MILLIS);
            remaining = Math.min(Math.max(seconds, 1), windowSeconds);
            render();
        } catch (error) {
            stopAll();
        }
    };

    const refreshQr = () => {
        qr.src = `${qrUrl}?v=${Date.now()}`;
        alignWithServer();
    };

    const tick = () => {
        remaining -= 1;
        if (remaining <= 0) {
            remaining = windowSeconds;
            refreshQr();
        }
        render();
    };

    // Un fallo del contador solo detiene el contador: el QR debe seguir rotando.
    const refreshCount = async () => {
        try {
            const data = await fetchJson(countUrl);
            count.textContent = String(data.count);
        } catch (error) {
            clearInterval(countTimer);
        }
    };

    countdownTimer = setInterval(tick, COUNTDOWN_MILLIS);
    countTimer = setInterval(refreshCount, COUNT_REFRESH_MILLIS);
    alignWithServer();
    refreshCount();
});
