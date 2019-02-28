import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IProspect } from 'app/shared/model/prospect.model';
import { ProspectService } from './prospect.service';

@Component({
    selector: 'jhi-prospect-update',
    templateUrl: './prospect-update.component.html'
})
export class ProspectUpdateComponent implements OnInit {
    prospect: IProspect;
    isSaving: boolean;

    constructor(protected prospectService: ProspectService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ prospect }) => {
            this.prospect = prospect;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.prospect.id !== undefined) {
            this.subscribeToSaveResponse(this.prospectService.update(this.prospect));
        } else {
            this.subscribeToSaveResponse(this.prospectService.create(this.prospect));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProspect>>) {
        result.subscribe((res: HttpResponse<IProspect>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
