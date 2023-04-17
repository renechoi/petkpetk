package com.petkpetk.service.domain.shopping.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileService {
	public String uploadFiles(String uploadPath, String originalFileName, byte[] fileData) throws
		IOException {
		UUID uuid = UUID.randomUUID();
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
		String savedFileName = uuid.toString() + extension;
		String fileUploadFullUrl = uploadPath + "/" + savedFileName;

		Files.write(Paths.get(fileUploadFullUrl), fileData);
		return savedFileName;
	}

	public void deleteFile(String filePath, String originalImageName) throws Exception {
		if (originalImageName.equals("") || originalImageName == null) {

		} else {
			File deleteFile = new File(filePath + "/" + originalImageName);
			System.out.println("filePath = " + filePath + "/" + originalImageName);
			if (deleteFile.exists()) {
				deleteFile.delete();
				log.info("○○○○○○○○○○○○○○○○ 파일 삭제 완료");
			} else {
				log.info("○○○○○○○○○○○○○○○○ 파일 삭제 실패");
			}
		}
	}
}
