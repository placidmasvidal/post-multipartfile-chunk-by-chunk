package com.backapifinal.v1.services;

import org.springframework.http.ResponseEntity;

import com.backapifinal.v1.entities.Chunk;
import com.backapifinal.v1.entities.ReceivedFile;

@org.springframework.stereotype.Service
public interface ReceivedFileService {

//    ResponseEntity<?> update(ReceivedFile file) throws Throwable;

	void createReceivedFile(Chunk chunk, Integer totalChunksFromFile);

}