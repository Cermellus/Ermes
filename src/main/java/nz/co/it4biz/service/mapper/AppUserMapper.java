package nz.co.it4biz.service.mapper;

import nz.co.it4biz.domain.*;
import nz.co.it4biz.service.dto.AppUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AppUser and its DTO AppUserDTO.
 */
@Mapper(componentModel = "spring", uses = {RoleMapper.class, CompanyMapper.class})
public interface AppUserMapper extends EntityMapper<AppUserDTO, AppUser> {

    @Mapping(source = "roleId.id", target = "roleIdId")
    @Mapping(source = "companyId.id", target = "companyIdId")
    AppUserDTO toDto(AppUser appUser);

    @Mapping(source = "roleIdId", target = "roleId")
    @Mapping(source = "companyIdId", target = "companyId")
    AppUser toEntity(AppUserDTO appUserDTO);

    default AppUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        AppUser appUser = new AppUser();
        appUser.setId(id);
        return appUser;
    }
}
