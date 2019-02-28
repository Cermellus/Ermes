import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ICreditRequestLine } from 'app/shared/model/credit-request-line.model';

@Component({
    selector: 'jhi-credit-request-line-detail',
    templateUrl: './credit-request-line-detail.component.html'
})
export class CreditRequestLineDetailComponent implements OnInit {
    creditRequestLine: ICreditRequestLine;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ creditRequestLine }) => {
            this.creditRequestLine = creditRequestLine;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
