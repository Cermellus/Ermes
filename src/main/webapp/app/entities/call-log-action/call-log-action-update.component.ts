import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ICallLogAction } from 'app/shared/model/call-log-action.model';
import { CallLogActionService } from './call-log-action.service';

@Component({
    selector: 'jhi-call-log-action-update',
    templateUrl: './call-log-action-update.component.html'
})
export class CallLogActionUpdateComponent implements OnInit {
    callLogAction: ICallLogAction;
    isSaving: boolean;

    constructor(protected callLogActionService: CallLogActionService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ callLogAction }) => {
            this.callLogAction = callLogAction;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.callLogAction.id !== undefined) {
            this.subscribeToSaveResponse(this.callLogActionService.update(this.callLogAction));
        } else {
            this.subscribeToSaveResponse(this.callLogActionService.create(this.callLogAction));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICallLogAction>>) {
        result.subscribe((res: HttpResponse<ICallLogAction>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
