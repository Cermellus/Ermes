export interface ICreditReturnType {
    id?: number;
    creditReturnTypeId?: number;
    creditReturnTypeDescription?: string;
    creditReturnTypeArrangeReturn?: boolean;
}

export class CreditReturnType implements ICreditReturnType {
    constructor(
        public id?: number,
        public creditReturnTypeId?: number,
        public creditReturnTypeDescription?: string,
        public creditReturnTypeArrangeReturn?: boolean
    ) {
        this.creditReturnTypeArrangeReturn = this.creditReturnTypeArrangeReturn || false;
    }
}
