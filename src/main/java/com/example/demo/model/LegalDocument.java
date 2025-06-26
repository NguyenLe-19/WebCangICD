package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LegalDocument {
	 private String code;
	    private String date;
	    private String issuerKey; // dùng key i18n để dịch nơi ban hành
	    private String summaryKey; // key i18n cho trích yếu
}
