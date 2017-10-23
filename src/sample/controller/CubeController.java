package sample.controller;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.util.Random;

public class CubeController {

    MeshView meshView;

    @FXML
    AnchorPane cubePane;

    private static final int VIEWPORT_SIZE = 1500;

    private static final double MODEL_SCALE_FACTOR = 2;
    private static final double MODEL_X_OFFSET = 0;
    private static final double MODEL_Y_OFFSET = 0;
    private static final double MODEL_Z_OFFSET = VIEWPORT_SIZE / 2;

    double anchorX, anchorY, anchorXAngle, anchorYAngle;
    double mousePosX, mousePosY, mouseOldX, mouseOldY, mouseDeltaX, mouseDeltaY;
    double mouseFactorX, mouseFactorY;


    double dx, dy;

    Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
    Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
    Rotate rotateZ = new Rotate(0, Rotate.Z_AXIS);

    public void initialize() {
        meshView = new MeshView(Cube.loadMeshView());
        meshView.setMaterial(generateRGBCube());

        rotateX.setPivotX(0);
        rotateX.setPivotY(0);
        rotateX.setPivotZ(0);

        rotateY.setPivotX(0);
        rotateY.setPivotY(0);
        rotateY.setPivotZ(0);

        rotateZ.setPivotX(0);
        rotateZ.setPivotY(0);
        rotateZ.setPivotZ(0);

        meshView.getTransforms().addAll(rotateX, rotateY, rotateZ);

        Group group = buildScene();
        group.setRotationAxis(Rotate.Y_AXIS);
        group.setRotate(200);

        SubScene subScene = createScene3D(group);
        subScene.setCamera(new PerspectiveCamera());
        subScene.setOnMousePressed(event -> {
//            anchorX = event.getSceneX();
//            anchorY = event.getSceneY();
//            anchorXAngle = rotateX.getAngle();
//            anchorYAngle = rotateY.getAngle();
            mouseOldX = event.getSceneX();
            mouseOldY = event.getSceneY();
        });

        subScene.setOnMouseDragged(event -> {
//            rotateX.setAngle(anchorXAngle - (anchorY - event.getSceneY()));
//            rotateY.setAngle(anchorYAngle + anchorX - event.getSceneX());
            rotateX.setAngle(rotateX.getAngle() - (event.getSceneY() - mouseOldY));
            rotateY.setAngle(rotateY.getAngle() + (event.getSceneX() - mouseOldX));
            mouseOldX = event.getSceneX();
            mouseOldY = event.getSceneY();
        });

        cubePane.getChildren().add((subScene));
    }

    private PhongMaterial generateRGBCube() {
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(createImage(1024));
        return material;
    }

    public Image createImage(double size) {
        Random rnd = new Random();
        int width = (int) size;
        int height = (int) size;
        WritableImage wr = new WritableImage(width, height);
        PixelWriter pw = wr.getPixelWriter();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                pw.setColor(x, y, color);
            }
        }
        for (int x = 0; x < 256; x++) {
            for (int y = 256; y < 512; y++) {
                pw.setColor(x, y, Color.rgb(511 - y, 0, 255 - x));
            }
        }
        for (int x = 256; x < 512; x++) {
            for (int y = 256; y < 512; y++) {
                pw.setColor(x, y, Color.rgb(511 - y, x - 256, 0));
            }
        }
        for (int x = 0; x < 256; x++) {
            for (int y = 256; y < 512; y++) {
                pw.setColor(x, y, Color.rgb(511 - y, 0, 255 - x));
            }
        }
        for (int x = 256; x < 512; x++) {
            for (int y = 0; y < 256; y++) {
                pw.setColor(x, y, Color.rgb(255, x - 256, 255 - y));
            }
        }
        for (int x = 256; x < 512; x++) {
            for (int y = 512; y < 768; y++) {
                pw.setColor(x, y, Color.rgb(0, x - 256, y - 512));
            }
        }
        for (int x = 512; x < 768; x++) {
            for (int y = 256; y < 512; y++) {
                pw.setColor(x, y, Color.rgb(511 - y, 255, x - 512));
            }
        }
        for (int x = 768; x < 1024; x++) {
            for (int y = 256; y < 512; y++) {
                pw.setColor(x, y, Color.rgb(511 - y, 1023 - x, 255));
            }
        }
        for (int x = 0; x < 256; x++) {
            for (int y = 0; y < 256; y++) {
                pw.setColor(x, y, Color.rgb(255, 0, Math.min(511 - x - y, 255)));
            }
        }
        for (int x = 512; x < 768; x++) {
            for (int y = 0; y < 256; y++) {
                pw.setColor(x, y, Color.rgb(255, 255, Math.min(x - y - 256, 255)));
            }
        }
        for (int x = 0; x < 256; x++) {
            for (int y = 512; y < 768; y++) {
                pw.setColor(x, y, Color.rgb(0, 0, Math.min(y - x - 256, 255)));
            }
        }
        for (int x = 512; x < 768; x++) {
            for (int y = 512; y < 768; y++) {
                pw.setColor(x, y, Color.rgb(0, 255, Math.min(y + x - 1024, 255)));
            }
        }
        for (int x = 768; x < 1024; x++) {
            for (int y = 0; y < 256; y++) {
                pw.setColor(x, y, Color.rgb(255, 1023 - x, 255));
            }
        }
        for (int x = 768; x < 1024; x++) {
            for (int y = 512; y < 768; y++) {
                pw.setColor(x, y, Color.rgb(0, 1023 - x, 255));
            }
        }
        for (int x = 256; x < 512; x++) {
            for (int y = 768; y < 896; y++) {
                pw.setColor(x, y, Color.rgb(0, x - 256, 255));
            }
        }
        for (int x = 256; x < 512; x++) {
            for (int y = 896; y < 1024; y++) {
                pw.setColor(x, y, Color.rgb(255, x - 256, 255));
            }
        }

        return wr;
    }

    private Group buildScene() {
        meshView.setTranslateX(VIEWPORT_SIZE / 2 + MODEL_X_OFFSET);
        meshView.setTranslateY(VIEWPORT_SIZE / 2 * 9.0 / 16 + MODEL_Y_OFFSET);
        meshView.setTranslateZ(VIEWPORT_SIZE / 2 + MODEL_Z_OFFSET);
        meshView.setScaleX(MODEL_SCALE_FACTOR);
        meshView.setScaleY(MODEL_SCALE_FACTOR);
        meshView.setScaleZ(MODEL_SCALE_FACTOR);
        Group group = new Group(meshView);
        group.getChildren().add(new AmbientLight());
        return group;
    }

    private RotateTransition rotate3dGroup(Group group) {
        RotateTransition rotate = new RotateTransition(Duration.seconds(10), group);
        rotate.setAxis(Rotate.Y_AXIS);
        rotate.setFromAngle(0);
        rotate.setToAngle(360);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setCycleCount(RotateTransition.INDEFINITE);

        return rotate;
    }

    private SubScene createScene3D(Group group) {
        SubScene scene3d = new SubScene(group, VIEWPORT_SIZE, VIEWPORT_SIZE * 9.0 / 16, true, SceneAntialiasing.BALANCED);
        scene3d.setFill(Color.rgb(38, 40, 38));
        PerspectiveCamera camera = new PerspectiveCamera(false);
        camera.setTranslateX(100);
        camera.setTranslateY(0);
        camera.setTranslateZ(100);
        camera.getTransforms().addAll(rotateX, rotateY, new Translate(0, 0, 400));
        scene3d.setCamera(camera);
        return scene3d;
    }

    private static class Cube extends TriangleMesh {

        Cube() {
            getPoints().setAll(points);
            getTexCoords().setAll(texCoords);
            getFaces().setAll(faces);
        }

        private static Cube loadMeshView() {
            return new Cube();
        }

        private static float[] points = {
                0, 0, 256,      //P0
                256, 0, 256,    //P1
                0, 256, 256,    //P2
                256, 256, 256,  //P3
                0, 0, 0,        //P4
                256, 0, 0,      //P5
                0, 256, 0,      //P6
                256, 256, 0     //P7
        };

        private static float[] texCoords = {
                0.25f, 0,       //T0
                0.5f, 0,        //T1
                0, 0.25f,       //T2
                0.25f, 0.25f,   //T3
                0.5f, 0.25f,    //T4
                0.75f, 0.25f,   //T5
                1, 0.25f,       //T6
                0, 0.5f,        //T7
                0.25f, 0.5f,    //T8
                0.5f, 0.5f,     //T9
                0.75f, 0.5f,    //T10
                1, 0.5f,        //T11
                0.25f, 0.75f,   //T12
                0.5f, 0.75f     //T13
        };
        private static int[] faces = {
                5, 1, 4, 0, 0, 3     //P5,T1 ,P4,T0  ,P0,T3
                , 5, 1, 0, 3, 1, 4    //P5,T1 ,P0,T3  ,P1,T4
                , 0, 3, 4, 2, 6, 7    //P0,T3 ,P4,T2  ,P6,T7
                , 0, 3, 6, 7, 2, 8    //P0,T3 ,P6,T7  ,P2,T8
                , 1, 4, 0, 3, 2, 8    //P1,T4 ,P0,T3  ,P2,T8
                , 1, 4, 2, 8, 3, 9    //P1,T4 ,P2,T8  ,P3,T9
                , 5, 5, 1, 4, 3, 9    //P5,T5 ,P1,T4  ,P3,T9
                , 5, 5, 3, 9, 7, 10   //P5,T5 ,P3,T9  ,P7,T10
                , 4, 6, 5, 5, 7, 10   //P4,T6 ,P5,T5  ,P7,T10
                , 4, 6, 7, 10, 6, 11  //P4,T6 ,P7,T10 ,P6,T11
                , 3, 9, 2, 8, 6, 12   //P3,T9 ,P2,T8  ,P6,T12
                , 3, 9, 6, 12, 7, 13  //P3,T9 ,P6,T12 ,P7,T13
        };
    }
}
