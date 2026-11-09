package com.attendance.session.infrastructure.qr;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Component;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

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
@Component
public class ZxingQrImageRenderer {

    public static final int DEFAULT_SIZE = 480;

    private static final String IMAGE_FORMAT = "PNG";

    public byte[] render(String content, int size) {
        try {
            final BitMatrix matrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, size, size);
            final ByteArrayOutputStream output = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, IMAGE_FORMAT, output);
            return output.toByteArray();
        } catch (WriterException | IOException e) {
            throw new IllegalStateException("No se pudo generar la imagen del QR", e);
        }
    }
}
