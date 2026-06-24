package com.imageprocessor.dtos;

public class ImageShapeResponse {

    private int width;
    private int height;
    private String shape;

    public ImageShapeResponse(
            int width,
            int height,
            String shape) {

        this.width = width;
        this.height = height;
        this.shape = shape;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getShape() {
        return shape;
    }
}