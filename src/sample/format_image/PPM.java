package sample.format_image;

import javafx.scene.canvas.Canvas;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

public class PPM {

    int maxValue;
    private BufferedImage bufferedImage;

    private int width, height;
    private FormatFile formatFile;

    public PPM(String filename, Canvas canvas) throws Exception {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filename));
        readHeader(bis);
        System.out.println("Rozpoczęcie czytania pliku");
        long start = System.currentTimeMillis();
        switch (formatFile) {
            case P3:
                readPictureP3(bis);
                break;
            case P6:
                readPictureP6(bis);
        }
        long end = System.currentTimeMillis();
        System.out.println("Skończenie pliku w: " + (end - start));
        bis.close();
    }

    private void readHeader(BufferedInputStream bis) throws IOException, UnsupportedEncodingException {
        System.out.println("Rozpoczęcie czytania nagłówku pliku: ");
        long start = System.currentTimeMillis();
        @SuppressWarnings("deprecation")
        StreamTokenizer st = new StreamTokenizer(bis);
        st.commentChar('#');
        st.nextToken();
        formatFile = FormatFile.valueOff(st.sval);
        if (formatFile == null) {
            throw new UnsupportedEncodingException("Coś mnie to nie wygląda na " + FormatFile.P3 + " czy " + FormatFile.P6 + " plik");
        }
        st.nextToken();
        width = (int) Math.round(st.nval);
        st.nextToken();
        height = (int) Math.round(st.nval);
        st.nextToken();
        maxValue = (char) (Math.round(st.nval) & 0xFFFF);
        if (maxValue != 0xFFFF && maxValue != 0xFF)
            throw new UnsupportedEncodingException("Nie napisałem tego, żeby wspierało innej wartości niż 255 czy 65535. Co je? " + maxValue);
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        long end = System.currentTimeMillis();
        System.out.println("Skończenie nagłówka pliku w: " + (end - start));
    }

    private BufferedImage readPictureP3(BufferedInputStream bis) throws Exception {
        //FIXME problemo w wydajności najpewniej leży po  stronie Scanner
        Scanner scanner = new Scanner(bis);
        scanner.useDelimiter("\\D+");
        int r, g, b;
        int w = 0, h = 0;
        while (scanner.hasNext()) {
            r = (int) (scanner.nextInt() * 255. / maxValue);
            g = (int) (scanner.nextInt() * 255. / maxValue);
            b = (int) (scanner.nextInt() * 255. / maxValue);
            if ((r > maxValue) || g > maxValue || b > maxValue) {
                System.out.println("OLABOGA");
                System.out.println("Znaleziono:\n\tr: " + r + "\n\tg: " + g + "\n\tb: " + b);
                throw new Exception("Jakiś tam jeszcze wymyślę, że w pliku jakaś nie taka liczba");
            }
            bufferedImage.setRGB(w, h, (r << 16) | (g << 8) | b);
            w = (w + 1) % width;
            if (w == 0) h++;
        }
        scanner.close();
        return bufferedImage;
    }

    private void readPictureP6(BufferedInputStream bis) throws IOException {
        byte[] line = new byte[width * 3];
        int h = -1;
        int r, g, b;
        while (bis.available() != 0 && h < height - 1) {
            h++;
            int count = bis.read(line);
            if (count != line.length)
                System.out.println("ups liczba wczytanych bitów jest różna niż się spodziewano\nSpodziewano: " + line.length + "\nDostano: " + count);
            for (int i = 0, w = 0; i < line.length; i = i + 3, w++) {
                r = line[i] & 0xFF;
                g = line[i + 1] & 0xFF;
                b = line[i + 2] & 0xFF;
                try {
                    bufferedImage.setRGB(w, h, (r << 16) | (g << 8) | b);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    private enum FormatFile {
        P3("P3"), P6("P6");

        private final String formatFile;

        FormatFile(String formatFile) {
            this.formatFile = formatFile;
        }

        @Override
        public String toString() {
            return super.toString();
        }

        private static FormatFile valueOff(String value) {
            if (value.equals(P3.toString()))
                return FormatFile.P3;
            if (value.equals(P6.toString()))
                return FormatFile.P6;
            return null;
        }
    }
}
