package org.example.pesadillamago.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private ImageView gameImageView;

    private List<Image> images;
    private int currentImageIndex = 0;

    @FXML
    public void initialize() {
        loadImages();
        showNextImage();
        rootPane.widthProperty().addListener((obs, oldVal, newVal) -> adjustImageView());
        rootPane.heightProperty().addListener((obs, oldVal, newVal) -> adjustImageView());
    }
    private void adjustImageView() {
        if (gameImageView.getImage() == null) return;

        double imageWidth = gameImageView.getImage().getWidth();
        double imageHeight = gameImageView.getImage().getHeight();
        double paneWidth = rootPane.getWidth();
        double paneHeight = rootPane.getHeight();

        double widthRatio = paneWidth / imageWidth;
        double heightRatio = paneHeight / imageHeight;
        double scale = Math.min(widthRatio, heightRatio);

        if (scale < 1) {
            gameImageView.setFitWidth(imageWidth * scale);
            gameImageView.setFitHeight(imageHeight * scale);
        } else {
            gameImageView.setFitWidth(imageWidth);
            gameImageView.setFitHeight(imageHeight);
        }

        double x = (paneWidth - gameImageView.getFitWidth()) / 2;
        double y = (paneHeight - gameImageView.getFitHeight()) / 2;

        AnchorPane.setLeftAnchor(gameImageView, x);
        AnchorPane.setTopAnchor(gameImageView, y);
    }


    private void loadImages() {
        images = new ArrayList<>();
        String[] extensions = {".png", ".jpg", ".jpeg"};
        String basePath = "/org/example/pesadillamago/images/game/";
        int index = 1;
        boolean imageFound;

        do {
            imageFound = false;
            for (String ext : extensions) {
                String imagePath = basePath + index + ext;
                InputStream is = getClass().getResourceAsStream(imagePath);
                if (is != null) {
                    Image image = new Image(is);
                    images.add(image);
                    imageFound = true;
                    index++;
                }
            }
        } while (imageFound);

        System.out.println("Imágenes cargadas: " + images.size());
        Collections.shuffle(images);
    }

    private void showNextImage() {
        if (!images.isEmpty()) {
            Image nextImage = images.get(currentImageIndex);
            gameImageView.setImage(nextImage);
            currentImageIndex = (currentImageIndex + 1) % images.size();
            adjustImageView();
        }
    }


    @FXML
    private void handleDirection(ActionEvent event) {
        Button button = (Button) event.getSource();
        String direction = button.getText();
        showNextImage();
    }
    private void updateHP(int current, int max) {
    }
}