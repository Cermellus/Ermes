import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ICallLogLine } from 'app/shared/model/call-log-line.model';
import { CallLogLineService } from './call-log-line.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';
import { ICallLogAction } from 'app/shared/model/call-log-action.model';
import { CallLogActionService } from 'app/entities/call-log-action';
import { ICallLog } from 'app/shared/model/call-log.model';
import { CallLogService } from 'app/entities/call-log';

@Component({
    selector: 'jhi-call-log-line-update',
    templateUrl: './call-log-line-update.component.html'
})
export class CallLogLineUpdateComponent implements OnInit {
    callLogLine: ICallLogLine;
    isSaving: boolean;

    products: IProduct[];

    calllogactions: ICallLogAction[];

    calllogs: ICallLog[];
    callLogDueDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected callLogLineService: CallLogLineService,
        protected productService: ProductService,
        protected callLogActionService: CallLogActionService,
        protected callLogService: CallLogService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ callLogLine }) => {
            this.callLogLine = callLogLine;
        });
        this.productService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProduct[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProduct[]>) => response.body)
            )
            .subscribe((res: IProduct[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.callLogActionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICallLogAction[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICallLogAction[]>) => response.body)
            )
            .subscribe((res: ICallLogAction[]) => (this.calllogactions = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.callLogService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICallLog[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICallLog[]>) => response.body)
            )
            .subscribe((res: ICallLog[]) => (this.calllogs = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.callLogLine.id !== undefined) {
            this.subscribeToSaveResponse(this.callLogLineService.update(this.callLogLine));
        } else {
            this.subscribeToSaveResponse(this.callLogLineService.create(this.callLogLine));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICallLogLine>>) {
        result.subscribe((res: HttpResponse<ICallLogLine>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCallLogActionById(index: number, item: ICallLogAction) {
        return item.id;
    }

    trackCallLogById(index: number, item: ICallLog) {
        return item.id;
    }
}
