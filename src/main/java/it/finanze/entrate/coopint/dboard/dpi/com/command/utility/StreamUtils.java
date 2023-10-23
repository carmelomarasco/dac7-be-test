package it.finanze.entrate.coopint.dboard.dpi.com.command.utility;

import org.apache.commons.codec.binary.Base64InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class StreamUtils {
    private static final Logger log = LoggerFactory.getLogger(StreamUtils.class);
    /**
     * returnds the length of the underling stream
     * @param inputStream the stream to measure
     * @param chunkSize the size of the byte array we read for each round
     * @return the lenght in bytes
     * @throws IOException
     */
    public static long streamLength(InputStream inputStream, int chunkSize) throws IOException {
        byte[] buffer = new byte[chunkSize];
        int chunkBytesRead = 0;
        long length = 0;
        while((chunkBytesRead = inputStream.read(buffer)) != -1) {
            length += chunkBytesRead;
        }
        return length;
    }

    /**
     * returns an input stream from a gzipped string based 64
     * @param messageXmlGzipBase64 the string to stream
     * @return the resulting stream
     * @throws IOException
     */
    public static InputStream getXmlInputStreamFromXmlGzipBase64(String messageXmlGzipBase64) throws IOException {
        // get stream from payload
        Base64InputStream b64is = new Base64InputStream(new ByteArrayInputStream(messageXmlGzipBase64.getBytes()),
                false, 80, "\n".getBytes(StandardCharsets.UTF_8));
        return new GZIPInputStream(b64is);

    }

    /**
     * return a stream from an URL passed in
     * @param url the url to open
     * @param args the eventually needed headers
     * @return the resulting stream
     */
    public static InputStream urlToInputStream(URL url, Map<String, String> args) {
        HttpURLConnection con = null;
        InputStream inputStream = null;
        try {
            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(15000);
            con.setReadTimeout(15000);
            if (args != null) {
                for (Map.Entry<String, String> e : args.entrySet()) {
                    con.setRequestProperty(e.getKey(), e.getValue());
                }
            }
            con.setRequestMethod("GET");
            con.connect();
            int responseCode = con.getResponseCode();
            /* By default the connection will follow redirects. The following
             * block is only entered if the implementation of HttpURLConnection
             * does not perform the redirect. The exact behavior depends to
             * the actual implementation (e.g. sun.net).
             * !!! Attention: This block allows the connection to
             * switch protocols (e.g. HTTP to HTTPS), which is <b>not</b>
             * default behavior. See: https://stackoverflow.com/questions/1884230
             * for more info!!!
             */
            if (responseCode < 400 && responseCode > 299) {
                String redirectUrl = con.getHeaderField("Location");
                try {
                    URL newUrl = new URL(redirectUrl);
                    return urlToInputStream(newUrl, args);
                } catch (MalformedURLException e) {
                    URL newUrl = new URL(url.getProtocol() + "://" + url.getHost() + redirectUrl);
                    return urlToInputStream(newUrl, args);
                }
            }
            /*!!!!!*/

            inputStream = con.getInputStream();
            return inputStream;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * transforms a gzipped based64 stream into a plain stream
     * @param gZippedBase64Stream the stream to transform
     * @return the resulting stream
     * @throws Exception
     */
    public static InputStream getXmlInputStreamFromXmlGzipBase64(InputStream gZippedBase64Stream) throws Exception{
        Base64InputStream b64is = new Base64InputStream(gZippedBase64Stream, false, 80, "\n".getBytes(StandardCharsets.UTF_8));
        return new GZIPInputStream(b64is);
    }
    
    /**
     * transforms a gzipped stream into a plain stream
     * @param gZippedStream the stream to transform
     * @return the resulting stream
     * @throws Exception
     */
    public static InputStream getXmlInputStreamFromXmlGzip(InputStream gZippedStream) throws Exception{        
        return new GZIPInputStream(gZippedStream);
    }
    
    /**
     * transforms based64 stream into a gzip stream
     * @param gZippedBase64Stream the stream to transform
     * @return the resulting stream
     * @throws Exception
     */
    public static InputStream getGzipXmlInputStreamFromXmlGzipBase64(InputStream gZippedBase64Stream) throws Exception{
    	return new Base64InputStream(gZippedBase64Stream, false, 80, "\n".getBytes(StandardCharsets.UTF_8));
        
    }

    /**
     * transforms a gzipped based64 stream into a plain stream, starting from aa URL
     * @param url the url which returns a gzipped based64 stream
     * @param headers eventually needed headers for the URL call
     * @return the resulting stream
     * @throws Exception
     */
    public static InputStream getXmlInputStreamFromXmlGzipBase64URL(URL url, Map<String, String> headers) throws Exception{
        Base64InputStream b64is = new Base64InputStream(urlToInputStream(url, headers), false, 80, "\n".getBytes(StandardCharsets.UTF_8));
        return new GZIPInputStream(b64is);
    }

    /**
     * copy an entire stream to another. you have to open/close streams in caller code
     * it copies at 8192 block size
     * @param source the source stream
     * @param target the target stream
     * @throws IOException
     */
    public static void copy(InputStream source, OutputStream target) throws IOException {
        byte[] buf = new byte[8192];
        int length;
        while ((length = source.read(buf)) > 0) {
            target.write(buf, 0, length);
        }
    }

    public static byte[] readAllBytes(InputStream inputStream) throws IOException {
        final int bufLen = 4 * 0x400; // 4KB
        byte[] buf = new byte[bufLen];
        int readLen;
        IOException exception = null;

        try {
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                while ((readLen = inputStream.read(buf, 0, bufLen)) != -1)
                    outputStream.write(buf, 0, readLen);

                return outputStream.toByteArray();
            }
        } catch (IOException e) {
            exception = e;
            throw e;
        } finally {
            if (exception == null) inputStream.close();
            else try {
                inputStream.close();
            } catch (IOException e) {
                exception.addSuppressed(e);
            }
        }
    }
}
