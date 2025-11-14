package com.example.InventoryService.dto.response;



import com.example.InventoryService.enums.ImportStatus;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportResultResponse {
    private boolean success;
    private int totalRows;
    private int successRows;
    private int failedRows;
    private String message;
    private List<String> errors;
    private List<ImportDetailResponse> importDetails;
    // status
    private ImportStatus status;
}
