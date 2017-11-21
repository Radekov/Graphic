package sample.enums;

public enum GrayLevelMethod {
    AVERAGE("AVERAGE"),
    YUV("YUV");

    private String method;

    GrayLevelMethod(String method) {
        this.method = method;
    }


    @Override
    public String toString() {
        return method;
    }
}
