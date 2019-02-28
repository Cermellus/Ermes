import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ICreditRequestLine } from 'app/shared/model/credit-request-line.model';
import { CreditRequestLineService } from './credit-request-line.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';
import { ICreditReason } from 'app/shared/model/credit-reason.model';
import { CreditReasonService } from 'app/entities/credit-reason';
import { ICreditReturnType } from 'app/shared/model/credit-return-type.model';
import { CreditReturnTypeService } from 'app/entities/credit-return-type';
import { ICreditRequest } from 'app/shared/model/credit-request.model';
import { CreditRequestService } from 'app/entities/credit-request';

@Component({
    selector: 'jhi-credit-request-line-update',
    templateUrl: './credit-request-line-update.component.html'
})
export class CreditRequestLineUpdateComponent implements OnInit {
    creditRequestLine: ICreditRequestLine;
    isSaving: boolean;

    products: IProduct[];

    creditreasons: ICreditReason[];

    creditreturntypes: ICreditReturnType[];

    creditrequests: ICreditRequest[];

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected creditRequestLineService: CreditRequestLineService,
        protected productService: ProductService,
        protected creditReasonService: CreditReasonService,
        protected creditReturnTypeService: CreditReturnTypeService,
        protected creditRequestService: CreditRequestService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ creditRequestLine }) => {
            this.creditRequestLine = creditRequestLine;
        });
        this.productService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProduct[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProduct[]>) => response.body)
            )
            .subscribe((res: IProduct[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.creditReasonService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICreditReason[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICreditReason[]>) => response.body)
            )
            .subscribe((res: ICreditReason[]) => (this.creditreasons = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.creditReturnTypeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICreditReturnType[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICreditReturnType[]>) => response.body)
            )
            .subscribe((res: ICreditReturnType[]) => (this.creditreturntypes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.creditRequestService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICreditRequest[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICreditRequest[]>) => response.body)
            )
            .subscribe((res: ICreditRequest[]) => (this.creditrequests = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.creditRequestLine.id !== undefined) {
            this.subscribeToSaveResponse(this.creditRequestLineService.update(this.creditRequestLine));
        } else {
            this.subscribeToSaveResponse(this.creditRequestLineService.create(this.creditRequestLine));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICreditRequestLine>>) {
        result.subscribe((res: HttpResponse<ICreditRequestLine>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackProductById(index: number, item: IProduct) {
        return item.id;
    }

    trackCreditReasonById(index: number, item: ICreditReason) {
        return item.id;
    }

    trackCreditReturnTypeById(index: number, item: ICreditReturnType) {
        return item.id;
    }

    trackCreditRequestById(index: number, item: ICreditRequest) {
        return item.id;
    }
}
