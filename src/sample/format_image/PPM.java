package sample.format_image;

import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.UnsupportedEncodingException;

public class PPM {

    protected int[] data;
    protected byte[] byteData;

    protected Color picture[][];
    private int width, height;

    public PPM(String filename) throws IOException {
        initializeHeader(filename);
        System.out.println("Tworzenie punktów - liniowe: ");
        long start = System.nanoTime();
        for (int i = 0; i < byteData.length; i += 3) {
            int r = byteData[i] & 0xFF;
            int g = byteData[i + 1] & 0xFF;
            int b = byteData[i + 2] & 0xFF;
            picture[(i / 3) / width][(i / 3) % height] = Color.rgb(r, g, b);
        }
        long end = System.nanoTime();
        System.out.println("Skończone w: " + (end - start) / 10000000);
    }

    private void initializeHeader(String filename) throws IOException {
        System.out.println("Rozpoczęcie czytania pliku: ");
        long start = System.nanoTime();
        FileInputStream fis = new FileInputStream(filename);
        StreamTokenizer st = new StreamTokenizer(fis);
        st.commentChar('#');
        st.nextToken();
        if (validateFormatFile(st)) {
            throw new UnsupportedEncodingException("Is not " + getFormatFile() + " file");
        }
        st.nextToken();
        width = (int) Math.round(st.nval);
        st.nextToken();
        height = (int) Math.round(st.nval);
        data = new int[width * height];
        picture = new Color[width][height];
        st.nextToken();
        int maxVal = (int) Math.round(st.nval);
        if (maxVal != 255)
            throw new UnsupportedEncodingException("Not a 255 color PPM");
        byteData = fis.readAllBytes();
        long end = System.nanoTime();
        System.out.println("Skończenie pliku w: " + (end - start) / 10000000);
    }

    private boolean validateFormatFile(StreamTokenizer st) {
        return !st.sval.equals(getFormatFile());
    }

    protected String getFormatFile() {
        return "P6";
    }
}
