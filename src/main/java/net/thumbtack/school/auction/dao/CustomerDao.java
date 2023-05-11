package net.thumbtack.school.auction.dao;

import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.model.Application;
import net.thumbtack.school.auction.model.Customer;

public interface CustomerDao {
    Customer insert(Customer user) throws ServerException;
    Customer getById(int id) throws ServerException;
    void addApplication(Customer customer, Application application) throws ServerException;
    void deleteApplication(int applicationId) throws ServerException;
    String watchStatusOfApplication(int applicationId) throws ServerException;
}
