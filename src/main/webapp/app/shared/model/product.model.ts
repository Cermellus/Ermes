export interface IProduct {
    id?: number;
    productId?: number;
    productCode?: string;
    productDescription?: string;
    productInactive?: boolean;
}

export class Product implements IProduct {
    constructor(
        public id?: number,
        public productId?: number,
        public productCode?: string,
        public productDescription?: string,
        public productInactive?: boolean
    ) {
        this.productInactive = this.productInactive || false;
    }
}
