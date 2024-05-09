package com.lat.promo.salesReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/sales-report")
public class SalesReportController {
    private final SalesReportService salesReportService;

    @Autowired
    public SalesReportController(SalesReportService salesReportService) {
        this.salesReportService = salesReportService;
    }

    @GetMapping
    public ResponseEntity<SalesReport> getSalesReport()
    {
        SalesReport report = salesReportService.generateSalesReport();

        return ResponseEntity.ok(report);
    }
}
