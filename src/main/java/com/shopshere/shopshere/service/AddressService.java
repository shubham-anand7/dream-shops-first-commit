package com.shopshere.shopshere.service;

import com.shopshere.shopshere.entity.Address;

import java.util.List;

public interface AddressService {
    Address addAddress(Long userId, Address address);
    List<Address> getUserAddresses(Long userId);
    void deleteAddress(Long addressId);
}