package com.booklibraryapp.data.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResults {
    private  String id;
    private  VolumeInfo volumeInfo;

}
