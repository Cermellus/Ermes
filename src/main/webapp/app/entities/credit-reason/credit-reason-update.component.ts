import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ICreditReason } from 'app/shared/model/credit-reason.model';
import { CreditReasonService } from './credit-reason.service';

@Component({
    selector: 'jhi-credit-reason-update',
    templateUrl: './credit-reason-update.component.html'
})
export class CreditReasonUpdateComponent implements OnInit {
    creditReason: ICreditReason;
    isSaving: boolean;

    constructor(protected creditReasonService: CreditReasonService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ creditReason }) => {
            this.creditReason = creditReason;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.creditReason.id !== undefined) {
            this.subscribeToSaveResponse(this.creditReasonService.update(this.creditReason));
        } else {
            this.subscribeToSaveResponse(this.creditReasonService.create(this.creditReason));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICreditReason>>) {
        result.subscribe((res: HttpResponse<ICreditReason>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
