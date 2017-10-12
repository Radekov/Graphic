package sample.format_image;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Pattern;

public class PPM {

    private FormatFile formatFile;

    private int width, height;

    private Canvas canvas;

    public PPM(String filename, Canvas canvas) throws IOException {
        this.canvas = canvas;
        System.out.println("Rozpoczęcie czytania pliku: ");
        long start = System.currentTimeMillis();
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filename));
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
        canvas.setWidth(width);
        st.nextToken();
        height = (int) Math.round(st.nval);
        canvas.setHeight(height);
        st.nextToken();
        int maxVal = (int) Math.round(st.nval);
        if (maxVal != 255)
            throw new UnsupportedEncodingException("Nie napisałem tego, żeby wspierało innej wartości niż 255. Co je?");

        long end;
        switch (formatFile) {
            case P3:
                readPictureP3(bis);
                end = System.currentTimeMillis();
                System.out.println("Skończenie pliku w: " + (end - start));
                break;
            case P6:
                end = System.currentTimeMillis();
                readPictureP6(bis);
                System.out.println("Skończenie pliku w: " + (end - start));
        }
        bis.close();
    }

    private void readPictureP3(BufferedInputStream bis) throws IOException {
        PixelWriter pw = canvas.getGraphicsContext2D().getPixelWriter();
        Scanner scanner = new Scanner(bis);
        Pattern pattern = Pattern.compile("^#.*");
        int[] color = new int[3];
        int whichColor = 0;
        int w = 0, h = 0;
        while (scanner.hasNext()) {
            if (scanner.hasNext(pattern)) {
                scanner.next();
            } else {
                color[whichColor] = scanner.nextInt();
                whichColor = (whichColor + 1) % 3;
            }
            if (whichColor == 0) {
                pw.setColor(w, h, Color.rgb(color[0], color[1], color[2]));
                w = (w + 1) % width;
                if (w == 0) {
                    h++;
                }
            }
        }
        scanner.close();
    }

    private void readPictureP6(BufferedInputStream bis) throws IOException {
        PixelWriter pw = canvas.getGraphicsContext2D().getPixelWriter();
        byte[] line = new byte[width * 3];
        int h = -1;
        while (bis.available() != 0) {
            h++;
            int count = bis.read(line);
            if (count != line.length)
                System.out.println("ups liczba wczytanych bitów jest różna niż się spodziewano\nSpodziewano: " + line.length + "\nDostano: " + count);
            for (int i = 0, w = 0; i < line.length; i = i + 3, w++) {
                int r = line[i] & 0xFF;
                int g = line[i + 1] & 0xFF;
                int b = line[i + 2] & 0xFF;
                pw.setColor(w, h, Color.rgb(r, g, b));

            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private enum FormatFile {
        P3("P3"), P6("P6");

        private String formatFile;

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

    private enum Now {RED, GREEN, BLUE}
}
