package com.daydoodle.daydoodle.common;

public class PictureDto {
    Long id;
    byte[] imageData;
    String imageName;
    String imageFormat;

    public PictureDto(Long id, byte[] imageData, String imageName, String imageFormat) {
        this.id = id;
        this.imageData = imageData;
        this.imageName = imageName;
        this.imageFormat = imageFormat;
    }
    public PictureDto(byte[] imageData, String imageName, String imageFormat) {
        this.imageData = imageData;
        this.imageName = imageName;
        this.imageFormat = imageFormat;
    }
    public PictureDto() {
    }

    public Long getId() {
        return id;
    }
    public byte[] getImageData() {
        return imageData;
    }
    public String getImageName() {
        return imageName;
    }
    public String getImageFormat() {
        return imageFormat;
    }
}
