package it.finanze.entrate.coopint.dboard.dpi.com.command.utility;

import lombok.SneakyThrows;
import lombok.extern.apachecommons.CommonsLog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@CommonsLog
public class IOUtils {

    public static String convertByteArrayToBase64(byte[] byteArray) {
        return Base64.getEncoder().encodeToString(byteArray);
    }

    public static byte[] convertBase64ToByteArray(String base64) {
        return Base64.getDecoder().decode(base64);
    }

    public static byte[] convertGzippedBase64ToByteArray(String base64) {
        try {
            return it.finanze.entrate.coopint.xml.utils.IOUtils.gUnzipper(Base64.getDecoder().decode(base64));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @SneakyThrows
    public static byte[] convertGzToZip(byte[] fileGz, String entryName) {

        byte[] file = it.finanze.entrate.coopint.xml.utils.IOUtils.gUnzipper(fileGz);

        return it.finanze.entrate.coopint.xml.utils.IOUtils.zip(file, entryName);

    }

    public static byte[] zip(byte[] uncompressed, String entryName) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        ZipEntry ze = new ZipEntry(entryName);
        zos.putNextEntry(ze);
        zos.write(uncompressed);
        zos.flush();
        zos.close();
        baos.close();
        return baos.toByteArray();
    }
}
