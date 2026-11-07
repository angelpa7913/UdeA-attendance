package com.attendance.session.infrastructure.qr;

/**
 * <h2>Propósito</h2>
 * Convierte la URL con el token (ej. {@code https://host/attend?t=...}) en una imagen PNG.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: solo dibuja el QR. Lo usa {@code SessionController} (misma capa de infraestructura).<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada: {@code String content}, tamaño en píxeles.</li>
 *   <li>Salida: {@code byte[]} PNG.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>{@code @Component}. Tamaño por defecto como constante (o en {@code AttendanceProperties}).</li>
 *   <li>{@code new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, size, size)}.</li>
 *   <li>{@code MatrixToImageWriter.writeToStream(matrix, "PNG", outputStream)}.</li>
 *   <li>Capturar {@code WriterException} e {@code IOException} específicas y envolverlas con causa.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>ZXing ({@code com.google.zxing})</li>
 * </ul>
 */
public class ZxingQrImageRenderer {
}
