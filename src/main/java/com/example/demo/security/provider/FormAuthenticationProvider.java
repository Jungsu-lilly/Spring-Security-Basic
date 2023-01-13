package com.example.demo.security.provider;

import com.example.demo.security.common.FormWebAuthenticationDetails;
import com.example.demo.security.service.AccountContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;

@Slf4j
public class FormAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public FormAuthenticationProvider(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override   // 검증을 위한 구현이 들어가야 함
    @Transactional
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 파라미터 - Authentication 객체는 AuthenticationManager 로부터 전달받는 인증객체 (아이디, 패스워드 정보 담겨져 있음)
        String username = authentication.getName();
        String password = (String)authentication.getCredentials();

        AccountContext accountContext = (AccountContext)userDetailsService.loadUserByUsername(username);

        if(!passwordEncoder.matches(password, accountContext.getPassword())){
            throw new BadCredentialsException("Invalid Password");
        }

//        // secret key 검증
//        FormWebAuthenticationDetails formWebAuthenticationDetails = (FormWebAuthenticationDetails) authentication.getDetails();
//        String secretKey = formWebAuthenticationDetails.getSecretKey();
//        if(secretKey == null || !"secret".equals(secretKey)){
//            throw new InsufficientAuthenticationException("InsufficientAuthenticationException");
//        }

        // 최종적으로 인증에 성공한 정보를 담은 인증 객체를 만들어서, AuthenticationManager 에게 정볼를 리턴한다.
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(accountContext.getAccount(), null, accountContext.getAuthorities());

        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
