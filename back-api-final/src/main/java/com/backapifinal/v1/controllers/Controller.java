package com.backapifinal.v1.controllers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backapifinal.v1.entities.Chunk;
import com.backapifinal.v1.services.ReceivedFileService;

@RestController
@RequestMapping("backapifinal/v1")
public class Controller {

	private static final Logger LOG = LoggerFactory.getLogger(Controller.class);
	
    private final ReceivedFileService receivedFileService;

    @Autowired
    public Controller(ReceivedFileService receivedFileService) {
        this.receivedFileService = receivedFileService;
    }


    @PostMapping("/post-chunk")
    public void receiveChunk(@RequestBody Chunk chunk, HttpServletRequest request) throws Throwable {
 //   	LOG.info("received chunk {} / {}" , chunk.getChunkId(), request.getHeader("totalChunksFromFile"));
    	
    	receivedFileService.createReceivedFile(chunk, Integer.valueOf(request.getHeader("totalChunksFromFile")));
    	
    }
    
}
