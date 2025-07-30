package com.mt.pharmacy_be.service.impl;

import com.mt.pharmacy_be.dto.userDTO.UserRequestDTO;
import com.mt.pharmacy_be.dto.userDTO.UserResponseDTO;
import com.mt.pharmacy_be.entity.*;
import com.mt.pharmacy_be.enums.ErrorCode;
import com.mt.pharmacy_be.enums.RoleType;
import com.mt.pharmacy_be.exception.ApiException;
import com.mt.pharmacy_be.mapper.UserMapper;
import com.mt.pharmacy_be.repository.*;
import com.mt.pharmacy_be.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    UserRoleRepository userRoleRepository;
    RoleRepository roleRepository;
    EmployeeRepository employeeRepository;
    CustomerRepository customerRepository;

    @Override
    public Object getAllUser(int page, int pageSize) {
        return null;
    }

    /**
     * Function: Registers a new user and assigns specified roles.
     * Author: Thanh Truc
     * Date: 16/07/2025
     * Description: Validates username uniqueness, encodes password, saves user,
     * resolves and associates roles, then returns user information.
     */
    @Override
    public UserResponseDTO register(UserRequestDTO request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ApiException(ErrorCode.USERNAME_EXISTS);
        }

        UserEntity user = userMapper.toUserEntity(request);
        user.setFlagDeleted(false);
        user.setFlagOnline(true);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        UserEntity savedUser = userRepository.save(user);

        List<RoleEntity> resolvedRoles = resolveRoles(request.getRoles());
        associateRolesWithUser(savedUser, resolvedRoles);

        if (resolvedRoles.stream().allMatch(role -> role.getName().equals(RoleType.CUSTOMER))) {
            saveCustomerEntity(savedUser);
        } else {
            saveEmployeeEntity(savedUser);
        }

        UserResponseDTO userMap = userMapper.toUserResponseDTO(savedUser);
        userMap.setRoles(resolvedRoles.stream()
                .map(role -> role.getName().toString())
                .collect(Collectors.joining(", ")));

        return userMap;
    }

    /**
     * Function: Resolves role entities from the provided role names.
     * Author: Thanh Truc
     * Date: 16/07/2025
     * Description: Finds roles by name from the database or throws an exception if any role is not found.
     */
    private List<RoleEntity> resolveRoles(List<RoleType> roleNames) {
        return roleNames.stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new ApiException(ErrorCode.ROLE_NOT_FOUND)))
                .collect(Collectors.toList());
    }

    /**
     * Function: Associates the given roles with the specified user.
     * Author: Thanh Truc
     * Date: 16/07/2025
     * Description: Creates and saves UserRoleEntity instances to link user and roles in the database.
     */
    private void associateRolesWithUser(UserEntity user, List<RoleEntity> roles) {
        roles.forEach(role -> {
            UserRoleEntity userHasRole = new UserRoleEntity();
            userHasRole.setUserEntity(user);
            userHasRole.setRoleEntity(role);
            userRoleRepository.save(userHasRole);
        });
    }

    /**
     * Function: Saves a new CustomerEntity linked to the user.
     * Author: Thanh Truc
     * Date: 18/07/2025
     * Description: Creates a CustomerEntity with the user and sets it as not deleted.
     */
    private void saveCustomerEntity(UserEntity user) {
        CustomerEntity customerEntity = CustomerEntity.builder()
                .userEntity(user)
                .flagDeleted(false)
                .build();
        customerRepository.save(customerEntity);
    }

    /**
     * Function: Saves a new EmployeeEntity linked to the user.
     * Author: Thanh Truc
     * Date: 18/07/2025
     * Description: Creates an EmployeeEntity with the user and sets it as not deleted.
     */
    private void saveEmployeeEntity(UserEntity user) {
        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .userEntity(user)
                .flagDeleted(false)
                .build();
        employeeRepository.save(employeeEntity);
    }
}
