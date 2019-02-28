export interface ICreditReferenceType {
    id?: number;
    creditReferenceTypeId?: number;
    creditReferenceTypeDescription?: string;
}

export class CreditReferenceType implements ICreditReferenceType {
    constructor(public id?: number, public creditReferenceTypeId?: number, public creditReferenceTypeDescription?: string) {}
}
