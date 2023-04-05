package com.shop.config;


import com.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity //SpringSecurityFilterChain 자동 포함.
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final MemberService memberService;

    @Override
    protected void configure(HttpSecurity http) throws Exception { //http 요청에 대한 보안 설정.
        http.formLogin()
                .loginPage("/members/login") //로그인 페이지 url
                .defaultSuccessUrl("/")  //로그인 성공시 이동할 url
                .usernameParameter("email")  //로그인시 사용할 파라미터
                .failureUrl("/members/login/error")  //로그인 실패시 이동할 url
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout")) //로그아웃 url
                .logoutSuccessUrl("/") //로그아웃 성공시 이동할 url
        ;

        http.authorizeRequests() //시큐리티 처리에 HttpServletRequest 를 사용
//                .mvcMatchers("/css/**", "/js/**", "/img/**").permitAll()
                .mvcMatchers("/", "/members/**", "/item/**", "/images/**").permitAll() //인증 없이 접근 가능한 경로
                .mvcMatchers("/admin/**").hasRole("ADMIN") // /admin으로 시작하는 경로는 ADMIN Role일 경우에만 접근 가능
                .anyRequest().authenticated() //나머지 경로는 모두 인증을 요구
        ;

        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()); //인증되지 않은 사용자가 리소스에 접근하였을 때 수행되는 핸들러
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**"); //static 디렉터리의 하위 파일은 인증을 무시.
    }

    @Bean
    public PasswordEncoder passwordEncoder() { //비밀번호 암호화
        return new BCryptPasswordEncoder();
    }

    @Override
    //인증은 AuthenticationManager 를 통해 이루어지며 AuthenticationManagerBuilder 가 AuthenticationManager 를 생성한다.
    //userDetailService 를 구현하고 있는 객체로 memberService 를 지정해주며, 비밀번호 암호화를 위해 passwordEncoder를 지정해준다.
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }
}

