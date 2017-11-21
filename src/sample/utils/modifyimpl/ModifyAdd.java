package sample.utils.modifyimpl;

import sample.utils.Modify;

public class ModifyAdd implements Modify {

    @Override
    public double calculateSingleColor(double oldPixel, double newPixel) {
        double result = oldPixel + newPixel;
        if (result > 1.0) return 1.0;
        else return result;
    }
}
