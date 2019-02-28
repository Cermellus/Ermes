import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICreditRequest } from 'app/shared/model/credit-request.model';

@Component({
    selector: 'jhi-credit-request-detail',
    templateUrl: './credit-request-detail.component.html'
})
export class CreditRequestDetailComponent implements OnInit {
    creditRequest: ICreditRequest;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ creditRequest }) => {
            this.creditRequest = creditRequest;
        });
    }

    previousState() {
        window.history.back();
    }
}
