package com.dat.book_hub.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dat.book_hub.application.dto.request.Tag.CreateTagRequest;
import com.dat.book_hub.application.dto.request.Tag.TagRequest;
import com.dat.book_hub.application.dto.response.DataResponse;
import com.dat.book_hub.application.dto.response.Tag.TagResponse;
import com.dat.book_hub.application.usecase.TagUseCase;

@RestController 
@RequestMapping("/tags")
public class TagController {
    private final TagUseCase tagService;

    public TagController(TagUseCase tagService) {
        this.tagService = tagService;
    }


    @PostMapping("/create-tag")
    public ResponseEntity<DataResponse<TagResponse>> createTag(@RequestBody CreateTagRequest tagRequest) {
        TagResponse createdTag = this.tagService.createTag(tagRequest);
        if (createdTag == null) {
            return ResponseEntity.status(500).body(new DataResponse<>("Failed to create tag.", null));
        }
        return ResponseEntity.ok(new DataResponse<>("Tag created successfully.", createdTag));
    }


    @GetMapping("/get-tags")
    public ResponseEntity<DataResponse<TagResponse>> getTagsForBook(@RequestParam("tagId") Long tagId) {
        TagResponse tags = this.tagService.getTagById(tagId);
        if (tags == null) {
            return ResponseEntity.status(404).body(new DataResponse<>("Tag not found.", null));
        }
        return ResponseEntity.ok(new DataResponse<>("Tags retrieved successfully.", tags));
    }
}
