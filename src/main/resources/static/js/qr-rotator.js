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
