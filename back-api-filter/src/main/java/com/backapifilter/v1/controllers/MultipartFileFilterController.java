package com.backapifilter.v1.controllers;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backapifilter.v1.service.MultipartFileFilterService;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("backapifilter/v1")
public class MultipartFileFilterController {
	
	private final static Logger LOG = LoggerFactory.getLogger(MultipartFileFilterController.class);
	
	private MultipartFileFilterService multipartFileFilterService;
	
	@Autowired
	public MultipartFileFilterController(MultipartFileFilterService multipartFileFilterService) {
		this.multipartFileFilterService = multipartFileFilterService;
	}
	
	@ApiOperation(value = "Filter received file")
    @PostMapping("/multipartfile")
	public void filterFile(@RequestParam("multipartfile") MultipartFile file) throws IOException {
		
		/*request = */multipartFileFilterService.saveAndChunkFile(file);
		
/*		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION)
				.body(null);*/

    }
    

}