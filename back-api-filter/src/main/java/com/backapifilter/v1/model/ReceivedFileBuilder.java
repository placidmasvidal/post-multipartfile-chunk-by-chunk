package com.backapifilter.v1.model;

import java.util.List;

public interface ReceivedFileBuilder {

	ReceivedFileBuilder withFileId(String fileId);

	ReceivedFileBuilder withFileName(String fileName);
	
	ReceivedFileBuilder withFileType(String fileType);
	
	ReceivedFileBuilder withFileData(byte[] fileData);
	
	ReceivedFileBuilder withChunks(List<Chunk> chunks);
	
	ReceivedFile build();

	ReceivedFile getReceivedFile();
		
}
