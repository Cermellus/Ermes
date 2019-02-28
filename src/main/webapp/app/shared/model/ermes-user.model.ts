import { IRole } from 'app/shared/model/role.model';
import { ICompany } from 'app/shared/model/company.model';

export interface IErmesUser {
    id?: number;
    ermesUserId?: number;
    ermesUserUsername?: string;
    ermesUserPassword?: string;
    ermesUserName?: string;
    ermesUserMail?: string;
    roleId?: IRole;
    companyId?: ICompany;
}

export class ErmesUser implements IErmesUser {
    constructor(
        public id?: number,
        public ermesUserId?: number,
        public ermesUserUsername?: string,
        public ermesUserPassword?: string,
        public ermesUserName?: string,
        public ermesUserMail?: string,
        public roleId?: IRole,
        public companyId?: ICompany
    ) {}
}
