export interface ICustomer {
    id?: number;
    customerId?: number;
    customerCode?: string;
    customerName?: string;
    customerInactive?: boolean;
    prospectIdId?: number;
    salesPersonIdId?: number;
}

export class Customer implements ICustomer {
    constructor(
        public id?: number,
        public customerId?: number,
        public customerCode?: string,
        public customerName?: string,
        public customerInactive?: boolean,
        public prospectIdId?: number,
        public salesPersonIdId?: number
    ) {
        this.customerInactive = this.customerInactive || false;
    }
}
