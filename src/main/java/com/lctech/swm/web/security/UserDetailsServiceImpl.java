package com.lctech.swm.web.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lctech.swm.domain.Account;
import com.lctech.swm.domain.RoleMapping;
import com.lctech.swm.service.AccountService;
import com.lctech.swm.service.RoleMappingService;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	@Autowired
	private AccountService as;
	@Autowired
	private RoleMappingService rms;
	
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account ac  = as.findByUsername(username);
        if (ac == null) {
            throw new UsernameNotFoundException("User not found");
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
      
    	List<RoleMapping> lrm = rms.findByGroupRole(ac.getGroupRole().getId());
    
    	log.info("User " + username + " has been Granted Authorities:");
    	for(RoleMapping rm : lrm){
    		log.info(rm.getRole().getCode() + " - " + rm.getRole().getName());
    		grantedAuthorities.add(new SimpleGrantedAuthority(rm.getRole().getCode()));
    	}        

        return new org.springframework.security.core.userdetails.User(
                ac.getUsername(), ac.getPassword(), grantedAuthorities);
    }

}