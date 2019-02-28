export interface ICreditRequestStatus {
    id?: number;
    creditRequestStatusId?: number;
    creditRequestStatusDescription?: string;
    creditRequestStatusExport?: boolean;
}

export class CreditRequestStatus implements ICreditRequestStatus {
    constructor(
        public id?: number,
        public creditRequestStatusId?: number,
        public creditRequestStatusDescription?: string,
        public creditRequestStatusExport?: boolean
    ) {
        this.creditRequestStatusExport = this.creditRequestStatusExport || false;
    }
}
