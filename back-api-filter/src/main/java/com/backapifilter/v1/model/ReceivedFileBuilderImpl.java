package com.backapifilter.v1.model;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ReceivedFileBuilderImpl implements ReceivedFileBuilder{

	private String fileId;

	private String fileName;

	private String fileType;

	private byte[] fileData;
	
	private List<Chunk> chunks;
	
	private ReceivedFile receivedFile;
	
	
	@Override
	public ReceivedFileBuilder withFileId(String fileId) {
		this.fileId = fileId;
		return this;		
	}

	@Override
	public ReceivedFileBuilder withFileName(String fileName) {
		this.fileName = fileName;
		return this;
	}

	@Override
	public ReceivedFileBuilder withFileType(String fileType) {
		this.fileType = fileType;
		return this;
	}

	@Override
	public ReceivedFileBuilder withFileData(byte[] fileData) {
		this.fileData = fileData;
		return this;
	}

	@Override
	public ReceivedFileBuilder withChunks(List<Chunk> chunks) {
		this.chunks = chunks;
		return this;
	}

	@Override
	public ReceivedFile build() {
		receivedFile = new ReceivedFile(fileId, fileName, fileType, fileData, chunks);
		return receivedFile;
	}

	@Override
	public ReceivedFile getReceivedFile() {
		return receivedFile;
	}
	

}
