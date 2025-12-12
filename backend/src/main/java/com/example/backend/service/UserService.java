package com.example.backend.service;

import org.springframework.stereotype.Service;

import com.example.backend.dto.request.UserRequest;
import com.example.backend.dto.response.AuthResponse;
import com.example.backend.dto.response.UserResponse;
import com.example.backend.entity.User;
import com.example.backend.exception.BadRequestException;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.repository.UserRepository;
import com.example.backend.security.JwtTokenProvider;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public UserService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * メールアドレスとパスワードでユーザーを認証するメソッド
     * 
     * @param email
     * @param password
     * @return 認証結果
     */
    public AuthResponse authUser(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("メールアドレスまたはパスワードが正しくありません。"));

        boolean passCheck = password.equals(user.getPassword());

        // エラー処理は後ほど実装する
        if (!passCheck) {
            throw new BadRequestException("メールアドレスまたはパスワードが正しくありません。");
        }

        // トークン生成
        String token = jwtTokenProvider.generateToken(user.getId(), user.getEmail());

        UserResponse userResponse = UserResponse.from(user);

        return new AuthResponse(token, userResponse);
    }

    /**
     * idからユーザーを取得するメソッド
     * 
     * @param id user_id
     * @return 取得したユーザー
     */
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        return UserResponse.from(user);
    }

    /**
     * ユーザーを作成するメソッド
     * 
     * @param userRequest フロントエンドからのリクエスト
     * @return 作成したユーザー
     */
    public UserResponse createUser(UserRequest userRequest) {
        boolean isExist = userRepository.existsByEmail(userRequest.getEmail());

        // エラー処理は後で実装
        if (isExist) {
            throw new BadRequestException("メールアドレス " + userRequest.getEmail() + "は既に存在します");
        }

        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setName(userRequest.getName());
        user.setPassword(userRequest.getPassword());

        User savedUser = userRepository.save(user);

        return UserResponse.from(savedUser);
    }

    /**
     * idから取得したユーザーを編集するメソッド
     * 
     * @param userRequest フロントエンドのリクエスト
     * @param id          指定されたid
     * @return 更新したユーザー
     */
    public UserResponse editUserById(UserRequest userRequest, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());

        User savedUser = userRepository.save(user);

        return UserResponse.from(savedUser);
    }

    /**
     * idから取得したユーザーを削除するメソッド
     * 
     * @param id 指定されたid
     */
    public void deleteById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userRepository.deleteById(id);
    }
}
