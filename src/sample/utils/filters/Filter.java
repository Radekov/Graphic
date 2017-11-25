package sample.utils.filters;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public interface Filter {

    WritableImage filter(Image picture);
}
