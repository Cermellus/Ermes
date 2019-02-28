export interface IRole {
    id?: number;
    roleId?: number;
    roleDescription?: string;
    roleApprove?: boolean;
    roleInsert?: boolean;
    roleEdit?: boolean;
    roleReturn?: boolean;
    roleProcess?: boolean;
    roleSeeAllRquests?: boolean;
    roleCourierClaim?: boolean;
    roleComment?: string;
}

export class Role implements IRole {
    constructor(
        public id?: number,
        public roleId?: number,
        public roleDescription?: string,
        public roleApprove?: boolean,
        public roleInsert?: boolean,
        public roleEdit?: boolean,
        public roleReturn?: boolean,
        public roleProcess?: boolean,
        public roleSeeAllRquests?: boolean,
        public roleCourierClaim?: boolean,
        public roleComment?: string
    ) {
        this.roleApprove = this.roleApprove || false;
        this.roleInsert = this.roleInsert || false;
        this.roleEdit = this.roleEdit || false;
        this.roleReturn = this.roleReturn || false;
        this.roleProcess = this.roleProcess || false;
        this.roleSeeAllRquests = this.roleSeeAllRquests || false;
        this.roleCourierClaim = this.roleCourierClaim || false;
    }
}
