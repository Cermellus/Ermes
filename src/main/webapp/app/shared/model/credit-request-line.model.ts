export interface ICreditRequestLine {
    id?: number;
    creditRequestLineId?: number;
    creditRequestLineAmount?: number;
    creditRequestLineQtyCredited?: number;
    creditRequestLineQtyReturn?: number;
    creditRequestLineComment?: string;
    creditRequestLineImageContentType?: string;
    creditRequestLineImage?: any;
    productIdId?: number;
    creditReasonIdId?: number;
    creditReturnTypeIdId?: number;
    creditRequestIdId?: number;
}

export class CreditRequestLine implements ICreditRequestLine {
    constructor(
        public id?: number,
        public creditRequestLineId?: number,
        public creditRequestLineAmount?: number,
        public creditRequestLineQtyCredited?: number,
        public creditRequestLineQtyReturn?: number,
        public creditRequestLineComment?: string,
        public creditRequestLineImageContentType?: string,
        public creditRequestLineImage?: any,
        public productIdId?: number,
        public creditReasonIdId?: number,
        public creditReturnTypeIdId?: number,
        public creditRequestIdId?: number
    ) {}
}
