package uz.educrmsystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.educrmsystem.entity.Attachment;
import uz.educrmsystem.service.AttachmentService;

import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/attachment")
@RequiredArgsConstructor
public class AttachmentController {
    final AttachmentService attachmentService;

    @SneakyThrows
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable String id) {
        Optional<Attachment> attachment = attachmentService.download(UUID.fromString(id));

        if (attachment.isPresent()) {
            Attachment file = attachment.get();

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(getMimeTypeFromSuffix(file.getSuffix())))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .body(new UrlResource(Paths.get(file.getPath()).normalize().toUri()));
        }

        return ResponseEntity.noContent().build();
    }

    private String getMimeTypeFromSuffix(String suffix) {
        return switch (suffix.toLowerCase()) {
            case "png" -> "image/png";
            case "jpg", "jpeg" -> "image/jpeg";
            case "gif" -> "image/gif";
            case "webp" -> "image/webp";
            case "bmp" -> "image/bmp";

            case "pdf" -> "application/pdf";
            case "zip" -> "application/zip";
            case "rar" -> "application/vnd.rar";

            case "doc" -> "application/msword";
            case "docx" -> "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case "xls" -> "application/vnd.ms-excel";
            case "xlsx" -> "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

            case "ppt" -> "application/vnd.ms-powerpoint";
            case "pptx" -> "application/vnd.openxmlformats-officedocument.presentationml.presentation";

            case "txt" -> "text/plain";
            case "csv" -> "text/csv";
            case "html", "htm" -> "text/html";

            default -> "application/octet-stream";
        };
    }
}
