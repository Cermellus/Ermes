import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ICreditRequestStatus } from 'app/shared/model/credit-request-status.model';
import { CreditRequestStatusService } from './credit-request-status.service';

@Component({
    selector: 'jhi-credit-request-status-update',
    templateUrl: './credit-request-status-update.component.html'
})
export class CreditRequestStatusUpdateComponent implements OnInit {
    creditRequestStatus: ICreditRequestStatus;
    isSaving: boolean;

    constructor(protected creditRequestStatusService: CreditRequestStatusService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ creditRequestStatus }) => {
            this.creditRequestStatus = creditRequestStatus;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.creditRequestStatus.id !== undefined) {
            this.subscribeToSaveResponse(this.creditRequestStatusService.update(this.creditRequestStatus));
        } else {
            this.subscribeToSaveResponse(this.creditRequestStatusService.create(this.creditRequestStatus));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICreditRequestStatus>>) {
        result.subscribe((res: HttpResponse<ICreditRequestStatus>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
