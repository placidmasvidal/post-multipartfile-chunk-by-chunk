package com.backapifinal.v1.entities;

import java.io.Serializable;

public class Chunk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8989442380402399849L;

	private String chunkId;
	
	private String parentDirectory;
	
	private String originalFileName;
	
	private String originalFileType;

	private Long fileSize;
	
	private final byte[] data;
	
	public Chunk(String chunkId, String parentDirectory, String chunkFileName, String originalFileType, Long fileSize,byte[] data) {
		super();
		this.chunkId = chunkId;
		this.parentDirectory = parentDirectory;
		this.originalFileName = chunkFileName;
		this.originalFileType = originalFileType;
		this.data = data;
		this.fileSize = fileSize;
	}

	public String getChunkId() {
		return chunkId;
	}

	public void setChunkId(String chunkId) {
		this.chunkId = chunkId;
	}

	public String getParentDirectory() {
		return parentDirectory;
	}

	public void setParentDirectory(String parentDirectory) {
		this.parentDirectory = parentDirectory;
	}

	public String getFileName() {
		return originalFileName;
	}

	public void setFileName(String fileName) {
		this.originalFileName = fileName;
	}

	public byte[] getData() {
		return data;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public String getOriginalFileType() {
		return originalFileType;
	}

	public void setOriginalFileType(String originalFileType) {
		this.originalFileType = originalFileType;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
}
