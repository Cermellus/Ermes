export interface IContact {
    id?: number;
    contactId?: number;
    contactCode?: string;
    contactName?: string;
    contactMail?: string;
    contactInactive?: boolean;
    customerIdId?: number;
}

export class Contact implements IContact {
    constructor(
        public id?: number,
        public contactId?: number,
        public contactCode?: string,
        public contactName?: string,
        public contactMail?: string,
        public contactInactive?: boolean,
        public customerIdId?: number
    ) {
        this.contactInactive = this.contactInactive || false;
    }
}
