package sample.utils.modifyimpl;

import sample.utils.Modify;

public class ModifyDivide implements Modify {

    @Override
    public double calculateSingleColor(double oldPixel, double newPixel) {
        double result = oldPixel / newPixel;
        if (result < 0.0) return 0.0;
        else return result;
    }
}
