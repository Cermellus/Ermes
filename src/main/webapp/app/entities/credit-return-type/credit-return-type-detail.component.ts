import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICreditReturnType } from 'app/shared/model/credit-return-type.model';

@Component({
    selector: 'jhi-credit-return-type-detail',
    templateUrl: './credit-return-type-detail.component.html'
})
export class CreditReturnTypeDetailComponent implements OnInit {
    creditReturnType: ICreditReturnType;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ creditReturnType }) => {
            this.creditReturnType = creditReturnType;
        });
    }

    previousState() {
        window.history.back();
    }
}
