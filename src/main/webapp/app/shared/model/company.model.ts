export interface ICompany {
    id?: number;
    companyId?: number;
    companyName?: string;
    companyFtpFolder?: string;
}

export class Company implements ICompany {
    constructor(public id?: number, public companyId?: number, public companyName?: string, public companyFtpFolder?: string) {}
}
