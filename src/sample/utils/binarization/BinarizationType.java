package sample.utils.binarization;

public enum BinarizationType {

    MANUAL("MANUAL"),
    PERCENT_BLACK_SELECTION("PERCENT_BLACK_SELECTION"),
    MEAN_ITERATIVE_SELECTION("MEAN_ITERATIVE_SELECTION"),
    ENTROPY_SELECTION("ENTROPY_SELECTION"),
    MINIMUM_ERROR("MINIMUM_ERROR"),
    FUZZY_MINIMUM_ERROR("FUZZY_MINIMUM_ERROR");

    private String mode;

    BinarizationType(String mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return mode;
    }
}
