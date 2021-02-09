package com.backapifilter.v1.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.backapifilter.v1.model.Chunk;
import com.backapifilter.v1.model.ReceivedFile;
import com.backapifilter.v1.model.ReceivedFileBuilder;
import com.backapifilter.v1.util.ApiConstants;

@Service
public class MultipartFileFilterServiceImpl implements MultipartFileFilterService{

	private static final Logger LOG = LoggerFactory.getLogger(MultipartFileFilterServiceImpl.class);

	private RestTemplate restTemplate;
	
	private ReceivedFileBuilder builder;

	@Autowired
	public MultipartFileFilterServiceImpl(RestTemplate restTemplate, ReceivedFileBuilder builder) {
		this.restTemplate = restTemplate;
		this.builder = builder;

	}
		
	@Override
	public void saveAndChunkFile(MultipartFile file) throws IOException {

		builder.withFileId(file.getOriginalFilename());
		builder.withFileId(file.getName());
		builder.withFileType(file.getContentType());
		builder.withFileData(file.getBytes());

		Path path = Paths.get(ApiConstants.TMP_FILE + file.getOriginalFilename());
		File willBeRead = Files.write(path, file.getBytes()).toFile();
		
		LOG.info("file temporary written in {}", willBeRead.getPath());
		
		ReceivedFile receivedFile = readAndFragment(willBeRead, file);
		
		receivedFile.getChunks().forEach(x -> sendChunk(x, receivedFile.getChunks().size()));
	}

	private ReceivedFile readAndFragment(File willBeRead, MultipartFile file) {

		int chunkSize = ApiConstants.CHUNK_SIZE;
		
		List<Chunk> chunks = new ArrayList<>();
		
		int FILE_SIZE = (int) willBeRead.length();

		LOG.info("Total File Size: {}", FILE_SIZE);

		int NUMBER_OF_CHUNKS = 0;
		byte[] temporary = null;

		try (InputStream inStream = new BufferedInputStream(new FileInputStream(willBeRead));) {

			int totalBytesRead = 0;

			while (totalBytesRead < FILE_SIZE) {
				String PART_NAME = "data" + NUMBER_OF_CHUNKS + ".bin";
				int bytesRemaining = FILE_SIZE - totalBytesRead;
				if (bytesRemaining < chunkSize) {
					chunkSize = bytesRemaining;
					LOG.info("CHUNK_SIZE: {}", chunkSize);
				}
				temporary = new byte[chunkSize];
				int bytesRead = inStream.read(temporary, 0, chunkSize);

				if (bytesRead > 0) {
					totalBytesRead += bytesRead;
					NUMBER_OF_CHUNKS++;
				}
				
				Path path = Paths.get(ApiConstants.TMP_CHUNK + PART_NAME);

				try {
				File chunkFile = Files.write(path, temporary).toFile();
				Chunk chunk = new Chunk(String.valueOf(NUMBER_OF_CHUNKS), chunkFile.getParent(), file.getOriginalFilename(), file.getContentType(), file.getSize(),temporary);
				chunks.add(chunk);
				builder.withChunks(chunks);
				LOG.info("Writing Process Was Performed");
				} catch (FileNotFoundException ex) {
					LOG.error(ex.getLocalizedMessage(), ex);
				} catch (IOException ex) {
					LOG.error(ex.getLocalizedMessage(), ex);
				}
				
				LOG.info("Total Bytes Read: {}", totalBytesRead);
			}

		} catch (FileNotFoundException ex) {
			LOG.error(ex.getLocalizedMessage(), ex);
		} catch (IOException ex) {
			LOG.error(ex.getLocalizedMessage(), ex);
		}
				
		return builder.build();
	}
	
	
	private void sendChunk(Chunk chunk, int totalChunksFromFile) {
		
		final String uri = "http://localhost:9090/backapifinal/v1/post-chunk";     
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("totalChunksFromFile", String.valueOf(totalChunksFromFile));      

		HttpEntity<Chunk> request = new HttpEntity<>(chunk, headers);

//		LOG.info("Sending chunk {} / {}", chunk.getChunkId(), request.getHeaders().get("totalChunksFromFile"));
		
		restTemplate.postForObject(uri, request, Chunk.class);

                
	}
}
