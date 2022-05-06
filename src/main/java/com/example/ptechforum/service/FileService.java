package com.example.ptechforum.service;

import com.example.ptechforum.model.File;
import com.example.ptechforum.model.Post;
import com.example.ptechforum.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;
    private Integer sequence = 0;

    private final FileRepository fileRepository;

    @PostConstruct
    public void initializeDirectory() {
        Path attachmentPath = Paths.get(this.uploadPath);
        Path summernoteImgPath = Paths.get(this.uploadPath + "/summernote");
        try {
            if (!Files.exists(attachmentPath)) {
                Files.createDirectories(attachmentPath);
            }
            if (!Files.exists(summernoteImgPath)) {
                Files.createDirectories(summernoteImgPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void saveAttachment(MultipartFile multipartFile, Post post) throws IOException {
        File file = createFile(multipartFile);
        file.assignPost(post);
        file = fileRepository.save(file);
        uploadFile(multipartFile, file);
    }

    private void uploadFile(MultipartFile multipartFile, File file) throws IOException {
        Path path = Paths.get(this.uploadPath + file.getRelativePath());
        Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
    }

    private File createFile(MultipartFile multipartFile) {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        String fileName = String.format("%s_%d", timeStamp, this.sequence);
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        File file = File.builder()
                .name(fileName)
                .originalName(multipartFile.getOriginalFilename())
                .size(BigInteger.valueOf(multipartFile.getSize()))
                .extension(extension)
                .relativePath(String.format("/%s.%s", fileName, extension))
                .sequence(this.sequence)
                .fileType(multipartFile.getContentType()).build();
        return file;
    }

    private List<File> createFiles(List<MultipartFile> multipartFileList) {
        List<File> files = new ArrayList<>();
        for (int i=0; i < multipartFileList.size(); i++) {
            this.sequence = i;
            File file = createFile(multipartFileList.get(i));
            files.add(file);
        }
        this.sequence = 0;
        return files;
    }
}
