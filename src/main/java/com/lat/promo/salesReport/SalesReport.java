package com.lat.promo.salesReport;

import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

public class SalesReport {
    private List<SalesReportElement> elements;

    public SalesReport(List<SalesReportElement> elements) {
        this.elements = elements;
    }

    public List<SalesReportElement> getElements() {
        return elements;
    }
}


