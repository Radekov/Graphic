package sample.enums;

public enum Mode {
    ADD("ADD"),
    SUBSTRACT("SUBSTRACT"),
    MULTIPLY("MULTIPLY"),
    DIVIDE("DIVIDE");

    private String mode;

    Mode(String mode) {
        this.mode = mode;
    }


    @Override
    public String toString() {
        return mode;
    }
}
