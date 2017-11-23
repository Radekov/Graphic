package sample.utils.filters;

public enum FilterType {
    AVERAGING_3x3("Filtr wygładzający (uśredniający)3x3"),
    AVERAGING_5x5("Filtr wygładzający (uśredniający)5x5"),
    AVERAGING_7x7("Filtr wygładzający (uśredniający)7x7"),
    MEDIAN("Filtr medianowy"),
    SOBEL_VERTICAL("Filtr wykrywania krawędzi (sobel) - pionowy"),
    SOBEL_HORIZONTAL("Filtr wykrywania krawędzi (sobel) - poziomy"),
    UNSHARP("-Filtr górnoprzepustowy wyostrzający"),
    GAUSS_1("Filtr rozmycie gaussowskie - 1"),
    GAUSS_2("Filtr rozmycie gaussowskie - 2"),
    GAUSS_3("Filtr rozmycie gaussowskie - 3"),
    GAUSS_4("Filtr rozmycie gaussowskie - 4"),
    GAUSS_5("Filtr rozmycie gaussowskie - 5"),
    SPLOT("-Splot maski dowolnego rozmiaru");

    private final String nameFilter;

    FilterType(String nameFilter) {
        this.nameFilter = nameFilter;
    }

    @Override
    public String toString() {
        return nameFilter;
    }
}
