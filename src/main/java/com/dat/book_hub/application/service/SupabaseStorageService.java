package com.dat.book_hub.application.service;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

@Service 
public class SupabaseStorageService {
    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.key}")
    private String supabaseKey;

    @Value("${supabase.bucket}")
    private String bucket;

    private final RestClient restClient = RestClient.create();

    public String uploadImage(MultipartFile file, String username, String bookTitle)
            throws IOException {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Image is required");
        }

        String contentType = file.getContentType();

        Set<String> allowedTypes = Set.of(
            "image/jpeg",
            "image/png",
            "image/webp"
        );

        if (contentType == null ||
                !allowedTypes.contains(contentType)) {
            throw new IllegalArgumentException(
                "Only JPG, PNG and WEBP are allowed"
            );
        }

        if (file.getSize() > 5 * 1024 * 1024) {
            throw new IllegalArgumentException(
                "Image must be smaller than 5MB"
            );
        }

        String extension = switch (contentType) {
            case "image/jpeg" -> ".jpg";
            case "image/png" -> ".png";
            case "image/webp" -> ".webp";
            default -> throw new IllegalArgumentException();
        };

        String fileName = UUID.randomUUID() + extension;
        String objectPath = "books/" + username + "/" + bookTitle + "/" + fileName;

        String uploadUrl = supabaseUrl
                + "/storage/v1/object/"
                + bucket + "/" + objectPath;

        restClient.post()
                .uri(uploadUrl)
                .header("apikey", supabaseKey)
                .header("Authorization", "Bearer " + supabaseKey)
                .contentType(MediaType.parseMediaType(contentType))
                .body(file.getBytes())
                .retrieve()
                .toBodilessEntity();

        return supabaseUrl
                + "/storage/v1/object/public/"
                + bucket + "/" + objectPath;
    }

    public void deleteImage(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            throw new IllegalArgumentException("Image URL is required");
        }

        String objectPath = imageUrl.replace(
                supabaseUrl + "/storage/v1/object/public/" + bucket + "/",
                "");

        String deleteUrl = supabaseUrl
                + "/storage/v1/object/"
                + bucket + "/" + objectPath;

        restClient.delete()
                .uri(deleteUrl)
                .header("apikey", supabaseKey)
                .header("Authorization", "Bearer " + supabaseKey)
                .retrieve()
                .toBodilessEntity();
    }

    public String updateImage(String oldImageUrl, MultipartFile newFile, String username, String bookTitle)
            throws IOException {
        if (oldImageUrl != null && !oldImageUrl.isEmpty()) {
            deleteImage(oldImageUrl);
        }
        return uploadImage(newFile, username, bookTitle);
    }
}
