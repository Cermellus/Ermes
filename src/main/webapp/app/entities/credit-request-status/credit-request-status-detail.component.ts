import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICreditRequestStatus } from 'app/shared/model/credit-request-status.model';

@Component({
    selector: 'jhi-credit-request-status-detail',
    templateUrl: './credit-request-status-detail.component.html'
})
export class CreditRequestStatusDetailComponent implements OnInit {
    creditRequestStatus: ICreditRequestStatus;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ creditRequestStatus }) => {
            this.creditRequestStatus = creditRequestStatus;
        });
    }

    previousState() {
        window.history.back();
    }
}
