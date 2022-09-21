package com.codecool.shop.service;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;

import java.util.List;

public class SupplierService {
    private final SupplierDao SUPPLIER_DAO;

    public SupplierService(SupplierDao supplier_dao) {
        SUPPLIER_DAO = supplier_dao;
    }

    public Supplier getSupplier(int id){
        return SUPPLIER_DAO.find(id);
    }

    public List<Supplier> getAllSuppliers(){
        return SUPPLIER_DAO.getAll();
    }
}
