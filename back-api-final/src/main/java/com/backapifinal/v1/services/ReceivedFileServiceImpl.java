package com.backapifinal.v1.services;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.backapifinal.v1.entities.Chunk;
import com.backapifinal.v1.entities.ReceivedFile;

@Service
public class ReceivedFileServiceImpl implements ReceivedFileService {

	private static final Logger LOG = LoggerFactory.getLogger(ReceivedFileServiceImpl.class);

	private List<Chunk> chunks;

	@Autowired
	public ReceivedFileServiceImpl() {
		chunks = new ArrayList<>();
	}

	@Override
	public void createReceivedFile(Chunk chunk, Integer totalChunksFromFile) {

		ReceivedFile wholeFile = null;
		
		if (Integer.parseInt(chunk.getChunkId()) <= totalChunksFromFile) {
			LOG.info("Adding chunk with size of {} bytes", chunk.getData().length);
			chunks.add(chunk);
		}
		try {
			wholeFile = mergeParts(chunks, chunk.getFileSize().intValue(), "tmp/merged/" + chunks.get(0).getOriginalFileName());
		} catch (Throwable e) {
			LOG.error("Couldn't create file, exception is: {}", e.getLocalizedMessage());
		}

		
	}

	private /* ResponseEntity<?> */ ReceivedFile mergeParts(List<Chunk> chunkList, Integer fileSize,String destinationPath)
			throws Throwable {

		ResponseEntity<?> out = new ResponseEntity<>(HttpStatus.OK);

		File[] file = new File[chunkList.size()];
		byte[] allFilesContent = null;
		int TOTAL_SIZE = 0;
		int FILE_NUMBER = chunkList.size();
		int FILE_LENGTH = 0;
		int CURRENT_LENGTH = 0;

		
		for (int i = 0; i < FILE_NUMBER; i++) {
			file[i] = Files.write(Paths.get(chunkList.get(i).getFileName()), chunkList.get(i).getData()).toFile();
			TOTAL_SIZE = (int) ((long) TOTAL_SIZE + file[i].length());
		}
		 

		try {
			allFilesContent = new byte[TOTAL_SIZE];
			InputStream inStream = null;

			for (int j = 0; j < FILE_NUMBER; j++) {
				inStream = new BufferedInputStream(new FileInputStream(file[j]));
				FILE_LENGTH = (int) file[j].length();
				inStream.read(allFilesContent, CURRENT_LENGTH, FILE_LENGTH);
				CURRENT_LENGTH += FILE_LENGTH;
				inStream.close();
			}
		} catch (FileNotFoundException var14) {
			out = new ResponseEntity<>(HttpStatus.NOT_FOUND);
//			LOG.error("File not found {}", var14);
//			LOG.debug(var14.getCause().getMessage());
		} catch (IOException var15) {
			out = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			LOG.error("Exception while reading the file " + var15);
		} finally {
//            write(AllFilesContent, destinationPath);
//			Path path = Paths.get(destinationPath);
			try {
				Files.write(Paths.get(destinationPath), allFilesContent);
			} catch (FileNotFoundException var15) {
				LOG.error(var15.getLocalizedMessage());
			} catch (IOException var16) {
				LOG.error(var16.getLocalizedMessage());
			}
		}

		return new ReceivedFile(chunks.get(0).getOriginalFileName(), chunks.get(0).getOriginalFileName(),
				chunks.get(0).getOriginalFileType(), allFilesContent, chunks);

//		return out;
	}

	/*
	 * private void write(byte[] DataByteArray, String DestinationFileName) throws
	 * Throwable {
	 * 
	 * try { Throwable var2 = null; Object var3 = null;
	 * 
	 * try { BufferedOutputStream output = new BufferedOutputStream(new
	 * FileOutputStream(DestinationFileName));
	 * 
	 * try { output.write(DataByteArray);
	 * System.out.println("Writing Process Was Performed"); } finally { if (output
	 * != null) { output.close(); }
	 * 
	 * } } catch (Throwable var14) { if (var2 == null) { var2 = var14; } else if
	 * (var2 != var14) { var2.addSuppressed(var14); }
	 * 
	 * throw var2; } } catch (FileNotFoundException var15) {
	 * var15.printStackTrace(); } catch (IOException var16) {
	 * var16.printStackTrace(); }
	 * 
	 * }
	 */
}
