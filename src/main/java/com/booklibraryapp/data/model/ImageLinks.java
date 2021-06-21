package com.booklibraryapp.data.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageLinks {
    private String smallThumbnails;
    private String thumbnail;
}
