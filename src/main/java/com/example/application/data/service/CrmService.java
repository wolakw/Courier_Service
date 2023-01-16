package com.example.application.data.service;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.Contact;
import com.example.application.data.entity.Package;
import com.example.application.data.entity.Status;
import com.example.application.data.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service 
public class CrmService {

    private final ContactRepository contactRepository;
    private final CompanyRepository companyRepository;
    private final StatusRepository statusRepository;
    private final CourierRepository courierRepository;
    private final PackageRepository packageRepository;

    public CrmService(ContactRepository contactRepository,
                      CompanyRepository companyRepository,
                      StatusRepository statusRepository,
                      CourierRepository courierRepository,
                      PackageRepository packageRepository) {
        this.contactRepository = contactRepository;
        this.companyRepository = companyRepository;
        this.statusRepository = statusRepository;
        this.courierRepository = courierRepository;
        this.packageRepository = packageRepository;
    }

    public List<Contact> findAllContacts(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) { 
            return contactRepository.findAll();
        } else {
            return contactRepository.search(stringFilter);
        }
    }

    public long countContacts() {
        return contactRepository.count();
    }

    public void deleteContact(Contact contact) {
        contactRepository.delete(contact);
    }

    public void saveContact(Contact contact) {
        if (contact == null) { 
            System.err.println("Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        contactRepository.save(contact);
    }

    public long countPackage() {
        return packageRepository.count();
    }

    public void deletePackage(Package pack) {
        packageRepository.delete(pack);
    }

    public void savePackage(Package pack) {
        if (pack == null) {
            System.err.println("Package is null. Are you sure you have connected your form to the application?");
            return;
        }
        packageRepository.save(pack);
    }

    public List<Company> findAllCompanies() {
        return companyRepository.findAll();
    }

    public List<Status> findAllStatuses(){
        return statusRepository.findAll();
    }

    public List<Package> findAllPackages(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return packageRepository.findAll();
        } else {
            return packageRepository.search(stringFilter);
        }
    }
}