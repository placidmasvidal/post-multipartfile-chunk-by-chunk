package com.backapifinal.v1.entities;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class ReceivedFile implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6224229235218587222L;

	private String fileId;

	private String fileName;

	private String fileType;

	private byte[] fileData;
	
	private List<Chunk> chunks;
	
	public ReceivedFile() {}

	public ReceivedFile(String fileId, String fileName, String fileType, byte[] fileData, List<Chunk> chunks) {
		super();
		this.fileId = fileId;
		this.fileName = fileName;
		this.fileType = fileType;
		this.fileData = fileData;
		this.chunks = chunks;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public byte[] getFileData() {
		return fileData;
	}

	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}

	public List<Chunk> getChunks() {
		return chunks;
	}

	public void setChunks(List<Chunk> chunks) {
		this.chunks = chunks;
	}

	@Override
	public String toString() {
		return "ReceivedFile [fileId=" + fileId + ", fileName=" + fileName + ", fileType=" + fileType + ", fileData="
				+ Arrays.toString(fileData) + ", chunks=" + chunks + "]";
	}
	
	
}
