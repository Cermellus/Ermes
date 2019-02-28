export interface ICreditReason {
    id?: number;
    creditReasonId?: number;
    creditReasonDescription?: string;
    creditReasonCode?: string;
    creditReasonProductRequired?: boolean;
    creditReasonCourierClaim?: boolean;
}

export class CreditReason implements ICreditReason {
    constructor(
        public id?: number,
        public creditReasonId?: number,
        public creditReasonDescription?: string,
        public creditReasonCode?: string,
        public creditReasonProductRequired?: boolean,
        public creditReasonCourierClaim?: boolean
    ) {
        this.creditReasonProductRequired = this.creditReasonProductRequired || false;
        this.creditReasonCourierClaim = this.creditReasonCourierClaim || false;
    }
}
