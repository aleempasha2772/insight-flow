package com.example.insight_flow.Model;

public enum FileType {
	CSV("csv"),
    XLSX("xlsx"),
    XLS("xls");
    
    private final String extension;
    
    FileType(String extension) {
        this.extension = extension;
    }
    
    public String getExtension() {
        return extension;
    }
    
    public static FileType fromExtension(String extension) {
        for (FileType type : values()) {
            if (type.extension.equalsIgnoreCase(extension)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unsupported file type: " + extension);
    }
}
