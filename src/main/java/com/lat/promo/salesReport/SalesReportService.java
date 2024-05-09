package com.lat.promo.salesReport;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@Component
public class SalesReportService {
    @PersistenceContext
    private EntityManager entityManager;

    public SalesReportService() {
    }

    public SalesReport generateSalesReport() {
        Query query = entityManager.createQuery("SELECT currency, SUM(price) AS totalRegularPrice, SUM(discount) AS totalDiscount, COUNT(currency) AS numberOfPurchases FROM Purchase GROUP BY currency");
        List<Object[]> rows = query.getResultList();
        List<SalesReportElement> reportElements = new ArrayList<>(rows.size());

        for (Object[] row : rows) {
            reportElements.add(new SalesReportElement((Currency)row[0], (BigDecimal)row[1], (BigDecimal)row[2], (Long)row[3]));
        }

        return new SalesReport(reportElements);
    }
}
