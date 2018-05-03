package com.lctech.swm.service;

import java.util.ArrayList;
import java.util.List;

import com.lctech.swm.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lctech.swm.config.Constants;
import com.lctech.swm.domain.Account;
import com.lctech.swm.domain.Users;
import com.lctech.swm.repository.UsersRepository;

@Service
public class UsersService {
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    ContractRepository contractRepository;

    public Page<Users> findAll(Pageable pageable) {
        return usersRepository.findAll(pageable);
    }

    public Page<Users> load(String code, Long regionId, Long districtId,
                            Long provinceId, Pageable pageable) {
        return usersRepository.load(code, regionId, districtId, provinceId,
                pageable);
    }

    public Users findById(Long id) {
        return usersRepository.findOne(id);
    }

    /**
     * @param device
     */
    public void save(Users users) {
        usersRepository.save(users);
    }

    /**
     * @param id
     */
    public void delete(List<Long> ids) {
        usersRepository.deleteLst(ids);
    }

    /**
     * Look for users within the scope of current user.
     *
     * @param account has logged in
     * @return If user is admin, get all user.
     *         If user is a company, get all user who have signed contracts with company.
     *         Otherwise, return current user has logged in.
     */
    public List<Users> findByAccount(Account account) {

        List<Users> users;
        if (account.getLevel() == Constants.LEVEL_TYPE_ADMIN) {
            users = usersRepository.findAll();
        } else if (account.getLevel() == Constants.LEVEL_TYPE_COMPANY) {
            users = usersRepository.findByCompany(account.getCompany().getId());
        } else {
            users = new ArrayList<>();
            users.add(account.getUser());
        }
        return users;
    }
}
