package sample.utils.filters;

public class MatrixConstans {
    //    @formatter:off
    //Wygładzający/Uśredniający
    public static final String averaging3x3 = "3x3";
    public static int[] averagingFilter3x3 = {1, 1, 1,
                                              1, 1, 1,
                                              1, 1, 1};

    public static final String averaging5x5 = "5x5";
    public static int[] averagingFilter5x5 = {1, 1, 1, 1, 1,
                                              1, 1, 1, 1, 1,
                                              1, 1, 1, 1, 1,
                                              1, 1, 1, 1, 1,
                                              1, 1, 1, 1, 1};

    public static final String averaging7x7 = "7x7";
    public static int[] averagingFilter7x7 = {1, 1, 1, 1, 1, 1, 1,
                                              1, 1, 1, 1, 1, 1, 1,
                                              1, 1, 1, 1, 1, 1, 1,
                                              1, 1, 1, 1, 1, 1, 1,
                                              1, 1, 1, 1, 1, 1, 1,
                                              1, 1, 1, 1, 1, 1, 1,
                                              1, 1, 1, 1, 1, 1, 1};

    //median
    //Sobel
    public static final String horizontal = "poziomy";
    public static int[] horizontalSobel = {1,2,1,
    0,0,0,
    -1,-2,-1};

    public static final String vertical = "pionowy";
    public static int[] verticalSobel = {1,0,-1,
    2,0,-2,
    1,0,-1};

    //Filtr górnoprzepustowy wyostrzający,
    //Filtr rozmycie gaussowskie,
    public static int[] gauss1 =
            {1,2,1,
                    2,4,2,
                    1,2,1};

    public static int[] gauss2 =
            {1,1,2,1,1,
    1,2,4,2,1,
    2,4,8,4,2,
    1,2,4,2,1,
    1,1,2,1,1};

    public static int[] gauss3 =
            {0,1,2,1,0,
                    1,4,8,4,1,
                    2,8,16,8,2,
                    1,4,8,4,1,
                    0,1,2,1,0};

    public static int[] gauss4 =
            {1,4,7,4,1,
                    4,16,26,16,4,
                    7,26,41,26,7,
                    4,26,16,26,4,
                    1,4,7,4,1};

    public static int[] gauss5 =
            {1,1,2,2,2,1,1,
            1,2,2,4,2,2,1,
            2,2,4,8,4,2,2,
            2,4,8,16,8,4,2,
            2,2,4,8,4,2,2,
            1,2,2,4,2,2,1,
            1,1,2,2,2,1,1};


    //Splot maski dowolnego rozmiaru.

//    @formatter:on
}
