import { Moment } from 'moment';

export interface ICreditRequest {
    id?: number;
    creditRequestId?: number;
    creditRequestDate?: Moment;
    creditRequestCode?: string;
    creditRequestReference?: string;
    creditRequestContactMail?: string;
    creditRequestRejectReason?: string;
    customerIdId?: number;
    creditReferenceTypeIdId?: number;
    creditRequestStatusIdId?: number;
    appUserIdId?: number;
}

export class CreditRequest implements ICreditRequest {
    constructor(
        public id?: number,
        public creditRequestId?: number,
        public creditRequestDate?: Moment,
        public creditRequestCode?: string,
        public creditRequestReference?: string,
        public creditRequestContactMail?: string,
        public creditRequestRejectReason?: string,
        public customerIdId?: number,
        public creditReferenceTypeIdId?: number,
        public creditRequestStatusIdId?: number,
        public appUserIdId?: number
    ) {}
}
