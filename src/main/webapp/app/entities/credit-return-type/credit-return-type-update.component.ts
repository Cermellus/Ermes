import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ICreditReturnType } from 'app/shared/model/credit-return-type.model';
import { CreditReturnTypeService } from './credit-return-type.service';

@Component({
    selector: 'jhi-credit-return-type-update',
    templateUrl: './credit-return-type-update.component.html'
})
export class CreditReturnTypeUpdateComponent implements OnInit {
    creditReturnType: ICreditReturnType;
    isSaving: boolean;

    constructor(protected creditReturnTypeService: CreditReturnTypeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ creditReturnType }) => {
            this.creditReturnType = creditReturnType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.creditReturnType.id !== undefined) {
            this.subscribeToSaveResponse(this.creditReturnTypeService.update(this.creditReturnType));
        } else {
            this.subscribeToSaveResponse(this.creditReturnTypeService.create(this.creditReturnType));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICreditReturnType>>) {
        result.subscribe((res: HttpResponse<ICreditReturnType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
