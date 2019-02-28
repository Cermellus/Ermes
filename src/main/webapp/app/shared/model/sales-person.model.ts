export interface ISalesPerson {
    id?: number;
    salesPersonId?: number;
    salesPersonCode?: string;
    salesPersonName?: string;
    salesPersonMail?: string;
    salesPersonInactive?: boolean;
}

export class SalesPerson implements ISalesPerson {
    constructor(
        public id?: number,
        public salesPersonId?: number,
        public salesPersonCode?: string,
        public salesPersonName?: string,
        public salesPersonMail?: string,
        public salesPersonInactive?: boolean
    ) {
        this.salesPersonInactive = this.salesPersonInactive || false;
    }
}
