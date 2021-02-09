package com.backapifilter.v1.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface MultipartFileFilterService {

	void saveAndChunkFile(MultipartFile file) throws IOException;

	
}
