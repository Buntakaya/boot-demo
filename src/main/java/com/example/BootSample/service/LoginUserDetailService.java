package com.example.BootSample.service;

import com.example.BootSample.config.UserDetails;
import com.example.BootSample.entity.UserInfoEntity;
import com.example.BootSample.mapper.UserInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginUserDetailService implements UserDetailsService {

    private final UserInfoMapper userInfoMapper;

    /**
     * idで検索し、ユーザーが見つからなかった場合は例外を発生させる
     * 見つかった場合はUserDetailsクラスを返却する
     *
     * @param id id
     * @return UserDetails
     * @throws UsernameNotFoundException 例外
     */
    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Optional<UserInfoEntity> _user = userInfoMapper.selectById(id);
        return _user.map(UserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("not found userId=" + id));
    }
}
