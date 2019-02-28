import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICreditReferenceType } from 'app/shared/model/credit-reference-type.model';

@Component({
    selector: 'jhi-credit-reference-type-detail',
    templateUrl: './credit-reference-type-detail.component.html'
})
export class CreditReferenceTypeDetailComponent implements OnInit {
    creditReferenceType: ICreditReferenceType;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ creditReferenceType }) => {
            this.creditReferenceType = creditReferenceType;
        });
    }

    previousState() {
        window.history.back();
    }
}
