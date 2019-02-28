import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ICreditReferenceType } from 'app/shared/model/credit-reference-type.model';
import { CreditReferenceTypeService } from './credit-reference-type.service';

@Component({
    selector: 'jhi-credit-reference-type-update',
    templateUrl: './credit-reference-type-update.component.html'
})
export class CreditReferenceTypeUpdateComponent implements OnInit {
    creditReferenceType: ICreditReferenceType;
    isSaving: boolean;

    constructor(protected creditReferenceTypeService: CreditReferenceTypeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ creditReferenceType }) => {
            this.creditReferenceType = creditReferenceType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.creditReferenceType.id !== undefined) {
            this.subscribeToSaveResponse(this.creditReferenceTypeService.update(this.creditReferenceType));
        } else {
            this.subscribeToSaveResponse(this.creditReferenceTypeService.create(this.creditReferenceType));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICreditReferenceType>>) {
        result.subscribe((res: HttpResponse<ICreditReferenceType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
