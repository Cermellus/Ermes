export interface IAppUser {
    id?: number;
    appUserId?: number;
    appUserUsername?: string;
    appUserPassword?: string;
    appUserName?: string;
    appUserMail?: string;
    roleIdId?: number;
    companyIdId?: number;
}

export class AppUser implements IAppUser {
    constructor(
        public id?: number,
        public appUserId?: number,
        public appUserUsername?: string,
        public appUserPassword?: string,
        public appUserName?: string,
        public appUserMail?: string,
        public roleIdId?: number,
        public companyIdId?: number
    ) {}
}
