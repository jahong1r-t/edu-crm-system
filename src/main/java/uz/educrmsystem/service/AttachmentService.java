package uz.educrmsystem.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import uz.educrmsystem.entity.Attachment;
import uz.educrmsystem.utils.Util;

import java.io.FileOutputStream;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
public class AttachmentService {
    @PersistenceContext
    private EntityManager entityManager;

    @SneakyThrows
    @Transactional
    public Optional<Attachment> upload(MultipartFile file) {
        if (!file.isEmpty()) {
            UUID id = UUID.randomUUID();
            String suffix = Objects.requireNonNull(file.getContentType()).split("/")[1];
            String fileName = Util.path.concat(id.toString()).concat(".").concat(suffix);

            Attachment attachment = Attachment.builder()
                    .id(id)
                    .name(file.getOriginalFilename())
                    .suffix(suffix)
                    .fileSize(file.getInputStream().available())
                    .path(fileName)
                    .build();

            try (FileOutputStream fos = new FileOutputStream(fileName)) {
                fos.write(file.getInputStream().readAllBytes());
            }

            entityManager.persist(attachment);

            return Optional.of(attachment);
        }

        return Optional.empty();
    }

    public Optional<Attachment> download(UUID id) {
        Attachment attachment = entityManager.find(Attachment.class, id);

        return Optional.ofNullable(attachment);
    }
}
