
package com.example.demo.repository.repositoryInterface;

import com.example.demo.entity.customer_report;
import java.util.List;
import java.util.Optional;

public interface ICustomerReportRepository {
    List<customer_report> findAllReports();
    public boolean insertReport(customer_report report);
    public boolean updateReport(customer_report report);
    public boolean deleteById(int reportId);
    public boolean checkExists(int reportId);
    public Optional<customer_report> findById(int reportId);
    public int getLastReportID();
    public boolean saveResponse(customer_report report);
    public boolean saveSeen(int id);
}
