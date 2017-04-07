package com.pervazive.kheddah.service.mapper;

import com.pervazive.kheddah.domain.Authority;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PAProject;
import com.pervazive.kheddah.domain.User;
import com.pervazive.kheddah.service.dto.UserDTO;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper for the entity User and its DTO UserDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserMapper {

    UserDTO userToUserDTO(User user);

    List<UserDTO> usersToUserDTOs(List<User> users);

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "persistentTokens", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activationKey", ignore = true)
    @Mapping(target = "resetKey", ignore = true)
    @Mapping(target = "resetDate", ignore = true)
    @Mapping(target = "password", ignore = true)
    User userDTOToUser(UserDTO userDTO);

    List<User> userDTOsToUsers(List<UserDTO> userDTOs);

    default User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }

    default Set<String> stringsFromAuthorities (Set<Authority> authorities) {
        return authorities.stream().map(Authority::getName)
            .collect(Collectors.toSet());
    }

    default Set<Authority> authoritiesFromStrings(Set<String> strings) {
        return strings.stream().map(string -> {
            Authority auth = new Authority();
            auth.setName(string);
            return auth;
        }).collect(Collectors.toSet());
    }
    
    default Set<String> stringsFromPaOrganizations (Set<PAOrganization> paOrganizations) {
        return paOrganizations.stream().map(PAOrganization::getOrganization)
            .collect(Collectors.toSet());
    }

    default Set<PAOrganization> paOrganizationsFromStrings(Set<String> strings) {
        return strings.stream().map(string -> {
        	PAOrganization paOrganization = new PAOrganization();
        	paOrganization.setOrganization(string);
            return paOrganization;
        }).collect(Collectors.toSet());
    }
    
    default Set<String> stringsFromPaProjects (Set<PAProject> paProjects) {
        return paProjects.stream().map(PAProject::getProjectname)
            .collect(Collectors.toSet());
    }

    default Set<PAProject> paProjectsFromStrings(Set<String> strings) {
        return strings.stream().map(string -> {
        	PAProject paProject = new PAProject();
        	paProject.setProjectname(string);
            return paProject;
        }).collect(Collectors.toSet());
    }
}
